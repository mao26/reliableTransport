public class LinkStrand implements IDnaStrand {
	private int myAppends;
	private Node myInfo;
	private Node last;
	
	public class Node {
		String info;
		Node next;

		Node(String s) {
			info = s;
			next = null;
		}
	}

	public LinkStrand(String dna) {
		initializeFrom(dna);
		myAppends = 0; 
	}

	public LinkStrand() {
		this("");
	}
	
	
	public void setMyInfo(Node myInfo) {
		this.myInfo = myInfo;
	}

	public Node getMyInfo() {
		return myInfo;
	}

	@Override
	public IDnaStrand cutAndSplice(String enzyme, String splicee) {
		int pos = 0;
		int start = 0;
		Node search = myInfo; // what we are going to be searching through (the node that contains our dna)
		boolean first = true;
		LinkStrand ret = null;

		/*
		 * The next line is very syntax-dense. .indexOf looks for the first
		 * index at which enzyme occurs, starting at pos. Saying pos = ...
		 * assigns the result of that operation to the pos variable; the value
		 * of pos is then compared against zero.
		 * 
		 * .indexOf returns -1 if enzyme can't be found. Therefore, this line
		 * is:
		 * 
		 * "While I can find enzyme, assign the location where it occurs to pos,
		 * and then execute the body of the loop."
		 */
		
		
		//search.info.substring(start,pos)) has the remaining string after taking care of the first part plus a splicee
		while ((pos = search.info.indexOf(enzyme, pos)) >= 0) {
			if (first) {
				ret = new LinkStrand(search.info.substring(start, pos));
				first = false;
			} else {
				ret.append(search.info.substring(start, pos));
			}
			search = new Node(search.info.substring(pos+enzyme.length()));
			start = 0;
			ret.append(splicee);
		}

		if (start < search.info.length()) {
			// NOTE: This is an important special case! If the enzyme
			// is never found, return an empty String.
			if (ret == null) {
				ret = new LinkStrand("");
			} else {
				ret.append(search.info.substring(start));
			}
		}
		return ret;
	}

	@Override
	public long size() {
		Node index = myInfo;
		int total = 0;
		while(true){
			total += index.info.length();
			if(index.next == null) {
				break;
			}
			index = index.next;
		}
		return total;
	}

	@Override
	public void initializeFrom(String source) {
		myInfo = new Node(source);
	}

	@Override
	public String strandInfo() {
		return this.getClass().getName();
	}

	@Override
	public IDnaStrand append(IDnaStrand dna) {
		Node add = ((LinkStrand) dna).getMyInfo();
		if(this.last == null){
			last = add;
			myInfo = add;
		} else {
			last.next = add;
			last = add;
		}
		myAppends++;
		return this;
	}

	@Override
	public IDnaStrand append(String dna) {
		LinkStrand beginning = new LinkStrand(dna);
		return append(beginning);
	}

	@Override
	
	public IDnaStrand reverse() {
		Node first = null;
		Node index = myInfo;
		while(true){
			StringBuilder string = new StringBuilder(index.info);
			Node temp = new Node(string.reverse().toString());
			if(index.next == null) {
				first = temp;
				break;
			}
			temp.next = first;
			first = temp;
			index = index.next;
		}
		LinkStrand result = new LinkStrand();
		result.setMyInfo(first);
        return result;
	}

	@Override
	public String toString() {
		Node index = myInfo;
		String result = "";
		while(true){
			result += index.info;
			if(index.next == null) {
				break;
			}
			index = index.next;		
		}
		return result;
	}

	@Override
	public String getStats() {
        return String.format("# append calls = %d",myAppends);
	}

}
