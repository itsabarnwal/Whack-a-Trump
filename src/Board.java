import java.awt.Color;
import java.awt.Graphics;


public class Board 
{
	int cols, rows;
	Hole[][] ovals;
	int numHolesFilled = 0;
	
	public Board(int xgrid, int ygrid, Color col)
	{
		cols = xgrid;
		rows = ygrid;
		ovals = new Hole[rows][cols];
		for(int a = 0; a < rows; a++)
		{
			for(int b = 0; b < cols; b++)
			{
				ovals[a][b] = new Hole((100*a + 50), (50 + 100*b));
			}
		}
	}
	
	public void paintBoard(Graphics g) //painting the board in the applet
	{
		
		
		//paints the board
		for (Hole[] u: ovals) 
		{
			for (Hole h: u) 
			{
				h.drawHole(g);
				//if it's filled, increment the seconds passed
			}
		}
		
	}
	
	public void fillRandomHoles() //randomly generate candidates in the holes
	{
		numHolesFilled = (int) (Math.random() * 5);
		//fills a random number of holes every second
		while(numHolesFilled >0) //when we fill a hole, we increment the numHolesFilled down 
								 //until we don't need to fill any more
		{
			for (int i = 0; i < ovals.length; i++) 
			{
				for (int j = 0; j < ovals[i].length; j++)
				{
					if (numHolesFilled > 0 && !ovals[i][j].isFilled())
					{
						double rand = Math.random();
						//16% chance of filling a hole, 8% dem and 8% republican
						//16% is kind of arbitrary but it produced a satisfactory number of holes filled
						if(rand>.92)
						{
							ovals[i][j].fillDem();
							numHolesFilled--;
							//return;
						}
						else if(rand<.08)
						{
							ovals[i][j].fillRep();
							numHolesFilled--;
							//return;
						}
					}
				}
			}
		}
		
	}
	
	public Hole[][] getHole()
	{
		//returns the 2D Array of Holes
		return ovals;
	}
		
	public boolean filledHoleContainsCoords(double xPos, double yPos) //check if we clicked a hole
	{
		boolean containsCoords = false;
		for(Hole[] u : ovals)
		{
			for(Hole h : u)
			{
				if(h.sameCoordinates(xPos, yPos))
				{
					h.setClicked(true); //when clicked is true the hammer will appear on screen

					if(h.isFilled())
					{
						containsCoords = true;
					}
				}
				
			}
		}
		return containsCoords;
	}
	
	public boolean isFilledRep(double xPos, double yPos) //is it filled with a republican?
	{
		boolean rep = false;
		for(Hole[] u : ovals)
		{
			for(Hole h : u)
			{
				if(h.sameCoordinates(xPos, yPos)&&h.filledWithRep())
				{
					rep = true;
				}
			}
		}
		return rep;
	}
	
	public boolean isFilledDem(double xPos, double yPos) //is it filled with a democrat?
	{
		boolean dem = false;
		for(Hole[] u : ovals)
		{
			for(Hole h : u)
			{
				if(h.sameCoordinates(xPos, yPos))
				{
					if(h.filledWithDem())
					{
						dem = true;
					}
					h.setWasHit(true);
					//set wasHit to true so that the sprite of the politician remains there for the hammer to hit
					h.empty();
				}
			}
		}
		return dem;
	}
	
	public boolean isFilledBernie(double xPos, double yPos) 
	{
		boolean clinton = false;
		for(Hole[] u : ovals)
		{
			for(Hole h : u)
			{
				if(h.sameCoordinates(xPos, yPos))
				{
					if(h.filledWithDem() && !h.isBernie())
					{
						clinton = true;
					}
					h.setWasHit(true);
					//set wasHit to true so that the sprite of the politician remains there for the hammer to hit
					h.empty();
				}
			}
		}
		return !clinton;
	}
	
	public boolean isFilledTrump(double xPos, double yPos)
	{
		boolean trump = false;
		for(Hole[] u : ovals)
		{
			for(Hole h : u)
			{
				if(h.sameCoordinates(xPos, yPos))
				{
					if(h.filledWithRep() && h.isTrump())
					{
						trump = true;
					}
					h.setWasHit(true);
					//set wasHit to true so that the sprite of the politician remains there for the hammer to hit
					h.empty();
				}
			}
		}
		return trump;
	}
	
	public void emptyHole(int x, int y) 
	{
		ovals[x][y].empty();
	}
	
	public void setNumHolesFilled(int nhf)
	{
		numHolesFilled = nhf;
	}
	
	public int getNumHolesFilled()
	{
		int sum = 0;
		for(Hole[] u : ovals)
		{
			for(Hole h : u)
			{
				if(h.isFilled()) sum++;
			}
		}
		return sum;
	}
	
}
