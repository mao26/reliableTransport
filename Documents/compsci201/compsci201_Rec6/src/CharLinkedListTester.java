import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Run this unit test to check your progress
 * 
 * @author Austin Lu
 *
 */
public class CharLinkedListTester {
  CharLinkedList alpha;
  CharLinkedList singleNode;
  CharLinkedList empty;
  CharLinkedList duplicates;

  @Before
  public void setUp() {
    alpha = new CharLinkedList();
    alpha.addToEnd('a');
    alpha.addToEnd('b');
    alpha.addToEnd('c');
    alpha.addToEnd('d');
    alpha.addToEnd('e');

    singleNode = new CharLinkedList();
    singleNode.addToEnd('x');

    empty = new CharLinkedList();
    
    duplicates = new CharLinkedList();
    duplicates.addToEnd('i');
    duplicates.addToEnd('k');
    duplicates.addToEnd('i');
    duplicates.addToEnd('i');
    duplicates.addToEnd('k');
    duplicates.addToEnd('j');
  }

  @Test
  public void testInsertAtIndex() {
    alpha.insertAtIndex(0, 'z');
    assertEquals("z a b c d e", alpha.toString());

    alpha.insertAtIndex(3, 'z');
    assertEquals("z a b z c d e", alpha.toString());

    alpha.insertAtIndex(7, 'z');
    assertEquals("z a b z c d e z", alpha.toString());

    alpha.insertAtIndex(99, 'z');
    assertEquals("z a b z c d e z z", alpha.toString());

    empty.insertAtIndex(0, 'z');
    assertEquals("z", empty.toString());

    empty = new CharLinkedList(); // reset to empty
    empty.insertAtIndex(1, 'z');
    assertEquals("z", empty.toString());
  }

  @Test
  public void testCountUniqueChars() {
    assertEquals(0, empty.countUniqueChars());
    assertEquals(5, alpha.countUniqueChars());
    assertEquals(1, singleNode.countUniqueChars());
    assertEquals(3, duplicates.countUniqueChars());
  }

  @Test
  public void testremoveDuplicates() {
    alpha.removeDuplicates();
    singleNode.removeDuplicates();
    duplicates.removeDuplicates();
    empty.removeDuplicates();

    assertEquals("a b c d e", alpha.toString());
    assertEquals("x", singleNode.toString());
    assertEquals("i k j", duplicates.toString());
    assertNull(empty.myHead);
  }
  
  @Test
  public void testGetLength() {
    assertEquals(0, empty.getLength());
    assertEquals(6, duplicates.getLength());
    duplicates.insertAtIndex(0, 'z');
    assertEquals(7, duplicates.getLength());
    duplicates.removeDuplicates();
    assertEquals(4, duplicates.getLength());
  }
}
