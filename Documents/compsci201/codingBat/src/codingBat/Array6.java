package codingBat;

public class Array6 {
	public static void main(String[] args){
		int[] nums = {1, 3, 4, 0};
		int index = 0;
		Array6 a6 = new Array6();
		System.out.println(a6.array6(nums, index));
	}
	
	public boolean array6(int[] nums, int index) {
		  if(index >= nums.length) return false; 
		  if(nums[index] == 6) return true;
		  return array6(nums, index+1);
	}
}
