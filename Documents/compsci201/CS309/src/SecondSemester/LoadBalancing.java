
package SecondSemester;

import java.util.Arrays;
import java.util.Scanner;

public class LoadBalancing {

	public static void main(String[] args) {
		LoadBalancing lb = new LoadBalancing();
		lb.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] servers = new int[n];
		for (int i = 0; i < n; i++) {
			servers[i] = scan.nextInt();
		}
		int end = n - 1;
		int totalMoves = 0;
		iterate(servers, end, n, totalMoves);
	}

	private void iterate(int[] servers, int endIdx, int length, int totalMoves) {
		Arrays.sort(servers);
		int thisIterationsMoves = 0;
		for (int i = 0; i < length; i++) {
			if (i > endIdx)
				break;
			if (i == endIdx) {
				isMiddleServerUpdated(servers, thisIterationsMoves, i);
				break;
			}
			int diff = Math.abs(servers[i] - servers[endIdx]);
			if (diff <= 1) {
				break;
			} else {
				totalMoves += updateTwoServers(i, endIdx, servers, thisIterationsMoves, diff);
				endIdx--;
			}
		}
		if (thisIterationsMoves > 0) {
			// means we had to make a change in our sort
			// so call our function again
			totalMoves += thisIterationsMoves;
			iterate(servers, endIdx, length, totalMoves);
		} else {
			System.out.println(totalMoves);
		}
	}

	private int updateTwoServers(int i, int endIdx, int[] servers, int thisIterationsMoves, int diff) {
		int middleVal = (servers[i] + servers[endIdx]) / 2;
		thisIterationsMoves += (Math.abs(servers[endIdx] - middleVal));
		if (diff % 2 == 0) {
			servers[i] = middleVal;
			servers[endIdx] = middleVal;
		} else {
			thisIterationsMoves--;
			servers[i] = middleVal;
			servers[endIdx] = middleVal;
		}
		return thisIterationsMoves;
	}

	private void isMiddleServerUpdated(int[] servers, int thisIterationsMoves, int i) {
		if (servers[0] != servers[i]) {
			thisIterationsMoves += Math.abs(servers[i] - servers[0]);
			servers[i] = servers[0];
		}
	}

}
/*
18606 18717 18475 19009 17295 19836 18193 17864 19207 18270 1415 225 2916 22 2014 368 766 19092 132 3199 839 19190 16369 172 118 19766 19834 19498 282 16319 1196 16686 19042 725 3094 19260 17400 16842 13900 1096 1215 18587 653 19826 207 19823 19206 15707 19682 15816 2690 3218 19786 19749 6375 2635 18004 18844 1523 19384 17928 19787 2771 17060 405 17942 18835 379 19043 63 18608 12158 451 818 19726 4200 255 2225 649 19586 19976 19742 19940 5368 17914 1926 18174 18487 2731 78 633 2565 2392 19601 19138 4853 19440 17745 18296 314 390738
[22, 63, 78, 118, 132, 172, 207, 225, 255, 282, 314, 368, 379, 405, 451, 633, 649, 653, 725, 766, 818, 839, 1096, 1196, 1215, 1415, 1523, 1926, 2014,
 2225, 2392, 2565, 2635, 2690, 2731, 2771, 2916, 3094, 3199, 3218, 4200, 4853, 5368, 6375, 12158, 13900, 15707, 15816, 16319, 16369, 16686, 16842, 
 17060, 17295, 17400, 17745, 17864, 17914, 17928, 17942, 18004, 18174, 18193, 18270, 18296, 18475, 18487, 18587, 18606, 18608, 18717, 18835, 18844,
  19009, 19042, 19043, 19092, 19138, 19190, 19206, 19207, 19260, 19384, 19440, 19498, 19586, 19601, 19682, 19726, 19742, 19749, 19766, 19786, 19787,
   19823, 19826, 19834, 19836, 19940, 19976]
 */
