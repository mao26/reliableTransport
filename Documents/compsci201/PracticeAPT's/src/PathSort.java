import java.util.ArrayList;
import java.util.Arrays;


public class PathSort {
	
	public static void main(String[] args){
		String[] dire = {"/","/usr/","/usr/local/","/usr/local/bin/","/games/","/games/tetris/","/hw/","/temp/downloads/"};
		PathSort ps = new PathSort();
		System.out.println(ps.sortPath(dire));
	}
	
	public String[] sortPath(String[] dire) {
		ArrayList<String[]> listOfPath = new ArrayList<String[]>();
		SString[] listOfP = new SString[dire.length];
		for(int i = 0; i < dire.length; i++){
			SString object = new SString();
			String[] splitItem = dire[i].split("/");
			object.count = splitItem.length;
			if(splitItem.length == 0){
				object.string = "";
			}
			else{
				object.string = splitItem[splitItem.length-1];
			}
			listOfP[i] = object;
		}
		for(int i = 0; i < listOfP.length; i++){
			System.out.println("path ends in str " + listOfP[i].string + " and is of list length " + listOfP[i].count);
		}
		Arrays.sort(listOfP);
		for(int i = 0; i < listOfP.length; i++){
			System.out.println(listOfP[i].string);
		}
		
//		for(int i = 0; i < dire.length; i++){
//			String[] apple = dire[i].split("/");
//			listOfPath.add(dire[i].split("/"));
////			listOfP[i] = dire[i].split("/");
//		}
//		ArrayList<SString> result = new ArrayList<SString>();
//		for(int j = 0; j < listOfPath.size(); j++){
////			System.out.println(each);
//			SString object = new SString();
//			object.count = listOfPath.get(j).length;
//			if(listOfPath.get(j).length == 0){
//				String need = "";
//				System.out.println(need);
//				object.string = need;
//			} else {
//				String need = listOfPath.get(j)[listOfPath.get(j).length-1];
//				System.out.println(need);
//				object.string = need;
//			}
//			
//		}
		String[] result = {};
		return result;
	}
	public class SString implements Comparable<SString>{
		int count; 
		String string;
		
		@Override
		public int compareTo(SString o) {
			if(this.count > o.count) return 1;
			if(this.count < o.count) return -1;
			else{
				return  (this.string.compareTo(o.string));
			}
		}
		
	}

}
