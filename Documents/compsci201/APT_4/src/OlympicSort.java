import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class OlympicSort {
//	public class Compare implements Comparator<String>{
//		public int compare(String[] string1, String[] string2){
//			if(Integer.valueOf(string1[1]) > Integer.valueOf(string2[1])) return -1;
//			if(Integer.valueOf(string1[1]) < Integer.valueOf(string2[1])) return 1;
//			if(Integer.valueOf(string1[2]) > Integer.valueOf(string2[2])) return -1;
//			if(Integer.valueOf(string1[2]) < Integer.valueOf(string2[2])) return 1;
//			if(Integer.valueOf(string1[3]) > Integer.valueOf(string2[3])) return -1;
//			if(Integer.valueOf(string1[3]) < Integer.valueOf(string2[3])) return 1;
//			return string1[0].compareTo(string2[0]);
//		}
//	}
	public class SString implements Comparable<SString>{
		int[] medals = new int[3];
		String country; 
		
		@Override
		public int compareTo(SString o) {
			if(this.medals[0] < o.medals[0]) return 1;
			if(this.medals[0] > o.medals[0]) return -1;
			else{
				if(this.medals[1] < o.medals[1]) return 1;
				if(this.medals[1] > o.medals[1]) return -1;
				else{
					if(this.medals[2] < o.medals[2]) return 1;
					if(this.medals[2] > o.medals[2]) return -1;
					else{
						return this.country.compareTo(o.country);
//						if(this.country.compareTo(o.country) == 1) return -1;
//						if(this.country.compareTo(o.country) == -1) return 1;
//						else{
//							return 0;
//						}
					}
				}
			}
		}
//		public int compareTo(SString o) {
//			if(this.country.compareTo(o.country) == 1) return 1;
//			if(this.country.compareTo(o.country) == -1) return -1;
//			else{
//				 if(this.medals[0] < o.medals[0]) return 1;
//				 if(this.medals[0] > o.medals[0]) return -1;
//				 else{
//					 if(this.medals[1] < o.medals[1]) return 1;
//					 if(this.medals[1] > o.medals[1]) return -1;
//					 else{
//						 if(this.medals[2] < o.medals[2]) return -1;
//						 if(this.medals[2] > o.medals[2]) return 1;
//						 return 0;
//					 }
//				 }
//			}
//		}
	}
	
	public String[] standings(String[] results) {
		Map<String, int[]> olympics = new HashMap<String, int[]>();
		for(String event : results){
//			String[] countries = event.split("[ ]+");
			String[] countries = event.split(" ");
			for(int i = 0; i < countries.length; i++){
				String country = countries[i];
				int medalArray[] = olympics.get(country);
				if(medalArray == null){
					medalArray = new int[3];
					olympics.put(country, medalArray);
				} 
				medalArray[i]++;
				
			}
		}
		//from here, until....
		ArrayList<SString> need = new ArrayList<SString>();
		for(String country: olympics.keySet()){
			SString ss = new SString();
			ss.country = country;
			ss.medals = olympics.get(country);
			need.add(ss);
		}
		Collections.sort(need);
//		for(int i = 0; i < need.size(); i++){
//			System.out.println("country " + need.get(i).country);
//			System.out.println("medals " + need.get(i).medals);
//		}
		
		//here is all the new code
		
		//the code that used to give me my answer
		String [] standings = new String [olympics.size()];
		int idx = 0;
		for(SString ss: need){
			String country = ss.country;
			int[] medalCount = ss.medals;
			String result = country + " " + medalCount[0] + " " + medalCount[1] + " " + medalCount[2];
			standings[idx] = result;
			idx++;
//			System.out.println(result);
		}
		
		
		
		
//		ArrayList<String> myList = new ArrayList<String>();
//		for(int i = 0; i<standings.length; i++) {
//			myList.add(standings[i]);
//		}
//		Compare mycomp = new Compare();
//		Collections.sort(myList, mycomp);
//		
//		System.out.println();
//		Compare compare = new Compare();
//		Collections.sort(olympics);
//		
		return standings;
	}
	
	
	public static void main(String[] args){
		String[] countries = new String []{
				"ITA JPN AUS", "USA TPE UKR", "USA USA GBR", "USA CHN TPE"
				};
		OlympicSort os = new OlympicSort();
		os.standings(countries);
//		for()
//		String[] standings = os.standings(countries);
//		for(int i = 0; i < standings.length; i++){
//			System.out.println(standings[i]);
//		}
	}

//	@Override
//	public int compareTo(String o) {
//		if(this. >)
//		return 0;
//	}
}
