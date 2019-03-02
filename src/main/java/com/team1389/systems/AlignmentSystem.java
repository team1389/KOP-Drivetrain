package com.team1389.systems;

import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.controllers.SynchronousPIDController;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Value;
import com.team1389.system.Subsystem;
import com.team1389.system.drive.DriveOut;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class AlignmentSystem extends Subsystem
{
    // TODO: Figure out a solution to the issue that vision will give us default
    // (320), whenever we switch sides.

    // NetworkTables IDs
    private final String VISION_NETWORK_TABLE_ID = "vision";
    private final String VISION_LEFT_SIDE_X_ID = "LeftSideX";
    private final String VISION_RIGHT_SIDE_X_ID = "RightSideX";
    private final String VISION_TOGGLE_RUNNING_SIDE_ID = "SwitchSides";

    // NetworkTables Entries
    private NetworkTableEntry leftSideXEntry;
    private NetworkTableEntry rightSideXEntry;
    private NetworkTableEntry toggleRunningSideEntry;

    private final int CENTER_X_VAL = 320;

    private DriveOut drive;

    private RangeIn<Position> leftDistance;
    private RangeIn<Position> rightDistance;
    private RangeIn<Position> robotAngle;

    private final double VISION_ALIGNMENT_TOLERANCE = 5;
    private final PIDConstants VISION_ALIGN_PID_CONSTANTS = new PIDConstants(0.01, 0, 0);

    private final double TURN_TOLERANCE_IN_DEGREES = 5;
    private final double STARTING_ANGLE_IN_DEGREES = 0;
    private final PIDConstants TURNING_PID_CONSTANTS = new PIDConstants(0.05, 0, 0);

    private Side currentState;

    /**
     * 
     * @param drive
     * @param leftDistance
     *                          distance reading for left side in inches
     * @param rightDistance
     *                          distance reading for right side in inches
     * @param robotAngle
     *                          heading of the robot in degrees, wrapped on the
     *                          range [0,360]
     */
    public AlignmentSystem(DriveOut<Percent> drive, RangeIn<Position> leftDistance, RangeIn<Position> rightDistance,
            RangeIn<Position> robotAngle)
    {
        this.drive = drive;
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.robotAngle = robotAngle;
    }

    @Override
    public void init()
    {
        NetworkTable table = NetworkTableInstance.getDefault().getTable(VISION_NETWORK_TABLE_ID);
        leftSideXEntry = table.getEntry(VISION_LEFT_SIDE_X_ID);
        rightSideXEntry = table.getEntry(VISION_RIGHT_SIDE_X_ID);
        toggleRunningSideEntry = table.getEntry(VISION_TOGGLE_RUNNING_SIDE_ID);
        // note this is the side of vision that runs on startup
        currentState = Side.RIGHT;
    }

    @Override
    public void update()
    {
        scheduler.update();
    }

    @Override
    public String getName()
    {
        return "Alignment System";
    }

    @Override
    public AddList<Watchable> getSubWatchables(AddList<Watchable> stem)
    {
        return stem;
    }

    private enum Side
    {
        LEFT, RIGHT
    }

    // TODO:pls tell some1 to mount camera to kop drivetrain, 37in

    // TODO: look into some way of shaking hands with pi regarding changedState.
    // Would make this command, only let next thing run after we get the
    // handshake.
    private void setSide(Side desired)
    {
        if (currentState != desired)
        {
            if (currentState == Side.LEFT)
            {
                currentState = Side.RIGHT;
            }
            else if (currentState == Side.RIGHT)
            {
                currentState = Side.LEFT;
            }
            // TODO: Touch base with Ethan about how he implemented his vision
            // switching once he fixes it.
            toggleRunningSideEntry.setBoolean(true);
        }
    }

    public void centerOnTarget()
    {
        scheduler.cancelAll();
        RangeIn<Position> xValSupplier;
        if (currentState == Side.LEFT)
        {
            xValSupplier = new RangeIn<Position>(Position.class, () -> leftSideXEntry.getDouble(320), 0, 720);
        }
        else
        {
            xValSupplier = new RangeIn<Position>(Position.class, () -> rightSideXEntry.getDouble(320), 0, 720);
        }

        SynchronousPIDController<Percent, Position> pidController = new SynchronousPIDController<Percent, Position>(
                new PIDConstants(0.005, 0, 0.5), xValSupplier, drive.left().getWithAddedFollowers(drive.right()));
        System.out.println("running center thing");
        scheduler.schedule(pidController.getPIDToCommand(320, VISION_ALIGNMENT_TOLERANCE));
    }

    public void alignAngle()
    {
        scheduler.cancelAll();
        /*
         * super(180, true, TOLERANCE_IN_DEGREES, robot.gyroInput,
         * TurnAngleCommand.createTurnController(robot.voltageDrive),
         * TURN_PID_CONSTANTS);
         */
        RangeOut<Percent> turnController = TurnAngleCommand.createTurnController(drive);
        SynchronousPIDController pidController = new SynchronousPIDController<>(TURNING_PID_CONSTANTS, robotAngle,
                turnController);
        double startingAngle = robotAngle.get();
        double targetAngle;
        if (startingAngle < 90 && startingAngle > -90)
        {
            targetAngle = 0;
        }
        else
        {
            targetAngle = 180;
        }
        scheduler.schedule(pidController.getPIDToCommand(targetAngle, TURN_TOLERANCE_IN_DEGREES));

    }
}