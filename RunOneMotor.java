// This example shows how to move only one DC motor.  The motor will run for the specified time, which pauses the execution of the program until the motor stops.
import rxtxrobot.*;

public class RunOneMotor
{
	static boolean track = false; //True: The bump is on the left side of the start
	//False: The bump is on the Right side of the start
	static int leftSpeed = 125;
	static int rightSpeed = -140;


	
	
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		//r.setVerbose(true);
		r.setPort("/dev/tty.usbmodem1411"); // Set port to 1411
		r.connect();
		//1 is left wheel, and 0 is right wheel
		//Motor 1 is left motor, Motor 0 is the right motor, the speed ratio is about 14/15
		
		r.attachMotor(0, 5);
		r.attachMotor(1, 6);
		r.attachMotor(2, 4);
		r.attachServo(0, 8);
		r.attachServo(1, 9);
		r.attachServo(2, 11);
		
//		r.moveServo(1, 180);
//		r.moveServo(2, 60);
//		r.sleep(1000);
		
		//*******Test Zone********//
//		adjustRobot(r);
//		testTurning(r);
//		goForward(r,100);		
//		powerUp(r,320);
//		
//		goBump(r);
//		r.sleep(1000);
//
//		moveArm(r);
//		r.sleep(3000);
//		
//		goDown(r);
//		r.sleep(1000);
//		crossGap2(r);
//		r.sleep(3000);
//	 	goBridge(r);
//		
//	 	r.sleep(1000);
//		crossBridge(r);
		//goSandbox(r); 
	//	r.runMotor(2,80,300);
	}
	

	public static void goBump(RXTXRobot r) {
		//Go forward a little bit
		goForward(r, (readPin(r) - 40));
		r.sleep(3000);
		
		//turn 90 Degree right, and speed up to the bump
		turnRight(r,track);
		r.sleep(1000);
		adjustRobot(r, 45, 1 );
		
		
		r.sleep(1000);
		double Ping = readPin(r);
		
		goForward(r,Ping - 5 );
		r.sleep(3000);
		
		for (int i = 0; i < 50; i++ ) {
			 Ping = r.getPing(7);
			 System.out.println(Ping);
			 r.sleep(300);
			 
			 if(Ping > 30) {
				 powerUp(r,320);
				 break;
			 }
		}
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
		turnLeft(r,track);
		goForward(r,50);	
	}
	
	public static void crossGap2(RXTXRobot r) {
		
		turnPinLeft(r,track);
		
		double Ping = readPin(r);
		int i = 0;
		
		while (Ping < 50){
			goForward(r,30);
			Ping = readPin(r);
			i++;
			
			
			
			if (i > 10) {
				System.out.println("Fail to find the gap");
				break;
			}
		}
		
		r.moveServo(0, 90);
		goForward(r,25);
		
		r.sleep(5000);
		turnLeft(r,track);
		goForward(r,75);	
	}

	public static void turnPinLeft(RXTXRobot r, boolean track) {
		r.moveServo(0,90);
		r.sleep(500);
		
		if (track) {
			r.moveServo(0,0);
		}else {
			r.moveServo(0,180);
		}
		
		r.sleep(500);
	}
	
	public static void turnPinRight(RXTXRobot r,boolean track) {

		r.moveServo(0,90);
		r.sleep(500);
		
		if (track) {
			r.moveServo(0,180);
		}else {
			r.moveServo(0,0);
		}
		r.sleep(500);
	}
	
	public static void goBridge(RXTXRobot r) {
		turnLeft(r, track);
		r.sleep(2000);
		goForward(r, readPin(r) -15);
		r.sleep(3000);
		
		
		turnRight(r,track);
		r.sleep(1000);
		adjustRobot(r, 30, 3);
		r.sleep(2000);
		goForward(r, readPin(r) -15);
		
		r.sleep(3000);
		turnRight(r,track);
		r.sleep(2000);
		adjustRobot(r, 20, 1);
	}

	public static void adjustRobot(RXTXRobot r, double original, int time) {
		int LSpeed = leftSpeed;
		int RSpeed = rightSpeed;
		
		turnPinLeft(r,track);
		double Ping1 ;
		double difference;
		
		for(int i = 0; i < time;i++){
			
			r.runEncodedMotor(1, LSpeed, 30, 0, RSpeed, 30);
			Ping1 = readPin(r);
			
			difference = ( original - Ping1);
			System.out.println("The distance difference is " + difference + "cm");
			
//			if (difference < -1 && difference > -5){
//				if (!track) {LSpeed += 20;}else {RSpeed += 20;}
//			
//				}else 
					if(difference <= -4) {
					r.sleep(2000);
					
					if(!track) {r.runEncodedMotor(1, 180, 15);
					}else {r.runEncodedMotor(0, -180, 15);}
					
					System.out.println("In the adjustment");
					
//				}else if(difference > 1 && difference < 5) {
//					if(!track) {LSpeed -= 20;}else
//					
//				}else 
					if (difference >= 4) {
					r.sleep(2000);
					
					if(!track) {r.runEncodedMotor(0, -180, 15);
					}else {r.runEncodedMotor(0, 180, 15);}
					}

					System.out.println("In the if");
					
				}
			
//			if (LSpeed < 80) {
//				LSpeed = leftSpeed;
//			}
			
			System.out.println("LSpeed " + LSpeed);
			
		}	
		r.moveServo(0,90);
	}
	
	public static void  crossBridge(RXTXRobot r) {
		powerUp(r, 240);
		r.sleep(500);
		goForward(r, 150);
	}
	
