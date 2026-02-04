import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class D3_1221 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			final Map<String, Integer> mapSI = new HashMap<>();
			mapSI.put("ZRO", 0);
			mapSI.put("ONE", 1);
			mapSI.put("TWO", 2);
			mapSI.put("THR", 3);
			mapSI.put("FOR", 4);
			mapSI.put("FIV", 5);
			mapSI.put("SIX", 6);
			mapSI.put("SVN", 7);
			mapSI.put("EGT", 8);
			mapSI.put("NIN", 9);
			
			final Map<Integer, String> mapIS = new HashMap<>();
			mapIS.put(0, "ZRO");
			mapIS.put(1, "ONE");
			mapIS.put(2, "TWO");
			mapIS.put(3, "THR");
			mapIS.put(4, "FOR");
			mapIS.put(5, "FIV");
			mapIS.put(6, "SIX");
			mapIS.put(7, "SVN");
			mapIS.put(8, "EGT");
			mapIS.put(9, "NIN");
			
			for (int t=1; t<=T; t++) {
				final String testCase = sc.next(); // #1 #2 등
				final int n = sc.nextInt();
				sc.nextLine();
				
				int[] arr = new int[n];
				for (int i=0; i<n; i++) {
					arr[i] = mapSI.get(sc.next());
				}
				
				Arrays.sort(arr);
				
				System.out.println(testCase);
				for (int i=0; i<n; i++)
					System.out.print(mapIS.get(arr[i])+" ");
				System.out.println();
			}
		}
	}
}
