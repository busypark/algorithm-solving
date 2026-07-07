
public class 징검다리건너기 {
    static boolean ok(int[] stones, int k, int h) {
    	int count = 0;
    	for (int stone : stones) {
    		if (stone >= h) {
    			count = 0;
    		} else {
    			count ++;
    		}
    		
    		if (count == k) return false;
    	}
    	return true;
    }
	
	public static int solution(int[] stones, int k) {
        // binary search for h
		int lh = 1;
		int rh = 200000001;
		
        while (lh + 1 < rh) {
    		int mh = (lh + rh) / 2;    		
        	boolean m = ok(stones, k, mh);
        	
        	if (m) {
        		lh = mh;
        	} else {
        		rh = mh;
        	}
        }
    	
        return lh;
    }
	
	public static void main(String[] args) {
		System.out.println(solution(new int[] {2, 4, 5, 3, 2, 1, 4, 2, 5, 1}, 3)); // 3
	}
}
