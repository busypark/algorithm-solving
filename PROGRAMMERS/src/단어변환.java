import java.util.*;

public class 단어변환 {
    public static int solution(String begin, String target, String[] words2) {
        String[] words = Arrays.copyOf(words2, words2.length+1);
        words[words2.length] = begin;
    	int countWords = words.length;
        Map<String, Integer> distMap = new HashMap<>();
        distMap.put(begin, 0);
        boolean includeTarget = false;
        for (int i=0; i<countWords; i++) {
        	if (!distMap.containsKey(words[i])) {
        		distMap.put(words[i], Integer.MAX_VALUE);
        	} // initialize distMap by INF
        	
        	if (target.equals(words[i])) {
        		includeTarget = true;
        	}
        }
        
        if (!includeTarget) return 0;
        
        int wordLen = begin.length();
        Map<String, List<String>> adj = new HashMap<>();
        //adj.put(begin, new ArrayList<>());
        for (int i=0; i<countWords-1; i++) {
        	nextWord:
        	for (int j=i+1; j<countWords; j++) {
        		// check if the two satisfy adjacency
        		String A = words[i];
        		String B = words[j];
        		if (A.equals(B)) {
        			continue nextWord;
        		} // overlapped by begin and words[~]
        		
        		int diffCount = 0;
        		for (int k=0; k<Math.min(A.length(), B.length()); k++) {
        			if (1 < diffCount)
        				continue nextWord;
        			
        			if (A.charAt(k) != B.charAt(k)) {
        				diffCount ++;
        			}
        		}
        		
        		if (diffCount != 1)
        			continue nextWord;
        		
        		// adjacency satisfied -> enroll HashMap for two directions
        		if (!adj.containsKey(A)) {
        			adj.put(A, new ArrayList<>());
        		}
        		
        		adj.get(A).add(B);
        		
        		if (!adj.containsKey(B)) {
        			adj.put(B, new ArrayList<>());
        		}
        		
        		adj.get(B).add(A);
        	}
        } // bidirectional graph(adj) constructed
        
        Queue<String> q = new LinkedList<>();
        q.offer(begin);
        while (!q.isEmpty()) {
        	String now = q.poll();
        	
        	if (now.equals(target)) {
        		return distMap.get(target);
        	} // return minimum distance for target from begin
        	
        	if (!adj.containsKey(now)) continue;
        	List<String> adjList = adj.get(now);
        	if (adjList == null) continue;
        	for (int i=0; i<adjList.size(); i++) {
        		String next = adjList.get(i);
        		if (distMap.get(next) == Integer.MAX_VALUE) {
        			q.offer(next);
        			distMap.put(next, distMap.get(now) + 1);
        		}
        	}
        }
    	
    	return 0;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(
    			"hit", "cog", new String[] {"hot", "dot", "dog", "lot", "log", "cog"}));
    	// 4 expected
    	
    	System.out.println(solution(
    			"hit", "cog", new String[] {"hot", "dot", "dog", "lot", "log"}));
    	// 0 expected
    	
    	System.out.println(solution(
    			"hit", "cog", new String[] {"hot", "hit", "hit", "dot", "dog", "lot", "log", "cog"}));
    	// 4 expected (begin overlapping)

    	System.out.println(solution(
    			"hit", "xxx", new String[] {"hot", "hit", "hit", "dot", "dog", "lot", "log", "xxx"}));
    	// 0 expected (nothing connected to target)
    }
}
