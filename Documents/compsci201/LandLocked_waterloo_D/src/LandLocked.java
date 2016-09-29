import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class LandLocked {
	private int[] id;
	private int[] sz;

	// set id of each object in the array equal to itself
	// N array accesses
	public void createUF(int N) {
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	// chase parent pointers until you reach the root
	public int root(int i) {
		while (i != id[i]) {
			id[i] = id[id[i]];
			i = id[i];
		}
		return i;
	}

	// check that p and q have the same root--- also what find is for weighted
	// quick union
	public boolean connectedQuickUnion(int p, int q) {
		return root(q) == root(p);
	} // this is also the algorithm used for weighted union find
	
	public void unionWeightedQuickUnion(int p, int q){ // takes constant time given root == depth at most of any node in the tree is log N
		int i = root(p);
		int j = root(q);
		if(i == j) return;
		if(sz[i] < sz[j]){
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
	}

	public static void main(String[] args) {
		LandLocked ll = new LandLocked();
		ll.run();

	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		while (scan.hasNext()) {
			String line = scan.nextLine();
			for (int i = 0; i < line.length(); i++) {

			}
		}
		scan.close();
	}

	public static void readin() {

		// File file = new File("");
		// Scanner scanner = new Scanner(file);
		File inFile = new File("c:/Users/mario_oliver93/Desktop/ICPC");

		FileReader ins = null;

		try {
			ins = new FileReader(inFile);

			int ch;
			while ((ch = ins.read()) != -1) {
				System.out.println((char) ch);

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				ins.close();
			} catch (Exception e) {
			}
		}

	}
}
