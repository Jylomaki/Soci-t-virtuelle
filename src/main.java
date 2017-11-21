import javax.swing.SwingUtilities;

import genome.*;
import genome.transition.*;
import genome.transition.Expr;
import ui.MainWindow;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	    		MainWindow mainWindow = new MainWindow();
	        }
	    });
		
		global.Randomized.init_rand(1);
		Cond c;
		Expr e;
		boolean print=false;
		int test_count =2000;
		System.out.println("Testing Expr ###########################################");
		for(int i=0; i<test_count; i++) {
			//System.out.println("Expr: " + i);
			e =new Expr();
			if(print)
				e.print();
		}
		
		System.out.println("Testing Cond ###########################################");
		for(int i=0; i< test_count; i++) {
			//System.out.println("Cond: " + i);
			c =new Cond();
			if(print)
				c.print();
		}
		
		System.out.println("Testing Automata ###########################################");

		Behaviour_Automata b = new Behaviour_Automata(1,100);
		for(int i=0; i<test_count; i++) {
			//System.out.println("Automata: " + i);
			if(i==70)
				System.out.println("The cursed auto is coming!");
			b = new Behaviour_Automata(1,100);
			if(i==70)
				System.out.println("The cursed auto declaration vent vell!");
			if(print)
				b.print();
		}
		System.out.println("Testing Automata vith comm ###########################################");

		Behaviour_Automata bC = new Behaviour_Automata(1, 100, true);
		for(int i=0; i<test_count; i++) {
			//System.out.println("Automata: " + i);
			b = new Behaviour_Automata(1,100,true);
			if(print)
				b.print();
		}
		System.out.println("Testing Mutation of automata ###########################################");
		int mutated=0;
		for(int i=0; i<test_count; i++) {
			//System.out.println("mutation: " + i);
			if(b.mutate(1, 100))
				mutated ++;
			if(print)
				b.print();
		}
		b.print();
		System.out.println("Mutated:" + mutated);
		//b.print();
		//Object b_A = new Behaviour_Automata(1,100);
		System.out.println("done");
	}

}
