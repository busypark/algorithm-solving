package test;

import java.io.*;
import java.util.*;

public class test {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		br.readLine();
		for (int t=1; t<=25; t++) {
			int N = Integer.parseInt(br.readLine());
			for (int i=0; i<N; i++)
				br.readLine();
			
			System.out.println("#"+t+" "+N);
		}
		
	}
}
