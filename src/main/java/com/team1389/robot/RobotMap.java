package com.team1389.robot;

import com.team1389.hardware.registry.port_types.Analog;
import com.team1389.hardware.registry.port_types.CAN;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * <p>
 * <b>Conventions</b>: <br>
 * For I/O ports, the naming convention is <em>type_ALL_CAPS_IDENTIFIER</em>.
 * for example, a talon port might be named can_RIGHT_MOTOR_A. Possible port
 * types and identifiers are CAN (can), Analog (anlg), PWM (pwm), USB (usb), PCM
 * (pcm), DIO (dio), etc
 * <p>
 * Inputs and Outputs may be inverted. The inversions in this map should only
 * relate to the physical configuration of the robot. A positive value should
 * cause the output to move in the most logical direction (I.e, the drive motors
 * should move forward with positive voltage values) <br>
 * the convention for inversion constants is
 * <em>inv_ASSOCIATED_IO_IDENTIFIER</em> for outputs and
 * <em>sinv_ASSOCIATED_IO_IDENTIFIER</em> for inputs.
 */
public class RobotMap
{

    public static final CAN LEFT_DRIVE_ONE = new CAN(2);
    public static final boolean inv_LEFT_DRIVE_ONE = false;
    public static final boolean sinv_LEFT_DRIVE_ONE = false;
    public static final CAN LEFT_DRIVE_TWO = new CAN(4);
    public static final boolean inv_LEFT_DRIVE_TWO = false;
    public static final boolean sinv_LEFT_DRIVE_TWO = false;

    public static final CAN RIGHT_DRIVE_ONE = new CAN(1);
    public static final boolean inv_RIGHT_DRIVE_ONE = true;
    public static final boolean sinv_RIGHT_DRIVE_ONE = false;
    public static final CAN RIGHT_DRIVE_TWO = new CAN(3);
    public static final boolean inv_RIGHT_DRIVE_TWO = true;
    public static final boolean sinv_RIGHT_DRIVE_TWO = false;

    // imu
    public static final CAN IMU_PORT = new CAN(7);

    // sensors
    public static final Analog LEFT_DISTANCE_SENSOR = new Analog(0);

}
