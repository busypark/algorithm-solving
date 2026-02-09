import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

public class D3_3499 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt();
				Deque<String> q1 = new ArrayDeque<>();
				Deque<String> q2 = new ArrayDeque<>();
				
				for (int n=0; n<N; n++) {
					final String card = sc.next();
					
					if (n < (N+1)/2)
						q1.addLast(card);
					else
						q2.addLast(card);
				}
				
				System.out.print("#"+t);
				while (!q1.isEmpty() || !q2.isEmpty()) {
					if (!q1.isEmpty())
						System.out.print(" "+q1.pollFirst());
					
					if (!q2.isEmpty())
						System.out.print(" "+q2.pollFirst());
				}
				
				System.out.println();
			}
		}
	}
}
