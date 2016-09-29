import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class Practice2 implements IHuffProcessor
{

   private HuffViewer myViewer;

   private int myCount; // count of bits saved by compression

   private int[] myFreqs; // array of occurence counts of each character

   private String[] myChunkToHuff; // map of 8-bit chunks to Huffman-codings

   private TreeNode myHuffTreeRoot; // root of the Huffman tree

   private boolean myHeaderType = true; // if TRUE the header is in STF, if FALSE SCF


   public int compress(InputStream in, OutputStream out, boolean force) throws IOException
   {
   	// Check to see if bits saved is negative and if force is true or false
   	if (force == false && myCount < 0)
   		throw new IOException("Error: compressing would cause the file to become larger");
   	
   	myCount = 0;
   	
   	if (in == null)
   		return 0;
   	
   	BitInputStream bis = new BitInputStream(in);
   	BitOutputStream bos = new BitOutputStream(out);
   	
   	// PART 1: Write the 'magic number' and store counts or store tree
   	bos.writeBits(BITS_PER_INT, MAGIC_NUMBER);
   	
   	if (myHeaderType == true)
   		bos.writeBits(BITS_PER_INT, STORE_TREE);
   	
   	else
   		bos.writeBits(BITS_PER_INT, STORE_COUNTS);
   	
   	myCount += 2*BITS_PER_INT;
   	
   	
   	// PART 2A: Generate the header (standard tree format)
   	if (myHeaderType == true)
   		treeHeader(myHuffTreeRoot, bos);
   	
   	// PART 2B: Generate the header (standard count format)
   	else
   		freqsHeader(bos);
   	
   	// PART 3: Write the encoded text file
   	
   	int chunk;
   	while ((chunk = bis.readBits(BITS_PER_WORD)) != -1)
   	{
   		// Get the string indicating the path
   		String path = myChunkToHuff[chunk];
   		
   		// Get each character as a string, convert it to an integer
   		// and write it
   		for (int i = 0; i < path.length(); i++)
   		{
   			String direction = Character.toString(path.charAt(i));
   			bos.writeBits(1, Integer.parseInt(direction));
   			myCount++;
   		}
   	}
   	
   	// Write the Pseudo EOF
   	for (int i = 0; i < myChunkToHuff[PSEUDO_EOF].length(); i++)
   	{
   		String direction = Character.toString(myChunkToHuff[PSEUDO_EOF].charAt(i));
			bos.writeBits(1, Integer.parseInt(direction));
			myCount++;
   	}
   	
   	bos.close();
   	
       return myCount;
   }


   /**
    * Count the number of times each char/chunk
    * occurs, create the Huffman tree, and
    * build a map from char/chunk to encoding
    */
   public int preprocessCompress(InputStream in) throws IOException
   {
   	myCount = 0;
   	
   	if (in == null)
   		return 0;
   	
   	BitInputStream bis = new BitInputStream(in);
   	
   	// PART 1: Count how many times every bit-sequence occurs in a file
   	
   	// Initialize array to count # of occurrences of each chunk
   	myFreqs = new int[ALPH_SIZE];
   	
   	int chunk;
   	
   	// Read in each bit and count the number of times it occurs
   	while ((chunk = bis.readBits(BITS_PER_WORD)) != -1)
   	{
   		myCount += BITS_PER_WORD; // tracks number of bits read in
   		myFreqs[chunk] ++;
   	}
   	
   	
   	// PART 2: Build the Huffman Tree
   	
   	// Create a priority queue to store nodes
   	PriorityQueue<TreeNode> nodeQueue = new PriorityQueue<TreeNode>();
   	
   	// Create one node per character
   	// Add these nodes to the priority queue
   	for (int i = 0; i < myFreqs.length; i++)
   	{
   		if (myFreqs[i] != 0)
   			nodeQueue.add(new TreeNode(i, myFreqs[i], null, null));
   	}
   	
   	// Add the Pseudo-EOF node to the priority queue
   	nodeQueue.add(new TreeNode(PSEUDO_EOF, 0, null, null));
   	
   	
   	// Initialize root outside of loop; will need to know root
   	// to do tree traversal
   	myHuffTreeRoot = new TreeNode(0, 0, null, null);
   	
   	// Create the Huffman Tree
   	while (nodeQueue.size() > 1)
   	{
   		TreeNode left = nodeQueue.remove();
   		TreeNode right = nodeQueue.remove();
   		
   		// Create new node with 2 children; weight of this node is sum of children's weight
   		TreeNode sumNode = new TreeNode(-1, left.myWeight + right.myWeight, left, right);
   		
   		// Add the node to the priority queue
   		nodeQueue.add(sumNode);
   		
   		// When the condition in the while-loop is met, the last node created will have
   		// been the root
   		myHuffTreeRoot = sumNode;
   	}
   	
   	
   	// PART 3: Create a table / map of chunks to Huffman-codings
   	
   	String path = ""; // String representing path with 0s and 1s; converted to int
   					  // then appended to chunkToHuff when leaf is found
   	
   	// Initialize instance variable (+1 to include the pseudo-EOF)
   	myChunkToHuff = new String[ALPH_SIZE + 1];
   	
   	
   	// Traverses each path starting from the root and appends paths to instance variable
   	// chunkToHuff
   	treeTraverse(myHuffTreeRoot, path);
   	
   	
   	// PART 4: Subtract the bits used to write the compressed path, magic number,
   	//		   store tree / store counts, and the header
   	
   	// A) Subtract the length of the MAGIC_NUMBER and STORE_TREE / STORE_COUNTS
   	myCount -= 2*BITS_PER_INT;
   	
   	
   	// B) Subtract the length of the header
   	
   	// If header is a tree header (STF), the length of the header is subtracted
   	// in the treeTraverse method!
   	
   	// If header is a freqs header (SCF)
   	if (myHeaderType == false)
   	{
   		for (int i = 0; i < ALPH_SIZE; i++)
   			myCount -= BITS_PER_INT;
   	}
   	
   	
   	// C) Subtract the path length * frequency for each character from myCount
   	for (int i = 0; i < myFreqs.length; i++)
   	{
   		if(myChunkToHuff[i] != null)
   		{
	    		int bitsSaved = myChunkToHuff[i].length() * myFreqs[i];
	    		myCount -= bitsSaved;
   		}
   	}
   	
   	
   	// Return the number of bits saved
   	return myCount;
   }

   public void setViewer(HuffViewer viewer) {
       myViewer = viewer;
   }


   public int uncompress(InputStream in, OutputStream out) throws IOException
   {
   	myCount = 0;
   	
   	BitInputStream bis = new BitInputStream(in);
   	BitOutputStream bos = new BitOutputStream(out);
   	
       // PART 1: Check for the magic number and store counts/tree; if it is not there throw an exception
   	if (bis.readBits(BITS_PER_INT) != MAGIC_NUMBER)
   		throw new IOException("Ethisssadasfadsf");
   	
   	int type = bis.readBits(BITS_PER_INT);
   	
   	if (type != STORE_TREE && type != STORE_COUNTS)
   		throw new IOException("what the fuck");
   	
   	// PART 2: Determine the header type and recreate the Huffman tree
   	TreeNode root = new TreeNode(0, 0, null, null);
   	
   	if (type == STORE_TREE)
   		root = treeReader(bis);
   	
   	else
   		root = freqsReader(bis);
   	
   	// PART 3: Read the encoded text file
   	TreeNode tnode = root;
   	
   	while (true)
   	{
   		int bits = bis.readBits(1);
   		
   		// If -1 is returned from readBits, the Pseudo-EOF
   		// was never found
   		if (bits == -1)
   			throw new IOException("Error: no Pseudo-EOF");
   		
   		// If bits is 0, go left in the tree
   		if (bits == 0)
   			tnode = tnode.myLeft;
   		
   		// If bits is 1, go right in the tree
   		else
   			tnode = tnode.myRight;
   		
   		// Check to see if the node is a leaf
   		if (tnode.myRight == null && tnode.myLeft == null)
   		{
   			// If the leaf is the Pseudo-EOF, break out of
   			// the while loop
   			if (tnode.myValue == PSEUDO_EOF)
   				return myCount;
   			
   			// Write the character into the output file
   			else
   			{
   				bos.writeBits(BITS_PER_WORD, tnode.myValue);
   				myCount += BITS_PER_WORD;
   				tnode = root;
   			}
   		}
   	}
   }

   private void showString(String s){
       myViewer.update(s);
   }

   /**
    * Helper method that traverses the tree and records
    * paths to characters in myChunkToHuff
    * 
    * @param n
    * @param path
    */
   private void treeTraverse(TreeNode n, String path)
   {
   	// Base Case: Leaf has been found
   	if (n.myLeft == null && n.myRight == null)
   	{
   		if (myHeaderType == true)
   			myCount -= 10; // The number of bits written each time a leaf is found
   		
   		myChunkToHuff[n.myValue] = path;
   		return;
   	}
   	
   	if (myHeaderType == true)
   		myCount -= 1; // The number of bits written each time an internal node is created
   	
   	treeTraverse(n.myLeft, path + "0");

   	treeTraverse(n.myRight, path + "1");

   	
   	return;
   }

   /**
    * Helper method to generate the header in
    * Standard Count Format (SCF)
    * 
    * @param bos
    */
   private void freqsHeader(BitOutputStream bos)
   {
   	for (int i = 0; i < ALPH_SIZE; i++)
   	{
   		bos.writeBits(BITS_PER_INT, myFreqs[i]);
   		myCount += BITS_PER_INT;
   	}
   }

   /**
    * Helper method to uncompress a header in SCF
    * 
    * @param bis
    * @return
    * @throws IOException
    */
   private TreeNode freqsReader(BitInputStream bis) throws IOException
   {
   	PriorityQueue<TreeNode> nodeQueue = new PriorityQueue<TreeNode>();
   	
   	int[] counts = new int[ALPH_SIZE];
   	
   	for (int i = 0; i < counts.length; i++)
   	{
   		int bits = bis.readBits(BITS_PER_INT);
   		counts[i] = bits;
   	}
   	
   	// Create one node per character
   	// Add these nodes to the priority queue
   	for (int i = 0; i < ALPH_SIZE; i++)
   	{
   		if (counts[i] != 0)
   			nodeQueue.add(new TreeNode(i, counts[i], null, null));
   	}
   	
   	// Add the Pseudo-EOF node to the priority queue
   	nodeQueue.add(new TreeNode(PSEUDO_EOF, 0, null, null));
   	
   	// Initialize root outside of loop; will need to know root
   	// to do tree traversal
   	TreeNode root = new TreeNode(0, 0, null, null);
   	
   	// Create the Huffman Tree
   	while (nodeQueue.size() > 1)
   	{
   		TreeNode left = nodeQueue.remove();
   		TreeNode right = nodeQueue.remove();
   		
   		// Create new node with 2 children; weight of this node is sum of children's weight
   		TreeNode sumNode = new TreeNode(-1, left.myWeight + right.myWeight, left, right);
   		
   		// Add the node to the priority queue
   		nodeQueue.add(sumNode);
   		
   		// When the condition in the while-loop is met, the last node created will have
   		// been the root
   		root = sumNode;
   	}
   	
   	return root;
   }

   /**
    * Helper method to generate the header in
    * Standard Tree Format (STF)
    * @param bos
    */
   private void treeHeader(TreeNode n, BitOutputStream bos)
   {
   	// Base Case: Leaf has been found; write a "1"
   	if (n.myLeft == null && n.myRight == null)
   	{
   		bos.writeBits(1, 1);
   		myCount++;
   		
   		// If the value is the pseudo-EOF
   		if (n.myValue == PSEUDO_EOF)
   		{
   			bos.writeBits(1, 1);
   			bos.writeBits(9, PSEUDO_EOF);
   			myCount += 10;
   		}
   		
   		// If not, write a "0" then the character value
   		else
   		{
   			bos.writeBits(1, 0);
   			bos.writeBits(BITS_PER_WORD, n.myValue);
   			myCount += 1 + BITS_PER_WORD;
			}
   		return;
   	}
   	
   	// If n is an internal node, write a "0" and recurse
   	bos.writeBits(1, 0);
   	treeHeader(n.myLeft, bos);
   	treeHeader(n.myRight, bos);
   	
   	return;
   }


   /**
    * Helper method to uncompress a header in STF
    * 
    * @param bis
    * @return
    * @throws IOException
    */
   private TreeNode treeReader(BitInputStream bis) throws IOException
   {
   	int bit = bis.readBits(1);
   	
   	if (bit == -1)
   		throw new IOException("Error: improperly formatted file");
   	
   	// If bit = 0, return a new internal node
   	if (bit == 0)
   	{
   		TreeNode newNode = new TreeNode(-1, 0, null, null);
   		newNode.myLeft = treeReader(bis);
   		newNode.myRight = treeReader(bis);
   		
   		return newNode;
   	}
   	
   	// If bit = 1, the node is a leaf
   	else
   	{
   		// Read the next bit to determine if the following
   		// 9 bits represent a character or the pseudo-EOF
   		int charOrPseudo = bis.readBits(1);
   		
   		// If that bit is a "0", the following code is a character
   		if (charOrPseudo == 0)
   			return new TreeNode(bis.readBits(BITS_PER_WORD), 0, null, null);
   		
   		// If the bit is a "1", the following code is the pseudo-EOF
   		else
   			return new TreeNode(bis.readBits(9), 0, null, null);
   	}
   }

}