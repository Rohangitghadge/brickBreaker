package brickBreaker;

import javax.swing.JFrame;

public class MainC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create the frame;
		JFrame obj=new JFrame();
		GamePlay gamePlay=new GamePlay();
		obj.setBounds(10,10,700,600);//(x,y,width,height)
		obj.setTitle("BreakOut Ball");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		obj.add(gamePlay);
	}

}
