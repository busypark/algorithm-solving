import java.util.Arrays;

public class 요격시스템 {
    public static int solution(int[][] targets) {
        final int k = targets.length;
        double[] x = new double[k]; // the most right x = always *.5 (e - 0.5)
        int[] a = new int[k]; // current answer
        
        Arrays.sort(targets, (t1, t2) -> Integer.compare(t1[1], t2[1])); // sort by e : O(NlogN)
        x[0] = targets[0][1] - 0.5;
        a[0] = 1;
        
        for (int i=0; i<k-1; i++) {	
        	if (targets[i][1] <= targets[i+1][0]) {
        		x[i+1] = targets[i+1][1] - 0.5;
        		a[i+1] = a[i] + 1;
        	} else {
        		if (x[i] <= targets[i+1][0]) {
        			x[i+1] = targets[i+1][1] - 0.5;
        			a[i+1] = a[i] + 1;
        		} else {
        			x[i+1] = x[i];
        			a[i+1] = a[i];
        		}
        	}
        }
    	
        return a[k-1];
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(new int[][] {{4,5},{4,8},{10,14},{11,13},{5,12},{3,7},{1,4}}));
    	// 3
    }
}
