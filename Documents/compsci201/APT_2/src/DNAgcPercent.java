
public class DNAgcPercent {
	public double percentage(String dnaStrand)
    {
		double g_count = 0; 
		double c_count = 0;
		for (int i = 0; i < dnaStrand.length(); i++){
			if(dnaStrand.charAt(i) == 'c'){    //need to separate indv chars
				c_count++;
			}
			if(dnaStrand.charAt(i) == 'g'){
				g_count++;
			}
		
		}
		double num_count = g_count + c_count; 
		double den_count = dnaStrand.length();
		if(den_count == 0) return 0;
		double total = num_count/ den_count; 
        return total;   // where to put return code
    }
	
}
