import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrumpApplet extends Applet implements ActionListener
{
	private TrumpCode tc;
	AudioClip entertainer;
	
	public void init() //create the applet
	{
		Frame t = (Frame)this.getParent().getParent();
		t.setTitle("Whack-a-Trump!");
		tc = new TrumpCode();
		tc.addMouseListener(tc);
		tc.setPreferredSize(new Dimension(680, 480));
		tc.setVisible(true);
		tc.setFocusable(true);
		this.add(tc);
		this.setVisible(true);
		this.setSize(new Dimension(640, 480));
		entertainer = getAudioClip(getDocumentBase(), "Files/theentertainer.au");
		entertainer.play();
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		this.setSize(new Dimension(640, 480));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}