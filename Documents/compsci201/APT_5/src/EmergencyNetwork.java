import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EmergencyNetwork {
	public static void main(String[] args){
		int[] bosses = {-1, 0, 0, 2, 2};
		EmergencyNetwork en = new EmergencyNetwork();
		en.lagTime(bosses);
	}
	public int lagTime(int[] bosses) {
		int count = 0; 
		int time = 0;
		int boss = -1;
		Map<Integer, Integer> listOfBosses = new HashMap<Integer, Integer>();
		ArrayList<ArrayList<Integer>> calling = new ArrayList<ArrayList<Integer>>();
		for(int i = 1; i < bosses.length; i++){
			while(count == bosses[i]){
				if(count == bosses[i+1]){
					
				}
			}
		}
		return 0;
	}
}
