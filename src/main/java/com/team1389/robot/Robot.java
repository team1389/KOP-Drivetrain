package com.team1389.robot;

import com.team1389.controllers.SynchronousPIDController;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.operation.TeleopMain;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
	NetworkTableEntry xEntry;
	RobotSoftware robot;
	TeleopMain teleOperator;
	Solenoid light = new Solenoid(1);
	private final int center = 320;
	SynchronousPIDController<Percent, Position> pidController;


	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("vision");
		xEntry = table.getEntry("X");
		robot = RobotSoftware.getInstance();
		teleOperator = new TeleopMain(robot);
		light.set(true);
		RangeIn<Position> diff = new RangeIn<Position>(Position.class, () -> xEntry.getDouble(center) - center, 0, 640);
		RangeOut<Percent> driveTrain = robot.leftDriveOneVolt.getWithAddedFollowers(robot.leftDriveTwoVolt).
			getWithAddedFollowers(robot.rightDriveOneVolt).getWithAddedFollowers(robot.rightDriveTwoVolt);
		pidController = new SynchronousPIDController<>(0.001, 0, 0, 0, diff, driveTrain);
		pidController.enable();
	}

	@Override
	public void autonomousInit() {


	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {

		teleOperator.init();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		teleOperator.periodic();
		System.out.println(xEntry.getDouble(center));
		pidController.update();
	}

	@Override

	public void disabledInit() {
	}

	@Override

	public void disabledPeriodic() {

	}
}
