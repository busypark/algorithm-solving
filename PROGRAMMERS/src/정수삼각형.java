
public class 정수삼각형 {
    public static int solution(int[][] triangle) {
        for (int r=triangle.length-1; 1<=r; r--) {
        	for (int c=0; c<r; c++) {
        		triangle[r-1][c] += Math.max(triangle[r][c], triangle[r][c+1]);
        	}
        }
    	
    	return triangle[0][0];
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(
    					   new int[][] {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}}));
    	// 30
    }
}
