package D5_14613;

import java.util.*;

class UserSolution
{
	int listSize = 0;
	List<Integer>[] lists = new List[10];
	
	Map<String, Integer> listIds = new HashMap<>();
	Map<String, Integer> listTs = new HashMap<>();
	
	public void init() {
		for (int i=0; i<10; i++) {
			lists[i] = new ArrayList<>();
		}
	}

	public void makeList(char mName[], int mLength, int mListValue[])
	{
		System.out.println("[makeList] "+mName.toString()+" "+mLength+" "+Arrays.toString(mListValue));
		
		int id = listSize;
		
		String mNameStr = mName.toString();
		
		listIds.put(mNameStr, id);
		listTs.put(mNameStr, 0);
		for (int i=0; i<mLength; i++) {
			lists[id].add(mListValue[i]);
		}
		
		listSize++;
	}

	public void copyList(char mDest[], char mSrc[], boolean mCopy)
	{
		System.out.println("[copyList] "+mDest.toString() +" "+mSrc.toString()+" "+mCopy);
		
		String mSrcStr = mSrc.toString();
		String mDestStr = mDest.toString();
		
		int orgId = listIds.get(mSrcStr);
		listIds.put(mDestStr, orgId);
		
		if (mCopy) {
			listTs.put(mDestStr, listTs.get(mSrcStr) + 1);
		} else {
			listTs.put(mDestStr, null)
		}
	}

	public void updateElement(char mName[], int mIndex, int mValue)
	{
		System.out.println("[updateElement] "+mName.toString()+" "+mIndex+" "+mValue);
		
	}

	public int element(char mName[], int mIndex)
	{
		System.out.println("[element] "+mName.toString()+" "+mIndex);
		
		return 0;
	}
}