
public class DNAgcPercent {
	public static double percentage(String dnaStrand){
		double g_count = 0;
		double c_count = 0;
		double dna_length = dnaStrand.length();
		for(int i = 0; i<dna_length; i++){
			if(dnaStrand.charAt(i) == 'g'){
				g_count++;
			}
			if(dnaStrand.charAt(i) == 'c'){
				c_count++;
			}
		}
		double numerator = c_count + g_count;
		double percentage = numerator/dna_length;
		System.out.println(percentage);
		return percentage;
    }
	public static void main(String[] args){
		percentage("agatc");
	}
}
