import java.util.Scanner;

public class D4_1231 {
	static TreeNode_D4_1231[] nodes;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int N = sc.nextInt();
				nodes = new TreeNode_D4_1231[N+1];
				
				sc.nextLine();
				for (int i=0; i<N; i++) {
					final String line = sc.nextLine();
					final String[] split = line.split(" ");
					
					final int index = Integer.parseInt(split[0]);
					final String data = split[1];
					final int left = (split.length >= 3) ? Integer.parseInt(split[2]) : 0;
					final int right = (split.length == 4) ? Integer.parseInt(split[3]) : 0;
					
					nodes[index] = new TreeNode_D4_1231(index, data, left, right);
				}
				
				String answer = inOrder(1, "");
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
	
	static String inOrder(int index, String pass) {
		TreeNode_D4_1231 thisNode = nodes[index];
		if (thisNode.data.equals(null)) return pass;
		
		String leftAnswer = (thisNode.left != 0) ? inOrder(thisNode.left, pass) : "";
		String thisAnswer = thisNode.data;
		String rightAnswer = (thisNode.right != 0) ? inOrder(thisNode.right, pass) : "";
		
		return leftAnswer + thisAnswer + rightAnswer;
	}
}

class TreeNode_D4_1231 {
	int index = -1;
	String data = null;
	int left = -1;
	int right = -1;
	
	TreeNode_D4_1231(int index, String data, int left, int right) {
		this.index = index;
		this.data = data;
		this.left = left;
		this.right = right;
	}
}