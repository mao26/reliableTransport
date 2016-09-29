public class ChessChampionship {
	public static void main(String[] args) {
		String[] games = { "-DD", "L-L", "WD-" };
		String[] games2 = {"-WW", "W-W", "WW-"};
		String[] games3 = {"-DWWD", "L-WLL", "DD-WD", "DDL-L", "DDLL-"};
		for (int i = 0; i < 5; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i = 0; i < games.length; i++) {
			System.out.println("|" + games[i] + "|");
		}
		for (int i = 0; i < 5; i++) {
			System.out.print("-");
		}
		System.out.println();
		ChessChampionship cc = new ChessChampionship();
		cc.points(games3);
	}

	public int[] points(String[] games) {
		// you write code here
		System.out.println("number of players " + games.length);
		int[] points = new int[games.length];
		for (int i = 0; i < games.length; i++) {
			for (int j = 0; j < games[i].length(); j++){
				String ch = games[i].substring(j,j+1);
				if(ch.equals( "W")){
					points[i] += 3;
					points[j] += 0;
				}
				if(ch.equals( "D")){
					points[i] += 1;
					points[j] += 1;
				}
				if(ch.equals("L")){
					points[i] += 0;
					points[j] += 3;
				}
			}
		}
		for(int k = 0; k < points.length; k++){
			System.out.println(points[k]);
		}
		return points;
	}
}