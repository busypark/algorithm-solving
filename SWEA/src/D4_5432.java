import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

// 객체지향적으로 풀어보려고 했는데 자꾸 시간초과 떠서 그냥 객체 없이 int 변수로 개수 세기해서 시간초과 해소

public class D4_5432 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			sc.nextLine();
			
			for (int t=1; t<=T; t++) {
				String s = sc.nextLine();
				//List<Double> lasorPositions = new ArrayList<>(); // mid points(N.5) of lasors
				//Deque<Stick> openSticks = new ArrayDeque<>();
				//Deque<Stick> completeSticks = new ArrayDeque<>();
				int stackSticks = 0;
				
				int answer = 0;
				for (int i=0; i<s.length(); i++) {
					if (i<s.length()-1 && s.charAt(i) == '(' && s.charAt(i+1) == ')') { // lasor detected
						//lasorPositions.add(i + 0.5);
						answer += stackSticks;
					} else {
						if (s.charAt(i) == '(') { // starting stick detected (not lasor)
							//openSticks.add(new Stick(i, -1)); // add an open stick which has undetermined finishing position
							stackSticks++;
						} else { // finishing point of a stick detected (not lasor)
							if (0<i && s.charAt(i-1) != '(') {
								/*
								Stick theStick = openSticks.pop();
								theStick.finishingPosition = i;
								
								int smallAnswer = 0;
								for (int j=0; j<lasorPositions.size(); j++) {
									if (theStick.startingPosition <= lasorPositions.get(j) && lasorPositions.get(j) <= theStick.finishingPosition) {
										smallAnswer++;
									}
								}
								if (0 < smallAnswer)
									answer += smallAnswer+1;
								*/
								//completeSticks.add(theStick);
								stackSticks--;
								answer++;
							}
						}
					}
				}
				
				/*1
				// Calculate the total number of seperations
				int answer = 0;
				while (!completeSticks.isEmpty()) {
					Stick thisStick = completeSticks.pop();
					int smallAnswer = 0;
					for (int j=0; j<lasorPositions.size(); j++) {
						if (thisStick.startingPosition <= lasorPositions.get(j) && lasorPositions.get(j) <= thisStick.finishingPosition) {
							smallAnswer++;
						}
					}
					if (0 < smallAnswer)
						answer += smallAnswer+1;
				}
				*/
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}

class Stick {
	int startingPosition, finishingPosition; // both inclusive
	Stick(int startingPosition, int finishingPosition) {
		this.startingPosition = startingPosition;
		this.finishingPosition = finishingPosition;
	}
}
