// This example shows how to move only one DC motor.  The motor will run for the specified time, which pauses the execution of the program until the motor stops.
import rxtxrobot.*;

public class RunOneMotor
{
	
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		//r.setVerbose(true);
		r.setPort("/dev/tty.usbmodem1421"); // Set port to 1411
		r.connect();
		//1 is left wheel, and 0 is right wheel
		//Motor 1 is left motor, Motor 0 is the right motor, the speed ratio is 14/15
				
		r.attachMotor(0, 5);
		r.attachMotor(1, 6);
		r.attachServo(0, 8);
		
		//goBump(r);
		crossGap(r);
		
		r.close();
	}
	
	public static void goBump(RXTXRobot r) {
		//turn 90 Degree right, and speed up to the bump
		//turnRight(r);
		double Ping = readPin(r) - 3;
		goForward(r,Ping);
		r.sleep(1000);
		
		for (int i = 0; i < 50; i++ ) {
			 Ping = r.getPing(13);
			 System.out.println(Ping);
			 
			 if(Ping > 10) {
				 goForward(r,30);
				 break;
			 }
		}
		
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
			r.runEncodedMotor(1, -140, 50, 0, 150, 50 );
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
	
	public static void climbBump(RXTXRobot r) {
		//We don't have to attach anything because these motors are attached by default
		//Motor 1 is left motor, Motor 2 is the right motor

		r.runEncodedMotor(1, 210, 300, 0, -225, 300); // Run both motors forward, one for 100,000 ticks and one for 50,000 ticks.
	
	}
	
	public static void testMotor(RXTXRobot r) {
		r.attachMotor(0, 5);
		r.attachMotor(1, 6);
		r.runMotor(1,125,500);
		//r.runMotor(1, 300, 2, -250, 0); // Run both motors forward indefinitely
		r.sleep(4000); // Pause execution for 5 seconds, but the motors keep running.
		r.runMotor(1,0,2,0,0); // Stop both motors
	}
	
	public static void turnRight(RXTXRobot r) {
				
		//Go forward a little bit
		r.runEncodedMotor(1, 140, 50, 0, -148, 50);
		r.sleep(500);
		//Turn right
		r.runEncodedMotor(1, 180, 120);
	}
	
	public static void turnLeft(RXTXRobot r) {
		
	
		//Turn left
		r.runEncodedMotor(0, -180, 100);
	}
	
	public static double readPin(RXTXRobot r) {
		double Ping = r.getPing(13);
		r.sleep(500);
		System.out.println("Response: " + Ping + " cm");
		return Ping;
	}
	
	public static void goForward(RXTXRobot r, double Ping) {
		//1 is left motor, 0 is right motor
		
		if (Ping > 5) {
		int distance = distance = (int)( (Ping * 2.8) - 4.58);
		r.runEncodedMotor(1, 140, distance, 0, -146, distance);
		}
	}
	
	
}
