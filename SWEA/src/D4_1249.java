import java.util.Scanner;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class D4_1249 {
	public static int N;
	public static int[][] map;
	public static List<Deque<Pos>> cases;
	public static int minTotal;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				N = sc.nextInt(); // 2~100
				sc.nextLine();
				map = new int[N][N];
				for (int r=0; r<N; r++) {
					String row = sc.nextLine();
					for (int c=0; c<N; c++) {
						map[r][c] = row.charAt(c) - '0';
					}
				}
				
				// 전략 : 2중 Deque를 만듦. 직전 index(경우)를 하나 pop함에 따라 다음 index의 경우들이 만들어짐
				//       경로 중간에 이미 minTotal을 넘어버리면 탐색 중단(더 가봤자 minTotal보다 못하므로)
				//       경로가 성립하지 않는 경우는 돌아가면 됨
				//       경로가 하나 완성된 경우는 기존의 minTotal과 비교해 갱신
				//       1차원 : 각 step
				//       2차원 : 각 step에 대한 경우의 수
				/* 수정 : 굳이 2차원으로 할 필요가 없는 것 같다
						 Deque<Pos>로 처리하고 'pop -> 처리' 반복해도 될듯
				   수정 : pop을 하면 기존 log가 날라가서 다음 갈 곳 후보를 뽑을 때 왔던 데로 돌아갈 수 있으므로 peek으로 변경
				   		 peek -> 처리 -> pop(버리기) 하면 한 번 들어갔다가 나온 다음 버리는 거니까 맞음
				   		 stepLog에 의해서만 지나온 여부를 검사하면 상관 없는 다른 경우의 길까지 고려하게 돼서 틀림. 별도의 passed 필요
				   		 그런데 pop을 안 하고 딱 맞게 로직을 짜기가 힘듦. pop을 하고 별도의 passed에 저장하는게 나을듯
				   		 가장 기초적인 경로 하나는 정상적으로 찾음. 근데 거기서 프로그램이 끝나버림. 다음 경로들이 스택에 있는데?
				   초기화--
				*/
				
				/*
				 스택 방식으로 풀어내면 잘 될 줄 알았는데, 각 Step에서 경우의 수를 뽑아내는 건 쉽지만 G에 도착하거나 dead-end인 경우를 처리하는 것이 깔끔하지 못함
				 좀 더 직관적인 해석 필요..
				 직관적인 걸로 치면 제일 처음의 2중 Deque가 더 직관적이었는데..
				 2중 Deque 방식으로 하려면 깊게 파는 방식만 가능함. 
				 그러니까, 각 i번째 요소(1차원 Deque)들은 i-1번째 Deque의 특정한 item(후보)으로 이동한 1가지 경우의 또다른 후보들을 표현하는 것만 가능
				 2중 deque보다는 List<Deque<Pos>>가 나을 듯. 각 Step은 번호로 접근하는 게 더 편함
				*/
				
				cases = new ArrayList<>();
				cases.add(new ArrayDeque<Pos>());
				cases.get(0).add(new Pos(0, 0));
				
				searchPath(0);
				
				int answer = minTotal;
				System.out.println("#"+t+" "+answer);
				
				break;
			}
		}
	}
	
	static void searchPath(int cost) {
		// 언제 이 재귀가 멈추나?
		
		if (cases.get(cases.size()-1).size() == 0) { // no more candidates. switch to another way
			
			return;
		}
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}
}

class Pos {
	int r, c;
	Pos(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pos) {
			Pos p = (Pos) o;
			return (p.r == r && p.c == c);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}
