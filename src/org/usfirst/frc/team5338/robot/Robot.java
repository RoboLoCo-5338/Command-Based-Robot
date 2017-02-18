package org.usfirst.frc.team5338.robot;

import java.util.*;
import java.lang.*;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;


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
	private static final int IMG_WIDTH = 1280;
	private static final int IMG_HEIGHT = 720;

	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();


//	private final Object imgLock = new Object();
//
//	private static ArrayList<Rect> raw;
//	private static long time, oldTime;
//
//	private static Snapshot lastObserved, observed;
//
//	private Relay light = new Relay(1);
//
//	public static Snapshot outputSnapshot;


	private static final Relay jetsonPower = new Relay(0);
	private static final Relay jetsonReset = new Relay(1);

	//NetworkTable table = NetworkTable.getTable("myContourReport");

	//private static final NetworkTable table = NetworkTable.getTable("GRIP/output");

//	VisionThread visionThread;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		// instantiate the command used for the autonomous period
		autonomousCommand = new Autonomous();
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
		SmartDashboard.putNumber("Throttle", drivetrain.getThrottle());
		Scheduler.getInstance().run();
	}

}
