
public class BSTpermutations {
	
	long[] memo; 
	
	public long howMany(int[] values) {
		int n = values.length;
		memo = new long[n+1];
		return count_memo(n); // since only n matters
		
	}

	public long count(int n) {
		if (n < 2) {
			return 1;
		} else {
			long ret = 0;
			for (int l = 0; l < n; l++) {
				long left = count(l);
				long right = count(n - l - 1);
				ret += left * right;
			}
			return ret;
		}
	}
	
	public long count_memo(int n) {
		if (n == 0) {
			memo[0] = 1;
			return 1;
		} else if(n == 1){
			memo[1] = 1;
			return 1;
		} else if(memo[n] != 0){
			return memo[n];
		} else {
			long ret = 0;
			for (int l = 0; l < n; l++) {
				long left = count_memo(l);
				memo[l] = left;
				if(left != 0){
					long right = count_memo(n - l - 1);
					memo[n-l-1] = right;
					ret += left * right;
				}
				
			}
			memo[n] = ret;
			return ret;
		}
	}
	
	public static void main(String[] args){
		BSTpermutations BST = new BSTpermutations();
		int[] count = new int[200];
		System.out.println("The number of binaryTrees with " + count.length + " nodes is: " + BST.howMany(count));
 	}
}