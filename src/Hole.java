import java.awt.*;

import javax.swing.ImageIcon;


public class Hole 
{
	private int x, y;
	private boolean isFill;
	private boolean dem;
	private boolean isBernie;
	private boolean isTrump;
	private final int DIAMETER = 100;
	private Image hillary, bernie, trump, cruz, molehole, hammer;
	private int numSecsPassed;
	private boolean clicked;
	private boolean wasHit = false;
	
	public Hole(int xPos, int yPos)
	{
		x = xPos;
		y = yPos;
		isFill = false;
		dem = true;
		
		//setting all the Image variables to their respective images
		hillary = new ImageIcon("/Files/Clinton.png").getImage();
		bernie = new ImageIcon("/Files/Bernie.png").getImage();
		trump = new ImageIcon("/Files/Trump.png").getImage();
		cruz = new ImageIcon("/Files/Cruz.png").getImage();
		
		molehole = new ImageIcon("Files/MoleHole.png").getImage();
		
		hammer = new ImageIcon("/Files/hammer.png").getImage();
		
		numSecsPassed = 0;
	}
	
	public void drawHole(Graphics g)
	{
		g.setColor(Color.BLACK);
		//g.fillOval(x, y, DIAMETER, DIAMETER);
		g.drawImage(molehole, x, y, null);
		
		if(isFilled())
		{
			numSecsPassed++;
			if(numSecsPassed == 8) //after the mole has been there a certain amount of time, have it leave 
			{
				empty();
				numSecsPassed = 0;
				return;
			}
		}
		
		if(clicked)
		{
			if (wasHit) //if it was hit, then we keep the politician sprite for a split second longer
						//so that we draw the hammer on top of the politician to show the hammer "hitting" the politician
			{
				drawPolSprite(g, true);
			}
			wasHit = false;
			g.drawImage(hammer, x+25, y+25, null);
			clicked = false;
		}
		
		if(isFill)
		{
			drawPolSprite(g, false);
		}
		
	}
	
	private void drawPolSprite(Graphics g, boolean hasMessage) //drawing the face of the candidate
	{
		if (dem)
		{
			g.setColor(Color.BLUE);
			if (isBernie)
			{
				//fill with Bernie

				g.drawImage(bernie, x + 30, y + 30, null); //plus 30 to put it in the center of the hole
				
				if (hasMessage) //if we're drawing the sprite after it's been hit, have them "say" a message
				{
					g.setFont(new Font("Times New Roman", Font.BOLD, 14));
					g.drawString("Darn", x + 30, y);
					g.drawString("Corporations!", x + 30, y + 14);
				}
				
			}
			else
			{
				//fill with Hillary
				g.drawImage(hillary, x + 30, y + 30, null);
				if (hasMessage) //if we're drawing the sprite after it's been hit, have them "say" a message
				{
					g.setFont(new Font("Times New Roman", Font.BOLD, 14));
					g.drawString("Darn", x + 30, y);
					g.drawString("Sexists!", x + 30, y + 14);
				}
			}
		} 
		else //if it isn't filled with a democrat, it's filled with a Republican 
		{
			g.setColor(Color.RED);
			if (isTrump)
			{
				g.drawImage(trump, x + 30, y + 30, null);
				if (hasMessage) 
				{
					//fill with Trump
					g.setFont(new Font("Times New Roman", Font.BOLD, 14));
					g.drawString("Darn", x + 30, y);
					g.drawString("Mexicans!", x + 30, y + 14);
				}
			}
			else 
			{
				//fill with Cruz
				g.drawImage(cruz, x + 30, y + 30, null);
				if (hasMessage) //if we're drawing the sprite after it's been hit, have them "say" a message
				{
					g.setFont(new Font("Times New Roman", Font.BOLD, 14));
					g.drawString("Darn", x + 30, y);
					g.drawString("Liberals!", x + 30, y + 14);
				}
			}
		}
	}
	
	public boolean sameCoordinates(double xPos, double yPos) //if we hit it
	{
		if((x<=xPos && x+DIAMETER>=xPos)&&(y<=yPos && y+DIAMETER>=yPos)) return true;
		else return false;
	}
	
	public void fillDem()
	{
		isFill = true;
		dem = true;
		if(Math.random()<.5) isBernie = false;
		else isBernie = true;
	}
	
	public void fillRep()
	{
		isFill = true;
		dem = false;
		if(Math.random()<.5) isTrump = true;
		else isTrump = false;
	}
	
	public void empty() {isFill = false;}
	
	public boolean filledWithDem() {return dem;}
	
	public boolean filledWithRep() {return !dem;}
	
	public boolean isFilled() {return isFill;}

	public boolean isBernie() {return isBernie;}
	
	public boolean isTrump() {return isTrump;}

	public void secsPassed() {numSecsPassed++;}
	
	public void resetSecs() {numSecsPassed = 0;}
	
	public int getSecs() {return numSecsPassed;}
	
	public void setClicked(boolean click) {clicked = true;}
	
	public void setWasHit(boolean wHit) {wasHit = wHit;}
}
