public class FileSystem {
	public long findUsed(int[] fileBytes, int blockBytes) {
		long blockCount = 0;
		for (int i = 0; i < fileBytes.length; i++) {
			if (fileBytes[i] != 0) {
				int count = fileBytes[i]/ blockBytes;
				if(fileBytes[i] % blockBytes != 0){
					count++;
				}
				blockCount += count; 
			}
		}
		
		return blockCount * blockBytes;
	}
	public static void main(String[] args) {
		int[] p = {600, 800, 700, 1200, 213, 0};
		FileSystem f = new FileSystem();
		long count = f.findUsed(p, 2);
		System.out.println("totalSize = " + count);
	}
}