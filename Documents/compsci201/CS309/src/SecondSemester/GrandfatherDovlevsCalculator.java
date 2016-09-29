package SecondSemester;

import java.util.Scanner;

public class GrandfatherDovlevsCalculator {

	public int[] segmentsPerNumberByIdx = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
	
	public static void main(String[] args){
		GrandfatherDovlevsCalculator gdc = new GrandfatherDovlevsCalculator();
		gdc.run();
	}
	
	public void run()
	{
		Scanner scan = new Scanner(System.in);
		int firstNum = scan.nextInt();
		int lastNum = scan.nextInt();
		int segments = 0;
		int copyFirst;
		while(firstNum <= lastNum){
			copyFirst = firstNum;
			int length = (firstNum + "").length();
			for(int i = 0; i < length; i++){
				int idx = firstNum % 10;
				segments += segmentsPerNumberByIdx[idx];
				firstNum = firstNum/10;
			}
			firstNum = copyFirst + 1;
			
		}
		System.out.println(segments);
	}
}
