import java.util.Scanner;

public class D2_1984 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				int max = -1;
				int min = 10001;
				
				int[] arr = new int[10];
				for (int i=0; i<10; i++) {
					arr[i] = sc.nextInt();
					if (max < arr[i]) max = arr[i];
					if (arr[i] < min) min = arr[i];
				}
				
				int sum = 0;
				int count = 0;
				for (int i=0; i<10; i++) {
					if (arr[i] != min && arr[i] != max) {
						count++;
						sum += arr[i];
					}
				}
				
				double avg = sum / (count+0.0D);
				long round = Math.round(avg);
				
				System.out.println("#"+t+" "+round);
			}
		}
	}
}
