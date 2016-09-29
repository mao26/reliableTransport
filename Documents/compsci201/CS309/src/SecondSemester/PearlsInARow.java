package SecondSemester;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import SecondSemester.Main.MyScanner;

public class PearlsInARow {
	public static void main(String[] args) {
		PearlsInARow p = new PearlsInARow();
		p.run();
	}

	public void run() {
		NewScanner sc = new NewScanner();
		out = new PrintWriter(new BufferedOutputStream(System.out));

		// Start writing your solution here.
		// -------------------------------------

		/*
		 * int n = sc.nextInt(); // read input as integer long k =
		 * sc.nextLong(); // read input as long double d = sc.nextDouble(); //
		 * read input as double String str = sc.next(); // read input as String
		 * String s = sc.nextLine(); // read whole line as String
		 * 
		 * int result = 3*n; out.println(result); // print via PrintWriter
		 */

		// Stop writing your solution here.
		// -------------------------------------
		int N;
		N = sc.nextInt();
		int[] myMap = new int[N];
		for(int i = 0; i < N; i++){
			int n = sc.nextInt();
			myMap[n]++;
		}
		for(int i = 0; i < myMap.length; i++){
			if(myMap[i] > 1) System.out.println("" + i + " " + myMap[i]);
		}
		
		
		out.close();
	}
	
	class Number{
		int number;
		int times;
	}

	// -----------PrintWriter for faster output---------------------------------
	public static PrintWriter out;

	// -----------MyScanner class for faster input----------
	public static class NewScanner {
		BufferedReader br;
		StringTokenizer st;

		public NewScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";// &amp;quot;&amp;quot;;
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

	}
}
