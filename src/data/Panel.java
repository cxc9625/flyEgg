package data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	private JFrame jf;
	private Graphics2D g;
	private static MouseListener mMouseListener;
	private static KeyListener mKeyListener;
	public static int state = 0;
	private int px;
	private int py;
	private float m = 0;
	private float m2 = 0;
	private int sTime = 0;
	private Image bg;
	private Image start;
	private Image[] bird = new Image[8];
	private Image column;
	private Image over;
	private static int drawing = 0;
	private int drawCount = 0;
	private static int up = 0;
	private static int toUp = 0;
	private int toUpCount = 0;
	private boolean pressed = false;
	private static boolean uping = false;
	private boolean show = false;
	private int gameCount = 0;
	private static ArrayList<Column> columns;
	private static int rotate;
	private ExecutorService es1 = Executors.newFixedThreadPool(3);
	public Panel(JFrame jf) {
		this.jf = jf;
		this.setBounds(0, 0, jf.getWidth() - 2, jf.getHeight() - 29 - 2);
		this.setLayout(null);
		this.setVisible(true);
		px = getWidth();
		py = getHeight();
		bg = new ImageIcon("Images/bg.png").getImage();
		start = new ImageIcon("Images/start.png").getImage();
		for (int i = 0; i < 8; i++) {
			bird[i] = new ImageIcon("Images/" + i + ".png").getImage();
		}
		column = new ImageIcon("Images/column.png").getImage();
		over = new ImageIcon("Images/gameover.png").getImage();
		columns = new ArrayList<>();
		uping = false;
		up = 0;
		toUp = 0;
		drawing = 0;
		state = 0;
		mKeyListener = null;
		mMouseListener = null;
		rotate = 0;
	}

	@Override
	public void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paint(arg0);
		g = (Graphics2D) arg0;
		g.drawImage(bg, (int) m, 0, (int) (px + m), py * 10 / 13, 0, 0, 432, 500, this);
		// 432 644 (500,144)
		g.drawImage(bg, (int) (0 + px + m), 0, (int) (px + px + m), py * 10 / 13, 0, 0, 432, 500, this);
		if (state == 0) {
			// ready start
			//78 1200(78 526 672 1200)
			g.drawImage(start, 0, 0, px, py, this);
			g.setColor(Color.red);
			g.setFont(new Font("¿¬Ìå",1,24));
			g.drawString("press any key or click to start", px/2-6*31, py/2+120);
			g.drawString("press 'space' key or click to up", px/2-6*32, py/2+180);
			mMouseListener = new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					state = 1;
					repaint();
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			};
			this.addMouseListener(mMouseListener);
			mKeyListener  = new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					state = 1;
					repaint();
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			};
			jf.addKeyListener(mKeyListener);
		} else if (state == 1) {
			this.removeMouseListener(mMouseListener);
			jf.removeKeyListener(mKeyListener);
			// ready doing
			sTime++;
			if (sTime < 5) {
				g.setColor(Color.yellow);
				g.setFont(new Font("Cooper", 1, 64));
				g.drawString((4 - sTime) == 0 ? "start!" : String.valueOf(4 - sTime),
						px / 2 - (4 - sTime == 0 ? 12 * 6 : 12), py / 2 - 12);
			} else {
				state = 2;
				mKeyListener = new KeyListener() {

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						pressed = false;
					}

					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						if (py / 3 - up > 0) {
							if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
								fly();
							}
						}
					}
				};
				jf.addKeyListener(mKeyListener);
				mMouseListener = new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						pressed = false;
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						if (py / 3 - up > 0) {
								fly();
						}
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				};
				this.addMouseListener(mMouseListener);
			}
			try {
				Thread.sleep(1000);
				repaint();
				Thread cut = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(state == 2){
						Random ra = new Random();
						int x = ra.nextInt(100)-50;
						int d = ra.nextInt(50)+150;
						int s = ra.nextInt(1000)+2000;
						columns.add(new Column(x,d,px));
						try {
							Thread.sleep(s);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return;
					}
				});
				es1.submit(cut);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (state == 2) {
			// play
			m -= 0.5;
			if (m < -px) {
				m = 0;
			}
			m2 -= 1;
			if (m2 < -px) {
				m2 = 0;
			}
			int bpx = px * 6 / 7;
			for(int i = 0;i<columns.size();i++){
				columns.get(i).move();
				if(columns.get(i).getX()+px/10<0){
					columns.remove(i);
				}else{
					Column co = columns.get(i);
					int x = co.getX();
					int cup = co.getUp();
					int d = co.getD();
					g.drawImage(column, x, py/3+cup-py*2/3, x+px/10, py/3+cup, 0, 0,78,526, this);//78 1200(78 526 672 1200)
					g.drawImage(column, x, py/3+d+cup, x+px/10, py/3+d+cup+py*2/3, 0, 672,78,1200, this);//78 1200(78 526 672 1200)
					if(show){
						g.setColor(Color.red);
						g.drawRect(x, py/3+cup-py*2/3, px/10, py*2/3);
						g.drawRect(x, py/3+d+cup, px/10, py*2/3);
					}
					if(x>px/3-px/10&&x<px/3){
						if(!co.getIn()){
							co.setIn(true);
						}
						Point axy1 = new Point(px/3,py / 3 - up);
						Point axy2 = new Point(x,py/3+cup-py*2/3);
						Point awh1 = new Point(px/15,bpx/15);
						Point awh2 = new Point(px/10,py*2/3);
						boolean alive1 = new CollTest().Test(axy1, axy2, awh1, awh2);
						Point bxy1 = new Point(px/3,py / 3 - up);
						Point bxy2 = new Point(x,py/3+d+cup);
						Point bwh1 = new Point(px/15,bpx/15);
						Point bwh2 = new Point(px/10,py*2/3);
						boolean alive2 = new CollTest().Test(bxy1, bxy2, bwh1, bwh2);
						if(alive1||alive2){
							jf.removeKeyListener(mKeyListener);
							state = 3;
						}
					}else{
						if(co.getIn()){
							co.setIn(false);
							gameCount++;
						}
					}
				}
			}
			if(uping){
				g.rotate(-((20-toUp)*Math.PI/180), px / 3 - px / 30+px / 30,py / 3 - up+bpx / 30);
				g.drawImage(bird[drawing], px / 3 - px / 30, py / 3 - up, px / 15, bpx / 15, this);
				g.rotate((20-toUp)*Math.PI/180, px / 3 - px / 30+px / 30,py / 3 - up+bpx / 30);
			}else{
				if(rotate<45){
					rotate+=5;
				}
				g.rotate(rotate*Math.PI/180, px / 3 - px / 30+px / 30,py / 3 - up+bpx / 30);
				g.drawImage(bird[drawing], px / 3 - px / 30, py / 3 - up, px / 15, bpx / 15, this);
				g.rotate(-rotate*Math.PI/180, px / 3 - px / 30+px / 30,py / 3 - up+bpx / 30);
			}
			if(show){
				g.setColor(Color.red);
				g.drawRect(px / 3 - px / 30, py / 3 - up,px / 15, bpx / 15);
			}
			if(toUpCount==10){
				if (toUp < 20) {
					toUp++;
				}
				toUpCount = 0;
			}
			if(toUp>5){
				uping = false;
			}
			toUpCount++;
			if(py / 3 - up<py * 10 / 13-bpx / 15){
				up -= toUp;
			}
			drawCount++;
			if (drawCount > 10) {
				drawCount = 0;
				drawing++;
			}
			if (drawing > 7) {
				drawing = 0;
			}
			try {
				Thread.sleep(5);
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (state == 3) {
			// gameOver
			g.drawImage(over, 0, 0, px, py, this);
			g.setColor(Color.red);
			if(show){
				g.drawRect(px/4, py/2, px/2, py/4);
			}
			g.setFont(new Font("¿¬Ìå",0,24));
			g.drawString("Press R or click to restart!", px/2-6*28, py/2);
			mMouseListener = new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if(arg0.getX()>px/4&&arg0.getX()<px/2&&arg0.getY()>py/2&&arg0.getY()<py/2+py/4){
						jf.removeKeyListener(mKeyListener);
						Main.reStart();
					}
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			};
			this.addMouseListener(mMouseListener);
			mKeyListener = new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					if(arg0.getKeyCode()==KeyEvent.VK_R){
						jf.removeKeyListener(mKeyListener);
						Main.reStart();
					}
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			};
			jf.addKeyListener(mKeyListener);
			System.out.println("gameOver");
		}
		g.drawImage(bg, (int) m2, py * 10 / 13, (int) (px + m2), py+10, 0, 500, 432, 644, this);
		g.drawImage(bg, (int) (0 + px + m2), py * 10 / 13, (int) (px + px + m2), py+10, 0, 500, 432, 644, this);
		g.setFont(new Font("¿¬Ìå",1,64));
		g.setColor(Color.white);
		g.drawString(""+gameCount, px-200, py-50);
	}
	private void fly(){
		if (!pressed) {
			pressed = true;
			Thread th = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int x = 1;
					int l = uping?5:10;
					uping = true;
					while (x < l) {
						synchronized (this) {
							drawing = 0;
							toUp = 0;
							up += 15;
							rotate = 0;
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated
								// catch
								// block
								e.printStackTrace();
							}
							x++;
						}
					}
					return;
				}
			});
			es1.submit(th);
		}
	}
}
