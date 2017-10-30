import javax.swing.SwingUtilities;

import ui.MainWindow;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	    		MainWindow mainWindow = new MainWindow();
	        }
	    });
	}

}
