import java.util.ArrayList;


public class CricketWorldCup {
	public static void main (String[] args){
		
		String[] teams = {"INDIA", "AUSTRALIA", "PAKISTAN", "WEST INDIES",
				 "SOUTH AFRICA", "NEW ZEALAND", "SRI LANKA", "ENGLAND"};
		String[] eliminatedBy = {"", "INDIA", "INDIA", "PAKISTAN",
				 "NEW ZEALAND", "SRI LANKA", "INDIA", "SRI LANKA"};
		CricketWorldCup cwc = new CricketWorldCup();
		cwc.standings(teams, eliminatedBy);
		
	}
	
	
	public String[] standings(String[] teams, String[] eliminatedBy) {
		Frequency[] freq = new Frequency[teams.length];
		int i = 0;
		for (String team: teams){
			freq[i] = new Frequency();
			freq[i].team = team;
			for (String elimTeam: eliminatedBy){
				if(elimTeam.equals(team)){
					freq[i].frequency++;
				}
			}
			i++;
		}
		String[] winners = new String[teams.length];
		int max = 0;
		int idx = -1;
		for (int r = 0; r < freq.length; r++){
			if(freq[r].frequency > max){
				max = freq[r].frequency;
				idx = r;
			}
		}
		for (int k = 0; k < freq.length; k++){
			if(freq[k].frequency == max-1){
				winners[1] = freq[k].team;
			}
		}
		winners[0] = freq[idx].team;
		
		int rankCount =2; // already added two teams, so make this start with 2
		for(int r = max-2; r >= 0; r--){ // picks the maximun count of remaining teams (the max appearances of the loosing teams)
			ArrayList<Frequency> tieList = new ArrayList<Frequency>();
			for(int k = 0; k<freq.length; k++){ // this loop makes our tie list
				int maxRank = 0; 
				int index = 0; 
				if(freq[k].frequency == r){
					tieList.add(freq[k]);
				}
			}
			//sort tie list by who beat them and if they have a higher rank
			boolean madeSwap = true;
			while(madeSwap){
				madeSwap = false;
				for(int j = 1; j < tieList.size(); j++){
					if(rankOfEliminator(tieList.get(j).team, eliminatedBy, teams, winners) < rankOfEliminator(tieList.get(j-1).team, eliminatedBy, teams, winners)){
						Frequency temp = tieList.get(j);
						tieList.set(j, tieList.get(j-1));
						tieList.set(j-1, temp);
						madeSwap = true;
					}
				}
			}
			//add tieList to winners array in order (from greatest to smallest)
			for(int j = 0; j<tieList.size();j++){
				winners[rankCount++] = tieList.get(j).team;
			}
		}
		
		return winners; 
	}
	public class Frequency {
		int frequency = 0;
		String team;
		
	}
	
	public int rankOfEliminator(String team, String[] eliminatedBy, String[] teams, String[] winners){
		String eliminator = null;
		for(int i = 0; i < teams.length; i++){
			if(teams[i].equals(team)){
				eliminator = eliminatedBy[i];
				break;
			}
		}
		for(int i = 0; i < winners.length;i++){
			if(eliminator.equals(winners[i])){
				return i;
			}
		}
		return -1;
	}
}
