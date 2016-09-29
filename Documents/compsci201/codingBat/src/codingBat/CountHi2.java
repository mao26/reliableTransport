package codingBat;

public class CountHi2 {
	public static void main(String[] args){
		CountHi2 ch2 = new CountHi2();
		String str = "ahixhihi";
		String str2 = "ahibhi";
		String str3 = "xhixhi";
		System.out.println(ch2.countHi2(str3));;
	}
	public int countHi2(String str) {
		if (str.length() < 2)
			return 0;
		if (str.substring(0, 2).equals("hi"))
			return 1 + countHi2(str.substring(2, str.length()));
		if (str.charAt(0) == 'x') {
			if(str.length() > 2){
				if (str.substring(1, 3).equals("hi")){
					return countHi2(str.substring(3, str.length()));
				}
			}
		}
		return countHi2(str.substring(1, str.length()));

	}
}
