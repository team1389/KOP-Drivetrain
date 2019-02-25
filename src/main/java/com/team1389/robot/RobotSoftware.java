package com.team1389.robot;

import java.util.function.UnaryOperator;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.AngleOut;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Position;
import com.team1389.system.drive.DriveOut;
import com.team1389.watch.Watcher;

public class RobotSoftware extends RobotHardware
{
	private static RobotSoftware INSTANCE = new RobotSoftware();
	private final double DIST_SENSOR_ANGLE = Math.toRadians(80);
	private final double DIST_SENSOR_HEIGHT_IN_INCHES = 6.5;
	private final double DIST_SENSOR_OFFSET = -4.5;

	public static RobotSoftware getInstance()
	{
		return INSTANCE;
	}

	public RangeOut leftDriveOneVolt, leftDriveTwoVolt;
	public RangeOut rightDriveOneVolt, rightDriveTwoVolt;
	public DriveOut voltageDrive;

	public AngleIn gyroInput;

	public RangeIn leftDistanceInput;

	public RobotSoftware()
	{

		initDrivetrainStreams();
		initGyro();
		initDistanceSensors();
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
		gyroInput = gyro.getYawInput().getWrapped();
		zeroRobotAngle();
	}

	private void initDistanceSensors()
	{
		UnaryOperator<Double> mapFromHypotToDistance = (val -> Math.sin(DIST_SENSOR_ANGLE) * val);
		leftDistanceInput = leftDistance.getPositionInInches().getMapped(mapFromHypotToDistance)
				.getOffset(DIST_SENSOR_OFFSET);
	}

	public void zeroRobotAngle()
	{
		double offset = -gyroInput.get();
		gyroInput.clone(gyroInput.getOffset(offset));
	}

}
