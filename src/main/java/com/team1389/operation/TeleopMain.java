package com.team1389.operation;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;
import com.team1389.system.drive.DriveOut;
import com.team1389.systems.TeleopAlignmentSystem;

public class TeleopMain
{
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;

	public TeleopMain(RobotSoftware robot)
	{
		this.robot = robot;
	}

	public void init()
	{
		controls = ControlBoard.getInstance();
		Subsystem drive = getDrivetrain();
		Subsystem align = getAlignSystem();
		manager = new SystemManager(align, drive);
		manager.init();
	}

	public void periodic()
	{

		manager.update();
	}

	private Subsystem getDrivetrain()
	{
		return new CurvatureDriveSystem(robot.voltageDrive, controls.driveLeftY(), controls.driveRightX(),
				controls.driveRightBumper());
	}

	private Subsystem getAlignSystem()
	{

		return new TeleopAlignmentSystem(robot.voltageDrive, null, null, robot.gyroInput, controls.aButton(),
				controls.bButton(), controls.xButton(), controls.leftDPad(), controls.rightDPad());
	}
}
