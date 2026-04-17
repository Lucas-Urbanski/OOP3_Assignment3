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
    return height(root);
  }

  private int height(BSTreeNode<E> node) {
    if (node == null) return 0;
    int leftHeight = height(node.getLeft());
    int rightHeight = height(node.getRight());
    return Math.max(leftHeight, rightHeight) + 1;
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

    private ArrayList<E> list = new ArrayList<>();
    public BSTreeIterator(TraversalOrder order) {
      
      switch (order) {
        case INORDER: inorder(root);
          break;
        case PREORDER: prereorder(root);
          break;
        case POSTORDER: postorder(root);
          break;
      }
    }

    private void inorder(BSTreeNode<E> node){
      if (node == null) return;
      inorder(node.getLeft());
      list.add(node.getElement());
      inorder(node.getRight());
    }

    private void prereorder(BSTreeNode<E> node){
      if (node == null) return;
      list.add(node.getElement());
      prereorder(node.getLeft());
      prereorder(node.getRight());
    }

    private void postorder(BSTreeNode<E> node){
      if (node == null) return;
      postorder(node.getLeft());
      postorder(node.getRight());
      list.add(node.getElement());
    }
    @Override
    public boolean hasNext() {
      return !list.isEmpty();
    }

    @Override
    public E next() throws NoSuchElementException {
      if (list.isEmpty()){
        throw new NoSuchElementException("No more elements in the iterator");
      }
      return list.remove(0);
    }
  }
}