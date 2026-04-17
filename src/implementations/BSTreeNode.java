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

}
