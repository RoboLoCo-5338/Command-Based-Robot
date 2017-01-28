package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors.
 */
public class DriveTrain extends Subsystem
{
	private final CANTalon DRIVEL1 = new CANTalon(4);
    private final CANTalon DRIVEL2 = new CANTalon(3);
    private final CANTalon DRIVER1 = new CANTalon(2);
    private final CANTalon DRIVER2 = new CANTalon(1);
    
	public final RobotDrive DRIVE = new RobotDrive(DRIVEL1, DRIVEL2, DRIVER1, DRIVER2);

	public DriveTrain()
	{
		super();
		DRIVE.setMaxOutput(0.75);
	}

	/**
	 * When no other command is running let the operator drive around using the
	 * Xbox joystick.
	 */
	@Override
	public void initDefaultCommand()
	{
		setDefaultCommand(new TankDriveWithJoysticks());
	}

	/**
	 * Arcade style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double left, double right)
	{
		DRIVE.tankDrive(left, right, true);
	}

	/**
	 * @param joy
	 *            The XBOX style joystick to use to drive arcade style.
	 */
	public void drive(Joystick joy1, Joystick joy2)
	{
		DRIVE.tankDrive(-joy1.getRawAxis(1), -joy2.getRawAxis(1), true);
	}
}
