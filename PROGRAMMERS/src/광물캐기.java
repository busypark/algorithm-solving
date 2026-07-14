import java.util.*;

public class 광물캐기 {
    public static int solution(int[] picks, String[] minerals) {
        Map<String, Integer> dict = Map.of("diamond", 0, "iron", 1, "stone", 2);
    	
    	// construct buckets : O(N)
    	int[][] buckets = new int[Math.min((minerals.length + 4) / 5, picks[0]+picks[1]+picks[2])][4]; // 3: bucket_id
        for (int i=0; i<minerals.length; i++) {
        	if (buckets.length <= i/5) break;
        	buckets[i/5][dict.get(minerals[i])] ++;
        }
        
        // sort by count of diamonds, irons, and stones - desc : O(NlogN)
        Arrays.sort(buckets, (b1, b2) -> (b1[0] != b2[0]) ? Integer.compare(b2[0], b1[0])
        							   : (b1[1] != b2[1]) ? Integer.compare(b2[1], b1[1])
        							   : Integer.compare(b2[2], b1[2]));  
    	
        // resolve buckets and construct the answer : O(N)
        int answer = 0; // fatigue (monotonically increasing)
        nextBucket:
        for (int[] bucket : buckets) {
        	for (int i=0; i<3; i++) {
        		if (picks[i] > 0) {
        			// choose this kind of pick
        			switch (i) {
        			case 0: // diamond
        				answer += bucket[0] + bucket[1] + bucket[2]; // every mineral == 1 fatigue
        				break;
        			case 1: // iron
        				answer += bucket[0] * 5 + bucket[1] + bucket[2];
        				break;
        			case 2: // stone
        				answer += bucket[0] * 25 + bucket[1] * 5 + bucket[2];
        				break;
        			}
        			
        			picks[i] --; // used
        			continue nextBucket;
        		}
        	}
        }
    	
        return answer;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(new int[] {1, 3, 2}, new String[] {"diamond", "diamond", "diamond", "iron", "iron", "diamond", "iron", "stone"}));
    	// 12
    	
    	System.out.println(solution(new int[] {0, 1, 1}, new String[] {"diamond", "diamond", "diamond", "diamond", "diamond", "iron", "iron", "iron", "iron", "iron", "diamond"}));
    	// 50
    }
}
