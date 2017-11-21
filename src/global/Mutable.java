package global;

public interface Mutable {
	boolean mutate(int treshold, int maxR);
	Mutable clone();
	String toString();
	
	void print();
	void print(String mise_forme);
}
