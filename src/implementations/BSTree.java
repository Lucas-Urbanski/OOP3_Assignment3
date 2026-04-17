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
    // TODO
    throw new UnsupportedOperationException("Unimplemented method 'add'");
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