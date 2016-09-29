package codingBat;

public class StrCopies {
	public static void main(String[] args) {
		StrCopies sc = new StrCopies();
		System.out.println("1: should return true");
		System.out.println("1: returns: " + sc.strCopies("dogcatdogcat", "dog", 2));
		System.out.print("no. two should return false, but returns ----- ");
		System.out.println(sc.strCopies("iiijjj", "ii", 2));
	}

	public int strCount(String str, String sub) {
		if (str.length() < sub.length())
			return 0;
		if (str.substring(0, sub.length()).equals(sub))
			return 1 + strCount(str.substring(1, str.length()), sub);
		return strCount(str.substring(1, str.length()), sub);
	}

	public boolean strCopies(String str, String sub, int n) {
		if(strCount(str,sub) >= n) return true;
		return true;
	}
}
