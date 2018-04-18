// This example shows how to move only one DC motor.  The motor will run for the specified time, which pauses the execution of the program until the motor stops.
import rxtxrobot.*;

public class RunOneMotor
{
	boolean track = true; //True: The bump is on the left side of the start
	//False: The bump is on the Right side of the start
	static int leftSpeed = 120;
	static int rightSpeed = -130;


	
	
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		//r.setVerbose(true);
		r.setPort("/dev/tty.usbmodem1421"); // Set port to 1411
		r.connect();
		//1 is left wheel, and 0 is right wheel
		//Motor 1 is left motor, Motor 0 is the right motor, the speed ratio is about 14/15
		
		r.attachMotor(0, 5);
		r.attachMotor(1, 6);
		r.attachServo(0, 8);
		r.attachServo(1, 9);
		
		r.moveServo(1, 180);
		r.sleep(1000);
		
		
		
		

		//turnLeft(r);
		goBump(r);
		r.sleep(1000);


		moveArm(r);
		r.sleep(3000);
		
		turnRight(r);
		goForward(r,25);

		r.sleep(1000);
		
		r.runEncodedMotor(1, 85, 120, 0, -100, 120);
////		r.sleep(1000);
		//turnRight(r);
		crossGap2(r);
	//	r.sleep(3000);
//	 	goBridge(r);
		
//	 	r.sleep(1000);
//		crossBridge(r);
		//goSandbox(r); 
	}
	
	public static void goBump(RXTXRobot r) {
		//Go forward a little bit
		goForward(r, (readPin(r) - 35));
		r.sleep(5000);
		
		//turn 90 Degree right, and speed up to the bump
		turnLeft(r);
		
		r.sleep(1000);
		double Ping = readPin(r) ;
//		int interval = (int)(120 - Ping);
//		System.out.println("interval: " + interval);
		
		goForward(r,Ping - 5 );
		r.sleep(3000);
		
		for (int i = 0; i < 50; i++ ) {
			 Ping = r.getPing(7);
			 System.out.println(Ping);
			 r.sleep(300);
			 
			 if(Ping > 30) {
				 powerUp(r);
				 break;
			 }
		}
		powerUp(r);
	}
	
	
	
	public static void moveArm(RXTXRobot r) {
		r.moveServo(1, 45);
		r.sleep(500);
		readThermistor(r);
		
		r.sleep(500);
		readThermistor(r);
		
		r.sleep(500);
		readThermistor(r);
		
		r.sleep(8000);
		r.moveServo(1, 180);
	}
	
	
	public static void crossGap(RXTXRobot r) {
		r.moveServo(0,90);
		
		double Ping = readPin(r) - 10 ;
		goForward(r,Ping);
		
		r.sleep(500);
		r.moveServo(0,0);
		
		r.sleep(500);
		
		Ping = readPin(r);
		int i = 0;
		
		 do{
			r.runEncodedMotor(1, -135, 50, 0, 160, 50 );
			Ping = readPin(r);
			i++;
			
			if (i > 10) {
				System.out.println("Fail to find the gap");
				break;
			}
		}while (Ping < 50);
		
		r.moveServo(0, 90);
		r.runEncodedMotor(1, -140, 50, 0, 150, 50 );
		
		r.sleep(1000);
		turnLeft(r);
		goForward(r,50);	
	}
	
	public static void crossGap2(RXTXRobot r) {
		r.moveServo(0,90);
		r.sleep(500);
		r.moveServo(0,180);
		r.sleep(500);
		
		
		
		double Ping = readPin(r);
		int i = 0;
		
		goForward(r,30);
		while (Ping < 50){
			goForward(r,45);
			Ping = readPin(r);
			i++;
			
			if (i > 10) {
				System.out.println("Fail to find the gap");
				break;
			}
		}
		
		r.moveServo(0, 90);
		goForward(r,20);
		
		r.sleep(5000);
		turnRight(r);
		goForward(r,100);	
	}
	
	public static void goBridge(RXTXRobot r) {
		turnLeft(r);
		r.sleep(2000);
		goForward(r, 150);
		r.sleep(4000);
		
		turnRight(r);
		r.sleep(2000);
		goForward(r,130);
		r.sleep(4000);
		
		turnRight(r);
		goForward(r,75);
	}

	
	public static void  crossBridge(RXTXRobot r) {
		powerUp(r);
		r.sleep(500);
		goForward(r, 120);
	}
	
