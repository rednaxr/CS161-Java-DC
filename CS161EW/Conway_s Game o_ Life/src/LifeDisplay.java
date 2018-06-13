//JPanel extending class for LifeSimulator, a Conway's Game of Life Simulator
//by: Alexander Dyall
//15 Dec 2017

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

public class LifeDisplay extends JPanel{
	
	//global variables
	boolean grid[][];		//grid storing alive/dead for all cells, + border
	double sqWidth;			//width of one cell
	double sqHeight;		//height of one cell
	int scale;				//size of displayed portion of grid (number of cells along one edge of the square grid)
	
	//Constructor
	public LifeDisplay(boolean inGrid[][]) {
		grid = inGrid;							//Use the grid entered from LifeSimulator
		scale = inGrid.length - 2;				//use the size of the displayed portion of the grid for scaling (exclude the border).
	}
	
	
	//paintComponent Modifier
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		sqWidth = (double)(this.getWidth()/scale);
		sqHeight = (double)(this.getHeight()/scale);
		
		//Squares
		for(int x = 1; x < scale + 1; x++) {			//Draw a rectangle for each cell...
			for(int y = 1; y < scale + 1; y++) {
				if(grid[x][y] == false) {				//White if dead
					graphic.setColor(Color.WHITE);
				}
				else if(grid[x][y] == true) {			//Black if alive
					graphic.setColor(Color.BLACK);
				}
				graphic.fillRect((int)((x-1)*sqWidth), (int)((y-1)*sqHeight), (int)(sqWidth), (int)(sqHeight));
			}
		}
		
		//GridLines
		graphic.setColor(Color.GRAY);			//draw grey gridlines between cells
		for(int x = 1; x < scale + 1; x++) {
			graphic.drawLine((int)(Math.round(x*sqWidth)), 0, (int)(Math.round(x*sqWidth)), (int)(Math.round(this.getHeight())));
		}
		for(int y = 1; y < scale + 1; y++) {
			graphic.drawLine(0, (int)(Math.round(y*sqHeight)), (int)(Math.round(this.getWidth())), (int)(Math.round(y*sqHeight)));
		}
	}
	
}
