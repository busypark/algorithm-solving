import java.util.*;

public class 외벽점검 {
    public static int solution(int n, int[] weak, int[] dist) {
        // Initialize for backtrack
    	minResult = Integer.MAX_VALUE;
    	curResult = 0;
    	distId = 0;
    	curCount = 0;
    	cover = new HashMap<>();
    	for (int w : weak) {
    		cover.put(w, false);
    	}
    	dist = Arrays.stream(dist).boxed().sorted(Collections.reverseOrder()).mapToInt(Integer::intValue).toArray();
    	// 왜 이거 넣으면 틀려짐? 순서 상관없어야 하지 않나?
    	
        backtrack(n, weak, dist);
    	
        return (minResult == Integer.MAX_VALUE)? -1 : minResult;
    }
    
    static int curResult, minResult; // #friend used
    static int distId;
    static int curCount = 0; // #weak covered
    static Map<Integer, Boolean> cover;
    static void backtrack(int n, int[] weak, int[] dist) {
    	if (minResult <= curResult) return;
    	if (distId == dist.length) return;
    	if (curCount == weak.length) {
    		minResult = Math.min(minResult, curResult);
    		return;
    	}
    	
    	for (int w : weak) {
    		if (!cover.get(w)) {
    			distId ++;
    			
    			// use friend
    			curResult ++;
    			
    			if (distId < dist.length) {
	    			List<Integer> coverNew = new ArrayList<>();
	    			// counter-clockwise
	    			for (int i=0; i<=dist[distId]; i++) {
	    				int p = (w - i + n) % n;
	    				if (cover.containsKey(p) && !cover.get(p)) {
	    					cover.put(p, true);
	    					coverNew.add(p);
	    					curCount ++;
	    				}
	    			}
	    			
	    			if (coverNew.size() > 0)
	    				backtrack(n, weak, dist);
	    			
	    			for (int rollback : coverNew) {
	    				cover.put(rollback, false);
	    			}
	    			curCount -= coverNew.size();
	    			coverNew.clear();
    			
	    			// clockwise
	    			for (int i=0; i<=dist[distId]; i++) {
	    				int p = (w + i + n) % n;
	    				if (cover.containsKey(p) && !cover.get(p)) {
	    					cover.put(p, true);
	    					coverNew.add(p);
	    					curCount ++;
	    				}
	    			}

	    			if (coverNew.size() > 0)
	    				backtrack(n, weak, dist);
	    			
	    			for (int rollback : coverNew) {
	    				cover.put(rollback, false);
	    			}
	    			curCount -= coverNew.size();
	    			coverNew.clear();
    			}

    			curResult --;
    			
    			// not use friend
    			backtrack(n, weak, dist);
    			
    			distId --;
    		}
    	}
    }
    
    public static void main(String[] args) {
    	//System.out.println(solution(12, new int[] {1, 5, 6, 10}, new int[] {1, 2, 3, 4}));
    	// 2
    	
    	System.out.println(solution(12, new int[] {1, 3, 4, 9, 10}, new int[] {3, 5, 7}));
    	// 1
    }
}
