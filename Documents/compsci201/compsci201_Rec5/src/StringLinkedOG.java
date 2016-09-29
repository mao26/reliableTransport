

public class StringLinkedOG {
	
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
	
	
	public void addAtBeginning(String valueToAdd){
		myHead = new Node (myHead, valueToAdd);
	}
	
	
	public void removeLongestString(){
		String maxString = "";
		Node curr = myHead;
		if(myHead == null){
			return;
		}
		maxString = curr.myValue;
		while(curr.myNext != null){
			if(curr.myValue.length() > curr.myNext.myValue.length() ){
				maxString = curr.myValue;
			} else {
				maxString = curr.myNext.myValue;
			}
			curr = curr.myNext;
		} 
		if(maxString == myHead.myValue){
			myHead = myHead.myNext;
		}
		curr = myHead;
		while (curr.myNext != null){
			if(curr.myNext.myValue == maxString){
				if(curr.myNext.myNext == null){
					curr.myNext = null;
				} else{ 
					curr.myNext = curr.myNext.myNext;
				}
				return;
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
	public void moveToEnd(int k){
		
	}
	
	
//
//  private class Node {
//    String myValue;
//    Node myNext;
//
//    Node(String value, Node next) {
//      myValue = value;
//      myNext = next;
//    }
//  }
//
//  Node myHead;
//
//  /**
//   * Adds a new node with valueToAdd as value to the <em>end</em> of this linked list
//   */
//  public void addAtEnd(String valueToAdd) {
//	  Node myTail = new Node(valueToAdd, null);
//	  Node current = myHead;
//	  if(myHead == null){
//		  myHead = myTail;
//	  }
//	  else{
//		  while(current.myNext != null){
//			  current = current.myNext;
//		  }
//	  current.myNext = myTail;
//	  }
//  }
//
//  /**
//   * Adds a new node with valueToAdd as value to the <em>beginning</em> of this linked list
//   */
//  public void addAtBeginning(String valueToAdd) {
//    myHead = new Node(valueToAdd, myHead);
//  }
//
//  /**
//   * Removes the longest string from the list,
//   * 
//   * if you have the list [a,b,longstring,z,q] after this function runs you end up with [a,b,z,q] if
//   * more than one string has the same longest length, remove the first one if the list is empty, do
//   * nothing if the list has only 1 element, remove it
//   */
//  public void removeLongestString() {
//    // TODO 2 Complete removeLongestString
//    // when you implement this function, be sure to think about
//    // a. what if the list is empty
//    // b. what if the longest element is the first element
//    // c. what if the list has only 1 element
//	  /*
//	   if(myHead == null) return;
//	   Node currentLongest = myHead;
//	   Node precedingLongest = null;
//	   String longest = myHead.myValue;
//	   while(currentLongest != null){
//	   		if(longest.length() < currentLongest.myNext.myValue.length()){
//	   			precedingLongest = currentLongest;
//	   			longest = currentLongest.myNext.myValue; 
//	   		}
//	   		currentLongest = currentLongest.myHead;
//	   }
//	   if (precidingLongest == null){
//	   		myHead = myHead.myNext;
//	   	} else {
//	   		precedingLongest.myNext = precedingLongest.myNext.myNext;
//	   	}
//	   */
//		String maxString = "";
//		Node current = myHead;
//		if(myHead == null){
//			return;
//		}
//		if(myHead.myNext == null){
//			myHead = null;
//			return;
//		}
//		maxString = myHead.myValue;
//		while (current.myNext != null) {
//			if (maxString.length() < current.myNext.myValue.length()) {
//				maxString = current.myNext.myValue;
//			}
//			current = current.myNext;
//		}
//		if(myHead.myValue == maxString){
//			myHead = myHead.myNext;
//			return;
//		}
//		current = myHead;
//		while (current.myNext != null) {
//			if (maxString.equals(current.myNext.myValue) ) {
//				current.myNext = current.myNext.myNext;
//				return;
//			}
//			current = current.myNext;
//
//	  }
//  }
//
//  /**
//   * Repeats (doubles) each element [a,b,c] -> [a,a,b,b,c,c]
//   */
//  public void doubleList() {
//    // TODO 3. Complete doubleList
//	  Node current = myHead;
//	  Node curr = current.myNext;  //will make it skip two at a time
//	  while(current != null){
//		  Node myNew = new Node(current.myValue, current.myNext);
//		  current.myNext = myNew;
//		  current = curr;
//		  if(curr != null){
//			  curr = curr.myNext;
//		  }
//		 
//	  }
//  }
//
//  /**
//   * Move k elements from the beginning of the list to the end [a,b,c,d,e] -> moveToEnd(2) ->
//   * [c,d,e,a,b]
//   */
//  public void moveToEnd(int k) {
//    // TODO 4. completeMoveToEnd
//	  Node current = myHead;
//	  while(current.myNext != null){
//		  current = current.myNext;
//	  }
//	  current.myNext = myHead;
//	  for(int i = 0; i < k ; i++){
//		  current = current.myNext;
//	  }
//	  myHead = current.myNext;
//	  current.myNext = null;
//	  current = myHead;
//  }
//
  /**
   * Two StringLinkedLists are equal iff they have the same elements and are are the same length
   */
  public boolean equals(StringLinkedOG other) {
	  Node curr = myHead;
	  while(curr.myNext != null){
		  //compare the myValues of each one, if they aren't the same then break
		  //if you run into a null value for one of the pointers then return false, because they aren't the same
		  //length
	  }
	  return false;
  }

  public String toString() {
    // Already complete!
    StringBuilder b = new StringBuilder();
    Node current = myHead;
    while (current != null) {
      b.append(current.myValue);
      b.append(" ");
      current = current.myNext;
    }
    return b.toString();
  }
}
