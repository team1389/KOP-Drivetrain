package com.team1389.autonomous;

import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.command_framework.command_base.Command;
import com.team1389.configuration.PIDConstants;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.robot.RobotSoftware;

import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class AutoCommands
{

    private RobotSoftware robot;

    private final double TOLERANCE_IN_DEGREES = 20;
    private final PIDConstants TURN_PID_CONSTANTS = new PIDConstants(0.05, 0, 0);

    public AutoCommands(RobotSoftware robot)
    {

        this.robot = robot;
    }

    public class TurnTo180AbsoluteCommand extends TurnAngleCommand<Percent>
    {
        RangeOut<Percent> turnController;
        TurnAngleCommand<Percent> turn180;

        public TurnTo180AbsoluteCommand()
        {
            super(180, true, TOLERANCE_IN_DEGREES, robot.gyroInput,
                    TurnAngleCommand.createTurnController(robot.voltageDrive), TURN_PID_CONSTANTS);

        }

    }

    public class TurnTo90AbsoluteCommand extends TurnAngleCommand<Percent>
    {
        RangeOut<Percent> turnController;
        TurnAngleCommand<Percent> turn180;

        public TurnTo90AbsoluteCommand()
        {
            super(90, true, TOLERANCE_IN_DEGREES, robot.gyroInput,
                    TurnAngleCommand.createTurnController(robot.voltageDrive), TURN_PID_CONSTANTS);

        }

    }
}