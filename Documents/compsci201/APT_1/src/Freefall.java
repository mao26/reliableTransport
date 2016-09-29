
public class Freefall {
	public static double fallingDistance(double time, double velo){
		double gravity = 9.8;
		double distance = (time * velo) + ( time * time * gravity *.5 );
		System.out.println(distance);
		return distance;
		
	}
	public static void main(String [] args){
		fallingDistance(3, 5);
		System.out.println(59.1);
	}
}
