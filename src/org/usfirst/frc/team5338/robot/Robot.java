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
//import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
//import com.kauailabs.sf2.frc.navXSensor;
//import com.kauailabs.sf2.orientation.OrientationHistory;
//import com.kauailabs.sf2.orientation.Quaternion;
//import com.kauailabs.sf2.time.TimestampedValue;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Command autonomousCommand;
//	private static final int IMG_WIDTH = 1280;
//	private static final int IMG_HEIGHT = 720;

	public static final DriveTrain drivetrain = new DriveTrain();
	public static final OI oi = new OI();

//	private final Object imgLock = new Object();
//
//	private static ArrayList<Rect> raw;
//	private static long time, oldTime;

//	private static Snapshot lastObserved, observed;
//	
//	public static Snapshot outputSnapshot;
	
//	public AHRS ahrs;
//    OrientationHistory orientation_history;
//    double last_write_timestamp = 0;
//    
//    float delta_yaw, delta_pitch, delta_roll;
    
 

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
		//runSF2();
		Scheduler.getInstance().run();
	}
	
//	public void runSF2() {
//
//        if ( oi.getJoystick().getRawButton(1)) {            	
//        	if ((Timer.getFPGATimestamp() - last_write_timestamp) > 5.0) {
//        		orientation_history.writeToDirectory("/home/lvuser/sf2");
//                last_write_timestamp = Timer.getFPGATimestamp();
//        	}
//        }            
//        
//        /* Acquire Historical Orientation Data */
//        long navx_timestamp = ahrs.getLastSensorTimestamp();
//        navx_timestamp -= 1000; /* look 1 second backwards in time */
//        float historical_yaw = orientation_history.getYawDegreesAtTime(navx_timestamp);
//        float historical_pitch = orientation_history.getPitchDegreesAtTime(navx_timestamp);
//        float historical_roll = orientation_history.getRollDegreesAtTime(navx_timestamp);
//
//        /* Acquire Current Orientation Data */
//        float curr_yaw = ahrs.getYaw();
//        float curr_pitch = ahrs.getPitch();
//        float curr_roll = ahrs.getRoll();
//        
//        /* Calculate orientation change */
//        delta_yaw = curr_yaw - historical_yaw;
//        delta_pitch = curr_pitch - historical_pitch;
//        delta_roll = curr_roll - historical_roll;
//        
//        /* Display historical orientation data on Dashboard */
//        SmartDashboard.putNumber("SF2_Historical_Yaw", historical_yaw);
//        SmartDashboard.putNumber("SF2_Historical_Pitch", historical_pitch);
//        SmartDashboard.putNumber("SF2_Historical_Roll", historical_roll);
//
//        TimestampedValue<Quaternion> historical_quat = new TimestampedValue<Quaternion>(new Quaternion());
//        orientation_history.getQuaternionAtTime(navx_timestamp, historical_quat);            
//        SmartDashboard.putNumber("SF2_Historical_QuaternionW", historical_quat.getValue().getW());
//        SmartDashboard.putNumber("SF2_Historical_QuaternionX", historical_quat.getValue().getX());
//        SmartDashboard.putNumber("SF2_Historical_QuaternionY", historical_quat.getValue().getY());
//        SmartDashboard.putNumber("SF2_Historical_QuaternionZ", historical_quat.getValue().getZ());            
//        
//        /* Display whether historical values are interpolated or not. */
//        SmartDashboard.putBoolean("SF2_Interpolated", historical_quat.getInterpolated());
//        
//        /* Display 6-axis Processed Angle & Quaternion Data on Dashboard. */
//        SmartDashboard.putNumber("IMU_Yaw",		 curr_yaw);
//        SmartDashboard.putNumber("IMU_Pitch",    curr_pitch);
//        SmartDashboard.putNumber("IMU_Roll",     curr_roll);
//        SmartDashboard.putNumber("QuaternionW",  ahrs.getQuaternionW());
//        SmartDashboard.putNumber("QuaternionX",  ahrs.getQuaternionX());
//        SmartDashboard.putNumber("QuaternionY",  ahrs.getQuaternionY());
//        SmartDashboard.putNumber("QuaternionZ",  ahrs.getQuaternionZ());
//        
//        SmartDashboard.putNumber("Delta_Yaw",	delta_yaw);
//        SmartDashboard.putNumber("Delta_Pitch",	delta_pitch);
//        SmartDashboard.putNumber("Delta_Roll",	delta_roll);
//
//	}

}