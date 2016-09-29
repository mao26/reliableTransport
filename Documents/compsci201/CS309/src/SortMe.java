import java.util.Scanner;

public class SortMe {

	static int id = 1;
	
	public static void main(String[] args){
		SortMe sm = new SortMe();
		sm.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		while(true){
			int datasets = scan.nextInt();
			if(datasets == 0) break;
			System.out.println("year " + id);
			id += 1;
			String alphabet = scan.next();
			String[] data = new String[datasets];
			for(int i = 0; i < datasets; i++){
				data[i] = scan.next();
			}
			Node head = new Node();
			head.word = null;
			head.next = null;
			organize(data, alphabet, head);
		}
		scan.close();
	}

	class Node{
		String word;
		Node next;
	}
	
	private void organize(String[] data, String alphabet, Node head) {
		for(String word: data){ //for each word in our data set
			Node newNode = new Node();
			newNode.word = word;
			if(head.word == null){
				newNode.next = null;
				head = newNode;
			} else {
				Node curr = head;
				boolean endWhile = false;
				while(curr.next != null){
					for(int i = 0; i < word.length(); i++){
						if(alphabet.indexOf(word.charAt(i)) < alphabet.indexOf(curr.word.charAt(i))){
							newNode.next = curr;
							endWhile = true;
							break;
						} else if (alphabet.indexOf(word.charAt(i)) > alphabet.indexOf(curr.word.charAt(i))){
							if(curr.next == null){ //this case can't occur because our while loop makes sure this doesn't happen
								curr.next = newNode;
								break;
							} else { //let's move our chains and have our loop check to see if the next val in linkedList is lower on the scale
								curr = curr.next;
								break;
							}
						} else { //chars are equal so we need to test our next characters
							continue;
						}
					}
					if(endWhile) break;
				}
				curr.next = newNode;
			}
		}
		Node curr = head;
		while(curr != null) {
			System.out.printf("%d\n", curr.word);
		}
	}
	
}
