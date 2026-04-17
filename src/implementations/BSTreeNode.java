package implementations;

public class BSTreeNode<E> {
  private E data;
  private BSTreeNode<E> left;
  private BSTreeNode<E> right;

  public BSTreeNode(E data) {
    this.data = data;
    this.left = null;
    this.right = null;
  }

  public BSTreeNode(E data, BSTreeNode<E> left, BSTreeNode<E> right) {
    this.data = data;
    this.left = left;
    this.right = right;
  }

/**
 * Returns the left child of this node.
 * @return the left child of this node
 */
  public BSTreeNode<E> getLeft(){
	  return this.left;
  }
  
/**
 * Returns the right child of this node.
 * @return the right child of this node
 */
  public BSTreeNode<E> getRight(){
	  return this.right;
  }

/**
 * Sets the left child of this node.
 * @param left the left child of this node
 */
  public void setLeft(BSTreeNode<E> left){
    this.left = left;
  }

/**
 * Sets the right child of this node.
 * @param right the right child of this node.
 */
  public void setRight(BSTreeNode<E> right){
    this.right = right;
  }
  
/**
 * Returns the element stored in this node.
 * @return the element stored in this node.
 */ 
  public E getElement(){
	  return this.data;
  }
}
