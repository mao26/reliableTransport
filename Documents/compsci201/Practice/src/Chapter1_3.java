public class StringLinkedList {

  private class Node {
    String myValue;
    Node myNext;

    Node(String value, Node next) {
      myValue = value;
      myNext = next;
    }
  }

  Node myHead;

  /**
   * Adds a new node with valueToAdd as value to the <em>end</em> of this linked list
   */
  public void addAtEnd(String valueToAdd) {
	  Node myTail = new Node(valueToAdd, null);
	  Node current = myHead;
	  if(myHead == null){
		  myHead = myTail;
	  }
	  else{
		  while(current.myNext != null){
			  current = current.myNext;
		  }
	  current.myNext = myTail;
	  }
  }

  /**
   * Adds a new node with valueToAdd as value to the <em>beginning</em> of this linked list
   */
  public void addAtBeginning(String valueToAdd) {
    myHead = new Node(valueToAdd, myHead);
  }

  /**
   * Removes the longest string from the list,
   * 
   * if you have the list [a,b,longstring,z,q] after this function runs you end up with [a,b,z,q] if
   * more than one string has the same longest length, remove the first one if the list is empty, do
   * nothing if the list has only 1 element, remove it
   */
  public void removeLongestString() {
    // TODO 2 Complete removeLongestString
    // when you implement this function, be sure to think about
    // a. what if the list is empty
    // b. what if the longest element is the first element
    // c. what if the list has only 1 element
		String maxString = "";
		Node current = myHead;
		if(myHead == null){
			return;
		}
		if(myHead.myNext == null){
			myHead = null;
			return;
		}
		maxString = myHead.myValue;
		while (current.myNext != null) {
			if (maxString.length() < current.myNext.myValue.length()) {
				maxString = current.myNext.myValue;
			}
			current = current.myNext;
		}
		if(myHead.myValue == maxString){
			myHead = myHead.myNext;
			return;
		}
		current = myHead;
		while (current.myNext != null) {
			if (maxString.equals(current.myNext.myValue) ) {
				current.myNext = current.myNext.myNext;
				return;
			}
			current = current.myNext;

	  }
  }

  /**
   * Repeats (doubles) each element [a,b,c] -> [a,a,b,b,c,c]
   */
  public void doubleList() {
    // TODO 3. Complete doubleList
	  Node current = myHead;
	  Node curr = current.myNext;  //will make it skip two at a time
	  while(curr.myNext != null){
		  Node myNew = new Node(current.myValue, current.myNext.myNext);
		  current.myNext = myNew;
	  }
  }

  /**
   * Move k elements from the beginning of the list to the end [a,b,c,d,e] -> moveToEnd(2) ->
   * [c,d,e,a,b]
   */
  public void moveToEnd(int k) {
    // TODO 4. completeMoveToEnd
  }

  /**
   * Two StringLinkedLists are equal iff they have the same elements and are are the same length
   */
  public boolean equals(StringLinkedList other) {
    // TODO 5. complete equals
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
    

