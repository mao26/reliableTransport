
import java.util.ArrayList;
import java.util.Scanner;

public class Numbers {

	static ArrayList<Integer> potentialPrimes;
	static ArrayList<Integer> hasGCD;

	public static void main(String[] args) {
		Numbers sn = new Numbers();
		sn.run();

	}

	private void run() {

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextInt()) {
			// System.out.println(scanner.nextInt());
			ArrayList<Integer> nums = new ArrayList<Integer>();
			int value = scanner.nextInt();
			for (int i = 0; i < value; i++) {
				int a = scanner.nextInt();
				nums.add(a);
			}
			potentialPrimes = new ArrayList<>();
			hasGCD = new ArrayList<>();
			greatestCommonFactor(nums);
		}
		scanner.close();
	}

	public int gcd(int a, int b) {
		while (a != 0 && b != 0) // until either one of them is 0
		{
			int c = b;
			b = a % b;
			a = c;
		}
		return a + b; // either one is 0, so return the non-zero value
	}

	void populatePotentialPrimes(int a, int b, int result) {
		if (result == 1) {
			if (!potentialPrimes.contains(a) || !potentialPrimes.contains(b)) {
				potentialPrimes.add(Integer.max(a, b));
			}
		}
		if (result != 1) {
			hasGCD.add(a);
			hasGCD.add(b);
		}
		potentialPrimes.removeAll(hasGCD);
	}

	/*void populatePotentialPrimes(int a, int b, int result) {
		if (result == 1) {
			if (!potentialPrimes.contains(a) || !potentialPrimes.contains(b)) {
				potentialPrimes.add(Integer.max(a, b));
			}
		}
		if (result != 1) {
			hasGCD.add(a);
			hasGCD.add(b);
			
		}
		for (Integer i : potentialPrimes) {
			if (hasGCD.contains(i)) {
				potentialPrimes.remove(i);
				break;
			}
		}
	}*/

	public void greatestCommonFactor(ArrayList<Integer> nums) {
		for (int i = 0; i < nums.size() - 1; i++) {
			for (int j = i + 1; j < nums.size(); j++) {
				int a = gcd(nums.get(i), nums.get(j));
				// System.out.printf("Result=%d with a=%d b=%d\n", nums.get(i),
				// nums.get(j), a);
				populatePotentialPrimes(nums.get(i), nums.get(j), a);
			}
		}
		int Result = potentialPrimes.get(potentialPrimes.size() - 1);
		System.out.println(Result);
	}

}
