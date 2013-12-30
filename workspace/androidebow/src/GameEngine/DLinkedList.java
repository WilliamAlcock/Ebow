package GameEngine;

public class DLinkedList {
	private DLinkedListNode head;
	private DLinkedListNode last;
	private int size;
	
	public DLinkedList() {
		head = new DLinkedListNode (null,null,null);
		last = new DLinkedListNode (null,null,null);
		size = 0;
		head.setNext(last);
		last.setPrevious(head);
	}

	public DLinkedListNode addFirst(Object data) {
		DLinkedListNode newNode = new DLinkedListNode (data,null,null);
		addNodeFirst(newNode);
		return newNode;
	}
	
	public DLinkedListNode addLast(Object data) {
		DLinkedListNode newNode = new DLinkedListNode (data,null,null);
		addNodeLast(newNode);
		return newNode;
	}
	
	public boolean removeFirst() {
		if (size>0) {
			DLinkedListNode deleteNode = head.getNext();
			DLinkedListNode newFirstNode = deleteNode.getNext();
			newFirstNode.setPrevious(head);
			head.setNext(newFirstNode);
			deleteNode.setNext(null);
			deleteNode.setPrevious(null);
			size --;
			return true;
		}
		return false;
	}
	
	public boolean removeLast() {
		if (size>0) {
			DLinkedListNode deleteNode = last.getPrevious();
			DLinkedListNode newLastNode = deleteNode.getPrevious();
			newLastNode.setNext(last);
			last.setPrevious(newLastNode);
			deleteNode.setNext(null);
			deleteNode.setPrevious(null);
			size--;
			return true;
		}
		return false;
	}
	
	public boolean remove(Object data) {
		DLinkedListNode curNode = head.getNext();
		boolean deleted = false;
		while ((curNode!=last) && (deleted==false)) {
			if (curNode.getData().equals(data)) {
				deleteNode(curNode);
				deleted = true;
				size --;
			}
			curNode = curNode.getNext();
		}
		return deleted;
	}
	
	private void deleteNode(DLinkedListNode curNode) {
		curNode.getPrevious().setNext(curNode.getNext());
		curNode.getNext().setPrevious(curNode.getPrevious());
	}			
	
	public boolean removeNode(DLinkedListNode delNode) {
		DLinkedListNode curNode = head.getNext();
		boolean deleted = false;
		while ((curNode!=last) && (deleted==false)) {
			if (curNode==delNode) {
				deleteNode(delNode);
				deleted = true;
				size --;
			}
			curNode = curNode.getNext();
		}
		return deleted;
	}
	
	public void addNodeFirst(DLinkedListNode curNode) {
		head.getNext().setPrevious(curNode);
		curNode.setPrevious(head);
		curNode.setNext(head.getNext());
		head.setNext(curNode);
		size ++;
	}
	
	public void addNodeLast(DLinkedListNode curNode) {
		last.getPrevious().setNext(curNode);
		curNode.setNext(last);
		curNode.setPrevious(last.getPrevious());
		last.setPrevious(curNode);
		size ++;
	}
	
	public DLinkedListNode getHead() {
		return head.getNext();
	}
	
	public DLinkedListNode getLast() {
		return last.getPrevious();
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int getSize() {
		return size;
	}

}
