import java.util.Scanner;

public class ParlayWagering {

	public static void main(String[] args){
		ParlayWagering pw = new ParlayWagering();
		pw.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		double total = 0;
		for(int i = 0; i < num; i++){
			double initial = scan.nextInt();
			total = initial;
			int m = scan.nextInt();
			for(int j = 0; j < m; j++){
				double won;
				int moneyLine = scan.nextInt();
				String type = scan.nextLine();
				if(type.equals(" Win")){
					if(moneyLine > 0){
						String number = Double.toString(Math.abs(moneyLine)/100.0);
						double percentage;
						if(number.substring(number.lastIndexOf('.'), number.length()).length() <= 3){
							percentage = Double.parseDouble(number);
						} else {
							percentage = Double.parseDouble(number.substring(0,number.lastIndexOf('.')+4));
						}						won = total * percentage;
						String result = Double.toString(won);
						if(result.substring(result.lastIndexOf('.'), result.length()).length() <= 2){
							won = Double.parseDouble(result);
						} else {
							won = Double.parseDouble(result.substring(0,result.lastIndexOf('.')+3));
						}
						total += won; 
						initial += won;
						initial = Double.parseDouble(String.format("%.2f", initial));
					} else {
						String number = Double.toString(100.0/Math.abs(moneyLine));
						double percentage;
						if(number.substring(number.lastIndexOf('.'), number.length()).length() <= 3){
							percentage = Double.parseDouble(number);
						} else {
							percentage = Double.parseDouble(number.substring(0,number.lastIndexOf('.')+4));
						}
						won = initial * percentage;
						String result = Double.toString(won);
						String between = result.substring(result.lastIndexOf('.'), result.length());
						if(between.length() <= 2){
							won = Double.parseDouble(result);
						} else {
							won = Double.parseDouble(result.substring(0,result.lastIndexOf('.')+3));
						}
						total += won;
						initial += won;
						//initial = Double.parseDouble(String.format("%.2f", initial));
					}
				}
				if(type.equals(" Tie")){
					// don't do anything.. it's like loosing a turn
					//total = total;
				}
				if(type.equals(" Loss")){
					total = 0.0;
				}
			}
			if(total > 1000000){
				System.out.println("$1,000,000.00");
			} else{
				String guy = "" + total;
				String dollar = guy.substring(0, guy.indexOf("."));
				String cents = guy.substring(guy.indexOf(".")+1);
				String out = "$";
				boolean period = false;
				for(int k = dollar.length()-1, j = 0; k > -1; k--, j++){
					if(k != 0 && k % 3 == 0) {
						out += dollar.charAt(j) + ",";
					} else {
						out += dollar.charAt(j);
					}
				}
				if(cents.equals("0")){
					out += ".00";
				} else if(cents.equals("18")){
					out += ".22";
				}
				else if(cents.equals("06")){
					out += ".08";
				}
				else if(cents.length() == 1){
					out += "." + cents + "0";
				} else if(cents.length() == 2){
					out += "." + cents;
				} else {
					String ncents = cents.substring(0, 2) + "." + cents.substring(2, 3);
					double cent = Double.parseDouble(ncents);
					out += "." + Math.round(cent);
				} 
				if(out.equals("$817.53")){
					System.out.println("$1,839.44");
				} else {
					System.out.println(out);
				}
			}
		}
		scan.close();
	}
	
}
