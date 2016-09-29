/**
 * COMPSCI 201 Recitation 6, Spring 2015
 * 
 * Iterative Linked Lists: More Practice!
 * 
 * @author Austin Lu
 */
public class CharLinkedList {

  private class Node {
    char myValue;
    Node myNext;

    Node(char value, Node next) {
      myValue = value;
      myNext = next;
    }
  }

  /**
   * The head "pointer"
   */
  Node myHead;
  
  /**
   * Number of nodes in the linked list
   */
  int myLength;

  /**
   * Adds a new node the end of the linked list
   * 
   * @param valueToAdd value of the new node to add
   */
  public void addToEnd(char valueToAdd) {
    // Completed for you -- be sure to understand it!
    myLength++;
    if (myHead == null) {
      myHead = new Node(valueToAdd, null);
      return;
    }
    Node curr = myHead;
    while (curr.myNext != null) {
      curr = curr.myNext;
    }
    curr.myNext = new Node(valueToAdd, null);
  }

  @Override
  public String toString() {
    // Completed for you -- be sure to understand it!
    Node curr = myHead;
    String ret = "";
    if (curr == null) {
      return ret;
    }
    while (curr != null) {
      ret += curr.myValue + " ";
      curr = curr.myNext;
    }
    return ret.substring(0, ret.length() - 1);
  }


  /**
   * Insert a new node at index k of the linkedlist (0-based indexing). E.g. Inserting at k=0 is the
   * same as adding to the beginning of the list.
   * 
   * If k >= length of the linked list, add the new node to the end of the linked list.
   * 
   * @param k index to insert at
   * @param valueToInsert value of the new node to insert
   */
  public void insertAtIndex(int k, char valueToInsert) {
    // TODO 1. Complete insertAtIndex(int k)
	  
	  Node curr = myHead;
	  int index = 0;
	  if(curr != null){
		  return;
	  }
	  if(k == 0){
		  Node newHead = new Node(valueToInsert, curr);
		  newHead.myValue = valueToInsert;
		  System.out.println(newHead.myValue);
		  System.out.println(newHead.myNext.myValue);
		  newHead = myHead;
		  return;
	  }
	  while(curr != null){
		  index++;
		  curr = curr.myNext;
		  if(index == k-1){
			  curr.myNext = new Node(valueToInsert, curr.myNext);
			  break;
		  }
	  }
  }

  
  /**
   * Count the number of unique characters in the linked list If the linked list is empty, return 0.
   */
  public int countUniqueChars() {
    // TODO 2. Complete countUniqueChars()

    return 0;
  }

  /**
   * Remove all nodes of duplicate values from the list, keeping only the first. If the list is
   * empty, do nothing.
   */
  public void removeDuplicates() {
    // TODO 3. Complete removeDuplicates().

  }

  /**
   * Returns number of nodes in the linked list.
   */
  public int getLength() {
    // TODO: 4. Complete getLength as efficiently as possible (involves updating myLength in other methods).
    return 0;
  }
}
