package codingBat;

public class NestedParen {
	public static void main(String[] args){
		NestedParen np = new NestedParen();
		String str = "(yy)";
		String str2 = "((()()";
		np.nestParen(str2);
	}
	public boolean nestParen(String str) {
		if (str.length() == 0)
			return true;
		if (str.charAt(0) == '(' && str.charAt(str.length()-1) == ')'){
			System.out.println(str.substring(1, str.length() - 1));
			return nestParen(str.substring(1, str.length() - 1));
		}
		return false;

	}
}
