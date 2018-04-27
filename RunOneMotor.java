// This example shows how to move only one DC motor.  The motor will run for the specified time, which pauses the execution of the program until the motor stops.
import rxtxrobot.*;

public class RunOneMotor
{
	static boolean track = true; //True: The bump is on the left side of the start
	//False: The bump is on the Right side of the start
	static int leftSpeed = 125;
	static int  rightSpeed = -160;


	
	
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
		r.attachMotor(2, 4);
		r.attachServo(0, 8);
		r.attachServo(1, 9);
		r.attachServo(2, 11);
		
		r.moveServo(1, 180);
		r.moveServo(2, 60);
		r.sleep(1000);
		
		//*******Test Zone********//
//		adjustRobot(r);
//		testTurning(r);
//		goForward(r,100);
//		moveCServo(r);
//		powerUp(r,310);
//		adjustBridge(r);
		
		goBump(r);
		r.sleep(1000);

		moveArm(r);
		r.sleep(3000);
		
		goDown(r);
		r.sleep(1000);
		crossGap2(r);
		r.sleep(3000);
	 	goBridge(r);
		
	 	r.sleep(1000);
		crossBridge(r);
		r.sleep(2000);
		goSandbox(r);
		
		
		}
	

	public static void goBump(RXTXRobot r) {
		//Go forward a little bit
		double distance = readPin(r);
		
		while(distance < 20) {
			 distance = readPin(r);
		}
		if(track) {	goForward(r, (distance - 35));
			}else {
		goForward(r, (distance - 45));}
		r.sleep(1000);
		
		//turn 90 Degree right, and speed up to the bump
		turnRight(r,track);
		r.sleep(1000);
		if(track) {	adjustRobot(r, 45, 3 );
		}else {
		adjustRobot(r, 53, 3 );}
		
		
		r.sleep(1000);
		double Ping = readPin(r);
		
		goForward(r,Ping - 5 );
		r.sleep(3000);
		
		for (int i = 0; i < 50; i++ ) {
			 Ping = r.getPing(7);
			 System.out.println(Ping);
			 r.sleep(300);
			 
			 if(Ping > 30) {
				 powerUp(r,315);
				 break;
			 }
		}
	}
	
	
	
	public static void moveArm(RXTXRobot r) {
		r.moveServo(1, 45);
		r.sleep(1000);
		readThermistor(r);
		
		r.sleep(1000);
		readThermistor(r);
		
		r.sleep(1000);
		readThermistor(r);
		
		r.sleep(5000);
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
		
		while (Ping < 80){
			goForward(r,28);
			Ping = readPin(r);
			i++;
			
			
			
			if (i > 10) {
				System.out.println("Fail to find the gap");
				break;
			}
		}
		
		r.moveServo(0, 90);
		goForward(r,20);
		
		r.sleep(2000);
		turnLeft(r,track);
		goForward(r,100);	
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
		r.sleep(1000);
		if (!track) 
		{turnLeft(r, track);
		
		
		}else {
			r.runEncodedMotor(1, -220, 108);
		}
		
		goForward(r, readPin(r) -25);
		r.sleep(1500);
		
		turnRight(r,track);
		r.sleep(1000);
		adjustRobot(r, 37, 3);
		r.sleep(1500);
		goForward(r, readPin(r) -23);
		
		r.sleep(1500);
		turnRight(r,track);
		r.sleep(1000);
		adjustRobot(r, 33, 3);
		
		r.sleep(1000);
		
		//add some codes over here
//		if(!track) {
//		goForward(r,10);
//		}
		
	}
	
	public static void adjustBridge(RXTXRobot r) {
		turnPinLeft(r,track);
		r.sleep(1000);
		double interval = readPin(r);
		
		while(interval < 40) {
			goForward(r,10);
			interval = readPin(r);
			r.sleep(500);
		}
		r.moveServo(0, 90);
		r.sleep(500);
		r.runEncodedMotor(1, -leftSpeed, 40, 0, -rightSpeed, 40);
		
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
					
					if(!track) {r.runEncodedMotor(1, 180, 12);
					}else {r.runEncodedMotor(0, -180, 12);}
					
					System.out.println("In the adjustment");
					
//				}else if(difference > 1 && difference < 5) {
//					if(!track) {LSpeed -= 20;}else
//					
				}else 
					if (difference >= 4) {
					r.sleep(2000);
					
					if(!track) {r.runEncodedMotor(0, -180, 12);
					}else {r.runEncodedMotor(1, 180, 12);}
					}

					System.out.println("In the if");
					
					if (difference > 200) {break;}
			System.out.println("LSpeed " + LSpeed);
			
		}	
		r.moveServo(0,90);
	}
	
	public static void  crossBridge(RXTXRobot r) {

		adjustBridge(r);
		
		r.setMotorRampUpTime(500);
		r.runEncodedMotor(1, 300, 245, 0, -370, 250);
		r.sleep(500);
		goForward(r, 150);
		
		r.sleep(1000);
		r.runEncodedMotor(1, 160, 15);
	}
	
