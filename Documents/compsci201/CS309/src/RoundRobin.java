import java.util.Scanner;

public class RoundRobin {
	
	public static void main(String[] args){
		RoundRobin rr = new RoundRobin();
		rr.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(true){
			int numberPlayers = scan.nextInt();
			if(numberPlayers == 0) break;
			int turns = scan.nextInt();
			initializeDataSet(numberPlayers, turns);
		}
		scan.close();
	}

	private void initializeDataSet(int numberPlayers, int turns) {
		int[] players = new int[numberPlayers];
		//this will contain the number of turns that players have made
		int idx = 0; // will contain the index of last player that went
		executeTurn(players, idx, turns, numberPlayers);
	}

	private void executeTurn(int[] players, int idx, int turns, int numPlayers) {
		if(checkIfEqual(players)) endGame(players);
		int rounds = turns/numPlayers;
		for(int i = 0; i < players.length; i++){ // doesn't matter our current idx of who started all get flat addition of the number of rounds
			players[i] += rounds;
		}
		if(turns % numPlayers != 0) { // if our mod is zero then it's a flat increase of rounds to all players
			int i = 0, j=0;
			while(j <= turns % numPlayers - 1){
				if(i + idx > numPlayers){
					idx = 0;
					i = 0;
				}
				players[i+idx] += 1;
				j++;
				i++;
			}
//			if(idx + (turns%numPlayers) < players.length -1){ //if we don't have to loop around
//				for(int i = idx; i < turns%numPlayers; i++){
//					players[i] += 1;
//				}
//			} else { // we have to loop around
//				// increment round by 1 for all the players who are after the index until the end of the list
//				for(int i = idx; i < (players.length -1) - idx; i++){
//					players[i] += 1;
//				}
//				//increment our remaining players who are at the beginning of the list
//				int remaining = (turns%numPlayers) - (players.length - 1 - idx); // the number of players left to increment their rounds 
//				for(int i = 0; i < remaining; i++) players[i] += 1;
//			}
		}
		int idxRemove = turns % numPlayers -1 + idx;
		int[] newPlayers = removeLastPlayer(players, idxRemove);
		
			executeTurn(newPlayers, idxRemove, turns, numPlayers - 1 );
	}

	private void endGame(int[] newPlayers) {
		System.out.printf("%d %d\n", newPlayers.length-1, newPlayers[0]);
	}

	private boolean checkIfEqual(int[] newPlayers) { //if all the values of our array are equal to each other return true
		int first = newPlayers[0];
		if(first == 0) return false;
		int idx = 0;
		for(int each: newPlayers){
			if(each == first){ //our array contains all the same value if our first value is repeated throughout every other index
				if(idx == newPlayers.length -1){ // if our last value and all have been equal return true
					return true;
				} else { // all have been equal but not our last value, then continue checking
					idx += 1;
					continue;
				}
			} else { // one of our values is not equal to the rest 
				return false;
			}
		}
		return false;
	}

	private int[] removeLastPlayer(int[] players, int idxRemove) {
		int[] newPlayers = new int[players.length - 1]; 
		int i = 0, j = 0;
		boolean need = true;
		for(int k = 0; k < players.length; k++){
			if(i == idxRemove && need) {
				j += 1;
				need = false;
				//only increment j because we don't add a value to our new array, so our new array needs as it's next value
				//our old arrays next value and this skipping of an idx needs to be kept track off (basically off-setting by one)
			} else {
				newPlayers[i] = players[j];
				i += 1;
				j += 1;
			}
		}
		return newPlayers;
	}
	
}