//	
	
	public static void powerUp(RXTXRobot r, int d) {
		//We don't have to attach anything because these motors are attached by default
		//Motor 1 is left motor, Motor 0 is the right motor
		
		r.setMotorRampUpTime(500);
		r.runEncodedMotor(1, 300, d, 0, -380, d - 5 ); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
//		
//		r.runMotor(1, 250, 0, -350, 4000);
	}
	
	public static void goDown(RXTXRobot r) {
			//adjust Right
			if(!track) {r.runEncodedMotor(1, -220, 35);
			}else {r.runEncodedMotor(0, 220, 25);}
			
			turnLeft(r,track);	
			r.sleep(1000);
			r.runEncodedMotor(1, leftSpeed, 120, 0, rightSpeed, 120);
			
			r.sleep(3000);
			r.runEncodedMotor(1, -220, 35);
	}
	
	
	
	public static void turnLeft(RXTXRobot r, boolean track) {
		
		r.setMotorRampUpTime(500);
		if(track) {
//			Turn left
			r.runEncodedMotor(0, -220, 115);
		}else {
			//Turn Right
			r.runEncodedMotor(1, 220, 105);
		}	
		
	
	}
	
	public static void turnRight(RXTXRobot r, boolean track) {
		r.setMotorRampUpTime(500);
		if(track) {
			//Turn Right
			r.runEncodedMotor(1, 220, 105);
		}else {
			// Turn left
			r.runEncodedMotor(0, -220, 115);
		}
		
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
	
	public static void testTurning (RXTXRobot r) {
		
		turnLeft(r,track);
		r.sleep(3000);
		
		for (int i = 1; i < 5; i++) {
		turnRight(r,track);
		r.sleep(3000);
		}
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
	
	public static int getConductivityReading(RXTXRobot r) {
	    int sum = 0;
	    int readingCount = 5;
	    //Read the analog pin values ten times, adding to sum each time
	    for (int i = 0; i < readingCount; i++) {
	       //Refresh the analog pins so we get new readings
	        r.refreshAnalogPins();
	        r.refreshDigitalPins();
	        int reading = r.getConductivity();
	        System.out.println("The conductivity read the value: " + reading);
			System.out.println("In volts: " + (reading * (5.0/1023.0)));
	        sum += reading;
	}
	    //Return the average reading
	    
	    return sum / readingCount;
	}
	
	public static int getBumpReading(RXTXRobot r) {
	    int sum = 0;
	    int readingCount = 10;
	    //Read the analog pin values ten times, adding to sum each time
	    for (int i = 0; i < readingCount; i++) {
	       //Refresh the analog pins so we get new readings
	        r.refreshAnalogPins();
	        int reading = r.getAnalogPin(3).getValue();
	       sum += reading;
	}
	    //Return the average reading
	   
	    return sum / readingCount;
	   // return readingCount;
	}
	
	public static void goSandbox(RXTXRobot r) {
		turnRight(r,track);
		r.sleep(3000);
		
		int bumpReading = getBumpReading(r);
		System.out.println("The bump read the value " + bumpReading);
	
		
		while(bumpReading > 500) {
			r.runMotor(1,120,0, -150, 0);
			bumpReading = getBumpReading(r);
			System.out.println("The bump read the value " + bumpReading);		
		}
			r.runMotor(1, 0, 0,0,0);
	}
	
	public static void moveCServo(RXTXRobot r) {
		r.moveServo(2, 180);
		r.sleep(60000);
		int ADC = getConductivityReading(r);
		
		r.moveServo(2,90);
	}

				
}
