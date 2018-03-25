package hashMap;

//Aljulanda Al Abri

import java.util.ArrayList;
import java.util.Iterator;


class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/**
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE
		buckets = new ArrayList< HashLinkedList<K,V> >();
		this.numBuckets = numBuckets; 

		for (int i = 0; i < numBuckets; i++) {
			buckets.add(new HashLinkedList<K,V>());
		}
		//  ADD YOUR CODE ABOVE HERE

	}


	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {

		return  Math.abs( key.hashCode() ) % numBuckets ;
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE
		entryCount++;
		double load_factor = (double) entryCount/numBuckets;
		int index = hashFunction(key);

		if(buckets.get(index) != null) {

			if(buckets.get(index).getListNode(key).getKey() != null){
				V toReturn = buckets.get(index).remove(key).getValue();
				buckets.get(index).add(key, value);

				if(load_factor > MAX_LOAD_FACTOR) {
					this.rehash();
				}
				return toReturn;
			}
			buckets.get(index).add(key, value);

			if(load_factor > MAX_LOAD_FACTOR) {
				this.rehash();
			}

			return value;

		}

		HashLinkedList<K, V> nodesList = new HashLinkedList<K, V>();

		nodesList.add(key, value);

		buckets.set(index, nodesList);



		if(load_factor > MAX_LOAD_FACTOR) {
			this.rehash();
		}

		return value;

		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE
		int index = hashFunction(key);
		if(containsKey(key)) {

			if(buckets.get(index).getListNode(key).getKey().equals(key)) {


				HashLinkedList<K,V> searchList = buckets.get(index);
				HashNode<K,V> foundNode = searchList.getListNode(key);

				return foundNode.getValue();


			}
		}
		return null;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE

		int index = hashFunction(key);

		if(this.get(key) != null) {
			entryCount--;

			HashLinkedList<K,V> toDelete = buckets.get(index);

			return toDelete.remove(key).getValue();
		}



		//  ADD  YOUR CODE ABOVE HERE
		return (null);

	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE
		entryCount = 0;
		//make new array list to copy buckets
		Iterator<HashLinkedList<K,V>> iterList = buckets.iterator();

		ArrayList< HashLinkedList<K,V> > tempBuckets = new ArrayList< HashLinkedList<K,V> >();

		while(iterList.hasNext()) {
			tempBuckets.add(iterList.next());
		}

		buckets.clear();

		numBuckets *= 2;

		for (int i = 0; i < numBuckets; i++) {
			buckets.add(new HashLinkedList<K,V>());
		}


		Iterator<HashLinkedList<K,V>> tempiterList = tempBuckets.iterator();

		while(tempiterList.hasNext()) {

			HashLinkedList<K,V> mys = tempiterList.next();
			if(mys != null) {
				HashNode<K,V> cur = mys.getHead();

				if(cur != null) {
					this.put(cur.getKey(), cur.getValue());
					while(cur.next != null) {
						cur = cur.next; 
						this.put(cur.getKey(), cur.getValue());
					}
				}
			}

		}

		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key).getKey() == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys(){



		//   ADD YOUR CODE BELOW HERE
		ArrayList<K> keys = new ArrayList<K>();
		Iterator<HashLinkedList<K,V>> iterList = buckets.iterator();

		while(iterList.hasNext()) {
			HashLinkedList<K,V> innerLists = iterList.next();
			HashNode<K,V> headNode = innerLists.getHead();

			while(headNode != null) {
				keys.add(headNode.getKey());
				headNode = headNode.next;
			}

		}

		return keys;
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values(){


		//   ADD YOUR CODE BELOW HERE
		ArrayList<V> values = new ArrayList<V>();
		Iterator<HashLinkedList<K,V>> iterList = buckets.iterator();

		while(iterList.hasNext()) {
			HashLinkedList<K,V> innerLists = iterList.next();
			HashNode<K,V> headNode = innerLists.getHead();

			while(headNode != null) {
				values.add(headNode.getValue());
				headNode = headNode.next;
			}

		}

		return values;
		//   ADD YOUR CODE ABOVE HERE




	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */

	public class HashIterator implements  Iterator<HashNode<K,V> > {
		HashLinkedList<K,V>  allEntries;


		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{

			//  ADD YOUR CODE BELOW HERE
			allEntries = new HashLinkedList<K,V>();
			for(HashLinkedList<K,V> hashList: buckets) {

				HashNode<K,V> node = hashList.getHead();

				if(node != null) {
					while(node != null) {
						allEntries.add(node.getKey(), node.getValue());
						node = node.next;
					}

				}

			}

			//  ADD YOUR CODE ABOVE HERE

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}
