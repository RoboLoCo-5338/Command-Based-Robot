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
import main.java.GripPipeline;

public class Main
{

	private static NetworkTable table;

	private static final int IMG_WIDTH = 1280;
	private static final int IMG_HEIGHT = 720;

//	static Snapshot observed, lastObserved;
//	static long time, oldTime;

  public static void main(String[] args)
  {
    // Loads our OpenCV library. This MUST be included
    System.loadLibrary("opencv_java310");

    // Connect NetworkTables, and get access to the publishing table
    NetworkTable.setServerMode();
    // Set your team number here
    NetworkTable.setTeam(5338);

    NetworkTable.initialize();


    // This is the network port you want to stream the raw received image to
    // By rules, this has to be between 1180 and 1190, so 1185 is a good choice
    int streamPort = 1185;
    MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);

    /***********************************************/

    // USB Camera

    // This gets the image from a USB camera
    // Usually this will be on device 0, but there are other overloads
    // that can be used
    UsbCamera camera = setUsbCamera(0, inputStream);
    // Set the resolution for our camera, since this is over USB
    // This creates a CvSink for us to use. This grabs images from our selected camera,
    // and will allow us to use those images in opencv
    CvSink imageSink = new CvSink("CV Image Grabber");
    imageSink.setSource(camera);

    // This creates a CvSource to use. This will take in a Mat image that has had OpenCV operations
    // operations
    CvSource imageSource = new CvSource("CV Image Source", VideoMode.PixelFormat.kMJPEG, IMG_WIDTH, IMG_HEIGHT, 30);
    MjpegServer cvStream = new MjpegServer("CV Image Stream", 1186);
    cvStream.setSource(imageSource);
    
    CvSource imageSource2 = new CvSource("CV Image Source 2", VideoMode.PixelFormat.kMJPEG, IMG_WIDTH, IMG_HEIGHT, 30);
    MjpegServer cvStream2 = new MjpegServer("CV Image Stream 2", 1187);
    cvStream2.setSource(imageSource2);

    // All Mats and Lists should be stored outside the loop to avoid allocations
    // as they are expensive to create
    Mat inputImage = new Mat();
    Mat hsv = new Mat();
	ArrayList<Rect> rects = new ArrayList<Rect>();
	ArrayList<MatOfPoint> fCO;
    GripPipeline gp = new GripPipeline();

    table = NetworkTable.getTable("camTable");
    // Infinitely process image
    while (true)
    {
      // Grab a frame. If it has a frame time of 0, there was an error.
      // Just skip and continue
      long frameTime = imageSink.grabFrame(inputImage);
      if (frameTime == 0)
      {
    	  continue;
      }
      gp.process(inputImage);
      hsv = gp.cvCannyOutput();
      imageSource.putFrame(hsv);
      imageSource2.putFrame(inputImage);
      fCO = gp.findContoursOutput();

		for (MatOfPoint mop : fCO)
		{
				rects.add(Imgproc.boundingRect(mop));
		}
		
		//remove rectangles that aren't the right size
		/*for(int i=0;i<rects.size();i++)
		{
			Rect r = rects.get(i);
			if((Math.abs(2.5 - r.height / (float)r.width)>0.5) && (r.y)> 1 ) //Values in the filtering are not useful atm
			{
				rects.remove(i);
				i--;
			}
		}*/

		if (!rects.isEmpty())
		{
				//if(rects.size()==2) {
					Rect r1 = rects.get(0);
					Rect r2 = rects.get(1);

					//observed = new Snapshot(time, (r1.x+r2.x+r1.width+r2.width)/2-IMG_WIDTH/2, (r1.y+r2.y+r1.height+r2.height)/2, Math.abs(r1.x-r2.x));
				/*} else if (time - oldTime < 200) {
					//use lastObserved to help determine the new position
					//TODO 1 or >3 rectangles
					//observed = new Snapshot(0,0,0,0);
				} else {
					//determine position with rectangle data only
					//TODO 1 or >3 rectangles
					Rect r1 = rects.get(0);
					Rect r2 = rects.get(1);
					Rect r3 = rects.get(2);*/

					/*ArrayList<Rect> rects = new ArrayList<Rect>();

					* rect at rects(0) will be the largest rectangle that we see
					

					if(r1.height > r2.height && r1.height > r3.height)
					{
						rects.add(r1);
						rects.add(r2);
						rects.add(r3);
					}
					else if(r2.height > r1.height && r2.height > r3.height)
					{
						rects.add(r2);
						rects.add(r1);
						rects.add(r3);
					}
					else
					{
						rects.add(r3);
						rects.add(r1);
						rects.add(r2);
					}
					//Biggest rectangle is added first*/

					/*if(rects.get(1).x == rects.get(2).x * 1.02 && rects.get(1).x == rects.get(2).x * 0.98) // this is determine if the rectangles are good
					{
						// dimensions of the rectangle: h: 130.175 w: 50.8 in millimeters, h/w = 2.5625

						double HEIGHT_OF_RECTANGLE_IN_PIXELS_WHEN_DOCKED = 300;
						// TODO: THIS NEEDS TO BE DETERMINED WITH ACTUAL TESTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

						double distance = 1 / ((HEIGHT_OF_RECTANGLE_IN_PIXELS_WHEN_DOCKED) / rects.get(0).height);
						// calculate distance by doing 1 meter / distance = expected height / height
						// need to check if true
						double expWidth = rects.get(0).height / 2.5625; // expected width
						double angle = asin(rects.get(0).width / expWidth); // find the angle of the robot compared to straight on

						int side = 0; // 1 = left of the peg - 2 = right of the peg

						if(rects.get(0).x > rects.get(1).x)
						{
							side = 2;
						}
						else
						{
							side = 1;
						}
					}
					//observed = new Snapshot(0,0,0,0);*/
					table.putNumber("r1x", r1.x);
					table.putNumber("r2x", r2.x);
					table.putNumber("r1y", r1.y);
					table.putNumber("r2y", r2.y);		
		} 
		/*else {
			if (time - oldTime < 500) {
				//observed = new Snapshot(lastObserved.time,lastObserved.x,lastObserved.y,lastObserved.width);
			} else {
				//observed = new Snapshot(0,0,0,0);}
				*/
    
    }

      // Below is where you would do your OpenCV operations on the provided image
      // The sample below just changes color source to HSV

      // Here is where you would write a processed image that you want to restreams
      // This will most likely be a marked up image of what the camera sees
      // For now, we are just going to stream the HSV image
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
}
