import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class Erdos {
	
	Set<String> results = new TreeSet<String>();
	
	public class Node{
		ArrayList<Node> children = new ArrayList<Node>();
		String author; 
		int level;
	}
	
	private int countNodes(Node current) {
		String str = current.author + " " + current.level;
		results.add(str);
		if (current.children.size() == 0) {
			return 0;
		}
		for(Node name: current.children){
			String result = name.author + " " + name.level;
			results.add(result);
			countNodes(name);
		}
		return 0;
	}
	
	public String[] number(String[] pubs) {
		ArrayList<Integer> publicationsUsed = new ArrayList<Integer>();
		ArrayList<Node> previousLvl = new ArrayList<Node>();
		ArrayList<Node> currLvl = new ArrayList<Node>();
		Node head = new Node();
		head.author = "ERDOS";
		head.level = 0; 
		previousLvl.add(head);
		int level = 1;
		while(publicationsUsed.size() < pubs.length){
			for(Node node: previousLvl){
				String author = node.author;
				for(int i = 0; i < pubs.length; i++){
					if(!publicationsUsed.contains(i)){
						String[] list = pubs[i].split(" ");
						
						for(String name: list){
							if(name.equals(author)){
								publicationsUsed.add(i);
								for(String person: list){
									if(!person.equals(author)){
										Node n = new Node();
										n.author = person;
										n.level = level;
										currLvl.add(n);
										node.children.add(n);
									}
								}
								break;
							}
						}
						
					}
				}
			}
			level++;
			previousLvl = currLvl;
			currLvl = new ArrayList<Node>();
		}
		countNodes(head);
		String[] result = new String[results.size()];
		for(int k = 0; k < results.size(); k++){
			result[k] = (String) results.toArray()[k];
		}
		return result;
	}

	public static void main(String[] args) {
		String[] pubs = { "ERDOS KLEITMAN", "CHUNG GODDARD KLEITMAN WAYNE",
				"WAYNE GODDARD KLEITMAN", "ALON KLEITMAN",
				"DEAN GODDARD WAYNE KLEITMAN STURTEVANT" };
		Erdos e = new Erdos();
		String[] answer = e.number(pubs);
		for(String answers: answer){
			System.out.println(answers);
		}
	}
}
