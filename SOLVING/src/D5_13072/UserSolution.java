package D5_13072;

// B형특강 2주차 예습숙제 (병사관리)

/*
 * 
 */

class UserSolution
{

	static class LinkedList {
		Node head, tail;
		
		LinkedList() {
			head = new Node(0, 0);
			tail = head;
		}
		
		Node append(int team, int id) {
			Node n = new Node(team, id, tail, null);
			
			tail.next = n;
			tail = n;
			
			return n;
		}
	}
	
	static class Node {
		int id, team;
		Node prev, next;
		boolean enabled;
		
		Node(int team, int data) {
			this.team = team;
			this.id = data;
			this.prev = null;
			this.next = null;
			this.enabled = true;
		}
		
		Node(int team, int data, Node prev, Node next) {
			this.team = team;
			this.id = data;
			this.prev = prev;
			this.next = next;
		}
		
		void destroy() {
			prev.next = next;
			
			if (next != null)
				next.prev = prev;
			
			prev = null;
			next = null;
		}
	}
	
	LinkedList[][] buckets;
	Node[] nodes;
	
	public void init()
	{
		buckets = new LinkedList[6][6];
		for (int t=1; t<=5; t++) {
			for (int s=1; s<=5; s++) {
				buckets[t][s] = new LinkedList();
			}
		}
		
		nodes = new Node[100001];
	}
	
	public void hire(int mID, int mTeam, int mScore)
	{
		LinkedList bucket = buckets[mTeam][mScore];
		nodes[mID] = bucket.append(mTeam, mID);
	}
	
	public void fire(int mID)
	{
		nodes[mID].destroy();
		nodes[mID] = null;
		
		printStatus();
		System.out.print(buckets[1][5].tail.id); // tail을 제거했는데 tail이 살아있음
	}

	public void updateSoldier(int mID, int mScore)
	{
		int team = nodes[mID].team;
		fire(mID);
		hire(mID, team, mScore);
	}

	public void updateTeam(int mTeam, int mChangeScore)
	{
		printStatus();
		
		LinkedList[] waiting = new LinkedList[6];
		for (int s=1; s<=5; s++) {
			waiting[s] = new LinkedList();
		}
		
		LinkedList[] teamBuckets = buckets[mTeam];
		for (int s=1; s<=5; s++) {
			if (teamBuckets[s].head.next == null) continue;
			
			int targetScore = Math.max(1, Math.min(5, s + mChangeScore));
			if (s == targetScore) continue;
		
			waiting[targetScore].tail.next = teamBuckets[s].head.next;
			teamBuckets[s].head.next.prev = waiting[targetScore].tail;
			waiting[targetScore].tail = teamBuckets[s].tail;
			
			teamBuckets[s].head.next = null;
			teamBuckets[s].tail.next = null;
		}
		
		printWaiting(waiting);
		
		for (int s=1; s<=5; s++) {
			if (waiting[s].head.next == null) continue;
			//System.out.println("waiting["+s+"].head NOT null");
		
			//System.out.println("teamBuckets["+s+"].head NOT null");
			if (teamBuckets[s].head.next != null) {
				System.out.println("current head : "+teamBuckets[s].head.next.id+" "+teamBuckets[s].head.next);
				System.out.println("current tail : "+teamBuckets[s].tail.id+" "+teamBuckets[s].tail);
			}
			
			teamBuckets[s].tail.next = waiting[s].head.next;
			waiting[s].head.next.prev = teamBuckets[s].tail;
			teamBuckets[s].tail = waiting[s].tail;
		}
		
		printStatus();
	}
	
	public int bestSoldier(int mTeam)
	{
		//printStatus();
		
		for (int score=5; 1<=score; score--) {
			Node cur = buckets[mTeam][score].head.next;
			
			int maxID = -1;
			if (cur == null) continue;
			
			while (cur != null) {
				maxID = Math.max(cur.id, maxID);
				
				cur = cur.next;
			}
			
			return maxID;
		}
		
		return 0;
	}
	
	void printStatus() {
		for (int t=1; t<=5; t++) {
			System.out.println("Team "+t);
			for (int s=1; s<=5; s++) {
				System.out.print(" (Score "+s+")");
				System.out.print(" [");
				Node cur = buckets[t][s].head;
				while (cur != null) {
					System.out.print(cur.id + " ");
					
					cur = cur.next;
				}
				System.out.print("]");
				System.out.println();
			}
		}
		
		System.out.println("------------------------------");
	}
	
	void printWaiting(LinkedList[] waiting) {
		System.out.println("Waiting");
		for (int s=1; s<=5; s++) {
			System.out.print(" (Score "+s+")");
			System.out.print(" [");
			Node cur = waiting[s].head;
			while (cur != null) {
				System.out.print(cur.id + " ");
				
				cur = cur.next;
			}
			System.out.print("]");
			System.out.println();
		}
		
		System.out.println("------------------------------");
	}
}




