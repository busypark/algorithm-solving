import java.io.*;
import java.util.*;

public class CodeTree_뱀은사과를좋아해 {
	static Map<Character, Integer> dmap = new HashMap<>(Map.of('U', 0, 'D', 1, 'L', 2, 'R', 3));
	static int[] Dr = {-1, 1, 0, 0};
	static int[] Dc = {0, 0, -1, 1};
	
	static class Node {
		Node prev, next;
		
		void destroy() {
			prev.next = next;
			next.prev = prev;
		}
	}
	
	static class Snake {
		Node head, tail, realHead, realTail;
		int r, c;
		
		Snake() {
			r = 1;
			c = 1;
			
			head = new Node();
			tail = new Node();
			realHead = new Node();
			realTail = realHead;
			
			head.next = realHead;
			
			realHead.prev = head;
			realHead.next = tail;
			
			tail.prev = realHead;
		}
		
		Node newHead() {
			Node newH = new Node();
			newH.prev = head;
			newH.next = realHead;
			
			head.next = newH;
			realHead.prev = newH;
			
			return newH;
		}
		
		void follow() {
			realTail = realTail.prev;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());		
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		Node[][] map = new Node[N][N];
		boolean[][] apple = new boolean[N][N];
		
		Snake snake = new Snake();
		map[1][1] = snake.realHead;
		
		for (int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			apple[r][c] = true;
		}
		
		int t = 0;
		
		Move:
		for (int k=0; k<K; k++) {
			st = new StringTokenizer(br.readLine());
			char d = st.nextToken().charAt(0);
			int p = Integer.parseInt(st.nextToken());
			
			int dr = Dr[dmap.get(d)];
			int dc = Dc[dmap.get(d)];
			
			for (int i=0; i<p; i++) {
				int nr = snake.r + dr;
				int nc = snake.c + dc;
				
				Node newHead = snake.newHead();
				
			}
			
			t ++;
		}
		
		System.out.println(t);
	}
}
