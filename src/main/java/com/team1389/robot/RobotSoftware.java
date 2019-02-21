package com.team1389.robot;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.outputs.software.AngleOut;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Position;
import com.team1389.system.drive.DriveOut;

public class RobotSoftware extends RobotHardware
{
	private static RobotSoftware INSTANCE = new RobotSoftware();

	public static RobotSoftware getInstance()
	{
		return INSTANCE;
	}

	public RangeOut leftDriveOneVolt, leftDriveTwoVolt;
	public RangeOut rightDriveOneVolt, rightDriveTwoVolt;
	public DriveOut voltageDrive;

	public AngleIn gyroInput;

	public RobotSoftware()
	{

		initDrivetrainStreams();
		initGyro();
	}

	private void initDrivetrainStreams()
	{
		leftDriveOneVolt = leftDriveOne.getVoltageController().getScaled(.5);
		leftDriveTwoVolt = leftDriveTwo.getVoltageController().getScaled(.5);

		rightDriveOneVolt = rightDriveOne.getVoltageController().getScaled(.5);
		rightDriveTwoVolt = rightDriveTwo.getVoltageController().getScaled(.5);

		voltageDrive = new DriveOut<>(leftDriveOneVolt.getWithAddedFollowers(leftDriveTwoVolt),
				rightDriveOneVolt.getWithAddedFollowers(rightDriveTwoVolt));
	}

	private void initGyro()
	{
		gyroInput = imu.getYawInput().getInverted().getWithSetRange(0, 360).getWrapped();
		zeroRobotAngle();
	}

	public void zeroRobotAngle()
	{
		double offset = -gyroInput.get();
		double origMin = gyroInput.min();
		double origMax = gyroInput.max();
		double newMin = origMin + offset;
		double newMax = origMax + offset;
		gyroInput = (AngleIn<Position>) gyroInput.getOffset(offset).getWithSetRange(newMin, newMax);
	}
}
