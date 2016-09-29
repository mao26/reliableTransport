import java.util.Set;
import java.util.TreeSet;


public class PizzaToppings {
	public static void main(String[] args){
		String[] favorites = {"mushrooms", "anchovies", "pepperoni"};
		String[] menu = {"anchovies pineapple", "anchovies mushrooms pepperoni", "ketchup", "beer"};
		PizzaToppings pizza = new PizzaToppings();
		System.out.println(pizza.whichToppings(favorites, menu)); 
	}

	
	
	public static int whichToppings(String[] menu, String[] favorites){
        // fill in code here
		
		Set<String> words = new TreeSet<String>();
		for(int i = 0; i < menu.length; i++){
			words.add(menu[i]);
			System.out.println(words);
		}
		System.out.println(words);
		for(int i = 0; i < favorites.length; i++){
			int count = 0; 
			String[] favorite_group = favorites[i].split(" ");
			System.out.println(favorite_group);
			for(int j = 0; j < favorite_group.length; j++){
				
				if(words.contains(favorite_group[j])){
					count++;
				}
				if(count == favorite_group.length){
					return i;
				}
			
			}
		
		}
		return -1; 
	}
}
