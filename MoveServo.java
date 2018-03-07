
	// This example moves each servo individually.  While this method should be used to move one servo individually, it is recommended to use moveBothServos if both servos must be moved simultaneously
	import rxtxrobot.*;

	public class MoveServo
	{
		public static void main(String[] args)
		{
			RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
			r.setPort("/dev/tty.usbmodem1411"); // Set the port to usbmodem1421
			r.setVerbose(true); // Turn on debugging messages
			r.connect();
			r.attachServo(0, 8); //Connect the servos to the Arduino
			//r.attachServo(RXTXRobot.SERVO2, 10);
			r.moveServo(0,180); // Move Servo 1 to location 30
			
			//r.moveServo(RXTXRobot.SERVO2, 170); // Move Servo 2 to location 170
			r.sleep(5000);
			r.close();
		}
	}

