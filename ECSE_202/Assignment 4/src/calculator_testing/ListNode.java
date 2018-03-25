/*Aljulanda Al Abri*/
package calculator_testing;

public class ListNode {
	//creates ListNode's elements
	public String data;
	public ListNode next;
	public ListNode previous;

	//default constructor
	public ListNode() {
		data = "";
		next = null;
		previous = null;
	}
	//adds data element to node
	public ListNode(String n) {
		this.data = n;
	}
}
