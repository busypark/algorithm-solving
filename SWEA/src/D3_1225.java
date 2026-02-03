import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

public class D3_1225 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				sc.nextInt();
				Deque<Integer> password = new ArrayDeque<>();
				for (int i=0; i<8; i++)
					password.addLast(sc.nextInt());
				
				boolean loop = true;
				while (loop) {
					for (int dec=1; dec<=5; dec++) {
						int first = password.pop();
						first = Math.max(first - dec, 0);
						password.addLast(first);
						if (first <= 0) {
							loop = false;
							break;
						}
					}
				}
				
				System.out.print("#"+t+" ");
				for (int i=0; i<8; i++)
					System.out.print(password.pop()+" ");
				System.out.println("");
			}
		}
	}
}
