import java.io.*;
import java.util.*;

/*
 * 논리적 오류 : 단어 위치가 끝인지 표시를 안 하면 app, apple 등록하고 app 뒤에 더 있다는 이유로 app이 없다고 판단
 * 성능적 오류 : map이 array보다 약 3배 이상 느리다고 함. hash 배정같은 절차가 데이터가 많아질수록 부하가 커짐
 */

public class S4_14425 {
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
			if (dict.find(word))
				count ++;
		}
		
		System.out.println(count);
	}
}

class Dict { 
	//Map<Character, Dict> map;
	Dict[] child;
	boolean end;
	
	Dict() {
		//map = new HashMap<>();
		child = new Dict[26];
		end = false;
	}
	
	void add(String s) {
		Dict self = this;
		
		for (int i=0; i<s.length(); i++) {
			int c = s.charAt(i) - 'a';
			//if (!self.map.containsKey(c)) {
			if (self.child[c] == null) {
				//self.map.put(c, new Dict());
				self.child[c] = new Dict();
			}
			
			//self = self.map.get(c);
			self = self.child[c];
		}
		
		self.end = true;
	}
	
	boolean find(String s) {
		Dict self = this;
		
		for (int i=0; i<s.length(); i++) {
			int c = s.charAt(i) - 'a';
			//if (!self.map.containsKey(c)) {
			if (self.child[c] == null) {
				return false;
			}

			//self = self.map.get(c);
			self = self.child[c];
		}
		
		return self.end;
	}
}