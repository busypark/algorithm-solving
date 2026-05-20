import java.util.*;

public class PROG_행렬과_연산 {
	public static class NewDeque<Integer> extends ArrayDeque<Integer> {
		public void newOfferFirst(Integer x) {
			if (x != null) {
				this.offerFirst(x);
			}
		}
		
		public void newOfferLast(Integer x) {
			if (x != null) {
				this.offerLast(x);
			}
		}
	}
	
	public static int[][] solution(int[][] rc, String[] operations) {	        
	    /* transform rc to Deques */
    	int h = rc.length;
        int w = rc[0].length;
        NewDeque<Integer> left = new NewDeque<>();
        for (int r=h-2; 1<=r; r--) {
        	left.offer(rc[r][0]);
        }
        
        NewDeque<Integer> right = new NewDeque<>();
        for (int r=1; r<=h-2; r++) {
        	right.offer(rc[r][w-1]);
        }
        
        NewDeque<Integer> top = new NewDeque<>();
        for (int c=0; c<w; c++) {
        	top.offer(rc[0][c]);
        }
        NewDeque<Integer> bottom = new NewDeque<>();
        for (int c=0; c<w; c++) {
        	bottom.offer(rc[h-1][c]);
        }
        NewDeque<NewDeque<Integer>> center = new NewDeque<>();
        center.offer(top);
        for (int r=1; r<=h-2; r++) {
        	NewDeque<Integer> mid = new NewDeque<>();
        	for (int c=1; c<=w-2; c++) {
        		mid.offer(rc[r][c]);
        	}
        	center.offer(mid);
        }
        center.offer(bottom);
        
        /* execute given operations */
        for (int i=0; i<operations.length; i++) {
    		NewDeque<Integer> t = center.peekFirst();
    		NewDeque<Integer> b = center.peekLast();
    		
        	switch (operations[i].charAt(0)) {
        	case 'S':
        		Integer tl = t.pollFirst();
        		Integer tr = t.pollLast();
        		
        		Integer bl = b.pollFirst();
        		Integer br = b.pollLast();
        		
        		center.newOfferFirst(center.pollLast());
        		t = center.peekFirst();
        		b = center.peekLast();
        		
        		t.newOfferFirst(bl);
        		t.newOfferLast(br);
        		
        		b.newOfferFirst(left.pollFirst());
        		b.newOfferLast(right.pollLast());
        		
        		left.newOfferLast(tl);
        		right.newOfferFirst(tr);
        		break;
        	case 'R':
        		right.newOfferFirst(t.pollLast());
        		b.newOfferLast(right.pollLast());
        		left.newOfferFirst(b.pollFirst());
        		t.newOfferFirst(left.pollLast());
        		break;
        	}
        }
        
        /* construct the answer */
        int[][] answer = new int[h][w];
        for (int r=1; r<=h-2; r++) {
        	answer[r][0] = left.pollLast();
        	answer[r][w-1] = right.pollFirst();
        }
        top = center.pollFirst();
        bottom = center.pollLast();
        
        System.out.println(bottom.size());
        
        for (int c=0; c<w; c++) {
        	answer[0][c] = top.pollFirst();
        	answer[h-1][c] = bottom.pollFirst();
        }
        for (int r=1; r<=h-2; r++) {
        	Deque<Integer> row = center.pollFirst();
        	for (int c=1; c<=w-2; c++) {
        		answer[r][c] = row.pollFirst();
        	}
        }
        
        return answer;
    }
	
	public static void main(String[] args) {
		System.out.println(Arrays.deepToString(solution(
				new int[][] {{1, 2, 3, 4}, {5, 6, 7, 8}}, 
				new String[] {"ShiftRow"})));
	}
}

