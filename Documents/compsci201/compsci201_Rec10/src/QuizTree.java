import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Program output valid only AFTER you have correctly completed all of the methods in BST.
 * 
 * Do not change this class or quiztree.txt.
 * 
 * @author Austin Lu
 *
 */
public class QuizTree {
  public static void main(String[] args) throws FileNotFoundException {
    BST bst = new BST();
    Scanner scanner = new Scanner(new File("quiztree.txt"));
    while (scanner.hasNextInt()) {
      bst.add(scanner.nextInt());
    }
    scanner.close();
    System.out.println("=== YOUR QUIZ TREE OUTPUT (valid only after BST.java completed) ===");
    System.out.printf("Total number of nodes: %d\n", bst.countNodes());
    System.out.printf("Tree height: %d\n", bst.computeHeight());
    System.out.printf("Number of leaves: %d\n", bst.numLeaves());
    System.out.printf("Number of nodes on level 20: %d\n", bst.levelCount(2));
    System.out.println("Has path sum 103: " + bst.hasPathSum(103));
    System.out.println("Has path sum 146: " + bst.hasPathSum(142));
    System.out.printf("The value in the 123rd smallest node is: %d\n", bst.findKth(123));
  }
}
