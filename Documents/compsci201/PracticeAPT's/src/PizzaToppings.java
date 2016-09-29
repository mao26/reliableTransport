import java.util.Set;
import java.util.TreeSet;


public class PizzaToppings {
	public static int WhichToppings(String[] menu, String[] favorites){
		
		Set<String> menuItems = new TreeSet<String>();
		for(int i = 0; i < menu.length; i++){
			menuItems.add(menu[i]);
			System.out.println(menuItems);
		}
		System.out.println(menuItems);
		int count = 0;
		for(int j = 0; j < favorites.length; j++){
			String[] favoriteItems = favorites[j].split(" ");
			System.out.println(favoriteItems);
			for(int i = 0; i < favoriteItems.length; i++){
				if(menuItems.contains(favoriteItems[i])){ //if menu item has the fav good
				   // then increase the count.. then we will compare the number of the favorite items
					count++;
					//System.out.println(count);
				}
				if(count == favoriteItems.length){
					System.out.println(j);
					return j;
				}
			}
		}
		return -1;
	}
	public static void main(String[] args){
		String[] favorites = { "anchovies pineapple", "anchovies mushrooms pepperoni", "ketchup", "beer" };
		String[] menu = { "anchovies", "mushrooms", "pepperoni" };
		
		WhichToppings(menu, favorites);
	}
}
