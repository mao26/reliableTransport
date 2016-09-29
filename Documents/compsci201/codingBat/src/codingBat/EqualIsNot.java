package codingBat;

public class EqualIsNot {
	public static void main(String[] args){
		String str = "This is not";
		EqualIsNot ein = new EqualIsNot();
		ein.equalIsNot(str);
	}
	public boolean equalIsNot(String str) {
		int isCount = 0;
		int notCount = 0;
		int strlen = str.length();
		String is = "is";
		String not = "not";
		for (int i = 0; i <= str.length() - 2; i++) {
			System.out.println(str.substring(i, i+2));
			if ( str.substring(i, i + 2).equals( is)) {
				isCount++;
				System.out.println("isCount= " + isCount);
			}
		}
		for (int i = 0; i <= str.length() - 3; i++) {
			System.out.println(str.substring(i, i+ 3));
			if ( str.substring(i, i + 3).equals(not)) {
				notCount++;
				System.out.println("notCount =" + notCount);
			}
		}
		if (notCount == isCount) {
			return true;
		} else {
			return false;
		}

	}
}
