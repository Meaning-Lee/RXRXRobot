import rxtxrobot.ArduinoUno;
import rxtxrobot.RXTXRobot;

public class BumpTrigger {

	public static int getBumpReading() {
	    int sum = 0;
	    int readingCount = 10;
	    //Read the analog pin values ten times, adding to sum each time
	    for (int i = 0; i < readingCount; i++) {
	       //Refresh the analog pins so we get new readings
	        robot.refreshAnalogPins();
	        int reading = robot.getAnalogPin(5).getValue();
	       sum += reading;
	}
	    //Return the average reading
	   
	    return sum / readingCount;
	   // return readingCount;
	}
	
	public static RXTXRobot robot;
	public static void main(String[] args) {
		//Connect to the arduino
	    robot = new ArduinoUno();
	    robot.setPort("/dev/tty.usbmodem1411");
	    robot.connect();
	    robot.attachMotor(1, 5);
	    
	    for (int x=0; x < 100; ++x)
		{
	    		int bumpReading = getBumpReading();
			System.out.println("The bump read the value " + bumpReading);
			    
			
			robot.runMotor(1,80,0);
			robot.sleep(100);
			
			if(bumpReading != 1023) {
				robot.runMotor(1,0,500);
				break;
				}
			}
	    
//	    int bumpReading = getBumpReading();
//	    while (bumpReading != 0) {
//	    			robot.runMotor(1,80,3000);
//	    			bumpReading = getBumpReading();
//	    			robot.sleep(1000);
//	    			System.out.println("The bump read the value " + bumpReading);
//	    		}
	   	}

}
