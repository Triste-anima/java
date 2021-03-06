package NationalChess;

public class My_map {
	/**
	 * 手工地图
	 * 1-黑兵  2-黑车  3-黑马  4-黑象  5-黑后  6-黑王
	 * -1-白兵  -2-白车  -3-白马  -4-白象  -5-白后  -6-白王
	 * 0-空地
	 */
	public static int[][] map;
	
	public My_map() {
		map = new int[][] {
			{2, 3, 4, 5, 6, 4, 3, 2},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{-1, -1, -1, -1, -1, -1, -1, -1},
			{-2, -3, -4, -5, -6, -4, -3, -2}
		};
	}
}
