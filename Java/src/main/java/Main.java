import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.cscore.*;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5338.robot.GripPipeline;
import org.usfirst.frc.team5338.robot.Snapshot;



public class Main {
	
	static NetworkTable table;
	
	private static final int IMG_WIDTH = 1280;
	private static final int IMG_HEIGHT = 720;
	
	static Snapshot observed, lastObserved;
	static long time, oldTime;
	
  public static void main(String[] args) {
    // Loads our OpenCV library. This MUST be included
    System.loadLibrary("opencv_java310");

    // Connect NetworkTables, and get access to the publishing table
    NetworkTable.setClientMode();
    // Set your team number here
    NetworkTable.setTeam(5338);

    NetworkTable.initialize();


    // This is the network port you want to stream the raw received image to
    // By rules, this has to be between 1180 and 1190, so 1185 is a good choice
    int streamPort = 1185;

    // This stores our reference to our mjpeg server for streaming the input image
    MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);

    // Selecting a Camera
    // Uncomment one of the 2 following camera options
    // The top one receives a stream from another device, and performs operations based on that
    // On windows, this one must be used since USB is not supported
    // The bottom one opens a USB camera, and performs operations on that, along with streaming
    // the input image so other devices can see it.

    // HTTP Camera
    /*
    // This is our camera name from the robot. this can be set in your robot code with the following command
    // CameraServer.getInstance().startAutomaticCapture("YourCameraNameHere");
    // "USB Camera 0" is the default if no string is specified
    String cameraName = "USB Camera 0";
    HttpCamera camera = setHttpCamera(cameraName, inputStream);
    // It is possible for the camera to be null. If it is, that means no camera could
    // be found using NetworkTables to connect to. Create an HttpCamera by giving a specified stream
    // Note if this happens, no restream will be created
    if (camera == null) {
      camera = new HttpCamera("CoprocessorCamera", "YourURLHere");
      inputStream.setSource(camera);
    }
    */
    
      

    /***********************************************/

    // USB Camera
    
    // This gets the image from a USB camera 
    // Usually this will be on device 0, but there are other overloads
    // that can be used
    UsbCamera camera = setUsbCamera(0, inputStream);
    // Set the resolution for our camera, since this is over USB
    camera.setResolution(IMG_WIDTH,IMG_HEIGHT);
	camera.setExposureManual(25);
	camera.setWhiteBalanceManual(0);    

    // This creates a CvSink for us to use. This grabs images from our selected camera, 
    // and will allow us to use those images in opencv
    CvSink imageSink = new CvSink("CV Image Grabber");
    imageSink.setSource(camera);

    // This creates a CvSource to use. This will take in a Mat image that has had OpenCV operations
    // operations 
    CvSource imageSource = new CvSource("CV Image Source", VideoMode.PixelFormat.kMJPEG, 640, 480, 30);
    MjpegServer cvStream = new MjpegServer("CV Image Stream", 1186);
    cvStream.setSource(imageSource);

    // All Mats and Lists should be stored outside the loop to avoid allocations
    // as they are expensive to create
    Mat inputImage = new Mat();
    Mat hsv = new Mat();
    
    GripPipeline gp = new GripPipeline();
    
    table = NetworkTable.getTable("datatable");
    // Infinitely process image
    while (true) {
      // Grab a frame. If it has a frame time of 0, there was an error.
      // Just skip and continue
      long frameTime = imageSink.grabFrame(inputImage);
      if (frameTime == 0) continue;
      
      gp.process(inputImage);
      
      ArrayList<MatOfPoint> fCO = gp.findContoursOutput();
      
		ArrayList<Rect> rects = new ArrayList<Rect>();
		for (MatOfPoint mop : fCO)
				rects.add(Imgproc.boundingRect(mop));
		
		//remove duplicates
		Set<Rect> hs = new HashSet<>();
		hs.addAll(rects);
		rects.clear();
		rects.addAll(hs);
		
		//remove rectangles that aren't the right size
		for(int i=0;i<rects.size();i++)
		{
			Rect r = rects.get(i);
			if((Math.abs(2.5 - r.height / (float)r.width)>0.5) && (r.y)> 1 )
			{
				rects.remove(i);
				i--;
			}
		}
		
		if (!rects.isEmpty()) {
			
			if(rects.size()==2) {
				Rect r1 = rects.get(0);
				Rect r2 = rects.get(1);
				
				observed = new Snapshot(time, (r1.x+r2.x+r1.width+r2.width)/2-IMG_WIDTH/2, (r1.y+r2.y+r1.height+r2.height)/2, Math.abs(r1.x-r2.x));
			} else if (time - oldTime < 200) {
				//use lastObserved to help determine the new position
				//TODO 1 or >3 rectangles
				observed = new Snapshot(0,0,0,0);

			} else {
				//determine position with rectangle data only
				//TODO 1 or >3 rectangles
				observed = new Snapshot(0,0,0,0);

			}
		} else {
			if (time - oldTime < 500) {
				observed = new Snapshot(lastObserved.time,lastObserved.x,lastObserved.y,lastObserved.width);
			} else {
				observed = new Snapshot(0,0,0,0);
			}
		}
		

      // Below is where you would do your OpenCV operations on the provided image
      // The sample below just changes color source to HSV

      // Here is where you would write a processed image that you want to restreams
      // This will most likely be a marked up image of what the camera sees
      // For now, we are just going to stream the HSV image
      imageSource.putFrame(hsv);
    }
  }

  private static HttpCamera setHttpCamera(String cameraName, MjpegServer server) {
    // Start by grabbing the camera from NetworkTables
    NetworkTable publishingTable = NetworkTable.getTable("CameraPublisher");
    // Wait for robot to connect. Allow this to be attempted indefinitely
    while (true) {
      try {
        if (publishingTable.getSubTables().size() > 0) {
          break;
        }
        Thread.sleep(500);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    HttpCamera camera = null;
    if (!publishingTable.containsSubTable(cameraName)) {
      return null;
    }
    ITable cameraTable = publishingTable.getSubTable(cameraName);
    String[] urls = cameraTable.getStringArray("streams", null);
    if (urls == null) {
      return null;
    }
    ArrayList<String> fixedUrls = new ArrayList<String>();
    for (String url : urls) {
      if (url.startsWith("mjpg")) {
        fixedUrls.add(url.split(":", 2)[1]);
      }
    }
    camera = new HttpCamera("CoprocessorCamera", fixedUrls.toArray(new String[0]));
    server.setSource(camera);
    return camera;
  }

  private static UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
    // This gets the image from a USB camera 
    // Usually this will be on device 0, but there are other overloads
    // that can be used
    UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
    server.setSource(camera);
    return camera;
  }
  
  public class Snapshot {
		public long time;
		public double x;
		public double y;
		public double width;

		public Snapshot(long time, double x, double y, double width) {
			this.time = time;
			this.x = x;
			this.y = y;
			this.width = width;
		}
}
