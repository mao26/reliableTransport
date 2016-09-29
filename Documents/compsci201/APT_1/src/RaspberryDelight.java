
public class RaspberryDelight {
	public static int toasts(int upper_limit, int layer_count){
		int max = 0;
		int count = 0;
		while(max < layer_count){
			max += upper_limit;
			count++;
		}
		System.out.println(count);
		return count;
	}
	public static void main(String[] args){
		toasts(73,345);
	}
}
