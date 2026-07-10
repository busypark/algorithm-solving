
public class 파괴되지않은건물 {
	static class SegNode {
		int tl_r, tl_c, br_r, br_c;
		int val;
		SegNode left, right;
		
		SegNode(int tl_r, int tl_c, int br_r, int br_c, int val) {
			this.tl_r = tl_r;
			this.tl_c = tl_c;
			this.br_r = br_r;
			this.br_c = br_c;
			this.val = val;
		}
	}
	
    static class SegTree {
    	int[][] mat;
    	
    }
	
	public static int solution(int[][] board, int[][] skill) {
		
		
		int answer = 0;
        return answer;
    }
	
	public static void main(String[] args) {
		System.out.println(solution(
				new int[][] {{5,5,5,5,5},{5,5,5,5,5},{5,5,5,5,5},{5,5,5,5,5}}, 
				new int[][] {{1,0,0,3,4,4},{1,2,0,2,3,2},{2,1,0,3,1,2},{1,0,1,3,3,1}}
				)); 
		// 10
		
		System.out.println(solution(
				new int[][] {{1,2,3},{4,5,6},{7,8,9}}, 
				new int[][] {{1,1,1,2,2,4},{1,0,0,1,1,2},{2,2,0,2,0,100}})); 
		// 6
	}
}
