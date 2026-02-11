import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class PRACTICE_hanoiTower {
	static final List<Integer[]> moves = new ArrayList<>();
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int n = sc.nextInt();
			
			getMoves(1, 2, 3);
			System.out.print("[ ");
			for (Integer[] move : moves) {
				System.out.print("");
			}
			
		}
	}
	
	static void getMoves(int init, int sub, int target) {
		
	}
}
