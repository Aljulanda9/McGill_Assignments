/*Aljulanda Al Abri*/
package calculator_testing;

public class Queue {
	//nodes for front and back
	public ListNode back; 
	public ListNode front; 

	//enqueue method
	public void Enqueue(String n) {
		ListNode NewNode = new ListNode(n);
		//check if queue is empty
		if(back == null) {
			back = NewNode;
			front = NewNode;
		}else {
			//queue is not empty
			NewNode.next = back;
			back.previous = NewNode;
			back = NewNode;
		}
	}


	//dequeue method
	public String dequeue() {
		//check if there's a front node
		if (front == null) {
			System.out.print("empty ");
			return null;
		}
		//store the result in a new string
		String outputStr = front.data;

		if (front.previous == null) {
			front = null;
			back = null;
		}else {
			ListNode pFront = front.previous;
			pFront.next = null;
			front = pFront; 
		}
		return outputStr;
	}

	//check if empty
	public boolean isEmpty() {
		if(back == null) {
			return true;
		}else {
			return false;
		}
	}
}
