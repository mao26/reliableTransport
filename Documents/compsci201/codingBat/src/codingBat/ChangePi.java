package codingBat;

public class ChangePi {
	public static void main(String[] args){
		String str = "xpix";
		String str2 = "pipi";
		String str3 = "pip";
		ChangePi cp = new ChangePi();
		System.out.println(cp.changePi(str3));
	}
	public String changePi(String str) {
		  if(str.length() < 2) return str;
		  if(str.substring(0, 2).equals("pi")){
			  return "" + 3.14 + changePi(str.substring(2,str.length()));
		  } else {
			  return str.charAt(0) + changePi(str.substring(1, str.length()));
		  }
	}
}
