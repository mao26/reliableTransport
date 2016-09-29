//package SecondSemester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GukiZRobot {

	public static void main(String[] args){
		GukiZRobot gz = new GukiZRobot();
		gz.run();
	}

	private void run() {
		MyScanner scan = new MyScanner();
		while(true){
		int x1 = scan.nextInt();
		int y1 = scan.nextInt();
		int x2 = scan.nextInt();
		int y2 = scan.nextInt();
		int diff1 = Math.abs(x2 - x1);
		int diff2 = Math.abs(y2 - y1);
		System.out.println(Math.max(diff1, diff2));
		}
	}
	
	 //-----------PrintWriter for faster output---------------------------------
	   public static PrintWriter out;
	       
	   //-----------MyScanner class for faster input----------
	   public static class MyScanner {
	      BufferedReader br;
	      StringTokenizer st;
	  
	      public MyScanner() {
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
	  
	      String nextLine(){
	          String str = "";//&amp;quot;&amp;quot;;
	      try {
	         str = br.readLine();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return str;
	      }
	 
	   }
	
}
