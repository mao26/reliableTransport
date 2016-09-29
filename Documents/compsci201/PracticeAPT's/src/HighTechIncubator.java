import java.util.ArrayList;


public class HighTechIncubator {
	public static void main(String[] args){
		String[] location1 = {"JOHN","JOHN","FRED","PEG"};
		String[] location2 = {"PEG","GEORGE"};
		String[] location3 = {"GEORGE","DAVID"};
		HighTechIncubator hti = new HighTechIncubator();
		hti.shameList(location1, location2, location3);
	}
	public String[] shameList(String[] location1, String[] location2, String[] location3) {
		String[] results;
		ArrayList<String> need = new ArrayList<String>();
		for(String name : location1){
			for(int i = 0; i < location2.length; i++){
				if(name == location2[i]){
					need.add(location2[i]);
				}
			}
		}
		for(String name : location2){
			for(int i = 0; i < location3.length; i++){
				if(name == location3[i]){
					need.add(location3[i]);
				}
			}
		}
		for(String name : location1){
			for(int i = 0; i < location3.length; i++){
				if(name == location3[i]){
					need.add(location3[i]);
				}
			}
		}
		results = new String [need.size()];
		for(int i = 0; i < need.size(); i++){
			results[i] = need.get(i);
		}
		return results;
	}
}
