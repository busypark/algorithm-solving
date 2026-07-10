import java.util.*;

public class 디스크컨트롤러 {
    static class Job {
    	int l, s, id;
    	int started;
    	Job(int l, int s, int id) {
    		this.l = l;
    		this.s = s;
    		this.id = id;
    	}
    }
	
	public static int solution(int[][] jobs) {
		Map<Integer, List<Integer>> jobSupplies = new HashMap<>();
		
		for (int i=0; i<jobs.length; i++) {
        	int s = jobs[i][0];
        	final int id = i;
        	System.out.println("id: "+id);
        	
        	if (jobSupplies.containsKey(s)) {
        		jobSupplies.get(s).add(id);
        	} else {
        		jobSupplies.put(s, new ArrayList<Integer>(Arrays.asList(id)));
        	}
        }
		
        PriorityQueue<Job> pq = new PriorityQueue<>((j1, j2)
        		-> (j1.l != j2.l) ? Integer.compare(j1.l, j2.l)
        		: ((j1.s != j2.s) ? Integer.compare(j1.s, j2.s)
        		: Integer.compare(j1.id, j2.id)));
        
        int t = 0;
		int count = 0;
		int sum = 0;
		Job working = null;
		while (count < jobs.length) {			
			if (working != null && working.started + working.l == t) {
				working = null;
			} // checkout
			
			if (jobSupplies.containsKey(t)) {
				for (int id : jobSupplies.get(t)) {
					pq.offer(new Job(jobs[id][1], jobs[id][0], id));
				}
			} // supply jobs
			
			if (working == null && !pq.isEmpty()) {
				working = pq.poll();
				working.started = t;
				int retTime = working.started + working.l - working.s;
				sum += retTime;
				count ++;
			} // start work and cumulate the sum
			
			t++;
		}
    	
        return sum / jobs.length;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(new int[][] {{0, 3}, {1, 9}, {3, 5}}));
    	// 8 expected
    }
}
