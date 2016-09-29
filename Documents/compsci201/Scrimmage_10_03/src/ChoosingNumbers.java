import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class ChoosingNumbers {

	static HashMap<Integer, ArrayList<Integer>> hash;
	static ArrayList<Integer> potentialPrimes;

	public static void main(String[] args) {
		ChoosingNumbers sn = new ChoosingNumbers();
		sn.run();

	}

	private void run() {

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextInt()) {
			//System.out.println(scanner.nextInt());
			int value = scanner.nextInt();
			ArrayList<Integer> nums = new ArrayList<Integer>();
			ArrayList<Integer> nums1 = new ArrayList<Integer>();
			
			for(int i = 0; i < value; i++){
				int a = scanner.nextInt();
				nums.add(a);
			}
			/*if(nums.contains(2)){
				for(int each: nums){
					if(!(each % 2 == 0)){
						nums1.add(each);
					}
				}
			}*/
			//System.out.println(nums.toString());
			//System.out.println(nums.toString());
			hash = new HashMap<Integer, ArrayList<Integer>>();
			newCommon(nums);
			greatestCommonFactor(nums);
		}
		scanner.close();
	}

	private void newCommon(ArrayList<Integer> nums) {
		// TODO Auto-generated method stub
		
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

	public void populateMap(Integer key, Integer value){
		if(!hash.containsKey(key)){
			ArrayList<Integer> valueArray = new ArrayList<>();
			valueArray.add(value);
			hash.put(key, valueArray);
		} else {
			ArrayList<Integer> valueArray = hash.get(key);
			valueArray.add(value);
			hash.put(key, valueArray);
		}
	}
	
	public int findPrime(){
		ArrayList<Integer> guy = new ArrayList<Integer>();
		guy.addAll(hash.keySet());
		Collections.sort(guy);
		Collections.reverse(guy);
		for(Integer k : guy){
			ArrayList<Integer> nums = hash.get(k);
			Collections.sort(nums);
			int need = Collections.max(nums);
			if(need == 1){
				System.out.println(k);
				return k;
			}
		}
		return 0;
	}
	
	public void greatestCommonFactor(ArrayList<Integer> nums) {
		for (int i = 0; i < nums.size() - 1; i++) {
			for (int j = i + 1; j < nums.size(); j++) {
				int a = gcd(nums.get(i), nums.get(j));
				
				System.out.printf("first val is %d second is %d and gcd is %d\n", nums.get(i), nums.get(j), a);
				populateMap(nums.get(i), a);
				populateMap(nums.get(j), a);
			}

		}
		findPrime();
		//eSystem.out.println(hash.toString());
	}

}
