import java.util.Arrays;

public class 섬연결하기 {
    public static int solution(int n, int[][] costs) {
        // sort by cost : O(ElogE)
    	Arrays.sort(costs, (e1, e2) -> Integer.compare(e1[2], e2[2]));
        
    	// initialize for kruskal
    	parent = new int[n];
    	for (int i=0; i<n; i++)
    		parent[i] = i; // initial root by itself
    	
    	// kruskal
    	int count = 0; // #constructed edge
    	int sum = 0; // sum of cost
    	for (int i=0; i<costs.length; i++) {
    		if (count == n-1) break;
    		
    		int[] edge = costs[i];
    		int a = find(edge[0]);
    		int b = find(edge[1]);
    		
    		if (a != b) {
    			// union
    			parent[a] = b;
    			count ++;
    			sum += edge[2];
    		}
    	}
    	
    	return sum;
    }
    
    // find and do path compression
    static int find(int x) {
    	if (parent[x] == x) return x;
    	return parent[x] = find(parent[x]);
    }
    
    static int[] parent;
    
    public static void main(String[] args) {
    	System.out.println(solution(4, new int[][] {{0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}}));
    	// 4
    }
}
