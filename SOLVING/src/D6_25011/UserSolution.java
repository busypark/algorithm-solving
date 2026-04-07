package D6_25011;

import java.util.*;

class UserSolution {

	Dict root;
	
	void init(int N, String mWordList[], int mWordSize)
	{
		root = new Dict(null);
	}
	
	void addOneWord(String word) {
		char[] w = word.toCharArray();
		Dict here = root;
		for (char c : w) {
			
		}
	}

	void addWord(String mWordList[], int mWordSize)
	{
		
	}
	
	void removeOneWord(String word) {
		
	}

	void removeWord(String mWordList[], int mWordSize)
	{
		
	}

	
	String findWord(int mPageNum)
	{
		String mRet = new String();
		
		return mRet;
	}

	int findPage(String mWord)
	{
		return 0;
	}
}

class Dict implements Comparable<Dict> {
	int count;
	List<Character> keys;
	Map<Character, List<Dict>> subDicts;
	
	Dict() {
		keys = new ArrayList<>();
		subDicts = new HashMap<>();
	}
	
	@Override
	public int compareTo(Dict d) {
		if (c == null && d.c == null) {
			return 0;
		}
		
		if (c == null) {
			return 1;
		}
		
		if (d.c == null) {
			return -1;
		}
		
		return c - d.c;
	}
}




