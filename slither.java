package slither;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Date; 
import java.util.Timer; 
import java.util.TimerTask;


import javax.swing.*;


public class Slither {

	MyFrame frame;
	ArrayList<Point> nonFoods;
	ArrayList<Point> Slither;
	ArrayList<Point> foods;
	Image OSC;
	PointerInfo a = MouseInfo.getPointerInfo();
	int size = 10;
	double speed = 10;
	Random r;
	int score = 100;
	static int highscore=0;
	String name = "";
	public Color rndClr = Clr();

	 //public Color rndClr = new Color(45,66,243);
	 //  sabit renk

	public Color Clr()
	{
		Random r= new Random();
		int a = r.nextInt(255);
		int b = r.nextInt(255);
		int c = r.nextInt(255);
		return (new Color(a,b,c));
	}




	public static void main(String[] args) {
		Slither c = new Slither();
		c.startGame(highscore);
	}



	public void startGame(int highscore){


		frame = new MyFrame("Slither");
		Slither = new ArrayList<Point>();
		foods = new ArrayList<Point>();
		nonFoods = new ArrayList<Point>();
		highscore = highscore;
		r = new Random();

		JFrame frame = new JFrame("InputDialog");
		name = JOptionPane.showInputDialog(frame, "What's your name?");	



		Slither.add(new Point(100, 100));
		MoveOfGame mof = new MoveOfGame();
		mof.start();

	}
	public void endGame() {
		speed = 0;
		if(score > highscore){
			highscore = score;
		}

		Object[] options = {"Exit",
		"Cont!"};
		int dialogButton= JOptionPane.showOptionDialog(frame,
				" Your score " + "'"+name+"'" + " = " + score + " \n Highscore is: " + highscore  ,
				"GAME OVER",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title

		if(dialogButton == JOptionPane.YES_OPTION) {
			System.exit(0);}
		else 
		{
			Slither a = new Slither();
			a.startGame(highscore);
		}

	}
	class MoveOfGame extends Thread  {

		public void run(){


			while(true){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (nonFoods.size() < 21) 
					nonFoods.add(new Point(r.nextInt(900),r.nextInt(900)));


				if(foods.size() < 100)
					foods.add(new Point(r.nextInt(900), r.nextInt(900)));
				a = MouseInfo.getPointerInfo();
				Point p = a.getLocation();
				Point last = Slither.get(Slither.size() - 1);
				Point n = new Point();
				if(last.distance(p) > 5){
					n = calcCoor(last, p);
					Slither.add(n);
					if(Slither.size() >= size){
						for(int i = 0; i < Slither.size() - size; i++){
							Slither.remove(i);
						}
					}
					System.out.println(n);
				}

				Iterator<Point> i = foods.iterator();
				while(i.hasNext()){
					Point food = i.next();
					if(food.distance(n) < 15){
						i.remove();
						size++;
						score=score+5;
						speed *= 0.95;
					}

					Iterator<Point> f = nonFoods.iterator();
					while(f.hasNext()) {
						Point nonFoods =f.next();
						if(nonFoods.distance(n) < 15) {
							f.remove();
							if(size > 1){
								size = 2*size/3;
								score=score-10;
								speed *= 0.95;
							}
							else {
								endGame();

								//                	        JButton button = new JButton();
								//                	        final JFrame parent = new JFrame();
								//                	        button.setText("Game Over");
								//                	        parent.setLocation(375,375);
								//                	        parent.add(button);
								//        	        			parent.pack();
								//        	        			parent.setVisible(true);
								//        	        			
								//                			
								//                		  button.addActionListener(new java.awt.event.ActionListener() {
								//                	            @Override
								//                	            public void actionPerformed(java.awt.event.ActionEvent evt) {
								//                	              // String name = JOptionPane.showInputDialog(parent, "What is your name?", null);
								//                          		 JOptionPane.showMessageDialog(parent, "Your Score = " + score + "\n Highscore " + "100");
								//                          		 
								//                          		 
								//                	            		 }
								//                	        });

								//	  String name1 = JOptionPane.showInputDialog("Your Score = " +score,"What is your name?" );              
							}

						}
					}

				}
				frame.repaint();
			}

		}
		public Point calcCoor(Point last, Point mouse){
			double degree = 0;
			if(last.x < mouse.x && last.y < mouse.y){
				degree = 360 - Math.toDegrees(Math.atan((double) (mouse.y - last.y) / (mouse.x - last.x)));
			}else if(last.x > mouse.x && last.y > mouse.y){
				degree = 180 - Math.toDegrees(Math.atan((double) (last.y - mouse.y) / (last.x - mouse.x)));
			}else if(last.y > mouse.y && last.x < mouse.x){
				degree = Math.toDegrees(Math.atan((double) (last.y - mouse.y) / (mouse.x - last.x)));
			}else if(last.y < mouse.y && last.x > mouse.x){
				degree = 180 + Math.toDegrees(Math.atan((double) (mouse.y - last.y) / (last.x - mouse.x)));
			}
			Point p = new Point((int)
					(last.x + Math.cos(Math.toRadians(degree)) * speed), (int)
					(last.y - Math.sin(Math.toRadians(degree)) * speed));
			return p;

		}
	}

	class MyMouseSensor extends MouseAdapter {
		boolean mouseDown = false;
		public void mousePressed(MouseEvent e){
			speed *= 2;
			mouseDown = true;
			new Thread(){
				public void run(){
					while(mouseDown){
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						if(size > 1)
						{
							size--;
						}
						else {
							endGame();
							break;
						}

					}
				}
			}.start();

		}
		public void mouseReleased(MouseEvent e){
			speed /= 2;
			mouseDown = false;
		}
	}

	class MyFrame extends JFrame {
		public MyFrame(String s){
			super(s);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(0, 0, 900, 900);
			setLayout(new FlowLayout());
			setBackground(Color.BLACK);
			addMouseListener(new MyMouseSensor());

			setVisible(true);
		}
		public void paint(Graphics g) {
			Dimension d = getSize();
			checkOffscreenImage();
			Graphics offG = OSC.getGraphics();
			offG.setColor(Color.white);
			offG.fillRect(0, 0, d.width, d.height);
			Color sdc = Clr();
			paintOffscreen(OSC.getGraphics(),sdc);
			g.drawImage(OSC, 0, 0, null);
		}

		private void checkOffscreenImage() {
			Dimension d = getSize();
			if (OSC == null || OSC.getWidth(null) != d.width
					|| OSC.getHeight(null) != d.height) {
				OSC = createImage(d.width, d.height);
			}
		}

		public int timer() {
			int count = 0; 
			for(;;) 
			{ 
				try { 
					Thread.sleep(1000); 
					count ++; 		
				} catch (InterruptedException e) { 
					// TODO Auto-generated catch block 
					e.printStackTrace(); 
				} 
			}
		}



		public void paintOffscreen(Graphics g,Color clr) {


			g.clearRect(0, 0, 900, 900);
			Point first = new Point();
			Point last = Slither.get(0);
			Color color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
			g.setColor(color);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(9 + (float) size / 10));
			g2.setColor(rndClr);
			for(int i = 1; i < Slither.size(); i++){ //Slither

				first = Slither.get(i);
				g2.fillOval(first.x, first.y,10, 10);
				last = new Point(first);
			}
			g2.setColor(color.CYAN );
			for(int i = 0; i < foods.size(); i++){ //foods
				g2.fillOval(foods.get(i).x, foods.get(i).y, 10, 10); 
			}
			g2.setColor(color.RED);
			for (int i = 0; i < nonFoods.size(); i++) { //nonFoods
				g2.fillOval(nonFoods.get(i).x, nonFoods.get(i).y, 10, 10);
			}
			g2.setColor(color.WHITE);
			g2.drawString("Score= "+score, 150, 70);
			g2.drawString("Speed= "+ (int) speed, 150, 85);
			g2.drawString("Size= "+size, 150, 100);

		}
	}



}
