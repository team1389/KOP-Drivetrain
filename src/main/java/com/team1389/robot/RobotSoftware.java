package com.team1389.robot;

import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.system.drive.DriveOut;

public class RobotSoftware extends RobotHardware {
	private static RobotSoftware INSTANCE = new RobotSoftware();

	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public PercentOut leftDriveOneVolt, leftDriveTwoVolt;
	public PercentOut rightDriveOneVolt, rightDriveTwoVolt;
	public DriveOut voltageDrive;

	public RobotSoftware(){
		initDrivetrainStreams();
	}

	private void initDrivetrainStreams(){
		leftDriveOneVolt = leftDriveOne.getVoltageController();
		leftDriveTwoVolt = leftDriveTwo.getVoltageController();

		rightDriveOneVolt = rightDriveOne.getVoltageController();
		rightDriveTwoVolt = rightDriveTwo.getVoltageController();

		voltageDrive = new DriveOut<>(leftDriveOneVolt.addFollowers(leftDriveTwoVolt), rightDriveOneVolt.addFollowers(rightDriveTwoVolt));
	}
	

}
