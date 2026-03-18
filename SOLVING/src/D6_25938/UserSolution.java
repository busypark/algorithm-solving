package D6_25938;

import java.util.*;

class UserSolution {
	Map<Integer, Integer> mIdToNode;
	List<Edge>[] edges;
	
	public void init(int N, int K, int mId[], int sCity[], int eCity[], int mTime[]) {
		mIdToNode = new HashMap<>();
		edges = new List[N];
		
		for (int k=0; k<K; k++) {
			mIdToNode.put(mId[k], sCity[k]);
			if (edges[sCity[k]] == null) {
				edges[sCity[k]] = new ArrayList<Edge>();
			}
			
			edges[sCity[k]].add(new Edge(eCity[k], mId[k], mTime[k]));
		}
	}

	public void add(int mId, int sCity, int eCity, int mTime) {
		mIdToNode.put(mId, sCity);
		if (edges[sCity] == null) {
			edges[sCity] = new ArrayList<Edge>();
		}
		
		edges[sCity].add(new Edge(eCity, mId, mTime));
	}

	public void remove(int mId) {
		int index = mIdToNode.get(mId);
		mIdToNode.remove(mId);
		
		for (int i=0; i<edges[index].size(); ) {
			Edge e = edges[index].get(i);
			if (e.mId == mId) {
				edges[index].remove(i);
			} else {
				i ++;
			}
		}
	}

	public int calculate(int sCity, int eCity) {
		
		return 0;
	}
}

class Edge {
	int eCity, mId, mTime;
	
	Edge(int eCity, int mId, int mTime) {
		//this.sCity = sCity;
		this.eCity = eCity;
		this.mId = mId;
		this.mTime = mTime;
	}
}