//	
	
	public static void powerUp(RXTXRobot r) {
		//We don't have to attach anything because these motors are attached by default
		//Motor 1 is left motor, Motor 0 is the right motor
		r.runEncodedMotor(1, 220, 350, 0, -280, 350); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
//		goForward(r,150);
		
	}
	
	
	
	public static void turnLeft(RXTXRobot r, boolean track) {

//		Turn left
		if(track) {r.runEncodedMotor(0, -180, 115);}	
		
	
	}
	
	public static void turnRight(RXTXRobot r, boolean track) {
		//Turn right
		r.runEncodedMotor(1, 180, 115);	
	}
	
	
	
	public static double readPin(RXTXRobot r) {
		double Ping = r.getPing(7);
		r.sleep(500);
		System.out.println("Response: " + Ping + " cm");
		return Ping;
	}
	
	public static void goForward(RXTXRobot r, double Ping) {
		//1 is left motor, 0 is right motor
//		int leftSpeed = 120 ;
//		int rightSpeed = -130;

		if (Ping > 5) {
		int distance = distance = (int)( (Ping * 2.8) - 4.58);
		r.runEncodedMotor(1, leftSpeed, distance, 0, rightSpeed, distance);
		}
	}
	
	public static void testMotor(RXTXRobot r) {
		r.attachMotor(0, 5);
		r.attachMotor(1, 6);
		r.runMotor(1,125,500);
		//r.runMotor(1, 300, 2, -250, 0); // Run both motors forward indefinitely
		r.sleep(4000); // Pause execution for 5 seconds, but the motors keep running.
		r.runMotor(1,0,2,0,0); // Stop both motors
	}
	
	public static int getThermistorReading(RXTXRobot r, int Pin) {
	    int sum = 0;
	    int readingCount = 10;
	    //Read the analog pin values ten times, adding to sum each time
	    for (int i = 0; i < readingCount; i++) {
	       //Refresh the analog pins so we get new readings
	        r.refreshAnalogPins();
	        int reading = r.getAnalogPin(Pin).getValue();
	        sum += reading;
	}
	    //Return the average reading
	    return sum / readingCount;
	}
	
	public static void readThermistor(RXTXRobot r) {
		int thermistorReading1 = getThermistorReading(r,0);
		int thermistorReading2 = getThermistorReading(r,1);
		
	    
	    //Calculate the C
	    double temp1 = (thermistorReading1 - 745.35)/(-8.1984);
	    
	    //Print the results
	    System.out.println("\n");
	    System.out.println("The probe 1 read the value: " + thermistorReading1);
	    System.out.println("The probe 1 read the temperature: " + temp1);
	    //System.out.println("In volts: " + (thermistorReading1 * (5.0/1023.0)));
	    
	
	
		//Calculate the C
	    double temp2 = (thermistorReading2 - 744.35)/(-6.9984);
	    
	    //Print the results
	    System.out.println("The probe 2 read the value: " + thermistorReading2);
	    System.out.println("The probe 2 read the temperature: " + temp2);
	  //  System.out.println("In volts: " + (thermistorReading2 * (5.0/1023.0)));
	    
	    //Print the result
	    double windSpeed = (thermistorReading2 - thermistorReading1 + 5)/(-7.53); 
	    System.out.println("The probe 2 read the wind speed " + windSpeed);
	}
	
	
				
}
