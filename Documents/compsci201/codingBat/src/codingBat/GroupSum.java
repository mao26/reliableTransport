package codingBat;

public class GroupSum {
	public boolean groupSum(int start, int[] nums, int target){
		if(start >= nums.length) return target == 0;
		System.out.println("length of nums " + nums.length);
		System.out.println(target - nums[start]);
		if(groupSum(start+1, nums, target - nums[start])) return true;
		System.out.println("nums[start] = " + nums[start] + " target " + target);
		if(groupSum(start+1, nums, target)) return true;
		return false;
	}
	public static void main(String[] args){
		int start = 0;
		int[] nums = {2,4,8};
		int target = 10;
		GroupSum gs = new GroupSum();
		System.out.println(gs.groupSum(start, nums, target));
	}
}
