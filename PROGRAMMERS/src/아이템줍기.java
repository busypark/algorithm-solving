import java.util.*;

public class 아이템줍기 {
	static class Vector {
		int x, y;
		Vector(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		void add(Vector d) {
			this.x += d.x;
			this.y += d.y;
		}
		
		Vector outerProductWithZ() {
			return new Vector(y, -x);
		}
		
		boolean equals(Vector v) {
			return x == v.x && y == v.y;
		}
	}
	
	static void setVF_byEdge(Vector[][] vf, Vector s, Vector e, Vector d) {
		// [s, e) by |d|=1
		Vector v = new Vector(s.x, s.y);
		while (!v.equals(e)) {
			if (vf[v.x][v.y] == null) {
				// not crossing
				vf[v.x][v.y] = new Vector(d.x, d.y);
			} else {
				// crossing -> determine where to go
				Vector out = vf[v.x][v.y].outerProductWithZ();
				if (vf[v.x][v.y].outerProductWithZ().equals(d)) {
					// next = v
					vf[v.x][v.y] = d;
				}
			}
			
			v.add(d);
		}
	}
	
	public static int solution(int[][] rectangles, int characterX, int characterY, int itemX, int itemY) {
        // construct vf
		Vector[][] vf = new Vector[51][51]; // directional vector field
        for (int[] rect : rectangles) {
        	int lbx = rect[0];
        	int lby = rect[1];
        	int rtx = rect[2];
        	int rty = rect[3];
        	
        	// counter-clockwise (lb -> rt -> lb)
        	setVF_byEdge(vf, new Vector(lbx, lby), new Vector(rtx, lby), new Vector(1, 0));
        	setVF_byEdge(vf, new Vector(rtx, lby), new Vector(rtx, rty), new Vector(0, 1));
        	setVF_byEdge(vf, new Vector(rtx, rty), new Vector(lbx, rty), new Vector(-1, 0));
        	setVF_byEdge(vf, new Vector(lbx, rty), new Vector(lbx, lby), new Vector(0, -1));
        }
        
        // follow vf
        Vector character = new Vector(characterX, characterY);
        Vector item = new Vector(itemX, itemY);
        
        boolean itemEaten = false;
        int distUntilItem = 0;
    	int distTotal = 0;
    	Vector v = new Vector(character.x, character.y);
    	do {	
    		v.add(vf[v.x][v.y]);
    		
    		distTotal ++;
    		if (!itemEaten) {
    			distUntilItem ++;
    		}
    		
    		if (v.equals(item)) {
    			itemEaten = true;
    		}
    	} while (!v.equals(character));
        
    	return Math.min(distUntilItem, distTotal - distUntilItem);
    }
	
	public static void main(String[] args) {
		System.out.println(
				solution(new int[][] {{1,1,7,4},{3,2,5,5},{4,3,6,9},{2,6,8,8}}, 
						 1, 3, 7, 8)); // 17
		System.out.println(
				solution(new int[][] {{1,1,8,4},{2,2,4,9},{3,6,9,8},{6,3,7,7}},
						 9, 7, 6, 1)); // 11
		System.out.println(
				solution(new int[][] {{1,1,5,7}},
						 1, 1, 4, 7)); // 9
		System.out.println(
				solution(new int[][] {{2,1,7,5},{6,4,10,10}},
						 3, 1, 7, 10)); // 15
		System.out.println(
				solution(new int[][] {{2,2,5,5},{1,3,6,4},{3,1,4,6}},
						 1, 4, 6, 3)); // 10
	}
}
