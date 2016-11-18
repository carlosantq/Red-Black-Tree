import java.util.Scanner;

/**
 * Test Class
 * @author carlosant
 */
public class Main{
	public static void main (String [] args){
		Scanner scanner;
		int root;
		int n;
		int node;
		int i = 1;
		
		System.out.println("Insert the value of the root of the tree: ");
		scanner = new Scanner(System.in);
		root = scanner.nextInt();

		RBTree tree  = new RBTree();
		tree.insert(tree.getRoot(), root);

		System.out.println("Insert the number of nodes of this tree: ");
		scanner = new Scanner(System.in);
		n = scanner.nextInt();

		while (n >= i){
			System.out.println("Insert the number that will be at the position number " + i);
			scanner = new Scanner(System.in);
			node = scanner.nextInt();
			if (tree.insert(tree.getRoot(), node) == true){
				i++;
			}
		}

		tree.print(tree.getRoot());

		System.out.println("Insert the number that you're looking for: ");
		scanner = new Scanner(System.in);
		node = scanner.nextInt();

		System.out.println((tree.search(tree.getRoot(), node)) ? "The number " + node + " was found into the tree!" : "The number " + node + " wasn't found!");

		System.out.println("Insert the number that will be deleted from the tree: ");
		scanner = new Scanner(System.in);
		node = scanner.nextInt();

		tree.remove(tree.getRoot(), node);

		tree.print(tree.getRoot());		
	}
}