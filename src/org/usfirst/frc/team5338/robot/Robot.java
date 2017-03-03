package org.usfirst.frc.team5338.robot;

import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.BallHandler;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5338.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.ConnectionInfo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	Command autonomousCommand;
//	private static final int IMG_WIDTH = 1280;
//	private static final int IMG_HEIGHT = 720;

	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();
	public static final BallHandler ballhandler = new BallHandler();
	public static final Winch winch = new Winch();

	private static final Relay jetsonPower = new Relay(0);
	public static final Relay jetsonReset = new Relay(1);
	

	//NetworkTable table = NetworkTable.getTable("datatable");
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		// instantiate the command used for the autonomous period
		autonomousCommand = new Autonomous();
		SmartDashboard.putString("Placement =", "");
	}

	@Override
	public void autonomousInit() {
		autonomousCommand.start(); // schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit()
	{
		// Jetson power spark on enable
		jetsonPower.set(Relay.Value.kOn);
		Timer.delay(1);
		jetsonPower.set(Relay.Value.kOff);
		autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic()
	{
//		ConnectionInfo[] foo = NetworkTable.connections();
//		SmartDashboard.putString("Jetson OK", foo[0].remote_ip);
		SmartDashboard.putNumber("Throttle", drivetrain.getThrottle());
		Scheduler.getInstance().run();
	}

}
