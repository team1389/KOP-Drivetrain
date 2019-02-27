package com.team1389.robot;

import com.team1389.auto.command.TurnAngleCommand;
import com.team1389.autonomous.AutoCommands;
import com.team1389.command_framework.CommandScheduler;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.operation.TeleopMain;
import com.team1389.system.SystemManager;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot
{
	RobotSoftware robot;
	TeleopMain teleOperator;
	Watcher watch;
	RangeOut turnController;
	AutoCommands autoCommands;
	CommandScheduler scheduler;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{

		robot = RobotSoftware.getInstance();
		robot.zeroRobotAngle();
		teleOperator = new TeleopMain(robot);
		watch = new Watcher();
		watch.watch(robot.leftDistanceInput.getWatchable("left dist"));
		watch.watch(robot.gyroInput.getWatchable("angle"));
		watch.outputToDashboard();
		turnController = TurnAngleCommand.createTurnController(robot.voltageDrive);
		autoCommands = new AutoCommands(robot);
		scheduler = new CommandScheduler();

	}

	@Override
	public void autonomousInit()
	{
		scheduler.schedule(autoCommands.new TurnTo180AbsoluteCommand());
	}

	@Override
	public void autonomousPeriodic()
	{
		scheduler.update();
		// clockwise
	}

	@Override
	public void teleopInit()
	{
		System.out.println("running");
		teleOperator.init();
		robot.zeroRobotAngle();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		Watcher.update();

		teleOperator.periodic();
	}

	@Override
	public void testInit()
	{
		scheduler.schedule(autoCommands.new TurnTo90AbsoluteCommand());
	}

	@Override
	public void testPeriodic()
	{
		scheduler.update();
	}

	@Override
	public void disabledInit()
	{
	}

	@Override

	public void disabledPeriodic()
	{
		Watcher.update();
	}
}
