import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

public class D3_8931 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int K = sc.nextInt();
				final Deque<Integer> stack = new ArrayDeque<>();
				for (int i=0; i<K; i++) {
					final int input = sc.nextInt();
					if (input == 0) stack.pop();
					else stack.push(input);
				}
				
				int answer = 0;
				while (!stack.isEmpty()) {
					answer += stack.pop();
				}
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}
