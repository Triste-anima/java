package Block;

public class Supply extends Position{
	// 补给种类 0-炸弹    1-子弹
	private int kind;

	public Supply(double x, double y, int k) {
		this.x = x;
		this.y = y;
		kind = k;
	}
	
	public int getKind() {
		return kind;
	}
	
	public void move() {
		y += 30;
	}
}
