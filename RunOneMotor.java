// This example shows how to move only one DC motor.  The motor will run for the specified time, which pauses the execution of the program until the motor stops.
import rxtxrobot.*;

public class RunOneMotor
{
	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("/dev/tty.usbmodem1421"); // Set port to COM3
		r.connect();
		r.attachMotor(1, 5);
		r.attachMotor(2, 6);
		//r.runMotor(1,125,500);
		r.runMotor(1, 180, 2, -180, 0); // Run both motors forward indefinitely
		r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running.
		r.runMotor(1,0,2,0,0); // Stop both motors
		r.close();
	}
}
