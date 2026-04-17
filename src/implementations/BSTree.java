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


/**
 * Retrieves the root node of the tree.
 * 
 * @return the root node of the tree
 * @throws NullPointerException if the tree is empty
 */
  @Override
  public BSTreeNode<E> getRoot() throws NullPointerException {
    if (root == null){
      throw new NullPointerException("The tree is empty");
    }
    return root;
  }


/**
 * Returns the height of the tree.
 * 
 * @return the height of the tree
 */

  @Override
  public int getHeight() {
    return height(root);
  }


/**
 * Computes the height of a binary search tree given a root node.
 *
 * @param node the root node of the tree
 * @return the height of the tree
 */

  private int height(BSTreeNode<E> node) {
    if (node == null) return 0;
    int leftHeight = height(node.getLeft());
    int rightHeight = height(node.getRight());
    return Math.max(leftHeight, rightHeight) + 1;
  }

/**
 * Returns the number of elements currently stored in the tree.
 * 
 * @return the number of elements currently stored in the tree
 */
  @Override
  public int size() {
    return size;
  }

/**
 * Checks if the tree is currently empty.
 * 
 * @return returns boolean true if the tree is empty otherwise false
 */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

/**
 * Clears all elements currently stored in the tree. All references to the elements are
 * lost. The tree size is reset to zero.
 * 
 * Postcondition: size is reset to 0 and root is set to null.
 */
  @Override
  public void clear() {
    root = null;
    size = 0;
  }

/**
 * Checks if the tree contains a given element.
 * 
 * @param entry the element to find in the tree
 * @return true if the tree contains the element, otherwise false
 * @throws NullPointerException if the element to find is null
 */
  @Override
  public boolean contains(E entry) throws NullPointerException {
    if (entry == null){
      throw new NullPointerException("Entry is null");
    }
    return search(entry) != null;
  }

/**
 * Retrieves a node from the tree given the object to search for.
 * 
 * @param entry element object being searched
 * @return the node with the element located in tree, null if not found
 * @throws NullPointerException if the element being passed in is null
 */
  @Override
  public BSTreeNode<E> search(E entry) throws NullPointerException {
    if (entry == null){
      throw new NullPointerException("Entry is null");
    }
    BSTreeNode<E> current = root;
    while (current != null){
      int cmpare = entry.compareTo(current.getElement());
      if (cmpare < 0) current = current.getLeft();
      else if (cmpare > 0) current = current.getRight();
      else return current;
    }
    return null;
  }

/**
 * Adds a new element to the tree according to the natural ordering established
 * by the Comparable implementation.
 * 
 * @param newEntry the element to add to the tree
 * @return true if the element is added successfully, otherwise false
 * @throws NullPointerException if the element to add is null
 */
  @Override
  public boolean add(E newEntry) throws NullPointerException {
      if (newEntry == null) throw new NullPointerException("Entry cannot be null.");
      if (root == null) {
          root = new BSTreeNode<>(newEntry);
          size++;
          return true;
      }
      BSTreeNode<E> current = root;
      while (true) {
          int cmpare = newEntry.compareTo(current.getElement());
          if (cmpare < 0) {
              if (current.getLeft() == null) {
                  current.setLeft(new BSTreeNode<>(newEntry));
                  size++;
                  return true;
              }
              current = current.getLeft();
          } else if (cmpare > 0) {
              if (current.getRight() == null) {
                  current.setRight(new BSTreeNode<>(newEntry));
                  size++;
                  return true;
              }
              current = current.getRight();
          } else {
              return false; // duplicate rejected
          }
      }
  }

	/**
	 * Removes the smallest element in the tree according to the natural ordering
	 * established by the Comparable implementation.
	 * 
	 * @return the removed element or null if the tree is empty
	 */
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

	/**
	 * Removes the largest element in the tree according to the natural ordering
	 * established by the Comparable implementation.
	 * 
	 * @return the removed element or null if the tree is empty
	 */
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

	/**
	 * Returns an iterator that iterates over the elements in the tree in
	 * ascending order according to the natural ordering of the elements
	 * established by the Comparable interface.
	 * 
	 * @return an iterator that iterates over the elements in the tree in
	 * ascending order.
	 */
  @Override
  public Iterator<E> inorderIterator() {
    return new BSTreeIterator(TraversalOrder.INORDER);
  }

	/**
	 * Returns an iterator that iterates over the elements in the tree in
	 * pre-order traversal order. The root node is visited first, then
	 * the left subtree and finally the right subtree.
	 * 
	 * @return an iterator that iterates over the elements in the tree in
	 * pre-order traversal order.
	 */
  @Override
  public Iterator<E> preorderIterator() {
    return new BSTreeIterator(TraversalOrder.PREORDER);
  }


/**
 * Returns an iterator that iterates over the elements in the tree in
 * post-order traversal order. The left subtree is visited first, then
 * the right subtree and finally the root node.
 * 
 * @return an iterator that iterates over the elements in the tree in
 * post-order traversal order.
 */
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

/**
 * Recursively traverses the tree in in-order traversal order, adding each
 * element to the list as it is visited. The left subtree is visited first,
 * then the current node, and finally the right subtree.
 * @param node the node to start the traversal from
 */
    private void inorder(BSTreeNode<E> node){
      if (node == null) return;
      inorder(node.getLeft());
      list.add(node.getElement());
      inorder(node.getRight());
    }

/**
 * Recursively traverses the tree in pre-order traversal order, adding each
 * element to the list as it is visited. The current node is visited first,
 * then the left subtree, and finally the right subtree.
 * @param node the node to start the traversal from
 */
    private void prereorder(BSTreeNode<E> node){
      if (node == null) return;
      list.add(node.getElement());
      prereorder(node.getLeft());
      prereorder(node.getRight());
    }

/**
 * Recursively traverses the tree in post-order traversal order, adding each
 * element to the list as it is visited. The left subtree is visited first,
 * then the right subtree, and finally the current node.
 * @param node the node to start the traversal from
 */
    private void postorder(BSTreeNode<E> node){
      if (node == null) return;
      postorder(node.getLeft());
      postorder(node.getRight());
      list.add(node.getElement());
    }
/**
 * Returns true if the iterator has more elements to iterate over.
 * 
 * @return true if the iterator has more elements, otherwise false
 */
    @Override
    public boolean hasNext() {
      return !list.isEmpty();
    }

/**
 * Returns the next element in the iteration.
 * 
 * @return the next element in the iteration.
 * @throws NoSuchElementException if there are no more elements to iterate over
 */
    @Override
    public E next() throws NoSuchElementException {
      if (list.isEmpty()){
        throw new NoSuchElementException("No more elements in the iterator");
      }
      return list.remove(0);
    }
  }
}