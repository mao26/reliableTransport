import java.util.Scanner;

public class CloseEnoughComputations {

	public static void main(String[] args) {
		CloseEnoughComputations cec = new CloseEnoughComputations();
		cec.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(true){ 
			double cals = scan.nextDouble();
			double fat = scan.nextDouble();
			double carbs = scan.nextDouble();
			double protein = scan.nextDouble();
			if(cals == fat && fat == carbs && carbs == protein && protein == 0) break;
			double min = checkRoundUp(fat, carbs, protein);
			double max = checkRoundDown(fat, carbs, protein);
			if(cals >= min && cals <= max){
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
		}
		scan.close();
	}

	private double checkRoundDown(double fat, double carbs, double protein) {
		double fatsCal = (fat + .49) * 9;
		double carbsCal = (carbs + .49) * 4;
		double protCal = (protein + .49) * 4;
		return fatsCal + carbsCal + protCal;
	}

	private double checkRoundUp(double fat, double carbs, double protein) {
		double fatsCal = (fat - .5) * 9;
		double carbsCal = (carbs - .5) * 4;
		double protCal = (protein - .5) * 4;
		return fatsCal + carbsCal + protCal;
	}

}
