import java.io.*;
import java.util.*;

// 피드백 : 빨리 풀긴 했는데 로봇 이동만 신경쓰고 컨베이어 벨트 이동에 의한 loadDown을 체크안해서 오래 헤맸음

public class G5_20055 {
	static int N, K;
	static int[] health;
	
	static int loadUp;
	static int loadDown;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input N, K, and health[N];
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		final int size = 2*N;
		health = new int[size];
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<size; i++) {
			health[i] = Integer.parseInt(st.nextToken());
		}
		
		int step = 0;
		Map<Integer, Integer> robots = new HashMap<>();
		boolean[] robotThere = new boolean[size];
		int lastRobotIdx = -1;
		loadUp = 0;
		loadDown = N-1;
		
		Progress:
		for (;;) {
			step ++;
			
			// 1. rotate (pointer, counter-clockwise)
			loadUp = (loadUp - 1 + size) % size;
			loadDown = (loadDown - 1 + size) % size;
			
			// 2. move robot
			List<Integer> robotIndex = new ArrayList<>(robots.keySet());
			robotIndex.sort(null);
			MoveRobot:
			for (int rIdx : robotIndex) {
				int rb = robots.get(rIdx);
				
				// load-down and remove
				if (rb == loadDown) {
					robots.remove(rIdx);
					robotThere[rb] = false;
					
					continue;
				}
				
				// move feasibility
				int next = (rb + 1) % size;
				if (health[next] == 0 || robotThere[next])
					continue MoveRobot;
				
				// health --
				health[next] --;
				
				// load-down and remove
				if (next == loadDown) {
					robots.remove(rIdx);
					robotThere[rb] = false;
					robotThere[next] = false;
					
					continue;
				}
				
				// move
				robots.put(rIdx, next);
				robotThere[rb] = false;
				robotThere[next] = true;
				
			}
			
			// 3. load-up
			if (health[loadUp] >= 1) {
				lastRobotIdx ++;
				robots.put(lastRobotIdx, loadUp);
				robotThere[loadUp] = true; 
				health[loadUp] --;
			}
			
			// 4. exit?
			int countZero = 0;
			for (int i=0; i<size; i++) {
				if (health[i] == 0) {
					countZero ++;
				}
				
				if (K <= countZero) {					
					break Progress;
				}
			}
		}
		
		System.out.println(step);
	}
}
