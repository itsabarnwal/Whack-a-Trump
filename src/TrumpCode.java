import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;


public class TrumpCode extends Canvas implements Runnable, MouseListener, KeyListener
{
	Board b = new Board(4,4, Color.RED);
	Thread th = new Thread();
	Graphics globalGraphics;
	
	Thread runThread;
	
	int demHits = 0;
	int clintonHits = 0;
	int repHits = 0;
	int trumpHits = 0;
	int score = 0;
	private String hiscore = "";
	//initialize scores
	
	Image bg = new ImageIcon("/Files/Background.png").getImage();
	Image menu = new ImageIcon("/Files/Menu.png").getImage();
	Image deathscreen = new ImageIcon("/Files/DScreen.png").getImage();
	Image htp = new ImageIcon("/Files/HowToPlay.png").getImage();
	Image partyScreen = new ImageIcon("/Files/PartyScreen.png").getImage();
	Image diffscreen = new ImageIcon("/Files/DifficultyScreen.png").getImage();
	Image hScreen = new ImageIcon("/Files/HardScreen.png").getImage(); //background screen for hard mode
	Image mScreen = new ImageIcon("/Files/MediumScreen.png").getImage(); //bg screen for medium mode
	Image hillary = new ImageIcon("/Files/Clinton.png").getImage();
	Image bernie = new ImageIcon("/Files/Bernie.png").getImage();
	Image trump = new ImageIcon("/Files/Trump.png").getImage();
	Image cruz = new ImageIcon("/Files/Cruz.png").getImage();
	
	//get all the images images
	
	int timekeeper = 250; //repaints the screen every 250 milliseconds, or every 1/4 of a second
	int moletimekeeper = 1; //timekeeper for each individual hole to determine when the mole leaves the hole
	int secs = 0; //the number of seconds left in the game
	
	boolean isAtMenu = true;
	boolean isGameOver = false;
	boolean isAtHTP = false;
	boolean isAtPartyScreen = false;
	boolean isAtDiffScreen = false;
	
	//boolean variables to determine which stage of the game we're on
	
	boolean uDem = true; //boolean determining user's political allegiance
	int difficulty = 0;
	