//	
	
	public static void powerUp(RXTXRobot r, int d) {
		//We don't have to attach anything because these motors are attached by default
		//Motor 1 is left motor, Motor 0 is the right motor
		if(!track) {
		r.setMotorRampUpTime(500);
		//r.runEncodedMotor(1, 305, d, 0, -380, d - 7 ); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
		r.runEncodedMotor(1, 300, d - 5, 0, -360, d  );
		} else {
			r.setMotorRampUpTime(500);
			//r.runEncodedMotor(1, 305, d , 0, -375, d  );
			r.runEncodedMotor(1, 300, d - 5, 0, -360, d  );
			
		}
	}
	
	public static void goDown(RXTXRobot r) {
			//adjust Right
//			if(!track) {r.runEncodedMotor(1, -220, 35);
//			}else {r.runEncodedMotor(0, 220, 25);}
			
			turnLeft(r,track);	
			r.sleep(1000);
			if(track) {
				r.runEncodedMotor(1, leftSpeed, 150, 0, rightSpeed, 150);
				}else{r.runEncodedMotor(1, leftSpeed, 150, 0, rightSpeed, 150);}
			
			r.sleep(3000);
			r.runEncodedMotor(1, 160, 15 );
	}
	
	
	
	public static void turnLeft(RXTXRobot r, boolean track) {
		
		r.setMotorRampUpTime(500);
		if(track) {
//			Turn left
			r.runEncodedMotor(0, -220, 128);
		}else {
			//Turn Right
			r.runEncodedMotor(1, 220, 108);
		}	
	}
	
	public static void turnRight(RXTXRobot r, boolean track) {
		r.setMotorRampUpTime(500);
		if(track) {
			//Turn Right
			r.runEncodedMotor(1, 220, 108);
		}else {
			// Turn left
//			r.runEncodedMotor(0, -300, 80);
			r.runEncodedMotor(0, -220, 128);
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
		
		turnRight(r,track);
		r.sleep(3000);
		
		for (int i = 1; i < 5; i++) {
		turnLeft(r,track);
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
	    double temp2 = (thermistorReading2 - 744.35)/(-6.9984) - 5;
	    
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
	    int readingCount = 3;
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
		r.sleep(1000);
		
		adjustRobot(r,40, 5);
		r.sleep(1000);
		
		int bumpReading = getBumpReading(r);
		System.out.println("The bump read the value " + bumpReading);
	
		
		while(bumpReading > 500) {
			r.runMotor(1,leftSpeed,0, rightSpeed, 0);
			bumpReading = getBumpReading(r);
			System.out.println("The bump read the value " + bumpReading);		
		}
			r.runMotor(1, 0, 0,0,0);
//			r.sleep(1000);
//			r.runMotor(1, -120, 0, 140, 500);
			moveCServo(r);
	}
	
	public static void moveCServo(RXTXRobot r) {
		r.moveServo(2, 170);
		r.sleep(1000);
		int ADC = getConductivityReading(r);
		double percentage = (ADC - 988.21)/(-19.37);
		System.out.println("The water percentage is " + percentage);		
		r.moveServo(2,90);
		r.sleep(1000);
		
		if (percentage < 10) {
			r.runMotor(2, 80, 2000);
			System.out.println("Drop!!! ");		
		}
	}

				
}
