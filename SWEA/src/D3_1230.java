import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

public class D3_1230 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int N = sc.nextInt();
				final List<Integer> orgList = new LinkedList<>();
				for (int i=0; i<N; i++)
					orgList.add(sc.nextInt());
				
				final int nCom = sc.nextInt(); // #commands
				for (int c=0; c<nCom; c++) {
					final String command = sc.next(); // "I", "D", "A"
					
					final int index;
					switch (command.charAt(0)) {
					case 'I':
						index = sc.nextInt();
						final int nInsert = sc.nextInt(); // #insert
						
						for (int i=0; i<nInsert; i++) {
							orgList.add(index+i, sc.nextInt());
						}
						
						break;
					case 'D':
						index = sc.nextInt();
						final int nDelete = sc.nextInt(); // #delete
						for (int i=0; i<nDelete; i++) {
							orgList.remove(index + 1);
						}
						
						break;
					case 'A':
						final int nAppend = sc.nextInt(); //#append
						for (int i=0; i<nAppend; i++) {
							orgList.add(sc.nextInt());
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