	public void Draw(Graphics g)
	{
		g.clearRect(0, 0, 680, 480); //clear the previous board and paint the new one
		
		if(difficulty == 0) //background changes to become more menacing depending on the difficulty 
		{
			g.drawImage(bg, 0, 0, null); //draw background
		}
		else if(difficulty == 1)
		{
			g.drawImage(mScreen, 0, 0, null);
		}
		else if(difficulty == 2)
		{
			g.drawImage(hScreen, 0, 0, null);
		}
		
		BufferedImage buffer = new BufferedImage(680,480, BufferedImage.TYPE_INT_ARGB);
		Graphics bufferGraphics = buffer.getGraphics();
		if (isAtMenu) //draw menuscreen
		{
			g.drawImage(menu, 0, 0, null);
		}
		else if(isAtHTP) //draw how to play screen
		{
			g.drawImage(htp, 0, 0, null);
		}
		else if(isGameOver) //draw Game Over screen
		{
			//showing all of the scores
			g.drawImage(deathscreen, 0, 0, null);
			g.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g.setColor(Color.BLUE);
			g.drawString(Integer.toString(clintonHits), 155, 110);
			g.drawString(Integer.toString(demHits - clintonHits), 155, 160);
			g.drawString(Integer.toString(demHits), 155, 220);
			g.setColor(Color.RED);
			g.drawString(Integer.toString(trumpHits), 455, 110);
			g.drawString(Integer.toString(repHits - trumpHits), 455, 160);
			g.drawString(Integer.toString(repHits), 455, 220);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(score), 315, 280);
			g.setFont(new Font("Times New Roman", Font.BOLD, 15));
			g.drawString("The high score is held by " + hiscore, 20, 15);
			
			if(demHits < repHits)//whoever had the least amount of hits won
			{
				if((double)clintonHits <= (double)demHits / 2)
				{
					g.drawImage(hillary, 315, 300, null);
				}
				else 
				{
					g.drawImage(bernie, 315, 300, null);
				}
			}
			else 
			{
				if((double)trumpHits <= (double)repHits / 2)
				{
					g.drawImage(trump, 315, 300, null);
				}
				else
				{
					g.drawImage(cruz, 315, 300, null);
				}
			}
			secs = 0; //reset the seconds left
			
			
		}
		else if(isAtPartyScreen)
		{
			g.drawImage(partyScreen, 0, 0, null); //draw the screen where choosing party
		}
		else if (isAtDiffScreen)
		{
			g.drawImage(diffscreen, 0, 0, null); //draw the difficulty screen
		}
		else //this is when we're playing the actual game
		{
			
			
			b.paintBoard(bufferGraphics); //paint the board
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			
			g.setColor(Color.BLUE);
			g.drawString("Democrat Hits: " + Integer.toString(demHits), 475, 100);
			g.setColor(Color.RED);
			g.drawString("Republican Hits: " + Integer.toString(repHits), 475, 250);
			g.setColor(Color.BLACK);
			g.drawString("Total Score: " + Integer.toString(score), 475, 400);
			
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.setColor(Color.ORANGE);
			g.drawString("Seconds Left: " + Integer.toString(61-secs), 40, 40);
			//show the number of times hits for Dems / Reps, the score, the seconds left
			
			if(secs == 60) //after 60 seconds, game over
			{
				isGameOver = true;
				difficulty = 0;
			}
			if (moletimekeeper == 1) 
			{
				b.fillRandomHoles();
				moletimekeeper = 1000/timekeeper;
				secs += 1;
				//add random moles about every second
			} 
			else if (moletimekeeper < 1) System.out.println("Hello"); //if the game glitches and increments the mtk one extra time, just reset to 0
			else moletimekeeper--;
		}
		g.drawImage(buffer, 0, 0, 680, 480, this);
		// repaint the image that we just created so that instead of deleting the image and painting a new one,
		// which would result in flashing, we are actually painting the new image on top of the old one after the board is refreshed
		
	}
	
	public void paint(Graphics g)
	{
		setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(640, 480));
		this.addKeyListener(this);
		globalGraphics = g.create(); // initializing globalGraphics
		if (runThread == null) 
		{
			runThread = new Thread(this);
			runThread.start();
		}
		
		if(hiscore.equals("")) //if the hiscore hasn't been initialized, set it equal to nobody:0
		{
			hiscore = this.getHSValue();
		}

	}
	
	public void run() 
	{
		while (true)
		{
			//runs indefinitely
			if (!isAtMenu && isGameOver) checkScore(); //when isGameOver is true, ask the new high score holder to enter their name
			Draw(globalGraphics);
			
			try
			{
				Thread.currentThread();
				Thread.sleep(timekeeper);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) 
	{
		double xPos = e.getX();
		double yPos = e.getY();
		
		if(isAtPartyScreen)
		{
			uDem = ((xPos>0 && xPos<340) && yPos > 100) ? true : false; //if clicked dem symbol, user is dem, otherwise they're rep
			isAtPartyScreen = false;
			isAtDiffScreen = true;
		}
		else if (isAtDiffScreen) //we're at the difficulty screen, set how fast the game runs
		{
			//80 to 200 to 330
			if(yPos < 200) //when user clicks the range on the 'easy' button
			{
				difficulty = 0;
				timekeeper = 250;
			}
			else if(yPos<330) //when user clicks the range on 'medium' button
			{
				difficulty = 1;
				timekeeper = 125;
			}
			else // 'hard' button
			{
				difficulty = 2;
				timekeeper = 100;
			}
			isAtDiffScreen = false;
		}
		else if(b.filledHoleContainsCoords(xPos, yPos)) //if we click on a hole that is filled 
		{
			if(b.isFilledDem(xPos, yPos))
			{
				demHits++;
				if(!b.isFilledBernie(xPos, yPos)) clintonHits++;
				if(uDem) score--;
				else score+=2;
			}
			else
			{
				repHits++;
				if(b.isFilledTrump(xPos, yPos)) trumpHits++; 
				if(uDem) score+=2;
				else score--;
			}
		}
		
		
	}
	
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("pressed");
		if(e.getKeyCode() == (KeyEvent.VK_SPACE))
		{
			
			if(isAtMenu)
			{
				isAtMenu = false;
				isGameOver = false;
				isAtPartyScreen = true;
				demHits = 0;
				repHits = 0;
				score = 0;
				secs = 0;
				
				//make sure everything is 0
			}
			else if(isGameOver)
			{
				isGameOver = false;
				demHits = 0;
				repHits = 0;
				score = 0;
				secs = 0;
				//reset everything
			}
		}
		
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_SPACE: 
				if(isAtMenu)
					{
						//isAtMenu = false; 
						isAtPartyScreen = true;
						demHits = 0;
						repHits = 0;
						score = 0;
						secs = 0;
					}
				
				else if(isGameOver)
					{
						isGameOver = false;
						demHits = 0;
						repHits = 0;
						score = 0;
						secs = 0;
					}
					break;
			case KeyEvent.VK_M: 
				if(isAtHTP || isGameOver)
				{
						isAtMenu = true;
						isAtHTP = false;
						isGameOver = false;
						demHits = 0; 
						repHits = 0;
						score = 0;
						secs = 0;
				}
				break;
			case KeyEvent.VK_H: if(isAtMenu) 
				{
					isAtMenu = false;
					isAtHTP = true;
					demHits = 0; 
					repHits = 0;
					score = 0;
					secs = 0;
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public int strToInt( String str ) //method that converts String to int
	{
	    int temp = 0;
	    int num = 0;
	    boolean isNeg = false;

	    //Check for negative sign; if it's there, set isNeg to true
	    if (str.charAt(0) == '-') {
	        isNeg = true;
	        temp = 1;
	    }

	    //Process each character of the string;
	    while( temp < str.length()) {
	        num *= 10;
	        num += str.charAt(temp++) - '0'; //Minus the ASCII code of '0' to get the value of the charAt(i++).
	    }

	    if (isNeg)
	        num = -num;
	    return num;
	}
	
	public String getHSValue() 
	{
		
		FileReader fr = null;
		BufferedReader br = null;
		//fr and br need to be initialized so we can access them outside of the try block
		try 
		{
			if(difficulty == 0) fr = new FileReader("/Files/hiscore.txt");
			if(difficulty == 1) fr = new FileReader("/Files/mHiscore.txt");
			if(difficulty == 2) fr = new FileReader("/Files/hHiscore.txt");
			br = new BufferedReader(fr);
			return br.readLine();
		}
		catch (Exception e)
		{
			e.printStackTrace(); //print source of error
			return "Nobody:0";
		}
		finally //at the end, close the reader
		{
			try {
				if(br!= null) br.close();
			} catch (IOException e) {
				// print source of error
				e.printStackTrace();
			}
		}
		
	}

	public void checkScore() //refreshing the hiscore when it is beaten
	{
		if(score > strToInt(hiscore.split(":")[1])) // if score > the number that is past the colon of the highscore string 
		{
			String name = JOptionPane.showInputDialog("You set a new high score. What is your name?");
			//popup window asking user to enter their name after they set the new hs
			hiscore = name + ":" + score;
			

			File hsFile;
			if(difficulty == 0) hsFile = new File("/Files/hiscore.txt"); //overwriting the hiscore file
			else if(difficulty == 1) hsFile = new File("/Files/mHiscore.txt"); //overwriting the hiscore file
			else hsFile = new File("/Files/hHiscore.txt"); //overwriting the hiscore file
			if(!hsFile.exists())
			{
				try {
					hsFile.createNewFile();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			FileWriter fw = null;
			BufferedWriter bw = null;
			try
			{
				fw = new FileWriter(hsFile);
				bw = new BufferedWriter(fw);
				bw.write(hiscore); //update the high score
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				
				
				try 
				{
					if(bw != null) bw.close(); //close the filewriter
				}
				catch (IOException e) 
				{
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
}
