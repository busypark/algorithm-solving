import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class D4_1218 {
	public static void main(String args[]) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int n = sc.nextInt();
				sc.nextLine();
				final String s = sc.nextLine();
				
				final Map<Character, Integer> stacks = new HashMap<>();
				stacks.put('(', 0);
				stacks.put('{', 0);
				stacks.put('[', 0);
				stacks.put('<', 0);
				
				final Map<Character, Character> openBracket = new HashMap<>();
				openBracket.put(')', '(');
				openBracket.put('}', '{');
				openBracket.put(']', '[');
				openBracket.put('>', '<');
				
				List<Character> openBrackets = new ArrayList<>();
				openBrackets.add('(');
				openBrackets.add('{');
				openBrackets.add('[');
				openBrackets.add('<');
				
				int i=0;
				for (; i<s.length(); i++) {

//					System.out.print("#"+t+" stacks ( { [ < : ");
//					System.out.print(stacks.get('(')+" "+stacks.get('{')+" "+stacks.get('[')+" "+stacks.get('<'));
//					System.out.println();
					
					char bracket = s.charAt(i);
					if (openBrackets.contains(bracket)) {
						stacks.put(bracket, stacks.get(bracket) + 1);
					} else {
						char oBracket = openBracket.get(bracket);
						stacks.put(oBracket, stacks.get(oBracket) - 1);
						if (stacks.get(oBracket) < 0) break;
					}
				}
				
				final int answer = (i == s.length() && stacks.get('(') + stacks.get('{') + stacks.get('[') + stacks.get('<') == 0)? 1 : 0;
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}
