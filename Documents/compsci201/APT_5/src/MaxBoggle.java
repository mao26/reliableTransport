import java.util.*;

public class MaxBoggle {
	public static void main(String[] args) { 
		String[] m = { "AAAA", "AAAA", "AAAA", "AAAA" };
		String[] words = { "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" };
		MaxBoggle mp = new MaxBoggle();
		mp.maxPoints(m, words);
	}

	public long maxPoints(String[] matrix, String[] words) { // total points
																// count method
		long total = 0;
		for (String word : words) {
			total += maxPoints(matrix, word);
		}
		return total % (10000000000000l);

	}

	private long maxPoints(String[] matrix, String word) { // will give the
															// points for each
															// word
		char[][] letters = new char[matrix.length][matrix.length];
		long[][] matrix1 = new long[matrix.length][matrix.length];
		long[][] matrix2 = null;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length(); j++) {
				letters[i][j] = matrix[i].charAt(j);
			}
		}
		for (int k = 0; k < word.length(); k++) {
			matrix2 = matrix1;
			matrix1 = new long[matrix.length][matrix.length];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length(); j++) {
					if (k == 0) {
						if (letters[i][j] == word.charAt(k)) {
							matrix1[i][j] = 1l;
						}
					} else {
						long total = 0l;
						if (letters[i][j] == word.charAt(k)) {
							int a = i;
							int b = j - 1;
							if (b >= 0) {
								total += matrix2[a][b];
							}
							a = i - 1;
							b = j - 1;
							if (a >= 0 && b >= 0) {
								total += matrix2[a][b];
							}
							a = i - 1;
							b = j;
							if (a >= 0) {
								total += matrix2[a][b];
							}
							a = i - 1;
							b = j + 1;
							if (a >= 0 && b < matrix.length) {
								total += matrix2[a][b];
							}
							a = i;
							b = j + 1;
							if (b < matrix.length) {
								total += matrix2[a][b];
							}
							a = i + 1;
							b = j + 1;
							if (b < matrix.length && a < matrix.length) {
								total += matrix2[a][b];
							}
							a = i + 1;
							b = j;
							if (a < matrix.length) {
								total += matrix2[a][b];
							}
							a = i + 1;
							b = j - 1;
							if (a < matrix.length && b >= 0) {
								total += matrix2[a][b];
							}
							matrix1[i][j] = total % 10000000000000l;
						}
					}
				}
			}

		}
		long total = 0l;
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix1[i].length; j++) {
				total += matrix1[i][j];
			}
		}
		return (total * (long)(word.length() * word.length())) % (10000000000000l);
	}
}
