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

  

}
