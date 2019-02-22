package com.team1389.robot;

import com.team1389.hardware.inputs.hardware.AnalogDistanceHardware;
import com.team1389.hardware.inputs.hardware.PigeonIMUHardware;
import com.team1389.hardware.inputs.hardware.AnalogDistanceHardware.SensorType;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.registry.Registry;

/**
 * responsible for initializing and storing hardware objects defined in
 * {@link RobotLayout}
 * 
 * @author amind
 * @see RobotLayout
 * @see RobotMap
 */
public class RobotHardware extends RobotLayout
{

	/**
	 * Initializes robot hardware by subsystem. <br>
	 * note: use this method as an index to show hardware initializations that
	 * occur, and to find the init code for a particular system's hardware
	 */
	protected RobotHardware()
	{
		registry = new Registry();
		System.out.println("initializing hardware");
		initDrivetrain();
		imu = new PigeonIMUHardware(IMU_PORT, registry);
		leftDistance = new AnalogDistanceHardware(SensorType.SHARP_GP2Y0A21YK0F, LEFT_DISTANCE_SENSOR, registry);
	}

	private void initDrivetrain()
	{
		leftDriveOne = new CANTalonHardware(inv_LEFT_DRIVE_ONE, LEFT_DRIVE_ONE, registry);
		leftDriveTwo = new CANTalonHardware(inv_LEFT_DRIVE_TWO, LEFT_DRIVE_TWO, registry);

		rightDriveOne = new CANTalonHardware(inv_RIGHT_DRIVE_ONE, RIGHT_DRIVE_ONE, registry);
		rightDriveTwo = new CANTalonHardware(inv_RIGHT_DRIVE_TWO, RIGHT_DRIVE_TWO, registry);
	}

}