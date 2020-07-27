/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          Train Hub Project
// FILE:             LinkedList.java
//
// TEAM:    Individual
// Authors: Michael Ball
// Author1: Michael Ball, Mball4@wisc.edu, Mball4, Lecture 001
// Author2: X
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Teacher Assistants during TA consuling time
// 
// Online sources: Piazza 
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * An Iterable list that is implemented using a singly-linked chain of nodes
 * with a header node and without a tail reference.
 * 
 * The "header node" is a node without a data reference that will 
 * reference the first node with data once data has been added to list.
 * 
 * The iterator returned is a LinkedListIterator constructed by passing 
 * the first node with data.
 * 
 * CAUTION: the chain of nodes in this class can be changed without
 * calling the add and remove methods in this class.  So, the size()
 * method must be implemented by counting nodes.  This counting must
 * occur each time the size method is called.  DO NOT USE a numItems field.
 * 
 * COMPLETE THIS CLASS AND HAND IN THIS FILE
 */
public class LinkedList<E> implements ListADT<E> {
		
		Listnode<E> head; //Reference to the first node (Header node in this case)
		
		public LinkedList() {
			Listnode<E> header = new Listnode<E>(null); //We need a header node and construct one as the first node in our chain
			head = header;
		}
	
	
	/** 
	 * Returns a reference to the header node for this linked list.
	 * The header node is the first node in the chain and it does not 
	 * contain a data reference.  It does contain a reference to the 
	 * first node with data (next node in the chain). That node will exist 
	 * and contain a data reference if any data has been added to the list.
	 * 
	 * NOTE: Typically, a LinkedList would not provide direct access
	 * to the headerNode in this way to classes that use the linked list.  
	 * We are providing direct access to support testing and to 
	 * allow multiple nodes to be moved as a chain.
	 * 
	 * @return a reference to the header node of this list. 0
	 */
	public Listnode<E> getHeaderNode() {
		return head;
	}

	/**
	 * An iterator that we have created for our specific type of list is returned in place
	 * of the usual iterator for our program to use instead.
	 * 
	 * @return The iterator we will use
	 */
	public LinkedListIterator<E> iterator() {
		LinkedListIterator<E> iterator = new LinkedListIterator<E>(head.getNext()); //An iterator to be used
		//We want to start at the first node WITH data, therefore we use head.getNext();
		return iterator;
	}


	@Override
	/**
	 * This added an object of some kind to the end of the list.
	 * 
	 * @param An object of some kind to be added
	 */
	public void add(E item) {
		
		Listnode<E> tmp = new Listnode<E>(item); //An item that will be added
		Listnode<E> curr = head; //Our current node we are looking at
		
		while(curr.getNext() != null)//We iterate until the next spot is empty so we may add our node
			curr = curr.getNext();
		
		curr.setNext(tmp); //The node is added in the empty spot
	}


	@Override
	/**
	 * We add the item at a specific area the user wants it
	 * 
	 * @param The position we want to add it at
	 * @param The item to be added
	 * @throws The position is not found
	 */
	public void add(int pos, E item) {
		
		Listnode<E> tmp = new Listnode<E>(item); //The node to be added
		Listnode<E> curr = head; //Our current node we are looking at
		
		for(int i = 0; i <= pos - 1; i++){ //We find the position we want to add at
			if(curr.getNext() == null){ //If the position doesn't exist we throw an error
				throw new IllegalArgumentException();
			}
			curr = curr.getNext();
		}
		
		tmp.setNext(curr.getNext());
		curr.setNext(tmp); //Process to add
	}


	@Override
	/**
	 * We check to see if the specific object exists in the linked list
	 * 
	 * @param The item we are checking to see if its in the list
	 * @return A true or false value to tell us if its there or not
	 */
	public boolean contains(E item) {
		
		Listnode<E> curr = head; //The node we are currently looking at
		
		while (curr.getNext() != null){
			if(curr.getNext().getData().equals(item))
				return true;
			else
				curr = curr.getNext();
		}
		return false;
	}


	@Override
	/**
	 * We find and return the object at the designated position
	 * 
	 * @param The position we want to find the object at
	 * @throws An exception due to position not existing
	 * @return The object at the designated position
	 */
	public E get(int pos) {
		
		Listnode<E> curr = head; //The node we are currently looking at
		
		for (int i = 0; i <= pos; i++){
			
			if(curr.getNext() == null){ //The position doesn't exist
				throw new IllegalArgumentException();
			}
			
			curr = curr.getNext();
		}
		return curr.getData();
	}


	@Override
	/**
	 * We see if the list has objects or not
	 * 
	 * @return A true or false value of if items exist or not
	 */
	public boolean isEmpty() {
		
		return (head.getNext() == null);
	}


	@Override
	/**
	 * We remove the item at the designated position
	 * 
	 * @param The position of the item to be removed
	 * @throws an exception since the position doesn't exist
	 * @return The object that was removed
	 */
	public E remove(int pos) {
		Listnode<E> curr = head; //The current node we are looking at
		
		for (int i = 0; i < pos; i++){
			if(curr.getNext() == null){
				throw new IllegalArgumentException();
			}
			curr = curr.getNext();
		}
		
		Listnode<E> removed = curr.getNext();
		curr.setNext(curr.getNext().getNext()); //Process to remove and return the node
		return removed.getData();
	}


	@Override
	/**
	 * We return the size of the linked list, excluding our header node.
	 * 
	 * @return the integer of our list's size
	 */
	public int size() {
		
		Listnode<E> curr = head; //The node we are currently looking at.
		int listSize = 0; //The counter for all our nodes
		
		while(curr.getNext() != null){
			listSize++;
			curr = curr.getNext();
		}
			
		return listSize;
	}
}
