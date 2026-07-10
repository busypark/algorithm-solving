import java.util.*;

public class 택배배달과수거하기 {
	static class Node {
		int p, q; // pointer, (remaining) quantity > 0
		Node(int p, int q) {
			this.p = p;
			this.q = q;
		}
	}
	
	public static long solution(int cap, int n, int[] deliveries, int[] pickups) {
        Stack<Node> deliver = new Stack<>();
        Stack<Node> pickup = new Stack<>();
        
//        System.out.println("#########################");
        
        // construct stacks : O(n)
        for (int i=0; i<n; i++) {
        	if (deliveries[i] > 0) {
//        		System.out.println("deliver.add(p="+(i+1)+" q="+deliveries[i]+")");
        		deliver.add(new Node(i+1, deliveries[i]));
        	}
        	
        	if (pickups[i] > 0) {
//        		System.out.println("pickups.add(p="+(i+1)+" q="+pickups[i]+")");
        		pickup.add(new Node(i+1, pickups[i]));
        	}
        }
        
        // resolve stacks by descending order(p)
        // : O(n) on average (resolve 2*cap*n by cap -> O(n))
        long d = 0; // total distance
        while (!deliver.isEmpty() || !pickup.isEmpty()) {
        	int p = 0; // current position(0)
            
        	// deliver until burden or stack is zero
        	//System.out.println("# deliver and pickup : d="+d+" #");
        	//System.out.println("## deliver");
        	if (!deliver.isEmpty()) {
	        	// start for deliver (+ additionally pickup)
        		int burden = cap;
	        	p = deliver.peek().p;
        		d += p;
	        	while (!deliver.isEmpty() && 0 < burden) {
	        		Node top = deliver.pop();
	        		//System.out.println("### p="+p+" top.p="+top.p);
	        		//d += Math.abs(p - top.p);
	        		//p = top.p;
	        		if (top.q <= burden) {
	        			burden -= top.q;
	        			top.q = 0;
	        		} else {
	        			top.q -= burden;
	        			burden = 0;
	        			deliver.push(top);
	        		}
	        	}
        	}
        	
        	// p > 0 if deliver finished
        	Stack<Node> temp = new Stack<>();
        	while (!pickup.isEmpty()) {
        		if (pickup.peek().p <= p) break;
        		temp.push(pickup.pop());
        	}
        	
        	List<Node> a = new LinkedList<>();
        	
        	if (!pickup.isEmpty()) {
	        	// pickup until burden is full or stack is zero
        		//System.out.println("d="+d+" p="+p);
//	        	System.out.println("## pickup : peek.p="+pickup.peek().p);
	        	int burden = 0;
	        	d += Math.abs(p - pickup.peek().p);
	        	p = pickup.peek().p;
	        	while (!pickup.isEmpty() && burden < cap) {
	        		Node top = pickup.pop();
	        		//d += Math.abs(p - top.p);
	        		//p = top.p;
	        		if (cap - burden < top.q) {
	        			top.q -= cap - burden;
	        			burden = cap;
	        			pickup.push(top);
	        		} else {
	        			burden += top.q;
	        			top.q = 0;
	        		}
	        	}
        	}
        	
//        	System.out.println("### returning : "+p);
//        	System.out.println("### size: deliver("+deliver.size()+" pickup("+pickup.size()+") temp("+temp.size()+")");
        	//System.out.println("### temp.head p="+temp.peek().p + " q="+temp.peek().q);
        	d += p; // returning distance
        	
        	// temp -> pickup
        	while (!temp.isEmpty()) {
        		pickup.push(temp.pop());
        	}

//        	System.out.println("### size: deliver("+deliver.size()+" pickup("+pickup.size()+") temp("+temp.size()+")");
        }
    	
        return d;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(
    					   4, 5, new int[] {1, 0, 3, 1, 2}, new int[] {0, 3, 0, 4, 0}));
    	// 16
    	
    	System.out.println(solution(
				   2, 7, new int[] {1, 0, 2, 0, 1, 0, 2}, new int[] {0, 2, 0, 1, 0, 2, 0}));
    	// 30
    }
}
