/*Aljulanda Al Abri*/
package calculator_testing;

public class Stack {
	public ListNode top; 
	//push method
	public void push(String input) {
		ListNode newNode = new ListNode(input);

		if(top!=null) {
			top.next = newNode;
			newNode.previous = top;
		}
		top = newNode;
	}
	//pop method
	public String pop() {
		if (top == null) {
			System.out.println("empty stack");
		}

		String poppedString = top.data;
		//there's one node in the stack
		if(top.previous == null) {
			top = null;
		}else {
			//more than one node
			ListNode pTop = top.previous;
			pTop.next = null;
			top = pTop;
		}
		return poppedString;
	}
	//peek method
	public String peek() {
		return top.data;
	}
	//check if empty method
	public boolean isEmpty() {
		if(top == null) {
			return true;
		}else {
			return false;
		}
	}
}
