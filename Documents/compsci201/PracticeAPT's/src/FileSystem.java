
public class FileSystem {
	public static void main(String[] args){
		int[] fileBytes = {1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000, 1000000000, 1000000000,
				 1000000000, 1000000000};
		int blockBytes = 1048576;
		System.out.println("value needed " + 50017075200l);
		FileSystem fs = new FileSystem();
		fs.findUsed(fileBytes, blockBytes);
	}
	public long findUsed(int[] fileBytes, int blockBytes) {
		long toKeepTrack = 0;
		for(int i = 0; i < fileBytes.length;i++){
			long count = 0; 
			while (count < fileBytes[i]){
				count += blockBytes;
			}
			toKeepTrack += count;
		}
		System.out.println(toKeepTrack);
		return toKeepTrack;
	}
}
