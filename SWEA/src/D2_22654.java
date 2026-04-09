import java.io.*;
import java.util.*;

// 한 15분만에 잘 풀었음. 특히 시계/반시계 90도 돌리기를 예전처럼 외적/내적으로 안 하고
// 그냥 dr/dc 배열에서 idx를 높이거나 낮추는 걸로 처리한 게 잘 한 듯

public class D2_22654 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			answer.append("#").append(t);
			
			int N = Integer.parseInt(br.readLine());
			int pr_backup = 0;
			int pc_backup = 0;
			char[][] map = new char[N][N];
			for (int r=0; r<N; r++) {
				String row = br.readLine();
				for (int c=0; c<N; c++) {
					map[r][c] = row.charAt(c);
					if (map[r][c] == 'X') {
						pr_backup = r;
						pc_backup = c;
					}
				}
			}
			
			int Q = Integer.parseInt(br.readLine());
			int[] dr = {-1, 0, 1, 0};
			int[] dc = {0, 1, 0, -1};
			for (int i=0; i<Q; i++) {
				int pr = pr_backup;
				int pc = pc_backup;
				int d = 0;
				
				st = new StringTokenizer(br.readLine());
				int l = Integer.parseInt(st.nextToken());
				String commands = st.nextToken();
				for (int j=0; j<l; j++) {
					switch (commands.charAt(j)) {
					case 'A':
						int nr = pr + dr[d];
						int nc = pc + dc[d];
						
						if (0<=nr && nr<N && 0<=nc && nc<N && map[nr][nc] != 'T') {
							pr = nr;
							pc = nc;
						}
						break;
					case 'L':
						d = (d + 3) % 4;
						break;
					case 'R':
						d = (d + 1) % 4;
						break;
					}
				}
				
				answer.append(" ").append((map[pr][pc] == 'Y') ? 1 : 0);
			}
			
			answer.append("\n");
		}
		
		System.out.print(answer);
	}
}
