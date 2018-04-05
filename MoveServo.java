
	// This example moves each servo individually.  While this method should be used to move one servo individually, it is recommended to use moveBothServos if both servos must be moved simultaneously
	import rxtxrobot.*;

	public class MoveServo
	{
		public static void main(String[] args)
		{
			RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
			r.setPort("/dev/tty.usbmodem1421"); // Set the port to usbmodem1421
			r.setVerbose(true); // Turn on debugging messages
			r.connect();
			r.attachServo(0, 8); //Connect the servos to the Arduino
			//r.attachServo(RXTXRobot.SERVO2, 10);
			
			//adjustServo(r);
			
			//move pin sensor
			//moveArm(r);
			
			rotatePin(r);
			r.close();
		}
		
		public static void rotatePin(RXTXRobot r) {
			r.moveServo(0,0); 
			r.sleep(2000);
			
			r.moveServo(0,90);
			r.sleep(2000);
			
			r.moveServo(0, 180);
			
			r.sleep(2000);
			r.close();
		}
		
		public static void moveArm(RXTXRobot r) {
			r.moveServo(0, 134);
		}
		
		public static void adjustServo(RXTXRobot r) {
			r.moveServo(0, 90);
		
		}
		
		
	}

