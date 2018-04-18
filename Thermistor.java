//Mingfu
//This program show how two functions to measure temp(Celcius) and wind speed.
//The function for conductivity sensor should be similar.
//you can try to write a function to filter data (like temp shoud be in 10~35 Degree range, values out of this range should be deleted), 
//and get mutiple values to get an average number.


	import rxtxrobot.RXTXRobot;
	public static RXTXRobot r;

	
	public static void main(String[] args) {
		readThermistor(r);
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
