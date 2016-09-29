public class CountHi {
	public int countHi(String str) {
		int result = 0;

		if (str.length() == 0)
			return 0;
		if (str.length() >= 2) {
			if (str.substring(0, 2).equals("hi")) {
				result++;
			}
			result += countHi(str.substring(1, str.length()));
		}
		return result;
	}

	public static void main(String[] args) {
		String str = "xhixhix";
		CountHi ch = new CountHi();
		System.out.println(ch.countHi(str));
	}
}
