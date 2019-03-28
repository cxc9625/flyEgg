package data;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	public static final Dimension Display = Toolkit.getDefaultToolkit().getScreenSize();
	static JFrame jf;
	static Panel mp;
	public static void main(String[] args) {
		jf = new JFrame("»á·ÉµÄµ°");
		jf.setIconImage(new ImageIcon("Images/0.png").getImage());
		jf.setBounds((Display.width-Display.height*3/4)/2,Display.height/20,Display.height*3/4,Display.height*9/10);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		start();
	}
	static void start(){
		mp = new Panel(jf);
		jf.add(mp);
		jf.setVisible(true);
	}
	@SuppressWarnings("static-access")
	public static void reStart(){
		System.out.println("restart!");
		jf.remove(mp);
		mp = new Panel(jf);
		mp.state = 1;
		jf.add(mp);
		jf.setVisible(true);
	}
}
