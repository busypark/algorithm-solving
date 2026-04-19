package D5_13072;

// B형특강 2주차 예습숙제 (병사관리)

/*
 * 
 */

/*
 * 
 */

class UserSolution
{
	public void init()
	{
	}
	
	public void hire(int mID, int mTeam, int mScore)
	{
	}
	
	public void fire(int mID)
	{
	}

	public void updateSoldier(int mID, int mScore)
	{
	}

	public void updateTeam(int mTeam, int mChangeScore)
	{
	}
	
	public int bestSoldier(int mTeam)
	{
		return 0;
	}
}

class LinkedList {
	Node head, tail;
	
	LinkedList() {
		this.head = null;
		this.tail = null;
	}
	
	void append(int data) {
		if (head == null) {
			head = new Node(data);
			tail = head;
		} else {
			tail.next = new Node(data);
			tail = tail.next;
		}
	}
	
	
}

class Node {
	int data;
	Node next;
	
	Node(int data) {
		this.data = data;
		this.next = null;
	}
}



