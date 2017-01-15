package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class DriveTrain extends Subsystem {
	private final CANTalon DRIVEL1 = new CANTalon(1);
    private final CANTalon DRIVEL2 = new CANTalon(2);
    private final CANTalon DRIVER1 = new CANTalon(3);
    private final CANTalon DRIVER2 = new CANTalon(4);
    private final double speed = 0.75;
	private final RobotDrive drive = new RobotDrive(DRIVEL1, DRIVEL2, DRIVER1, DRIVER2);

	public DriveTrain() {
		super();
	}

	/**
	 * When no other command is running let the operator drive around using the
	 * PS3 joystick.
	 */
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveWithJoystick());
	}

	/**
	 * Tank style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double left, double right) {
		drive.tankDrive(-speed * left, -speed * right, true);
	}

	/**
	 * @param joy
	 *            The XBOX style joystick to use to drive tank style.
	 */
	public void drive(Joystick joy) {
		drive.tankDrive(-speed * joy.getRawAxis(1), -speed * joy.getRawAxis(3), true);
	}
}
