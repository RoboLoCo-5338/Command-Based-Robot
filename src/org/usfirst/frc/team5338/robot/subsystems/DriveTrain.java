package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.MechanumDriveWithJoystick;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors.
 */
public class DriveTrain extends Subsystem
{
	public static final AHRS IMU = new AHRS(SPI.Port.kMXP, (byte) 200);
	private final CANTalon DRIVEL1 = new CANTalon(2);
    private final CANTalon DRIVEL2 = new CANTalon(3);
    private final CANTalon DRIVER1 = new CANTalon(1);
    private final CANTalon DRIVER2 = new CANTalon(4);
	public final RobotDrive DRIVE = new RobotDrive(DRIVEL1, DRIVEL2, DRIVER1, DRIVER2);

	public DriveTrain()
	{
		super();
		while(DriveTrain.IMU.isCalibrating())
	    {
	    }
		DriveTrain.IMU.reset();
		DriveTrain.IMU.zeroYaw();
		DRIVE.setMaxOutput(0.5);
	}

	/**
	 * When no other command is running let the operator drive around using the
	 * Xbox joystick.
	 */
	@Override
	public void initDefaultCommand()
	{
		setDefaultCommand(new MechanumDriveWithJoystick());
	}

	/**
	 * Arcade style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double forward, double direction, double rotation)
	{
		DRIVE.mecanumDrive_Cartesian(forward, direction, rotation, 0);
	}

	/**
	 * @param joy
	 *            The XBOX style joystick to use to drive arcade style.
	 */
	public void drive(Joystick joy)
	{
		DRIVE.mecanumDrive_Cartesian(joy.getRawAxis(0), joy.getRawAxis(1), rotationDeadZone(joy.getRawAxis(2)), 0);
		SmartDashboard.putDouble("Rotation", joy.getRawAxis(2));
	}
	public double rotationDeadZone(double Value){
		if (Value > 0.3 || Value < -0.3){
		 return Value;
		}
		double Zero = 0;
		return Zero;
	}
}
