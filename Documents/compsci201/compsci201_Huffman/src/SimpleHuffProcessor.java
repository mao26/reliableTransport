import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class SimpleHuffProcessor extends TreeHuffProcessor {

//	Map<Integer, String> binary = new HashMap<Integer, String>();
//	String store_tree = "";
//	TreeNode huff_tree = null;
//	String encodeStr = "";
	// String decodeStr = "";
	private HuffViewer myViewer;
	private int needFreq;

	@Override
	public int preprocessCompress(InputStream in) throws IOException {
		// throw new IOException("preprocess not implemented");
		// BufferedInputStream bi = new BufferedInputStream(in);
		// bi.mark(readlimit);
		// BitInputStream bi = new BitInputStream(in);
//		in.mark(500000);
		freq = new HashMap<Integer, Integer>();
		PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>();
		int value;
		int count = 0;
		while ((value = in.read()) != -1) {
			Integer frequency = freq.get(value);
			if (frequency == null) {
				freq.put(value, 1);
			} else {
				freq.put(value, frequency + 1);
			}
			count++;
		}
		pq.add(new TreeNode(PSEUDO_EOF, 0, null, null));
		freq2Huff(pq);
		treeTraverse(huff_tree, "");
//		in.reset();
		int totalBits = 0;
		for (Integer key : freq.keySet()){
			if(freq.get(key) != null){
				totalBits += (freq.get(key) * binary.get(key).length());
			}
		}
		return (8 * count) - (BITS_PER_INT + totalBits + (256*BITS_PER_INT));
	}
	
	
	@Override
	public int compress(InputStream in, OutputStream out, boolean force)
			throws IOException {
		// throw new IOException("compress is not implemented");
		preorder(huff_tree);
		// should have our string representation of the huff_tree with bit
		int value;
		int count = 0;
		encodeStr="";
		while ((value = in.read()) != -1) {
			encodeStr += binary.get(value);
			count++;
		}

		int length = encodeStr.length();
		// write out the magic number
		if (out != null) {
			if ((count*8 > length + (ALPH_SIZE * 32) + BITS_PER_INT) || force) {

				BitOutputStream bos = new BitOutputStream(out);

				bos.writeBits(BITS_PER_INT, STORE_COUNTS);
				freq.put(256,-1);
				for (int i = 0; i < ALPH_SIZE+1; i++) {
					Integer frequency = freq.get(i);
					if (frequency == null) {
						frequency = 0;
					}
					bos.writeBits(BITS_PER_INT, frequency);
				}

				for (int i = 0; i < encodeStr.length(); i++) {
					if (encodeStr.charAt(i) == '0') {
						bos.writeBits(1, 0);
					} else {
						bos.writeBits(1, 1);
					}
				}
				for (int i = 0; i < binary.get(PSEUDO_EOF).length(); i++) {
					String need1 = binary.get(PSEUDO_EOF);
					if (need1.charAt(i) == '0') {
						bos.writeBits(1, 0);
					} else {

						bos.writeBits(1, 1);
					}
				}
				bos.close();
			} else {
				throw new IOException("Compression does not save any space");
			}
		}
		return encodeStr.length() + (ALPH_SIZE * 32) + BITS_PER_INT;
	}
	
	@Override
	public int uncompress(InputStream in, OutputStream out) throws IOException {
		// throw new IOException("uncompress not implemented");
		// return 0;
		BitInputStream bi = new BitInputStream(in);
		int magic = bi.readBits(BITS_PER_INT);
		if (magic != STORE_COUNTS) {
			throw new IOException("magic number not right");
		}
//		TreeNode n = new TreeNode(0, 400);
//		bi.readBits(1);
		// huff_tree = n;
		freq = new HashMap<Integer,Integer>();
		for (int i = 0; i < ALPH_SIZE+1; i++) {
			int val = bi.readBits(BITS_PER_INT);
			if (val != 0) {
				if (val == -1){
					val = 0;
				}
				freq.put(i, val);
			}
		}
		PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>();
		freq2Huff(pq);
		treeTraverse(huff_tree, "");
		binary = binary;
		return finalTranslateWords(bi, out);
		
//		// decompressPreorder(bi, n);
//		// treeTraverse(huff_tree, "");
//		convertMapObj(binary);
//		if(eofLocator()){
//			for(Integer each: binary.keySet()){
//				String hello = binary.get(each);
//				if(binary.get(each).equals("100000000")){
//					needFreq = freq.get(each);
//				} else {
//					needFreq = 1;
//				}
//			}
//		}
//		translateNeed(bi, needFreq);
//		writeOutFile(out, finalAnswer);
//		return finalAnswer.length();
	}

	public void translateNeed(BitInputStream bi,int needFreq) throws IOException{
		int requiredBits = 9;
		int value = 0;
		finalAnswer = "";
		int count=0;
		int count2=0;
		while(true){
			value = value << requiredBits;
			value += bi.readBits(requiredBits);
			count+=requiredBits;
//			if(count > 5610){
//				System.out.println();
//			}
			int need = value & 0x01ff;
			if((value & 0x01ff) == PSEUDO_EOF){
				count2++;
//				if(needFreq == 0) break;
				if(count2 > needFreq){
					break;
				}
//				break;
			}
			int used = translate(nineBitString(value), bi);
			requiredBits = (used>9) ? 9: used;
		}
	}
}
