import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		JFrame frame=new JFrame("Breakout Ball");
		frame.setBounds(10,10,710,600);
		frame.setVisible(true);
		//frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new GamePlay());

	}

}
