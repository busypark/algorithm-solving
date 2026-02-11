import java.util.Scanner;

public class D4_1232 {
	static TreeNode_D4_1232[] nodes;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int N = sc.nextInt();
				nodes = new TreeNode_D4_1232[N+1];
				
				sc.nextLine();
				for (int i=0; i<N; i++) {
					final String line = sc.nextLine();
					final String[] split = line.split(" ");
					final int index = Integer.parseInt(split[0]);
					
					if (split.length == 2) {
						nodes[index] = new TreeNode_D4_1232(index, split[1], 0, 0);
					} else if (split.length == 4) {
						final int left = Integer.parseInt(split[2]);
						final int right = Integer.parseInt(split[3]);
						nodes[index] = new TreeNode_D4_1232(index, split[1], left, right);
					} else {
						System.out.println("[ERROR]");
					}
				}

				double answer = postOrder(1);
				
				System.out.println("#"+t+" "+(int)answer);
			}
		}
	}
	
	static double postOrder(int index) {
		TreeNode_D4_1232 thisNode = nodes[index];
		if (thisNode.left == 0) return thisNode.data; // 왼쪽만 없어도 오른쪽은 자동으로 없어야 하며, 그런 thisNode는 곧 숫자(피연산자)
		
		double leftAnswer = postOrder(thisNode.left);
		double rightAnswer = postOrder(thisNode.right);
		double answer = 0D;
		switch (thisNode.rawData.charAt(0)) {
		case '+':
			answer = leftAnswer + rightAnswer;
			break;
		case '-':
			answer = leftAnswer - rightAnswer;
			break;
		case '*':
			answer = leftAnswer * rightAnswer;
			break;
		case '/':
			answer = leftAnswer / rightAnswer;
			break;
		}
		
		return answer;
	}
}

class TreeNode_D4_1232 {
	int index = -1;
	String rawData = null;
	double data = 0D;
	int left = -1;
	int right = -1;
	
	TreeNode_D4_1232(int index, String rawData, int left, int right) {
		this.index = index;
		this.rawData = rawData;
		try {
			this.data = Double.parseDouble(rawData);
		} catch (Exception e) {	}
	
		this.left = left;
		this.right = right;
	}
}