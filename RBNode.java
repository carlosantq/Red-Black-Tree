/**
 * Red-Black Tree Node Class
 * @author carlosant
 */
public class RBNode{

	//Attributes
	private int value;
	private RBNode father;
	private RBNode leftSon;
	private RBNode rightSon;
	private Color color;

	/**
	 * Constructor
	 * @param value Number to be stored into the node.
	 */
	public RBNode(int value){
		this.value = value;
		father = null;
		leftSon = null;
		rightSon = null;
		color = Color.BLACK;
	}

	public RBNode getFather(){
		return this.father;
	}

	public RBNode getLeftSon(){
		return this.leftSon;
	}

	public RBNode getRightSon(){
		return this.rightSon;
	}

	public int getValue(){
		return this.value;
	}

	public Color getColor(){
		return this.color;
	}

	public void setFather(RBNode father){
		this.father = father;
	}

	public void setValue(int value){
		this.value = value;
	}

	public void setLeftSon(RBNode leftSon){
		this.leftSon = leftSon;
	}

	public void setRightSon(RBNode rightSon){
		this.rightSon = rightSon;
	}

	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * Searches for the grandfather of a node.
	 * @return RBNode containing the grandfather if the node has one or null if not.
	 */
	public RBNode grandfather(){
		if (this != null && this.getFather() != null){
			return this.getFather().getFather();
		}else{
			return null;
		}
	}

	/**
	 * Searches for the uncle of a node.
	 * @return RBNode containg the uncle if the node has one or null if not.
	 */
	public RBNode uncle(){
		if (this.grandfather() == null){
			return null;
		}

		if (this.getFather() == this.grandfather().getLeftSon()){
			return this.grandfather().getRightSon();
		}else{
			return this.grandfather().getLeftSon();
		}
	}
}