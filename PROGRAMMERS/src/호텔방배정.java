import java.util.*;

public class 호텔방배정 {
    public static long[] solution(long k, long[] room_number) {
    	long[] result = new long[room_number.length];
    	parent = new HashMap<>();
    	
    	for (int i=0; i<room_number.length; i++) {
    		result[i] = assign(room_number[i]);
    	}
    	
    	return result;
    }
    
    static Map<Long, Long> parent;
    static long assign(long x) {
    	if (parent.containsKey(x)) {
    		long p = parent.get(x);
    		long next;
    		if (p == x) {
    			next = assign(x+1);
    		} else {
    			next = assign(p);
    		}

			parent.put(x, next);
			return next;
    	} else {
    		parent.put(x, x);
    		return x;
    	}
    }
    
    public static void main(String[] args) {
    	System.out.println(Arrays.toString(solution(10, new long[] {1,3,4,1,3,1})));
    	// 1,3,4,2,5,6
    }
}
