
public class LinkList2 {
	public class Node{
		Node myNext;
		String myValue;
		
		public Node (Node next, String value){
			myNext = next;
			myValue = value;
			
		}		
	}
	Node myHead;
	
	
	
	public void addAtEnd (String valueToAdd){
		Node myTail = new Node (null, valueToAdd);
		Node curr = myHead;
		if(myHead == null){
			myHead = myTail; 
		} else {
			while(curr.myNext != null){
				curr = curr.myNext;
				
			}
			curr.myNext = myTail;
		}
	}
	
	
	public void addAtBeggining(String valueToAdd){
		myHead = new Node (myHead, valueToAdd);
	}
	
	
	public void removeLongestString(){
		String maxString = "";
		Node curr = myHead;
		if(myHead == null){
			return;
		}
		maxString = curr.myValue;
		while(curr != null){
			if(curr.myValue.length() > curr.myNext.myValue.length() ){
				maxString = curr.myValue;
			} else {
				maxString = curr.myNext.myValue;
			}
			curr = curr.myNext;
		}
	}
	
	/**
	   * Repeats (doubles) each element [a,b,c] -> [a,a,b,b,c,c]
	   */
	  
	public void doubleList() {
		  Node curr = myHead;
		  if(myHead == null){
			  return;
		  }
		  while(curr.myNext != null){
			  String val = curr.myValue;
			  Node repeat = new Node(null, val);
			  repeat.myNext = curr.myNext;
			  curr.myNext = repeat;
			  curr = curr.myNext.myNext;
		  }
	  }
}
