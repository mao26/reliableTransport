public class TimeWarp1 {

	public String timeWarp(String input){
		double degPerMinByMinHand = 6, degPerSecByMinHand = 0.1, degPerMinByHourHand = 0.5, degPerSecByHourHand = 0.0083;
		double hourHandOnTheClock = 0, minHandOnTheClock = 0;
		int firstWS = 0, secondWS = 0, index = 0, hour = 0, degree = 0, degreeLookingFor = 0;
		String middle = "", degreeTemp = "";
		
		//this is our normal case were we are only given the hour, so return that input
		if(input.contains("til") == false && input.contains("after") == false){
			return input;
		}
		
		//this is to extract our degree from our input
		for(char ch : input.toCharArray()){
			if(ch == ' '){
				firstWS = index + 1; //first white space
				degree = Integer.parseInt(degreeTemp);
				break;
			}
			index = index + 1;
			degreeTemp = degreeTemp + ch;
		}
		System.out.println("degree : " + degree);
		
		//this is our input minus our degree / first part of input
		String secondHalfOfInput = input.substring(firstWS, input.length());
		//System.out.println("secondHalfOfInput : " + secondHalfOfInput);
		
		//re-initialize our index
		index = 0;
		//to extract our hour and after/til from the input
		boolean notSet = true;
		for(char ch : secondHalfOfInput.toCharArray()){
			if(ch == ' '){
				notSet = false;
				secondWS = index + 1;
				hour = Integer.parseInt(secondHalfOfInput.substring(secondWS, secondHalfOfInput.length()));
			} else if(notSet){
				index = index + 1;
				middle = middle + ch;
			}
		}
		System.out.println("our middle is : " + middle);
		//System.out.println("our hour is : " + hour);
		
		double keepTrackDegree = hour * 30;
		int keepTrackTimeHour = hour, keepTrackTimeMinute = hour * 5, keepTrackTimeSec = 0;
		boolean hourCorrect = false, minuteCorrect = false, secondCorrect = false;
		
		if(hour == 12){
			keepTrackTimeMinute = 0;
		}
		
		if(middle.equals("after")){
			degreeLookingFor = (hour * 30) + degree;
			
			/*if(degreeLookingFor > 180){
				//hour hand will be halfway btw last hour and new hour == 0.25 and 
				hourHandOnTheClock = 0.25 + ()
			}*/
			
			
			while(degreeLookingFor >= keepTrackDegree) {
				if(keepTrackTimeMinute > 60){
					keepTrackTimeMinute = 0;
				} 
				
				if(degreeLookingFor == keepTrackDegree){
					// our time is correct
				}
				
				if(!hourCorrect){ // our hour is not correct
					if(keepTrackDegree + 30 <= degreeLookingFor){// adding an extra hour is correct in this case
						keepTrackDegree = keepTrackDegree + 30;
						keepTrackTimeHour = keepTrackTimeHour + 1;
						
						if(keepTrackDegree + 30 == degreeLookingFor){
							//our time is correct --> keep our new hour, plus minute = 0, second = 0
							keepTrackTimeHour = keepTrackTimeHour;
							hourCorrect = true;
							minuteCorrect = true;
							secondCorrect = true;
						}
						
					} else if(keepTrackDegree + 30 > degreeLookingFor){ //adding an extra hour is incorrect so our current hour is correct
						
						hourCorrect = true; //will never let us enter this if flow again
						keepTrackTimeHour = keepTrackTimeHour; //just for show
						
					} else { // on the money without adding an extra hour old hour, minute = 0, second = 0 -->keepTrackDegree == degreeLookingFor
						keepTrackTimeHour = keepTrackTimeHour;
						hourCorrect = true;
						minuteCorrect = true;
						secondCorrect = true;
					}
					
				}
				
				if(!minuteCorrect){ // when minute hand moves the hour hand moves by .5 causing the angle to change
					if(keepTrackDegree + 6 - 0.5 < degreeLookingFor){ // adding an extra minute doesn't catch up with our degrees then add an extra minute
						
						keepTrackTimeMinute = keepTrackTimeMinute + 1;
						keepTrackDegree = keepTrackDegree + 6 -.5; //add minute movement minus hour movement
						
					} else if(keepTrackDegree + 6 -.5 > degreeLookingFor){ // adding an extra minute puts us over our degrees, so last minute was correct
						
						minuteCorrect = true;
						keepTrackTimeMinute = keepTrackTimeMinute;
						
					} else { //adding a minute gives us our exact degree
						
						minuteCorrect = true;
						secondCorrect = true;
						keepTrackTimeMinute = keepTrackTimeMinute;
						
					}
				}
				
				//to exit our loop
				if(hourCorrect && minuteCorrect){
					break;
				}
			}
			
			
		} 

		System.out.println("h:"+keepTrackTimeHour + " m:"+keepTrackTimeMinute);
		
		
		
		System.out.println("degree looking for " + degreeLookingFor);
 		
		return "no answer";
		
	}

	public static void main(String[] args) {
		TimeWarp1 tw = new TimeWarp1();
		String case1 = tw.timeWarp("4");
		String case2 = tw.timeWarp("20 after 8"); // 8:47:16
		// String case3 = tw.timeWarp("126 til 4"); //3:39:16
		// String case4 = tw.timeWarp("180 til 1"); //12:32:44
		String case5 = tw.timeWarp("0 after 12"); // 1:05:27
		System.out.println("case1: " + case1);
		System.out.println("case2: " + case2);
		// System.out.println("case3: " + case3);
		// System.out.println("case4: " + case4);
		System.out.println("case5: " + case5);
	}

}
