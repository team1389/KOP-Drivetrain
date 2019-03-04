package com.team1389.operation;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CurvatureDriveSystem;

public class TeleopMain {
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;

	public TeleopMain(RobotSoftware robot) {
		this.robot = robot;
	}

	public void init() {
		controls = ControlBoard.getInstance();
		manager = new SystemManager(getDrivetrain());
		manager.init();
	}


	public void periodic() {
		manager.update();
	}
	private Subsystem getDrivetrain(){
		return new CurvatureDriveSystem(robot.voltageDrive, controls.driveLeftY(), controls.driveRightX(), controls.driveRightBumper());
	}
}
