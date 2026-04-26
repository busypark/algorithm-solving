package D5_13072;

// B형특강 2주차 예습숙제 (병사관리)

/*
 * 아이디어! dummy head/tail을 항상 일관적으로 유지하고, 그 사이에서만 삽입/삭제한다. 이러면 head/tail 포인터를 안 바꿔도 됨
 * 한 번에 pass.. 일단 이중 링크드 리스트를 떠올린 것부터 시작했는데
 * 노드 자체를 destroy하는 방식이 tail에서 문제가 있음을 발견.. 노드 각각은 자기가 어느 LinkedList 소속인지를 모름
 * 그걸 명시하게 되면 자료구조 비효율이 생겨버림
 * 그걸 타파한게 바로 dummy head/tail.. 아이디어 좋은데 왜 하필 그렇게 해야하는지, 근본적인 논리를 gemini와 얘기해봐야 할듯
 */

/*
 * 피드백(강의)
 * '버전'이라는 개념을 도입해서 single linkedlist로도 풀 수 있음을 알게 됨
 * 역시 '풀었다' 자체보다는 '어떻게 더 나은 생각을 할 수 있느냐'에 대해 배우는 게 중요!
 */

class UserSolution
{

	static class LinkedList {
		Node head, tail;
		
		LinkedList() {
			head = new Node(0, 0);
			tail = new Node(0, 0);
			head.next = tail;
			tail.prev = head;
		}
		
		Node append(int team, int id) {
			Node n = new Node(team, id);
			
			n.prev = tail.prev;
			n.next = tail;
			tail.prev.next = n;
			tail.prev = n;
			
			return n;
		}
	}
	
	static class Node {
		int id, team;
		Node prev, next;
		
		Node(int team, int data) {
			this.team = team;
			this.id = data;
			this.prev = null;
			this.next = null;
		}
		
		void destroy() {			
			prev.next = next;
			next.prev = prev;
			
			prev = null;
			next = null;
		}
	}
	
	static LinkedList[][] buckets;
	static Node[] nodes;
	
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
		//System.out.println("[hire] "+mID+" "+mTeam+" "+mScore);
		
		LinkedList bucket = buckets[mTeam][mScore];
		nodes[mID] = bucket.append(mTeam, mID);
	}
	
	public void fire(int mID)
	{
		//System.out.println("[fire] "+mID);
		
		nodes[mID].destroy();
		nodes[mID] = null;
		
		//printStatus();
		//System.out.println("alive tail : "+buckets[1][5].tail.id); // tail을 제거했는데 tail이 살아있음
	}

	public void updateSoldier(int mID, int mScore)
	{
		//System.out.println("[updateSoldier] "+mID+" "+mScore);
		//printStatus();
		
		int team = nodes[mID].team;
		fire(mID);

		//printStatus();
		
		hire(mID, team, mScore);

		//printStatus();
	}

	public void updateTeam(int mTeam, int mChangeScore)
	{
		//System.out.println("[updateTeam] "+mTeam+" "+mChangeScore);
		//printStatus();
		
		LinkedList[] waiting = new LinkedList[6];
		for (int s=1; s<=5; s++) {
			waiting[s] = new LinkedList();
		}
		
		LinkedList[] teamBuckets = buckets[mTeam];
		for (int s=1; s<=5; s++) {
			if (teamBuckets[s].head.next == teamBuckets[s].tail) continue;
			
			int targetScore = Math.max(1, Math.min(5, s + mChangeScore));
			if (s == targetScore) continue;
		
			Node wp = waiting[targetScore].tail.prev;
			Node wt = waiting[targetScore].tail;
			
			teamBuckets[s].head.next.prev = wp;
			wp.next = teamBuckets[s].head.next;
			teamBuckets[s].tail.prev.next = wt;
			wt.prev = teamBuckets[s].tail.prev;
			
			teamBuckets[s].head.next = teamBuckets[s].tail;
			teamBuckets[s].tail.prev = teamBuckets[s].head;
		}
		
		//printWaiting(waiting);
		
		for (int s=1; s<=5; s++) {
			if (waiting[s].head.next == waiting[s].tail) continue;
			/*
			System.out.println("waiting["+s+"].head NOT null");
		
			System.out.println("teamBuckets["+s+"].head NOT null");
			if (teamBuckets[s].head.next != null) {
				System.out.println("current head : "+teamBuckets[s].head.next.id+" "+teamBuckets[s].head.next);
				System.out.println("current tail : "+teamBuckets[s].tail.id+" "+teamBuckets[s].tail);
			}
			*/
			
			Node tp = teamBuckets[s].tail.prev;
			Node tt = teamBuckets[s].tail;
			
			waiting[s].head.next.prev = tp;
			tp.next = waiting[s].head.next;
			waiting[s].tail.prev.next = tt;
			tt.prev = waiting[s].tail.prev;
		}
		
		//printStatus();
	}
	
	public int bestSoldier(int mTeam)
	{

		//System.out.println("[bestSoldier] "+mTeam);
		//printStatus();
		
		for (int score=5; 1<=score; score--) {
			Node cur = buckets[mTeam][score].head.next;
			
			int maxID = -1;
			if (cur == buckets[mTeam][score].tail) continue;
			
			while (cur != buckets[mTeam][score].tail) {
				maxID = Math.max(cur.id, maxID);
				
				cur = cur.next;
			}
			
			//System.out.println("[bestSoldier(6)] : "+maxID);
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




