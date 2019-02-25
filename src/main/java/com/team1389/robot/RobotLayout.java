package com.team1389.robot;

import com.team1389.hardware.inputs.hardware.AnalogDistanceHardware;
import com.team1389.hardware.inputs.hardware.NavXHardware;
import com.team1389.hardware.inputs.hardware.PigeonIMUHardware;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.registry.Registry;

/**
 * contains a list of declared hardware objects for this robot. Separated from
 * {@link RobotHardware} to make it easier to see what hardware is connected to
 * the robot.
 * 
 */
public class RobotLayout extends RobotMap
{
	public Registry registry;
	public CANTalonHardware leftDriveOne, leftDriveTwo;
	public CANTalonHardware rightDriveOne, rightDriveTwo;
	public AnalogDistanceHardware leftDistance;
	public NavXHardware gyro;
}
