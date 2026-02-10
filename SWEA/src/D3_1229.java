import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

// 의외의 함정. '다음 위치'라는 말 때문에 index가 범위 안에 무조건 주어질 것 같지만 사실 index = -10000인 경우.. 그 다음부터는 0부터로 조정해야 함

public class D3_1229 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int N = sc.nextInt();
				final List<Integer> orgList = new LinkedList<>();
				for (int i=0; i<N; i++)
					orgList.add(sc.nextInt());
				
				final int nCom = sc.nextInt(); // #commands
				for (int c=0; c<nCom; c++) {
					final String command = sc.next(); // "I", "D"
					
					int index = sc.nextInt();
					index = Math.min(Math.max(index, 0), orgList.size());
					switch (command.charAt(0)) {
					case 'I':
						final int nInsert = sc.nextInt(); // #insert
						
						for (int i=0; i<nInsert; i++) {
							orgList.add(index+i, sc.nextInt());
						}
						
						break;
					case 'D':
						final int nDelete = sc.nextInt(); // #delete
						for (int i=0; i<nDelete; i++) {
							orgList.remove(index);
						}
						
						break;
					}
				}
				
				System.out.print("#"+t+" ");
				for (int i=0; i<Math.min(10, orgList.size()); i++)
					System.out.print(orgList.get(i)+" ");
				System.out.println();
			}
		}
	}
}
