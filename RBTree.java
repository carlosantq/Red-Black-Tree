public class RBTree{
	private RBNode root;

	/**
	 * Constructor
	 */
	public RBTree(){
		this.root = null;
	}

	/**
	 * Inserts a new node into the tree.
	 * @param root Root of the current subtree.
	 * @param value Number to be stored into the node.
	 */
	public boolean insert(RBNode root, int value){

		if (root == null){
			this.root = new RBNode(value);
			return true;
		}

		if (value > root.getValue()){
			if (root.getRightSon() == null){
				root.setRightSon(new RBNode(value));
				root.getRightSon().setColor(Color.RED);

				root.getRightSon().setFather(root);

				root = root.getRightSon();
				insert1(root);

			}else{
				root = root.getRightSon();
				insert(root, value);
			}
		}else if (value < root.getValue()){
			if (root.getLeftSon() == null){
				root.setLeftSon(new RBNode(value));
				root.getLeftSon().setColor(Color.RED);
				
				root.getLeftSon().setFather(root);
				
				root = root.getLeftSon();
				insert1(root);

			}else{
				root = root.getLeftSon();
				insert(root, value);
			}
		}else{
			System.out.println("This number is already a member of this tree.");
			return false;
		}

		return true;
	}

	/**
	 * Case 1: The root of the Red-Black Tree has the color Red.
	 * @param currentNode The current node being analysed.
	 */
	private void insert1(RBNode currentNode){
		if (currentNode.getFather() == null){
			currentNode.setColor(Color.BLACK);
		}else{
			insert2(currentNode);
		}
	}

	/**
	 * Case 2: The current node's parent is not Black, so the analysis keep going...
	 * @param currentNode The current node being analysed.
	 */
	private void insert2(RBNode currentNode){
		if (currentNode.getFather().getColor() != Color.BLACK){
			insert3(currentNode);
		}
	}

	/**
	 * Case 3: The uncle and/or the parent of the current node are red, so they need to be painted black and the 
	 * grandfather is painted red. If the grandfather is the root of the tree, it is repainted black.
	 * @param currentNode The current node being analysed.
	 */
	private void insert3(RBNode currentNode){
		RBNode uncle = currentNode.uncle();
		RBNode grandfather = currentNode.grandfather();

		if (uncle != null && uncle.getColor() == Color.RED){
			uncle.setColor(Color.BLACK);
			currentNode.getFather().setColor(Color.BLACK);
			grandfather.setColor(Color.RED);
			insert1(grandfather);
		}else{
			insert4(currentNode);
		}
	}

	/**
	 * Case 4: The current node and its parent are both red, so a rotation is needed in the father.
	 * @param currentNode The current node being analysed.
	 */
	private void insert4(RBNode currentNode){

		RBNode grandfather = currentNode.grandfather();
		RBNode father = currentNode.getFather();

		if (currentNode == father.getRightSon() && father == grandfather.getLeftSon()){
			
			rotate_left(currentNode.getFather());

			currentNode = currentNode.getLeftSon();

		}else if (currentNode == father.getLeftSon() && father == grandfather.getRightSon()){

			rotate_right(currentNode.getFather());

			currentNode = currentNode.getRightSon();
		}
		insert5(currentNode);
	}

	/**
	 * Case 5: The current node and its parent are still both red, so the parent 
	 * is painted black, the grandfather is painted red and a new rotation occurs 
	 * in the grandfather according to the position of the current node.
	 * @param currentNode The current node being analysed.
	 */
	private void insert5(RBNode currentNode){

		RBNode grandfather = currentNode.grandfather();

		currentNode.getFather().setColor(Color.BLACK);
		grandfather.setColor(Color.RED);
		if (currentNode == currentNode.getFather().getLeftSon()){
			rotate_right(grandfather);
		}else{
			rotate_left(grandfather);
		}
	}

	/**
	 * Rotates a node and its connections to the right.
	 * @param currentNode The current node being analysed.
	 */
	private void rotate_right(RBNode node){

		RBNode saved_leftSon_rightSon = node.getLeftSon().getRightSon();

		node.getLeftSon().setRightSon(node); 
		node.getLeftSon().setFather(node.getFather());

		if (node.getFather() != null && node == node.getFather().getRightSon()){
			node.getFather().setRightSon(node.getLeftSon());
		}else if (node.getFather() != null && node == node.getFather().getLeftSon()){
			node.getFather().setLeftSon(node.getLeftSon());
		}

		node.setFather(node.getLeftSon());

		node.setLeftSon(saved_leftSon_rightSon);

		changeRoot(node);

	}

	/**
	 * Removes a node from the tree.
	 * @param root Root of the current subtree.
	 * @param value Number to be deleted from the tree.
	 */
	public boolean remove(RBNode root, int value) {

		boolean returnInformation = false;
		
		// The element isn't into the tree
		if (root.getLeftSon() == null && root.getRightSon() == null && value != root.getValue()) {
			
			returnInformation = false;
		}else if (value > root.getValue()) {
			remove(root.getRightSon(), value);
		}else if (value < root.getValue()) {
			remove(root.getLeftSon(), value);
		}else{						
			// Removes a leaf node
			if (root.getLeftSon() == null && root.getRightSon() == null) {
				// The node is at the left
				if (root.getFather().getLeftSon() == root) {
					if (root.getColor() == Color.BLACK){
						fixRemove(root);
					}
					root.getFather().setLeftSon(null);
				}
				// The node is at the right
				else if (root.getFather().getRightSon() == root) {
					if (root.getColor() == Color.BLACK){
						fixRemove(root);
					}
					root.getFather().setRightSon(null);
				}
				
				returnInformation = true;
			}
			// Removes a node with one left son
			else if (root.getLeftSon() != null && root.getRightSon() == null) {
				// This node is at the left
				if (root.getFather().getLeftSon() == root) {
					if (root.getColor() == Color.BLACK){
						fixRemove(root);
					}
					root.getFather().setLeftSon(root.getLeftSon());
					root.getLeftSon().setFather(root.getFather());
				}
				// The node is at the right
				else if (root.getFather().getRightSon() == root) {
					if (root.getColor() == Color.BLACK){
						fixRemove(root);
					}
					root.getFather().setRightSon(root.getLeftSon());
					root.getLeftSon().setFather(root.getFather());
				}
				
				returnInformation = true;
			}
			// Removes a node with one right son
			else if (root.getLeftSon() == null && root.getRightSon() != null) {
				// The node is at the left
				if (root.getFather().getLeftSon() == root) {
					if (root.getColor() == Color.BLACK){
						fixRemove(root);
					}
					root.getFather().setLeftSon(root.getRightSon());
					root.getLeftSon().setFather(root.getFather());
				}
				// The node is at the right
				else if (root.getFather().getRightSon() == root) {
					if (root.getColor() == Color.BLACK){
						fixRemove(root);
					}
					root.getFather().setRightSon(root.getRightSon());
					root.getRightSon().setFather(root.getFather());
				}
				
				returnInformation = true;
			}
			
			// Removes a node with two sons
			else {
				//Removes the root of the tree or a node at the right
				if (root.getFather() == null || root.getFather().getRightSon() == root){
					RBNode biggest = biggest(root.getLeftSon());

					if (biggest.getColor() == Color.BLACK){
						fixRemove(biggest);
					}

					root.setValue(biggest.getValue());

					if (root.getLeftSon() == biggest){
						root.setLeftSon(root.getLeftSon().getLeftSon());
					}else{
						biggest.getFather().setRightSon(null);
					}
				}
				// Node is at the left
				else if (root.getFather().getLeftSon() == root){
					RBNode smallest = smallest(root.getRightSon());

					if (smallest.getColor() == Color.BLACK){
						fixRemove(smallest);
					}

					root.setValue(smallest.getValue());

					if (root.getRightSon() == smallest){
						root.setRightSon(root.getRightSon().getRightSon());
					}else{
						smallest.getFather().setLeftSon(null);
					}
				}

				returnInformation = true;
			}
		}
		
		return returnInformation;
	}

	/**
	 * Returns the biggest number of a subtree.
	 * @param node Root of the current subtree.
	 */
	private RBNode biggest(RBNode node){
		if (node.getRightSon() != null){
			return biggest(node.getRightSon());
		}else{
			return node;
		}
	}

	/**
	 * Returns the smallest number of a subtree.
	 * @param node Root of the current subtree.
	 */
	private RBNode smallest(RBNode node){
		if (node.getLeftSon() != null){
			return smallest(node.getLeftSon());
		}else{
			return node;
		}
	}

	/**
	 * Fix the Red-Black Tree properties after removal of a node.
	 * @param node Root of the current subtree.
	 */
	private void fixRemove(RBNode node){
		//Node is at left
		if (node.getFather().getLeftSon() == node){
			//Case 1
			if (node.getFather().getRightSon() != null && node.getFather().getRightSon().getColor() == Color.RED){
				node.getFather().getRightSon().setColor(Color.BLACK);
				node.getFather().setColor(Color.RED);
				rotate_left(node.getFather());
			//Case 2
			}else if(node.getFather().getRightSon() != null && node.getFather().getRightSon().getColor() == Color.BLACK){
				//Case 2.1
				if (node.getFather().getRightSon().getLeftSon() != null && node.getFather().getRightSon().getLeftSon().getColor() == Color.BLACK && 
					node.getFather().getRightSon().getRightSon() != null && node.getFather().getRightSon().getRightSon().getColor() == Color.BLACK){
					node.getFather().getRightSon().setColor(Color.RED);
					node.getFather().setColor(Color.BLACK);
				}

				//Case 2.2
				else if (node.getFather().getRightSon().getLeftSon() != null && node.getFather().getRightSon().getLeftSon().getColor() == Color.RED && 
					node.getFather().getRightSon().getRightSon() != null && node.getFather().getRightSon().getRightSon().getColor() == Color.BLACK){
					node.getFather().getRightSon().getLeftSon().setColor(node.getFather().getRightSon().getColor());
					node.getFather().getRightSon().setColor(Color.RED);
					rotate_right(node.getFather().getRightSon());

				//Case 2.3
				}else if (node.getFather().getRightSon().getRightSon() != null && node.getFather().getRightSon().getRightSon().getColor() == Color.RED){
					node.getFather().getRightSon().setColor(node.getFather().getColor());
					node.getFather().setColor(Color.BLACK);
					node.getFather().getRightSon().getRightSon().setColor(Color.BLACK);
					rotate_left(node.getFather());
				}
			}
		//Node is at right
		}else if (node.getFather().getRightSon() == node){

			//Case 1
			if (node.getFather().getLeftSon() != null && node.getFather().getLeftSon().getColor() == Color.RED){
				System.out.println("1");
				node.getFather().getLeftSon().setColor(Color.BLACK);
				node.getFather().setColor(Color.RED);
				rotate_right(node.getFather());
			}
			//Case 2
			else if (node.getFather().getLeftSon() != null && node.getFather().getLeftSon().getColor() == Color.BLACK){
				//Case 2.1
				if (node.getFather().getLeftSon().getLeftSon() != null && node.getFather().getLeftSon().getLeftSon().getColor() == Color.BLACK && 
					node.getFather().getLeftSon().getRightSon() != null && node.getFather().getLeftSon().getRightSon().getColor() == Color.BLACK){
					node.getFather().getLeftSon().setColor(Color.RED);
					node.getFather().setColor(Color.BLACK);
				}

				//Case 2.2
				else if (node.getFather().getLeftSon().getLeftSon() != null && node.getFather().getLeftSon().getLeftSon().getColor() == Color.RED && 
					node.getFather().getLeftSon().getRightSon() != null && node.getFather().getLeftSon().getRightSon().getColor() == Color.BLACK){
					node.getFather().getLeftSon().getLeftSon().setColor(node.getFather().getLeftSon().getColor());
					node.getFather().getLeftSon().setColor(Color.RED);
					rotate_left(node.getFather().getLeftSon());
				}

				//Case 2.3
				else if (node.getFather().getLeftSon().getLeftSon() != null && node.getFather().getLeftSon().getLeftSon().getColor() == Color.RED){
					node.getFather().getLeftSon().setColor(node.getFather().getColor());
					node.getFather().setColor(Color.BLACK);
					node.getFather().getLeftSon().getLeftSon().setColor(Color.BLACK);
					rotate_right(node.getFather());

				}
			}
		}
	}

	/**
	 * Rotates a node andi ts connections to the left.
	 * @param currentNode The current node being analysed.
	 */
	private void rotate_left(RBNode node){

		RBNode saved_rightSon_leftSon = node.getRightSon().getLeftSon();

		node.getRightSon().setLeftSon(node); 
		node.getRightSon().setFather(node.getFather());

		if (node.getFather() != null && node == node.getFather().getLeftSon()){
			node.getFather().setLeftSon(node.getRightSon());
		}else if (node.getFather() != null && node == node.getFather().getRightSon()){
			node.getFather().setRightSon(node.getRightSon());
		}
		node.setFather(node.getRightSon());

		node.setRightSon(saved_rightSon_leftSon);

		changeRoot(node);

	}

	/**
	 * Changes the root of the tree. This method is necessary when a rotation 
	 * occurs and the root of the tree is changed.
	 * @param currentNode The current node being analysed.
	 */
	private void changeRoot(RBNode node){
		if (node.getFather() != null){
			changeRoot(node.getFather());
		}else{
			this.root = node;
		}
	}

	/**
	 * Searches for a number into the tree.
	 * @param root Root of the current subtree.
	 * @param value Number to be searched.
	 * @return true (number found) or false (number not found)
	 */
	public boolean search(RBNode root, int value){

		boolean returnInformation = false;

		if (root == null){
			returnInformation = false;
		}else if (value != root.getValue() && root.getRightSon() == null && root.getLeftSon() == null){
			returnInformation = false;
		}else if (value == root.getValue()){
			returnInformation = true;
		}else if (value > root.getValue()){
			return search(root.getRightSon(), value);
		}else if (value < root.getValue()){
			return search(root.getLeftSon(), value);
		}

		return returnInformation;
	}

	/**
	 * Prints the tree.
	 */
	public void print(RBNode root){
		System.out.println("> Node: " + root.getValue());
		
		if (root.getFather() != null) {
			System.out.println("\tFather: " + root.getFather().getValue());
		}else {
			System.out.println("\tFather: null");
		}
		
		if (root.getLeftSon() != null) {
			System.out.println("\tLeft Son: " + root.getLeftSon().getValue());
		}else {
			System.out.println("\tLeft Son: null");
		}

		if (root.getRightSon() != null) {
			System.out.println("\tRight Son: " + root.getRightSon().getValue());
		}else {
			System.out.println("\tRight Son: null");
		}
		
		if (root.grandfather() != null){
			System.out.println("\tGrandfather: " + root.grandfather().getValue());
		}else{
			System.out.println("\tGrandfather: null");
		}

		if (root.uncle() != null){
			System.out.println("\tUncle: " + root.uncle().getValue());
		}else{
			System.out.println("\tUncle: null");
		}

		System.out.println("\tColor: " + root.getColor());
		
		if (root.getLeftSon() != null) {
			print(root.getLeftSon());
		}
		if (root.getRightSon() != null) {
			print(root.getRightSon());
		}

	}

	/*
	 * Get the root of the tree.
	 */
	public RBNode getRoot(){
		return this.root;
	}
}