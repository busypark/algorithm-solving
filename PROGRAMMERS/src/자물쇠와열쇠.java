
public class 자물쇠와열쇠 {
    static int[][] rotate(int[][] mat) {
    	// rotate clockwise
    	int[][] result = new int[mat.length][mat.length];
    	for (int nr=0; nr<mat.length; nr++) {
    		for (int nc=0; nc<mat.length; nc++) {
    			int r = mat.length - 1 - nc;
    			int c = nr;
    			
    			result[nr][nc] = mat[r][c];
    		}
    	}
    	
    	return result;
    }
    
    static void printMat(int[][] mat) {
    	System.out.println("#################");
    	for(int r=0; r<mat.length; r++) {
    		for(int c=0; c<mat.length; c++) {
    			System.out.print(mat[r][c]+" ");
    		}
    		
    		System.out.println();
    	}
    }
	
	public static boolean solution(int[][] originalKey, int[][] lock) {
        // count every hole
		int M = originalKey.length;
		int N = lock.length;
		
		int count = 0;
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				if (lock[r][c] == 0) {
					count ++;
				}
			}
		}
		
		if (count == 0) return true;
		
		// generate 4 keys for 4 directions
    	int[][][] keys = new int[4][][];
    	keys[0] = originalKey;
    	for (int i=1; i<4; i++) {
    		keys[i] = rotate(keys[i-1]);
    	}
    	
    	// shift and check for 4 keys
    	for (int i=0; i<4; i++) {
    		for (int ltr=1-M; ltr<=N; ltr++) {
    			for (int ltc=1-M; ltc<=N; ltc++) {
    				int localCount = count;
    				
    				// (ltr, ltc) indicates left-top point
    		    	nextKey:
    				for (int r=ltr; r<ltr+M; r++) {
    					for (int c=ltc; c<ltc+M; c++) {
    						// (r, c) indicates every point in the square
    						if (r < 0 || N <= r || c < 0 || N <= c) {
    							continue; // out of range(lock)
    						}
    						
    						if (lock[r][c] == 1 && keys[i][r-ltr][c-ltc] == 1) {
    							// collision -> invalid key
    							continue nextKey;
    						}
    						
    						if (lock[r][c] == 0 && keys[i][r-ltr][c-ltc] == 1) {
    							// key fills a hole
    							localCount --;
    						}
    						
    						
    						if (localCount == 0) {
    							// key filled every hole
    							return true;
    						}
    					}
    				}
    				

					//System.out.println("key: "+i+" | "+ltr+" "+ltc +" | "+count);
    			}
    		}
    	}
    	
    	return false;
    }
    
    public static void main(String[] args) {
    	//printMat(rotate(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
    	
    	System.out.println(solution(
    						new int[][] {{0, 0, 0}, {1, 0, 0}, {0, 1, 1}}, 
    						new int[][] {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}));
    	// true
    }
}
