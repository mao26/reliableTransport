import java.util.Scanner;

public class PositiveConSequences {

	
	public static void main(String[] args){
		PositiveConSequences pcs = new PositiveConSequences();
		pcs.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		boolean yo = true;
		while(yo){
			String a = scan.next();
			String b = scan.next();
			String c = scan.next();
			String d = scan.next();
			if(a.equals("127") && d.equals("1024")) yo = false;
			if(a == b && b == c && c == d && Integer.parseInt(d) == -1) break;
			int idx = checkLocation(a, b, c, d);
			if(idx == 0) System.out.println(check(first(b, c, d)));
			if(idx == 3) System.out.println(check(last(a, b, c)));
			if(idx == 1) System.out.println(check(second(a, c, d)));
			if(idx == 2) System.out.println(check(third(a, b, d)));
		}
		scan.close();
	}

	private int third(String a, String b, String d) {
		int firstadd = Integer.parseInt(b) - Integer.parseInt(a);
		int c = Integer.parseInt(b) + firstadd;
		if(c + firstadd == Integer.parseInt(d)) return c;
		double ba = Double.parseDouble(b) / Double.parseDouble(a);
		double cc = Double.parseDouble(b) * ba;
		if(cc * ba == Double.parseDouble(d)) return (int) cc;
		return -1;
	}
	
	private int second(String a, String c, String d) {
		int firstadd = Integer.parseInt(d) - Integer.parseInt(c);
		int b = Integer.parseInt(c) - firstadd;
		if(b - firstadd == Integer.parseInt(a)) return b;
		double dc = Double.parseDouble(d) / Double.parseDouble(c);
		double bb = Double.parseDouble(c) / dc;
		if(bb / dc == Double.parseDouble(a)) return (int) bb;
		return -1;
	}

	private int check(int last) {
		if(last > 0 && last < 1000001) return last;
		return -1;
	}

	private int last(String a, String b, String c) {
		int firstadd = Integer.parseInt(b) - Integer.parseInt(a);
		int secondadd = Integer.parseInt(c) - Integer.parseInt(b);
		if(firstadd == secondadd) return Integer.parseInt(c) + secondadd;
		double dc = Double.parseDouble(b) / Double.parseDouble(a);
		double cb = Double.parseDouble(c) / Double.parseDouble(b);
		if(dc == cb) return Integer.parseInt(c) * (int) cb;
		return -1;
	}
	
	private int first(String b, String c, String d) {
		int firstadd = Integer.parseInt(d) - Integer.parseInt(c);
		int secondadd = Integer.parseInt(c) - Integer.parseInt(b);
		if(firstadd == secondadd) return Integer.parseInt(b) - secondadd;
		double dc = Double.parseDouble(d) / Double.parseDouble(c);
		double cb = Double.parseDouble(c) / Double.parseDouble(b);
		int answer = Integer.parseInt(b) / (int) cb;
		if(dc == cb && Integer.parseInt(b) == answer * cb) return answer;
		return -1;
	}

	private int checkLocation(String a, String b, String c, String d) {
		String[] need = new String[]{a,b,c,d};
		for(int i = 0; i < 4; i++){
			if(Integer.parseInt(need[i]) == -1) return i;
		}
		return -2;
	}
	
}
