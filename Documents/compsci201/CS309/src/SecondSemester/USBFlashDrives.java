package SecondSemester;

import java.util.Arrays;
import java.util.Scanner;

public class USBFlashDrives {

	public USBFlashDrives() {
		
	}
	
	public static void main(String[] args){
		USBFlashDrives usb = new USBFlashDrives();
		usb.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int fileSize = scan.nextInt();
		int[] usbsAvail = new int[n];
		for(int i = 0; i < n; i++){
			usbsAvail[i] = scan.nextInt();
		}
		Arrays.sort(usbsAvail);
		int numNeeded = 0;
		int spaceUsed = 0;
		int idx = n - 1;
		while(spaceUsed < fileSize){
			numNeeded += 1;
			spaceUsed += usbsAvail[idx];
			idx -= 1;
		}
		System.out.println(numNeeded);
	}

}
