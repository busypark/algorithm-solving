import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayDeque;

public class D4_1224 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = 10;
			for (int t=1; t<=T; t++) {
				sc.nextInt();
				sc.nextLine();
				String s = sc.nextLine();
				final int n = s.length();
				
				// 후위 표기식으로 변환
				Map<Character, Integer> priority = new HashMap<>();
				priority.put('(', -1);
				priority.put(')', -1);
				priority.put('*', 1);
				priority.put('+', 0);
				Deque<Character> opStack = new ArrayDeque<>();
				StringBuilder postfix = new StringBuilder();
				for (int i=0; i<n; i++) {
					char thisChar = s.charAt(i);
					if (!priority.containsKey(thisChar)) { // operand(number)
						postfix.append(thisChar);
					} else { // among operators
						if (thisChar == '(') {
							opStack.push('(');
						} else if (thisChar == ')') {
							while (opStack.peek() != '(') {
								postfix.append(opStack.pop()); // print before '('
							}
							
							opStack.pop(); // pop '('
						} else { // operators '+' or '*'
							while (!opStack.isEmpty() && priority.get(opStack.peek()) >= priority.get(thisChar)) {
								char pop = opStack.pop();
								postfix.append(pop);
							}
							opStack.push(thisChar);
						}
					}
					//System.out.println(postfix);
				}
				while (!opStack.isEmpty())
					postfix.append(opStack.pop());
				
				//System.out.println(postfix);
				
				// Stack으로 계산
				Deque<Integer> operandStack = new ArrayDeque<>();
				for (int i=0; i<postfix.length(); i++) {
					char thisChar = postfix.charAt(i);
					if (priority.containsKey(thisChar)) { // among operators(+, *) -> parenthesis were removed
						int a = operandStack.pop();
						int b = operandStack.pop();
						
						if (thisChar == '+') operandStack.push(a+b);
						if (thisChar == '*') operandStack.push(a*b);
					} else { // operand(number)
						operandStack.push(thisChar - '0');
					}
				}
				
				int answer = operandStack.pop();
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}
