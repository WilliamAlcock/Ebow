package GameEngine;

public class DLinkedListNode {
	private Object data;
	private DLinkedListNode previous;
	private DLinkedListNode next;
	
	public DLinkedListNode (Object data, DLinkedListNode previous, DLinkedListNode next) {
		this.data = data;
		this.previous = previous;
		this.next = next;
	}
	
	public DLinkedListNode getNext() {
		return next;
	}
	
	public DLinkedListNode getPrevious() {
		return previous;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setNext(DLinkedListNode next) {
		this.next = next;
	}
	
	public void setPrevious(DLinkedListNode previous) {
		this.previous = previous;
	}
	
	public void setMove(Object data) {
		this.data = data;
	}
}
