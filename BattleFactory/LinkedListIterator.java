import java.util.Iterator;
import java.util.NoSuchElementException;
/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Fall 2017 
//PROJECT:          Train Hub Project
//FILE:             LinkedListIterator.java
//
//TEAM:    Individual
//Authors: Michael Ball
//Author1: Michael Ball, Mball4@wisc.edu, Mball4, Lecture 001
//Author2: X
//
//---------------- OTHER ASSISTANCE CREDITS 
//Persons: Teacher Assistants during TA consuling time
//
//Online sources: Piazza 
////////////////////////////80 columns wide //////////////////////////////////
/**
 * The iterator implementation for LinkedList.  The constructor for this
 * class requires that a reference to a Listnode with the first data
 * item is passed in.
 * 
 * If the Listnode reference used to create the LinkedListIterator is null,
 * that implies there is no data in the LinkedList and this iterator
 * should handle that case correctly.
 * 
 * COMPLETE THIS CLASS AND HAND IN THIS FILE
 */
public class LinkedListIterator<T> implements Iterator<T> {
	
	
	private Listnode<T> curr; //Our current node to look at
	

	/**
	 * Constructs a LinkedListIterator when given the first node
	 * with data for a chain of nodes.
	 * 
	 * Tip: do not construct with a "blank" header node.
	 * 
	 * @param a reference to a List node with data. 
	 */
	public LinkedListIterator(Listnode<T> head) {
		
		this.curr = head; //It starts at the first node with data
		
		
	}
	
	/**
	 * Returns the next element in the iteration and then advances the
	 * iteration reference.
	 * 
	 * @return the next data item in the iteration that has not yet been returned 
	 * @throws NoSuchElementException if the iteration has no more elements 
	 */
	@Override
	public T next() {
		if (curr == null){ //In case our given list is null
			throw new NoSuchElementException();
		}
		T currentData = curr.getData(); //We take the current data before moving on to the next
		curr = curr.getNext();
		return currentData;
	}
	
	/**
	 * Returns true if their are no more data items to iterate through 
	 * for this list.
	 * 
	 * @return true if their are any remaining data items to iterate through
	 */
	@Override
	public boolean hasNext() {
		if(curr == null){
			return false;
		}
		return true;
	}
       
    /**
     * The remove operation is not supported by this iterator
     * @throws UnsupportedOperationException if the remove operation is not 
     * supported by this iterator
     */
    @Override
	public void remove() {
	  throw new UnsupportedOperationException();
	}

}