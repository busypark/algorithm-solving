import java.io.*;
import java.util.*;

public class G4_17298 {
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		final int N = Integer.parseInt(br.readLine());
		final int[] input = new int[N];
		final int[] output = new int[N];
		
		st = new StringTokenizer(br.readLine());
		input[0] = Integer.parseInt(st.nextToken());
		Stack<Integer> stack = new Stack<>();
		for (int i=1; i<N; i++) {
			input[i] = Integer.parseInt(st.nextToken());
			if (input[i-1] < input[i]) {
				output[i-1] = input[i];
				while (!stack.isEmpty() && input[stack.peek()] < input[i]) {
					output[stack.pop()] = input[i];
				}
			} else {
				stack.push(i-1);
			}
		}
		
		output[N-1] = -1;
		
		// release remaining stack to -1
		for (int ele : stack) {
			output[ele] = -1;
		}
		
		// print
		for (int ele : output) {
			answer.append(ele).append(" ");
		}
		
		System.out.println(answer);
	}
}
