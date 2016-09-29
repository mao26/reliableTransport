package codingBat;

public class StringClean {
	public static void main(String[] args){
		StringClean sc = new StringClean();
		String str = "abbazzzyyyyy";
		System.out.println(sc.stringClean(str));
	}
	public String stringClean(String str) {
		if (str.length() == 1)
			return str;
		if (str.charAt(0) == str.charAt(1))
			return stringClean(str.substring(1, str.length()));
		return str.charAt(0) + stringClean(str.substring(1, str.length()));
	}
}
