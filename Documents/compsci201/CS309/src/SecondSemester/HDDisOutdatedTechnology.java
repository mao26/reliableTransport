package SecondSemester;

import java.util.Arrays;
import java.util.Scanner;

public class HDDisOutdatedTechnology {

	public static void main(String[] args) {
		HDDisOutdatedTechnology hdd = new HDDisOutdatedTechnology();
		hdd.run();
	}

	private void run() {
		//int jumps = 0;

		Scanner scan = new Scanner(System.in);
		while(scan.hasNextInt()){
			int N = scan.nextInt();
			Integer[] nums = new Integer[N];
			for(int i = 0; i < N; i++){
				int num = scan.nextInt();
				//use zero index
				//pretend N = 4
				//nums = 4 1 3 2 --> use zero index so subtract one 3 0 2 1
				// then grab the indexes and put them into a new array 1 3 2 0
				// if 1 3 2 0 = a, then a[i] is where i is
				// then subtract a[i] - a[i+1] and grab absolute value through the array
				nums[i] = num - 1;
			}
			int[] grabIdx = grabIdx(N, nums);
			int subtract = 0;
			for(int i = 0; i < N-1; i++){
				subtract += Math.abs(grabIdx[i] - grabIdx[i+1]);
			}
			System.out.println(subtract);
		}
	}

	private int[] grabIdx(int N, Integer[] nums) {
		int[] idxs = new int[N];
		for(int i = 0; i < N; i++){
			idxs[i] = Arrays.asList(nums).indexOf(i);
		}
		return idxs;
	}

}
