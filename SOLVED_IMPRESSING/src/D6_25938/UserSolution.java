package D6_25938;

// https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AZt8IiBqxEDHBIN6&contestProbId=AZvan5jq8gHHBIN6&probBoxId=AZt8IiBqxEHHBIN6&type=PROBLEM&problemBoxTitle=B%ED%98%95+%EA%B8%B0%EC%B6%9C&problemBoxCnt=41

import java.util.*;

class UserSolution {
	int N;
	
	List<int[]>[] adjList;
	Map<Integer, int[]> idAdj;
	Map<Integer, Integer> adjId;
	public void init(int N, int K, int mId[], int sCity[], int eCity[], int mTime[]) {
		this.N = N;
		
		adjList = new List[N];
		idAdj = new HashMap<>();
		adjId = new HashMap<>();
		
		for (int n=0; n<N; n++) {
			adjList[n] = new ArrayList<>();
		}
		
		for (int k=0; k<K; k++) {
			add(mId[k], sCity[k], eCity[k], mTime[k]);
		}
	}

	int hash(int s, int e) {
		return (s << 10) | e;
	}
	
	public void add(int mId, int sCity, int eCity, int mTime) {
		if (adjId.containsKey(hash(sCity, eCity))) {
			System.out.println("ERROR!");
		}
		
		idAdj.put(mId, new int[] {sCity, eCity});
		adjList[sCity].add(new int[] {eCity, mTime});
		adjId.put(hash(sCity, eCity), mId);
	}
	
	public int find(int s, int e) {
		for (int i=0; i<adjList[s].size(); i++) {
			if (adjList[s].get(i)[0] == e) {
				return i;
			}
		}
		
		return -1;
	}

	public void remove(int mId) {
		int[] se = idAdj.get(mId);
		int s = se[0];
		int e = se[1];
		
		idAdj.remove(mId);
		adjList[s].remove(find(s, e));
		adjId.remove(hash(s, e));
	}
	
	int dijkstra(int sCity, int eCity, int[] parents) {
		int[] dist = new int[N];
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
		pq.add(new int[] {sCity, 0});
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[sCity] = 0;
		
		while(!pq.isEmpty()) {
			int[] cityInfo = pq.poll();
			int base = cityInfo[0];
			int cost = cityInfo[1];
			
			if (base == eCity) {
				return cost;
			}
			
			for (int[] edge : adjList[base]) {
				int e = edge[0];
				int eCost = edge[1];
				int newCost = cost + eCost;
				
				if (newCost < dist[e]) {
					pq.add(new int[] {e, newCost});
					parents[e] = base;
				}
				
				dist[e] = Math.min(dist[e], newCost);
			}
		}
		
		// not able to get eCity
		return -1;
	}

	public int calculate(int sCity, int eCity) {
		int[] parents = new int[N];
		int minDist = dijkstra(sCity, eCity, parents);
		
		if (minDist == -1) return -1;
		
		int p = eCity;
		int[] dummy = new int[N];
		int delay = -1;
		while (p != sCity) {
			int s = parents[p];
			int e = p;
			
			/*
			System.out.println("s, e : "+s+" "+e);
			System.out.println("adjList[s].size : "+adjList[s].size());
			for (int[] edge : adjList[s])
				System.out.println(" "+edge[0]);
			System.out.println("hash(s, e) : "+hash(s, e));
			for (int key : adjId.keySet()) {
				System.out.println(" key : "+key);
			}
			*/
			
			int mId = adjId.get(hash(s, e));
			int mTime = adjList[s].get(find(s, e))[1];
			remove(mId);
			int newDist = dijkstra(sCity, eCity, dummy);
			add(mId, s, e, mTime);
			
			if (newDist != -1) {
				delay = Math.max(delay, newDist - minDist);
			} else {
				return -1;
			}
			
			p = parents[p];
		}
		
		return delay;
	}
}




