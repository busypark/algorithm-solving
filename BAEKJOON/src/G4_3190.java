
/*
 * 종료 : 벽 또는 자신의 몸
 * 초기 : 길이 1, 좌상단, 오른쪽 향함
 * 이동 : 몸 길이 1칸 늘려, 부딪히면 끝, 사과 있으면 그대로 멈춤(길이+1) / 사과 없으면 꼬리 비움
 * solution : 이 게임이 몇 초에 끝나는가?
 * 
 * 이동 경로는 deque로 표현하면 어떨까?
 * 꼬리 하나 빠지는 거, 혹은 머리 하나 붙이는 거 모두 deque가 어울림
 * head 붙일 때에만 방향전환 고려하면 됨
 * 처음 머리 뻗을 때 1초 올라가고, 그때 부딪혀도 1초 올라갔던 건 반영됨
 * 
 * 피드백 : 강의 들으면서 30분 정도만에 풀었음. 심지어 첫 번째 제출에 바로 통과.
 * 문제를 맞이할 때의 그 설렘만 있으면 되는데.
 */

// https://www.acmicpc.net/problem/3190

import java.io.*;
import java.util.*;

public class G4_3190 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, K;
	static boolean[][] apple;

	static int L;
	static int[] timeLine;
	static boolean[] direction;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		K = Integer.parseInt(st.nextToken());
		
		apple = new boolean[N+1][N+1];
		for (int k=0; k<K; k++) {
			st = new StringTokenizer(br.readLine());
			final int r = Integer.parseInt(st.nextToken());
			final int c = Integer.parseInt(st.nextToken());
			
			apple[r][c] = true;
		}
		
		st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		timeLine = new int[L];
		direction = new boolean[L];
		
		Deque<SnakePart> snake = new ArrayDeque<>();
		snake.add(new SnakePart(1, 1, N));
		SnakePart dir = new SnakePart(0, 1, N);
		
		for (int l=0; l<L; l++) {
			st = new StringTokenizer(br.readLine());
			final int t = Integer.parseInt(st.nextToken());
			final char command = st.nextToken().charAt(0);
			
			timeLine[l] = t;
			direction[l] = command == 'D';
		}
		
		int time = 0;
		int curIdx = 0;
		while (true) {
			SnakePart head = snake.peekFirst();
			SnakePart newHead = head.newHead(dir);
			time++;
			
			// 벽에 부딪힘
			if (!newHead.valid()) {
				break;
			}
			
			// 자기 몸에 부딪힘
			boolean collide = false;
			Object[] snakeArray = snake.toArray();
			for (int i=0; i<snakeArray.length; i++) {
				if (snakeArray[i] instanceof SnakePart) {
					SnakePart sp = (SnakePart) snakeArray[i];
					if (sp.r == newHead.r && sp.c == newHead.c) {
						collide = true;
						break;
					}
				}
			}
			
			if (collide) break;
			
			// 진행 - 머리 추가
			snake.addFirst(newHead);
			
			if (apple[newHead.r][newHead.c]) {
				// 사과 먹으면 꼬리 안 자름
				apple[newHead.r][newHead.c] = false;
			} else {
				// 사과 안 먹으면 꼬리 자름
				snake.removeLast();
			}
			
			// 방향전환
			if (curIdx < L && timeLine[curIdx] == time) {
				if (direction[curIdx]) {
					dir.R();
				} else {
					dir.L();
				}
				
				curIdx++;
			}
		}
		
		System.out.println(time);
	}
	
}

class SnakePart {
	int r, c;
	final int N;
	SnakePart(int r, int c, int N) {
		this.r = r;
		this.c = c;
		this.N = N;
	}
	
	void L() {
		int temp = r;
		r = -c;
		c = temp;
	}
	
	void R() {
		int temp = r;
		r = c;
		c = -temp;
	}
	
	SnakePart newHead(SnakePart dir) {
		SnakePart result = new SnakePart(r + dir.r, c + dir.c, N);
		return result;
	}
	
	boolean valid() {
		return 1<=r && r<=N && 1<=c && c<=N;
	}
}
