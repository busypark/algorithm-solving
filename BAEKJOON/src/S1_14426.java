import java.io.*;
import java.util.*;

// 이게 거의 최적 풀이인듯?

public class S1_14426 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		Dict dict = new Dict();
		for (int i=0; i<N; i++) {
			String word = br.readLine();
			dict.add(word);
		}
		
		int count = 0;
		for (int i=0; i<M; i++) {
			String word = br.readLine();
			if (dict.prefix(word)) {
				count ++;
			}
		}
		
		System.out.println(count);
	}
}

class Dict {
	Dict[] child;
	
	Dict() {
		child = new Dict[26];
	}
	
	void add(String s) {
		int n = s.length();
		Dict self = this;
		for (int i=0; i<n; i++) {
			int idx = s.charAt(i) - 'a';
			if (self.child[idx] == null) {
				self.child[idx] = new Dict();
			}
			
			self = self.child[idx];
		}
	}
	
	boolean prefix(String s) {
		int n = s.length();
		Dict self = this;
		for (int i=0; i<n; i++) {
			int idx = s.charAt(i) - 'a';
			if (self.child[idx] == null) {
				return false;
			}
			
			self = self.child[idx];
		}
		
		return true;
	}
}
