package org.usfirst.frc.team5338.robot;

import java.util.*;

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
	
	private static final Relay jetsonPower = new Relay(0);
	private static final Relay jetsonReset = new Relay(1);
	
	NetworkTable table = NetworkTable.getTable("myContourReport");
 

	//private static final NetworkTable table = NetworkTable.getTable("GRIP/output");

//	VisionThread visionThread;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
//		ahrs = new AHRS(SPI.Port.kMXP);
//		
//        navXSensor navx_sensor = new navXSensor(ahrs, "Drivetrain Orientation");
//        orientation_history = new OrientationHistory(navx_sensor,
//    		ahrs.getRequestedUpdateRate() * 10);
//		
//		lastObserved = new Snapshot(0, 0, 0, 0);
//		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

//		visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
//			
//			oldTime = lastObserved.time;
//			time = System.currentTimeMillis();
//
//			ArrayList<Rect> rects = new ArrayList<Rect>();
//			for (MatOfPoint mop : pipeline.findContoursOutput())
//					rects.add(Imgproc.boundingRect(mop));
//			
//			//remove duplicates
//			Set<Rect> hs = new HashSet<>();
//			hs.addAll(rects);
//			rects.clear();
//			rects.addAll(hs);
//			
//			//remove rectangles that aren't the right size
//			for(int i=0;i<rects.size();i++)
//			{
//				Rect r = rects.get(i);
//				if((Math.abs(2.5 - r.height / (float)r.width)>0.5) && (r.y)> 1 )
//				{
//					rects.remove(i);
//					i--;
//				}
//			}
//			
//			
//
//			if (!rects.isEmpty()) {
//				
//				if(rects.size()==2) {
//					Rect r1 = rects.get(0);
//					Rect r2 = rects.get(1);
//					
//					observed = new Snapshot(time, (r1.x+r2.x+r1.width+r2.width)/2-IMG_WIDTH/2, (r1.y+r2.y+r1.height+r2.height)/2, Math.abs(r1.x-r2.x));
//				} else if (time - oldTime < 200) {
//					//use lastObserved to help determine the new position
//					//TODO 1 or >3 rectangles
//					observed = new Snapshot(0,0,0,0);
//
//				} else {
//					//determine position with rectangle data only
//					//TODO 1 or >3 rectangles
//					observed = new Snapshot(0,0,0,0);
//
//				}
//			} else {
//				if (time - oldTime < 500) {
//					observed = new Snapshot(lastObserved.time,lastObserved.x,lastObserved.y,lastObserved.width);
//				} else {
//					observed = new Snapshot(0,0,0,0);
//				}
//			}
//
//			synchronized (imgLock) {
//				lastObserved = observed;
//				raw = new ArrayList<Rect>(rects);
//			}
//
//		});
//		visionThread.start();
		// instantiate the command used for the autonomous period
		jetsonPower.set(Relay.Value.kOn);
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
	public void teleopInit() {
		autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic()
	{
//		String rawString;
//		synchronized (imgLock) {
//			outputSnapshot = lastObserved;
//			rawString = "";
//			for (Rect i : raw)
//				rawString = rawString + i.toString() + "    ";
//		}
//		SmartDashboard.putNumber("X",outputSnapshot.x);
//		SmartDashboard.putNumber("Y",outputSnapshot.y);
//		SmartDashboard.putNumber("Z",outputSnapshot.width);
//		SmartDashboard.putString("raw data", rawString);
		SmartDashboard.putNumber("Throttle", drivetrain.getThrottle());
		Scheduler.getInstance().run();
	}
}