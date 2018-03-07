

	import rxtxrobot.*;

	public class thermometer {
		//This function reads the voltage coming into analog pin 0 ten times
		//takes the average, then returns the result.
		public static int getThermistorReading() {
		    int sum = 0;
		    int readingCount = 10;
		    //Read the analog pin values ten times, adding to sum each time
		    for (int i = 0; i < readingCount; i++) {
		       //Refresh the analog pins so we get new readings
		        robot.refreshAnalogPins();
		        int reading = robot.getAnalogPin(0).getValue();
		        sum += reading;
		}
		    //Return the average reading
		    return sum / readingCount;
		}
		
		public static int getBumpReading() {
		    int sum = 0;
		    int readingCount = 10;
		    //Read the analog pin values ten times, adding to sum each time
		    for (int i = 0; i < readingCount; i++) {
		       //Refresh the analog pins so we get new readings
		        robot.refreshAnalogPins();
		        int reading = robot.getAnalogPin(1).getValue();
		        sum += reading;
		}
		    //Return the average reading
		    return sum / readingCount;
		}
		
		public static RXTXRobot robot;
		//Your main method, where your program starts
		public static void main(String[] args) {
		    //Connect to the arduino
		    robot = new ArduinoUno();
		    robot.setPort("/dev/tty.usbmodem1421");
		    robot.connect();
		    //Get the average thermistor reading
		    int thermistorReading = getThermistorReading();
		    
		    //Calculate the C
		  //  double temp = (thermistorReading - 744.35)/(-6.4984);
		    
		    //Print the results
//		    System.out.println("The probe read the value: " + thermistorReading);
//		    System.out.println("The probe read the temperature: " + temp);
//		    System.out.println("In volts: " + (thermistorReading * (5.0/1023.0)));
		    
		    for (int x=0; x < 100; ++x)
			{
				  int bumpReading = getBumpReading();
				    System.out.println("The bump read the value " + bumpReading);
				    
				//Read the ping sensor value, which is connected to pin 12, PING_PIN = 12 
								robot.sleep(300);
			}

		    
		}
	}
