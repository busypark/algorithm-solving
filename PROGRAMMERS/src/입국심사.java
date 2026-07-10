public class 입국심사 {
    public static boolean f(int n, int[] times, int k, long s) {
    	long sum = 0;
    	for (int i=0; i<k; i++) {
    		sum += s / times[i];
    		
    		if (n <= sum) return true;
    	}
    	
    	return false;
    }
	
	public static long solution(int n, int[] times) {
        int minI = 0;
        int k = times.length;
        for (int i=1; i<k; i++) {
        	if (times[i] < times[minI])
        		minI = i;
        } // for initial value of upper bound
        
        long start = 1;
        long end = ((long)times[minI]) * (long)n;
        while (start + 1 < end) {
        	long mid = (start + end) / 2;
        	if (f(n, times, k, mid)) {
        		end = mid;
        	} else {
        		start = mid;
        	}
        }
    	
        return end;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(6, new int[] {7, 10}));
    	// 28 expected
    }
}
