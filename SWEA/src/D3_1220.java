import java.util.Scanner;

public class D3_1220 {
	public static final Scanner sc = new Scanner(System.in);	
	public static void main(String[] args) {
		int[][] map;
		for (int t=1; t<=10; t++) {
			final int N = sc.nextInt();
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					map[r][c] = sc.nextInt();
				}
			}
			
			// 전략 : 위에서부터 빨이 나오기 전까지의 파는 모두 제거, 아래에서부터 파가 나오기 전까지의 빨은 모두 제거
			int count = 0;
			// count top
			for (int c=0; c<N; c++) {
				for (int r=0; r<N; r++) {
					if (map[r][c] == 1) // 빨
						break;
					if (map[r][c] == 2)
						map[r][c] = 0;
				}
			}
			
			// count bottom
			for (int c=0; c<N; c++) {
				for (int r=N-1; 0<=r; r--) {
					if (map[r][c] == 2) // 파
						break;
					if (map[r][c] == 1)
						map[r][c] = 0;
				}
			}
			
			// count deadlock
			// 전략 : 빈 열이 아니면 반드시 위쪽은 빨, 아래는 파이므로 사이에 낀 것만 판별하면 됨
			//       이때, 첫 행이면 빨이 있기만 하면 count+1, 나머지 행이면 그 행 + 다음 행이 (빈 칸, 빨)인 경우 count+1
			//       위 전략은 이미 교착까지 map을 바꾼 상황에 유효할 것이므로, map을 안 바꿀 거라면 동일 색-다른 색 count 로직을 구성해야 함
			for (int c=0; c<N; c++) {
				boolean red = false;
				for (int r=0; r<N-1; r++) {					
					if (map[r][c] == 1 && !red) {
						count++;
						red = true;
					}
					
					if (map[r][c] == 2)
						red = false;
				}
				
				//System.out.println("c="+c+" "+count);
			}
			
			System.out.println("#"+t+" "+count);
			
			//break;
		}
	}
}
