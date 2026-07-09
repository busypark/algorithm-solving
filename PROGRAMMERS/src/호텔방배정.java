import java.util.*;

public class 호텔방배정 {
    public static long[] solution(long k, long[] room_number) {
        Map<Long, Long> nextPointer = new HashMap<>();
        long[] result = new long[room_number.length];
        for (int i=0; i<result.length; i++) {
        	long rn = room_number[i];
        	if (nextPointer.containsKey(rn)) {
        		// overlapped -> search for next null key
        		long j=rn;
        		while (nextPointer.containsKey(j)) {
        			long next = nextPointer.get(j);
        			nextPointer.put(j, next + 1);
        			
        			j = next;
        		}
        		
        		nextPointer.put(rn, j+1);
        		result[i] = j;
        	} else {
        		// new
        		nextPointer.put(rn, rn+1);
        		result[i] = rn;
        	}
        }
    	
    	return result;
    }
    
    public static void main(String[] args) {
    	System.out.println(Arrays.toString(solution(10, new long[] {1,3,4,1,3,1})));
    	// 1,3,4,2,5,6
    }
}
