package org.usfirst.frc.team5338.robot;

//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5338.robot.commands.Autonomous;
import org.usfirst.frc.team5338.robot.subsystems.DriveTrain;

import org.usfirst.frc.team5338.robot.GripPipeline;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

import java.util.*;

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
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;

	public static DriveTrain drivetrain;
	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		final double tapeDistanceRatio = 3.0;
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    new VisionThread(camera, new GripPipeline(), pipeline ->
	    {
	    /*if (!pipeline.filterLinesOutput().isEmpty())
	    	{
	            ArrayList<GripPipeline.Line> lines = pipeline.findLinesOutput();
	            ArrayList<Double> x = new ArrayList<Double>();
	            for(int i=0;i<lines.size();i++)
	            {
	            	x.add((lines.get(i).x1+lines.get(i).x2)/2);
	            }
	            Collections.sort(x);
	            for(int i=0;i<x.size()-1;i++)
	            {
	            	if(Math.abs(x.get(i)-x.get(i+1))<5)
	            	{
	            		x.remove(i);
	            		i--;
	            	}
	            }
	            String array = "X: ";
	            for(Double i: x)
	            {
	            	array += i + ", ";
	            }
	            SmartDashboard.putString("array", array);
	        }*/
	    }).start();

	    drivetrain = new DriveTrain();
		oi = new OI();

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
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		autonomousCommand.cancel();

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}
}
