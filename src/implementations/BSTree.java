package implementations;

import java.util.ArrayList;
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
    if (root == null){
      throw new NullPointerException("The tree is empty");
    }
    return root;
  }

  @Override
  public int getHeight() {
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'getHeight'");
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  @Override
  public boolean contains(E entry) throws NullPointerException {
    if (entry == null){
      throw new NullPointerException("Entry is null");
    }
    return search(entry) != null;
  }

  @Override
  public BSTreeNode<E> search(E entry) throws NullPointerException {
    if (entry == null){
      throw new NullPointerException("Entry is null");
    }
    BSTreeNode<E> current = root;
    while (current != null){
      int compar = entry.compareTo(current.getElement());
      if (compar < 0) current = current.getLeft();
      else if (compar > 0) current = current.getRight();
      else return current;
    }
    return null;
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
    if (root == null) {
    	return null;
    }
    BSTreeNode<E> findMin = root;
    while(findMin.getLeft().getLeft() != null) {
    	findMin = findMin.getLeft();
    }
    BSTreeNode<E> min = findMin.getLeft();
    if (findMin.getLeft().getRight() == null) {
    	findMin.setLeft(null); 
    	return min;
    }
    findMin.setLeft(min.getRight());
    return min;
  }

  @Override
  public BSTreeNode<E> removeMax() {
	  if (root == null) {
	    	return null;
	    }
	    BSTreeNode<E> findMin = root;
	    while(findMin.getRight().getRight() != null) {
	    	findMin = findMin.getRight();
	    }
	    BSTreeNode<E> min = findMin.getRight();
	    if (findMin.getRight().getLeft() == null) {
	    	findMin.setRight(null); 
	    	return min;
	    }
	    findMin.setRight(min.getLeft());
	    return min;
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

    private ArrayList<E> list = new ArrayList<>();
    public BSTreeIterator(TraversalOrder order) {
      
      switch (order) {
        case INORDER: walkInorder(root);
          break;
        case PREORDER: walkPreorder(root);
          break;
        case POSTORDER: walkPostorder(root);
          break;
      }
    }

    private void walkInorder(BSTreeNode<E> node){
      if (node == null) return;
      walkInorder(node.getLeft());
      list.add(node.getElement());
      walkInorder(node.getRight());
    }

    private void walkPreorder(BSTreeNode<E> node){
      if (node == null) return;
      list.add(node.getElement());
      walkPreorder(node.getLeft());
      walkPreorder(node.getRight());
    }

    private void walkPostorder(BSTreeNode<E> node){
      if (node == null) return;
      walkPostorder(node.getLeft());
      walkPostorder(node.getRight());
      list.add(node.getElement());
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