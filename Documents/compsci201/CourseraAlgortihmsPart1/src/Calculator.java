import java.util.Scanner;

public class Calculator {
	public static void main(String[] args){
		Calculator c = new Calculator();
		c.run();
	}

	private void run() {
		Scanner s = new Scanner(System.in);
		System.out.println("Input a starting salary: ");
		double salary = s.nextDouble();
		System.out.println("Input a annual contribution: ");
		int ann_contribution = s.nextInt();
		System.out.println("Input the number of years of your financial plan: ");
		int years = s.nextInt();
		double amt_in_savings_acct = savings_account(salary, (ann_contribution * .1), years);
		double amt_in_401k = retirement_401k(salary, (ann_contribution * .05), years);
		double amt_in_bonds = index_fund(salary, (ann_contribution * .15), years);
		amt_in_bonds = amt_in_bonds - (amt_in_bonds * .0004);
		double total_assets = (int) amt_in_401k + (int) amt_in_savings_acct + (int) amt_in_bonds;
		System.out.println("Total amount in financial assets: " + total_assets);
	}
	
	private double index_fund(double salary, double annual_cont, int years) {
		double earned = 0;
		double amt_invested = ((salary * .3) * .4166666667); // 10000 is about 41.67 % of 24000
		double annual_contribution = 0;
		for(int i = 0; i < years; i++){
			earned += amt_invested;
			if(i != 0) annual_contribution += annual_cont;
			earned += annual_contribution;
			earned += (earned * .13);
			System.out.println("year " + (i+1) + " amount in index_fund is: " + earned);
		}
		return earned;
	}

	private double retirement_401k(double salary, double ann_contribution, int years) {
		double earned = 0;
		double amount_invested = (salary * .05) * 2;
		double annual = 0;
		for(int i = 0; i < years; i++){
			earned += amount_invested;
			if(i != 0) annual += (ann_contribution * 2);
			earned += annual;
			earned = earned + (earned * .04);
			System.out.println("year " + (i+1) + " amount in 401k is: " + earned);
		}
		return earned;
	}

	private double savings_account(double salary, double contribution, int years) {
		double earned = 0;
		double amt_invested = salary * .1;
		double annual = 0;
		for(int i = 0; i < years; i++){
			earned += amt_invested;
			if(i != 0) annual += contribution;
			earned += annual;
			earned +=  (earned * .01);
			System.out.print("year " + (i + 1) + " in high-yield savings accound with 1% interest: " + earned + "\n");
		}
		return earned;
	}
}
