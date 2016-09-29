public class ChangeXY {
	public static void main(String[] args) {
		String str = "xxhixx";
		ChangeXY cxy = new ChangeXY();
		cxy.changeXY(str);
	}

	public String changeXY(String str) {
		if(str.length() == 0){
			System.out.println(str);
			return str;
		}
		if(str.charAt(0) == 'x'){
			return "y" + changeXY(str.substring(1,str.length()));
		}
		return str.charAt(0) + changeXY(str.substring(1,str.length()));
		
	}
}
