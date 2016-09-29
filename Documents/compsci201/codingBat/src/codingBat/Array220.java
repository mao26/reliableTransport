package codingBat;

public class Array220 {
	public static void main(String[] args){
		int[] nums = {0,5,2,21};
		int index = 0;
		Array220 a220 = new Array220();
		System.out.println(a220.array220(nums, index));
	}
	public boolean array220(int[] nums, int index){
		if(index >= nums.length-1) return false;
		if(nums[index+1] == nums[index]*10) return true;
		return array220(nums, index+1);
	}
}
