import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1406

/*
 * 기존의 자료구조로 처리하면 최대 60만이 될 지도 모르는 문자열을 60만번씩 순회해야 할 수도 있음
 * 어차피 처리 자체가 LinkedList의 성격을 띄고, 커서는 한 칸씩만 움직이므로 직접 구현을 해서
 * 계속 current cursor의 위치를 따라다녀야 가장 효율적으로(빠르게 목적 노드 찾아가기) 가능할 듯
 * current cursor의 위치는 0~L 중 하나로 지정 (i = i번째 문자 오른쪽임)
 */

/*
 * 1차 : (강의, 40분) 실패. 출력 다름 (abcdyx가 아니라 abcd라고 뜸)
 * 2차 : (prev.next = appNode_1406 추가) 에러 (L 계속하면)
 * 3차 : 에러 (P에서 에러)
 * 4차 : 에러 (B에서 에러)
 * 5차 : 에러 (D)
 * 6차 : 테스트 케이스 통과 -> 틀림
 * 7차 : 에러 (next != null 추가)
 * 8차 : 테스트 케이스 통과 -> 정답 (강의 40분 + 그냥 30분)
 */

/*
 * 피드백 : 전략은 잘 짠 듯. 근데 '시간 초과가 문제'라는 힌트를 안 들었었다면 처음부터 저렇게 전략 짰을지?.. 의문
 * 그리고 자잘한 에러를 잘 못 잡았는데, double linked list 구현할 때에는
 * 상상/그림 어떻게든 prev/next의 이미지를 손이 튀어나온 것처럼 그려보면 도움이 되는 듯
 */

public class S2_1406 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static StringBuilder input = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		input.append(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		
		DLL_1406 DLL_1406 = new DLL_1406(input.toString());
		
		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			final char command = st.nextToken().charAt(0);
			switch (command) {
			case 'L':
				DLL_1406.L();
				break;
			case 'D':
				DLL_1406.D();
				break;
			case 'B':
				DLL_1406.B();
				break;
			case 'P':
				char app = st.nextToken().charAt(0);
				DLL_1406.P(app);
				break;
			}
		}
		
		System.out.println(DLL_1406.toString());
	}
}

class DLL_1406 {
	Node_1406 head;
	Node_1406 prev, next;
	
	DLL_1406(String s) {		
		head = null;
		Node_1406 last = null;
		Node_1406 cur = null;
		for (int i=0; i<s.length(); i++) {
			cur = new Node_1406(s.charAt(i));
			cur.prev = last;
			if (last != null) {
				last.next = cur;
			}
			
			if (i == 0) {
				head = cur;
			}
			
			last = cur;
		}
		
		prev = cur;
		next = null;
	}
	
	void L() {
		if (prev == null) return;
		next = prev;
		prev = prev.prev;
	}
	
	void D() {
		if (next == null) return;
		prev = next;
		next = next.next;
	}
	
	void B() {
		if (prev == null) return;
		if (head == prev) {
			head = next;
		}
		
		prev = prev.prev;
		if (prev != null)
			prev.next = next;
		
		if (next != null)
			next.prev = prev;
	}
	
	void P(char app) {
		Node_1406 appNode_1406 = new Node_1406(app);
		appNode_1406.prev = prev;
		appNode_1406.next = next;
		
		if (prev == null) {
			head = appNode_1406;
		}
		if (prev != null)
			prev.next = appNode_1406;
		if (next != null)
			next.prev = appNode_1406;
		
		prev = appNode_1406;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		while (head != null) {
			sb.append(head.c);
			head = head.next;
		}
		
		return sb.toString();
	}
}

class Node_1406 {
	Node_1406 prev, next;
	char c;
	
	Node_1406(char c) {
		prev = null;
		next = null;
		this.c = c;
	}
}
