package hashMap;

//Aljulanda Al Abri

public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;
	public Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
		size++;
		HashNode<K,V> newNode = new HashNode<K,V>(key,value);
		if(head == null) {
			head = newNode; 
		}else {
			newNode.next = head; 
			head = newNode; 
		}

		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE

		HashNode<K,V> cur = head; 
		HashNode<K,V> emptyNode = new HashNode<K,V>(null,null);

		if(head != null) {
			if(head.getKey().equals(key)) {

				return head; 

			}else {
				while(cur.next != null && cur.getKey() != key) {
					cur = cur.next;
				}

				if(cur.getKey().equals(key)) {
					return cur;
				}else {
					return emptyNode;
				}

			}
		}
		return emptyNode;
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE
		HashNode<K,V> curOne = this.head; 
		size--;
		if(size == 1) {
			head = null;
		}else {
			head = head.next;
		}
		return curOne; 
		// ADD CODE ABOVE HERE

	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){

		// ADD CODE BELOW HERE

		HashNode<K,V> emptyNode = new HashNode<K,V>(null,null);

		if(getListNode(key).getKey() != null) {
			
			if(size == 1) {
				HashNode<K, V> whenOne = this.head;
				this.head = null;
				size--;
				return whenOne; 
				
			}
			HashNode<K,V> cur = head;

			while(cur.next != null) {
				if(cur.next.getKey().equals(key)) {
					HashNode<K,V> fcur = cur.next;
					cur.next = cur.next.next;
					size--;
					return fcur;
				}
				cur = cur.next;
				if(cur.next == null) {
					size--;
					return head; 
				}
			}
		}

		return emptyNode;
	}


	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	public HashNode<K,V> getHead(){
		return this.head;
	}


	//ADD YOUR HELPER METHODS ABOVE THIS


}
