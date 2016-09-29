package codingBat;

public class GroupSum6 {
	public boolean groupSum6(int start, int[] nums, int target){
		if(start >= nums.length) return target == 0;
		int diff = target - (6* helper(start, nums));
		if(groupSum(start, nums, diff)){
			return true;
		}
		return false;
	}
	public int helper(int start, int[]nums){
		int count = 0;
		if(nums[start] == 6){
			count++;
			nums[start] = 0;
		}
		if(start +1 < nums.length){
			count += helper(start+1, nums);
		}
		return count;
	}
	public boolean groupSum(int start, int[] nums, int target){
		if(start >= nums.length) return target == 0;
		if(groupSum(start+1, nums, target - nums[start])) return true;
		if(groupSum(start +1, nums, target)) return true;
		return false;
	}
	
	public static void main(String[] args){
		GroupSum6 gs6 = new GroupSum6();
		int start = 0;
		int[] nums = {5,6,2};
		int target = 8;
		System.out.println(gs6.groupSum6(start, nums, target));
	}
}
