package codingBat;

public class GroupSum5 {

	public static void main(String[] args) {
		GroupSum5 gs5 = new GroupSum5();
		int start = 0;
		int target = 19;
		int[] nums = { 2, 5, 1, 10, 4 };
		System.out.println(gs5.groupSum5(start, nums, target));
	}

	public boolean groupSum5(int start, int[] nums, int target) {
		if (start >= nums.length)
			return target == 0;
		int diff = target - counter5(start, nums);
		if (groupSum(start, nums, diff)) {
			return true;
		}
		return false;
	}

	public boolean groupSum(int start, int[] nums, int target) {
		if (start >= nums.length)
			return target == 0;
		if (groupSum(start + 1, nums, target - nums[start]))
			return true;
		if (groupSum(start + 1, nums, target))
			return true;
		return false;
	}

	public int counter5(int start, int[] nums) {
		int counter = 0;
		if (nums[start] % 5 == 0) {
			counter += nums[start];
			nums[start] = 0;
			if (start + 1 < nums.length) {
				if (start + 1 == 1) {
					nums[start + 1] = 0;
				}
			}
		}
		if (start + 1 < nums.length) {
			counter += counter5(start + 1, nums);
		}

		return counter;
	}

}
