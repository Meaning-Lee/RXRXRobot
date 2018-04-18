import rxtxrobot.ArduinoUno;
import rxtxrobot.RXTXRobot;

public class BumpTrigger {

	public static int getBumpReading() {
	    int sum = 0;
	    int readingCount = 10;
	    //Read the analog pin values ten times, adding to sum each time
	    for (int i = 0; i < readingCount; i++) {
	       //Refresh the analog pins so we get new readings
	        r.refreshAnalogPins();
	        int reading = r.getAnalogPin(5).getValue();
	       sum += reading;
	}
	    //Return the average reading
	   
	    return sum / readingCount;
	   // return readingCount;
	}
	
	public static void goSandbox(RXTXRobot r) {
		
		int bumpReading = getBumpReading();
		System.out.println("The bump read the value " + bumpReading);
	
		
		while(bumpReading > 500) {
			r.runMotor(1,120,0, -150, 0);
			bumpReading = getBumpReading();
			System.out.println("The bump read the value " + bumpReading);		
		}
			r.runMotor(1, 0, 0,0,0);
//		
//		 for (int x=0; x < 100; ++x)
//			{
//		    		int bumpReading = getBumpReading();
//				System.out.println("The bump read the value " + bumpReading);
//				
//				
//				if(bumpReading > 500) {
//					goForward(r,50); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
//					r.sleep(100);
//					}
//				}
	}
	
	public static void goForward(RXTXRobot r, double Ping) {
		//1 is left motor, 0 is right motor
		
		if (Ping > 5) {
		int distance = distance = (int)( (Ping * 2.8) - 4.58);
		r.runEncodedMotor(1, 100, distance, 0, -125, distance);
		}
	}
	
	public static void powerUp(RXTXRobot r) {
		//We don't have to attach anything because these motors are attached by default
		//Motor 1 is left motor, Motor 0 is the right motor
		r.runEncodedMotor(1, 220, 300, 0, -280, 300); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
//		goForward(r,150);
		
	}
	
	public static void moveCServo(RXTXRobot r) {
		r.moveServo(2, 180);
		r.sleep(3000);
		r.moveServo(2,90);
	}
	
	public static void  crossBridge(RXTXRobot r) {
		powerUp(r);
		r.sleep(500);
		goForward(r, 120);
	}
	
	
	public static void turnRight(RXTXRobot r) {
		
		//Turn right
		r.runEncodedMotor(1, 120, 122);
	}
	
	
	public static RXTXRobot r;
	public static void main(String[] args) {
		//Connect to the arduino
	    r = new ArduinoUno();
		r.setPort("/dev/tty.usbmodem1421"); // Set port to 1411
		r.connect();
		//1 is left wheel, and 0 is right wheel
		//Motor 1 is left motor, Motor 0 is the right motor, the speed ratio is 14/15
				
		r.attachMotor(0, 5);
		r.attachMotor(1, 6);
		r.attachServo(0, 8);
	    r.attachServo(2, 10);
		
//	    goForward(r,125);
//		turnRight(r);
	    
	    crossBridge(r);
	    r.sleep(5000);
	    
	    
		turnRight(r);
		goSandbox(r);
		
		r.sleep(1000);
		moveCServo(r);
		r.close();
		
//	    for (int x=0; x < 100; ++x)
//		{
//	    		int bumpReading = getBumpReading();
//			System.out.println("The bump read the value " + bumpReading);
//			    
//			
//			r.runMotor(1,80,0);
//			r.sleep(100);
//			
//			if(bumpReading != 1023) {
//				r.runMotor(1,0,500);
//				break;
//				}
//			}
	    
//	    int bumpReading = getBumpReading();
//	    while (bumpReading != 0) {
//	    			robot.runMotor(1,80,3000);
//	    			bumpReading = getBumpReading();
//	    			robot.sleep(1000);
//	    			System.out.println("The bump read the value " + bumpReading);
//	    		}
	   	}

}
