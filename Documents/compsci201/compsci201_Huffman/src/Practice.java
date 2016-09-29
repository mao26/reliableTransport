import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Practice implements IHuffProcessor {

	Map<Integer, String> binary = new HashMap<Integer, String>();
	String store_tree = "";
	TreeNode huff_tree = null;
	String encodeStr = "";
	// String decodeStr = "";
	private HuffViewer myViewer;
	Map<Integer, Integer> freq;
	String finalAnswer;
	ArrayList<SString> myList;

	public int compress(InputStream in, OutputStream out, boolean force)
			throws IOException {
		// throw new IOException("compress is not implemented");
		store_tree = "";
		preorder(huff_tree);
		// should have our string representation of the huff_tree with bit
		int value;
		int count = 0;
		encodeStr = "";
		while ((value = in.read()) != -1) {
			encodeStr += binary.get(value);
			count++;
		}

		int length = encodeStr.length();
		// write out the magic number
		if (out != null) {
			if ((count * 8 > length + store_tree.length() + BITS_PER_INT)
					|| force) {

				BitOutputStream bos = new BitOutputStream(out);

				bos.writeBits(BITS_PER_INT, STORE_TREE);
				for (int i = 0; i < store_tree.length(); i++) {
					if (store_tree.charAt(i) == '0') {
						bos.writeBits(1, 0);
					} else {
						bos.writeBits(1, 1);
					}
				}
				for (int i = 0; i < encodeStr.length(); i++) {
					if (encodeStr.charAt(i) == '0') {
						bos.writeBits(1, 0);
					} else {
						bos.writeBits(1, 1);
					}
				}
				bos.writeBits(9, PSEUDO_EOF);
				// bos.flush();
			} else {
				throw new IOException("Compression does not save any space");
			}
		}
		return encodeStr.length() + store_tree.length() + BITS_PER_INT;
	}

	
	
	public void preorder(TreeNode node) {
		if (node == null)
			return;
		if (node.myLeft == null && node.myRight == null) {
			try {
				String need = Integer.toBinaryString(("" + (char) node.myValue)
						.getBytes("UTF-8")[0]);
				while (need.length() < 8) {
					need = "0" + need;
				}
				store_tree += "1" + need;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			store_tree += "0";
			preorder(node.myLeft);
			preorder(node.myRight);
		}

	}

	private TreeNode decompressPreorder(BitInputStream bi, TreeNode node) {
		if (node.myValue == 1) {
			int total = 0;
			int bit = 9;
			try {
				bit = bi.readBits(BITS_PER_WORD);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			node.myValue = bit;
			return node;
		}
		int value = PSEUDO_EOF;
		try {
			value = bi.readBits(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (value == PSEUDO_EOF)
			return null;

		if (node.myValue == 0) {
			TreeNode n = new TreeNode(value, value);
			node.myLeft = decompressPreorder(bi, n);
			int val = PSEUDO_EOF;
			try {
				val = bi.readBits(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			TreeNode d = new TreeNode(val, val);
			node.myRight = decompressPreorder(bi, d);
		}
		return node;
	}

	protected void treeTraverse(TreeNode node, String str) {
		if (node.myLeft == null && node.myRight == null) {
			// no children
			binary.put(node.myValue, str);
			return;
		}
		if (node.myLeft != null) {
			treeTraverse(node.myLeft, str + "0");
		}
		if (node.myRight != null) {
			treeTraverse(node.myRight, str + "1");
		}
	}

	public void freq2Huff(PriorityQueue<TreeNode> pq) {
		pq.clear();
		for (Integer n : freq.keySet()) {
			TreeNode node = new TreeNode(n, freq.get(n));
			pq.add(node);
		}
		while (pq.size() > 1) {
			TreeNode n1 = pq.poll();
			TreeNode n2 = pq.poll();
			TreeNode node = new TreeNode(0, n1.myWeight + n2.myWeight, n1, n2);
			pq.add(node);
		}
		huff_tree = pq.poll();
	}

	public int preprocessCompress(InputStream in) throws IOException {
		// throw new IOException("preprocess not implemented");
		// BufferedInputStream bi = new BufferedInputStream(in);
		// bi.mark(readlimit);
		// BitInputStream bi = new BitInputStream(in);
		in.mark(500000);
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
		freq2Huff(pq);
		treeTraverse(huff_tree, "");
		eofRemove();
		in.reset();

		return (8 * count) - compress(in, null, true);
	}

	public void eofRemove() {
		Integer saveKey = null;
		Integer saveKey2 = null;
		String longestVal = "";
		for (Integer key : binary.keySet()) {
			if (key.equals("100000000")) {
				binary.put(key, "100000001");
			}
		}
		// int counter=0;
		// for(Integer key : binary.keySet()){
		// if (binary.get(key).length()>counter){
		// counter=binary.get(key).length();
		// longestVal=binary.get(key);
		// saveKey2=key;
		// }
		// if (binary.get(key).equals("100000000")){
		// saveKey=key;
		// }
		//
		// }
		// binary.put(saveKey, longestVal+"1");
		// binary.put(saveKey2, longestVal+"0");

	}

	public void setViewer(HuffViewer viewer) {
		myViewer = viewer;
	}

	public int uncompress(InputStream in, OutputStream out) throws IOException {
		// throw new IOException("uncompress not implemented");
		// return 0;
		BitInputStream bi = new BitInputStream(in);
		int magic = bi.readBits(BITS_PER_INT);
		if (magic != STORE_TREE) {
			throw new IOException("magic number not right");
		}
		TreeNode n = new TreeNode(0, 400);
		bi.readBits(1);
		huff_tree = n;
		decompressPreorder(bi, n);
		treeTraverse(huff_tree, "");
		// recoverCoded(bi);
		convertMapObj(binary);
		translateWords(bi);
		writeOutFile(out, finalAnswer);
		return finalAnswer.length();
	}

	protected void writeOutFile(OutputStream out, String result)
			throws IOException {
		// String end = result.substring(result.length()-100,result.length());
		for (int i = 0; i < result.length(); i++) {
			int val = (int) result.charAt(i);
			out.write((int) result.charAt(i));
		}
		out.flush();
		out.close();
	}

	protected String recoverCoded(BitInputStream bi) {
		encodeStr = "";
		int allBits8 = 0;
		int count = 0;
		int count2 = 0;
		while (true) {
			allBits8 = allBits8 << 1;
			allBits8 = allBits8 & 0x01ff;
			try {
				allBits8 += bi.readBits(1);
				count++;
			} catch (IOException e) {
				e.printStackTrace();
			}
			if ((allBits8 & 0x01ff) == PSEUDO_EOF) {
				count2++;
				if (count2 == 2) {
					break;
				}
			}
			if (count < 9) {
				continue;
			} else {
				if ((allBits8 & 0x0100) != 0) {
					encodeStr += "1";
				} else {
					encodeStr += "0";
				}
			}
		}
		return encodeStr;
	}

	public void convertMapObj(Map<Integer, String> binary) {
		ArrayList<SString> need = new ArrayList<SString>();
		for (Integer each : binary.keySet()) {
			SString ss = new SString(each, binary.get(each));
			need.add(ss);
		}
		boolean isDone = false;
		while (!isDone) {
			isDone = true;
			for (int i = 0; i < need.size() - 1; i++) {
				if (need.get(i).bin.length() > need.get(i + 1).bin.length()) {
					isDone = false;
					String temp = need.get(i).bin;
					int a = need.get(i).ch;
					// need.set(i, need.get(i+1));
					need.get(i).ch = need.get(i + 1).ch;
					need.get(i).bin = need.get(i + 1).bin;
					need.get(i + 1).ch = a;
					need.get(i + 1).bin = temp;
					// need.set(i+1, new SString(a, temp));
				}
			}
		}
		myList = need;
	}

	public class SString {
		Integer ch;
		String bin;

		SString(Integer ch, String bin) {
			this.ch = ch;
			this.bin = bin;
		}
	}

	public void translateWords(BitInputStream bi) throws IOException {
		int requiredBits = 9;
		int value = 0;
		finalAnswer = "";
		int count = 0;
		// int count2=0;
		while (true) {
			value = value << requiredBits;
			value += bi.readBits(requiredBits);
			count += requiredBits;
			if ((value & 0x01ff) == PSEUDO_EOF) {
				// count2++;
				// if(count2==2){
				// break;
				// }
				break;
			}
			int used = translate(nineBitString(value), bi);
			requiredBits = (used > 9) ? 9 : used;
		}
	}

	public String nineBitString(int value) {
		int mask = 256;
		String result = "";
		value = value & 0x01ff; // makes it nine values
		for (int i = 0; i < 9; i++) {
			int x = value & mask;
			if (x > 0) {
				result += "1";
			} else {
				result += "0";
			}
			mask = mask >> 1;
		}
		return result;
	}

	public int translate(String encoded, BitInputStream bi) throws IOException {
		// String myAnswer = "";
		String newString = encoded;
		int subtractor = 0;

		// TreeMap<Integer, String> tempMap = new TreeMap<Integer,
		// String>(binary);

		// String[] encodedSplit = encoded.split("");
		// System.out.println(Arrays.toString(encodedSplit));
		for (int i = 0; i < myList.size(); i++) {
			SString ss = myList.get(i);
			while (ss.bin.length() > encoded.length()) {
				int val = bi.readBits(1);
				if (val > 0) {
					encoded += "1";
				} else {
					encoded += "0";
				}
			}
			if (encoded.indexOf(ss.bin) == 0) {
				char cha = (char) (int) ss.ch;
				finalAnswer += cha;
				return ss.bin.length();
			}
		}
		return 0;
	}

	// System.out.println("tempMap size " + tempMap.size());
	// if (tempMap.size() > 1) {
	// for (Iterator<Map.Entry<Integer, String>> it = tempMap
	// .entrySet().iterator(); it.hasNext();) {
	// Map.Entry<Integer, String> entry = it.next();
	// String x = entry.getValue();
	// String[] split = x.split("");
	// System.out.println(Arrays.toString(split));
	// if (!split[i - subtractor].equals(encodedSplit[i])) {
	// System.out.println("remove " + it);
	// it.remove();
	// } // else {
	// // System.out.println("I didnt remove it");
	// // }
	// }
	// counter++;
	// }
	//
	// if (tempMap.size() == 1) {
	// for (Map.Entry<Integer, String> each : tempMap.entrySet()) {
	// String x = each.getValue();
	// if (counter == x.length() || counter == 0) {
	// char ch = (char) ('\0' + each.getKey());
	// myAnswer = myAnswer + ch;
	// System.out.println("FINAL ANSWER = " + myAnswer);
	// System.out.println("each.getValue() = "
	// + each.getValue());
	// newString = encoded.substring(i + 1, encoded.length());
	// subtractor = encoded.length() - newString.length();
	// tempMap.clear();
	// tempMap.putAll(binary);
	// counter = 0;
	// } else {
	// counter++;
	// }
	// }
	// }
	// System.out.println("I made it though once");
	// System.out.println();

	private void showString(String s) {
		myViewer.update(s);
	}

}
