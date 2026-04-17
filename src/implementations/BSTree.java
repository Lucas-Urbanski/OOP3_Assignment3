package implementations;

import java.util.NoSuchElementException;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {

  private BSTreeNode<E> root;
  private int size;

  private enum TraversalOrder {
    INORDER, PREORDER, POSTORDER
  }

  public BSTree() {
    root = null;
    size = 0;
  }
  
  public BSTree(E firstNode) {
	root = new BSTreeNode<E>(firstNode);
	size = 1;
  }

  @Override
  public BSTreeNode<E> getRoot() throws NullPointerException {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'getRoot'");
  }

  @Override
  public int getHeight() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'getHeight'");
  }

  @Override
  public int size() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'size'");
  }

  @Override
  public boolean isEmpty() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
  }

  @Override
  public void clear() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'clear'");
  }

  @Override
  public boolean contains(E entry) throws NullPointerException {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'contains'");
  }

  @Override
  public BSTreeNode<E> search(E entry) throws NullPointerException {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'search'");
  }

  @Override
  public boolean add(E newEntry) throws NullPointerException {
	  if (newEntry == null)
	  {
			throw new NullPointerException("Null elements are not allowed.");
	  }
	  BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry);
	  if (root == null) 
	  {
		  root = newNode;
		  return true;
	  }
	  if (newNode.getElement().compareTo(root.getElement()) > 0)
	  {
		  newNode.setRight(root);
		  root = newNode;
		  return true;
	  } 
	  else if (newNode.getElement().compareTo(root.getElement()) < 0) {
		  root.setLeft(newNode);
		  return true;
	  }
	  return false;
  }

  @Override
  public BSTreeNode<E> removeMin() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'removeMin'");
  }

  @Override
  public BSTreeNode<E> removeMax() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'removeMax'");
  }

  @Override
  public Iterator<E> inorderIterator() {
    return new BSTreeIterator(TraversalOrder.INORDER);
  }

  @Override
  public Iterator<E> preorderIterator() {
    return new BSTreeIterator(TraversalOrder.PREORDER);
  }

  @Override
  public Iterator<E> postorderIterator() {
    return new BSTreeIterator(TraversalOrder.POSTORDER);
  }

  private class BSTreeIterator implements Iterator<E> {

    public BSTreeIterator(TraversalOrder order) {
      // TODO: walk the tree according to 'order'
    }

    @Override
    public boolean hasNext() {
      // TODO
      return false;
    }

    @Override
    public E next() throws NoSuchElementException {
      // TODO
      return null;
    }
  }
}