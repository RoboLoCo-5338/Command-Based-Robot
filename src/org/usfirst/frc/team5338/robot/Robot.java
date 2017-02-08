package org.usfirst.frc.team5338.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalDouble;
//
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
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
	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 360;
	
	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();
	
	private final Object imgLock = new Object();
	
	private static ArrayList<Rect> raw;
	private static ArrayList<Double> processedData;
	
    VisionThread visionThread;

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    visionThread = new VisionThread(camera, new GripPipeline(), pipeline ->
	    {
	    	
	    	 if (!pipeline.filterContoursOutput().isEmpty())
	    	 {
	    		 
	    		 ArrayList<Rect> rects = new ArrayList<Rect>();
	    		 for(MatOfPoint mop: pipeline.filterContoursOutput())
	    		 {
	    			 rects.add(Imgproc.boundingRect(mop));
	    		 }
	    		 ArrayList<Integer> centerX = new ArrayList<Integer>();
	    		 for(Rect r: rects)
	    		 {
	    			 centerX.add(r.x + (r.width / 2));
	    		 }
//	    		 Collections.sort(centerX);
//	    		 OptionalDouble averageX = centerX.stream().mapToDouble(a -> a).average();
	    		 synchronized (imgLock)
	    		 {
	    			 processedData = new ArrayList<Double>();
	    			 raw = new ArrayList<Rect>(rects);
	             }
	    	 }
	    });
	    visionThread.start();
		// instantiate the command used for the autonomous period
		autonomousCommand = new Autonomous();
	}
	@Override
	public void autonomousInit()
	{
		autonomousCommand.start(); // schedule the autonomous command (example)
	}
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit()
	{
		autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic()
	{
		String rawString;
		synchronized (imgLock) 
		{
			rawString = "";
			for(Rect i: raw)
				rawString = rawString + i.toString()+"    ";
			SmartDashboard.putString("raw data", rawString);
		}
		Scheduler.getInstance().run();
	}
}
