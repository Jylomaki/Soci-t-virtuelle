package global;

public class Vector {

	public float x,y;
	
	public Vector(){
		x = 0;
		y = 0;
	}
	
	public Vector(float x,float y){
		this.x = x;
		this.y = y;
	}
	
	public void rotate(float angle){
        float sin = (float) Math.sin(Math.toRadians(angle));
        float cos = (float) Math.cos(Math.toRadians(angle));

        float tempX = x;
        float tempY = y;
        
        x = (cos * tempX) - (sin * tempY);
        y = (sin * tempX) + (cos * tempY);


	}
	
	
}
