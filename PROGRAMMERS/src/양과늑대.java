import java.util.*;

public class 양과늑대 {
    static class Sheep {
    	int aboveWolves, jumpWolves;
    	Sheep(int aboveWolves, int jumpWolves) {
    		this.aboveWolves = aboveWolves;
    		this.jumpWolves = jumpWolves;
    	}
    }
	
	public static int solution(int[] info, int[][] edges) {
    	// transform edge list into binary tree
    	final int n = info.length;
    	int[] parent = new int[n];
    	for (int i=0; i<n; i++) {
    		parent[i] = -1; // no parent
    	}
    	
    	for (int[] edge : edges) {
    		int s = edge[0];
    		int e = edge[1];
    		
    		parent[e] = s;
    	}
    	
    	// Collect information of sheeps
    	List<Sheep> sheepInfo = new ArrayList<>();
    	for (int i=0; i<n; i++) {
    		if (info[i] == 0) { // if i-th indicates a sheep
    			int j = i;
    			int aboveWolves = 0;
    			int jumpWolves = 0;
    			boolean startJump = false;
    			boolean endJump = false;
    			while (j != -1) {
    				if (info[j] == 1) {
    					aboveWolves ++;
    				}
    				
    				if (!endJump && !startJump && info[j] == 1) {
    					startJump = true;
    				}
    				
    				if (startJump && info[j] == 1) {
    					jumpWolves ++;
    				}
    				
    				if (startJump && info[j] == 0) {
    					startJump = false;
    					endJump = true; // monotonically F to T
    				}
    				
    				j = parent[j];
    			}
    			
    			sheepInfo.add(new Sheep(aboveWolves, jumpWolves));
    			System.out.println("above: "+aboveWolves + " jump: "+jumpWolves);
    		}
    	}
    	
    	// sort by aboveWolves
    	sheepInfo.sort((s1, s2) -> Integer.compare(s1.aboveWolves, s2.aboveWolves));
    	
    	// construct the answer
        int answer = 1;
        int sumWolves = 0;
        for (Sheep sheep : sheepInfo) {
        	sumWolves += sheep.jumpWolves;
        	if (answer <= sumWolves) break;
        	
        	answer ++;
        }
        
        return answer - 1;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(new int[] {0,0,1,1,1,0,1,0,1,0,1,1}, new int[][] {{0,1},{1,2},{1,4},{0,8},{8,7},{9,10},{9,11},{4,3},{6,5},{4,6},{8,9}}));
    	// 5
    	
    	System.out.println(solution(new int[] {0,1,0,1,1,0,1,0,0,1,0}, new int[][] {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,8},{6,9},{9,10}}));
    	// 5
    }
}
