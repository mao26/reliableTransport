
public class AccessList {
	public static String mayAccess(int[] rights, int minPermission){
		String blank = "";
		for(int i = 0; i < rights.length; i++){
			if(rights[i] > minPermission-1){
				blank += "A";
			} else {
			blank += "D";
			}
		}
		System.out.println(blank);
		return blank;
	}
	public static void main(String[] args){
		int[] List = {0,1,2,3,4,5};
		mayAccess(List, 2);
	}
}
