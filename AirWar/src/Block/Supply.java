package Block;

public class Supply extends Position{
	// �������� 0-ը��    1-�ӵ�
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
