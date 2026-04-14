import java.util.*;

// 복잡해보였는데 재귀함수로 접근해서 잘 푼 듯? 대략 20분 소요
// 피드백 : 

public class G5_2447 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringBuilder answer = new StringBuilder();
		
		int N = sc.nextInt();
		boolean[][] inverseGrid  = new boolean[N][N];
		
		inverseStamp(inverseGrid, N / 3, 0, 0);
		
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				answer.append(inverseGrid[r][c] ? " " : "*");
			}
			
			answer.append("\n");
		}
		
		System.out.print(answer);
	}
	
	static void inverseStamp(boolean[][] inverseGrid, int unit, int rs, int cs) {
		if (unit == 0) return;
		
		for (int r = rs + unit; r < rs + unit*2; r++) {
			for (int c = cs + unit; c < cs + unit*2; c++) {
				inverseGrid[r][c] = true;
			}
		}
		
		int newUnit = unit / 3;
		for (int r=0; r<=2; r++) {
			for (int c=0; c<=2; c++) {
				if (r != 1 || c != 1) {
					inverseStamp(inverseGrid, newUnit, rs + r * unit, cs + c * unit);
				}
			}
		}
	}
}
