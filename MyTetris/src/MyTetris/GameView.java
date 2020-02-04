package MyTetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	/**
	 * 主要游戏游戏框 10 * 20（像素：250*500）
	 */
	private JLabel[][] box;	 //存放当前游戏框内的所有方块
	private Cell movingCell;
	private LinkedList<Cell> nextCells;
	
	public GameView(int r, int c) {
		super();
		this.setLayout(new GridLayout(r, c));
		box = new JLabel[r][c];
		for(int i = 0; i < r; ++i) {
			for(int j = 0; j < c; ++j) {
				box[i][j] = new JLabel();
				box[i][j].setPreferredSize(new Dimension(25, 25));
				box[i][j].setBackground(Color.YELLOW);
				this.add(box[i][j]);
			}
		}
		nextCells = new LinkedList<Cell>();
	}
	
	// 返回消去的行数
	public int removeRow() {
		movingCell = null;
		int count = 0;
		for(int i = box.length - 1; i >= 0; --i) {
			int j;
			for(j = 0; j < box[0].length; ++j)
				if(!box[i][j].isOpaque())
					break;
			if(j == box[0].length) {
				count++;
				for(int k = i;k > 0;k--)
					for(int l = 0;l< box[0].length; l++)
						box[k][l].setOpaque(box[k - 1][l].isOpaque());
				i++;
			}
		}
		return count;
	}
	
	// 增加行
	public void addRow(int n) {
		Random random = new Random();
		for(int i = 0; i < box.length - n; ++i)
			for(int j = 0; j < box[0].length; ++j)
				box[i][j].setOpaque(box[i + n][j].isOpaque());
		
		for(int i = box.length - n; i < box.length; ++i)
			for(int j = 0; j < box[0].length; ++j)
				box[i][j].setOpaque(random.nextBoolean());
	}
	
	// 新增一个即将显示的方块
	public Cell creatNextCell() {
		if(nextCells.isEmpty())
			nextCells.add(new Cell(new Point(0, box[0].length / 2 - 1)));
		
		nextCells.add(new Cell(new Point(0, box[0].length / 2 - 1)));
		return nextCells.getLast();
	}
	
	// 试着绘制新方块，若不能绘制说明已顶满，游戏结束
	public boolean getNextUnit() {
		return paintCell(nextCells.poll());
	}
	
	// 试着用给出的unit绘制方块，若撞到边界或其他方块不能绘制就返回false
	boolean paintCell(Cell cell) {
		List<Point> add_point = new ArrayList<Point>();
		for(Point p:cell.getPaintLocation())
			add_point.add(p);
		
		if(movingCell != null)
			for(Point p:movingCell.getPaintLocation())
				add_point.remove(p);
		
		for(Point p:add_point)
			if(p.x < 0 || p.x >= box.length || p.y < 0 || p.y >= box[0].length || box[p.x][p.y].isOpaque())
				return false;
		
		if(movingCell != null)
			for(Point p:movingCell.getPaintLocation())
				box[p.x][p.y].setOpaque(false);
		movingCell = cell;
		
		for(Point p:movingCell.getPaintLocation())
			box[p.x][p.y].setOpaque(true);
		
		this.updateUI();
		return true;
	}
	
	// 根据给出的k值试着移动或变形方块，若方块落到底部，返回false，否则返回true
	boolean moveCell(int k) {
		if(movingCell == null)
			return true;
		
		switch(k) {
		case 0: // 改变方向， 不断在刷新， 但是要在原来位置改变方向， 所以y减小
			if(!paintCell(movingCell.getChangeCell(true, 0, 0)))
				if(!paintCell(movingCell.getChangeCell(true, 0, -1)))
					if(!paintCell(movingCell.getChangeCell(true, 0, -2)))
						if(!paintCell(movingCell.getChangeCell(true, 0, -3)));
			break;
		case 1: // 左移
			paintCell(movingCell.getChangeCell(false, 0, -1));
			break;
		case 2: // 右移
			paintCell(movingCell.getChangeCell(false, 0, 1));
			break;
		case 3: // 加速向下
			return paintCell(movingCell.getChangeCell(false, 1, 0));
		}
		return true;
	}
	
	// 重置新游戏
	public void resetGame() {
		for(int i = 0; i < box.length; i++)
			 for(int j=0;  j <box[0].length; j++)
					 box[i][j].setOpaque(false);
		movingCell = null;
		nextCells.clear();
	}
	
	// 结束游戏
	public void endGame() {
		movingCell = null;
	}
}
