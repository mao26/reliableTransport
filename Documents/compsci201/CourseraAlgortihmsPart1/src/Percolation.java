import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    
	boolean[] status; //tells us if we have opened our site
    WeightedQuickUnionUF wuf;
    int Nsize;
    int curr;
    boolean percs;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
    	
    	//keep track of our N for this example
    	Nsize = N;
    	
    	//our percalotes() doens't keep track of percolates after the whole method runs, so this will keep track of it for the whole program
    	percs = false;
    	
    	//create our status array that says whether a site is blocked or open
    	status = new boolean[N*N + 1];
    	Arrays.fill(status, false);
    	
    	//because our 0 and last index are used for virtual top and bottom sites to see what if top and bottom are connected == percolation = true
    	//let's make these spots have a status of true (always open)
    	status[0] = true;
    	//status[N*N+1] = true;

    	//this has a count--> number of components
    	//this has a size array, keeping track of the size of each component
    	//this has parent array, telling us what the parent of each node is.. we will implement this with a weighted algorithm
    	wuf = new WeightedQuickUnionUF(N*N + 1);
    	
    	
    	//let's make our root for the top and bottom rows
    	// our 0 index is our first root
    	//our N^2 + 1 index is our bottom root
    	for(int i = 1; i < N + 1; i++){
    		wuf.union(0, i);
    	}
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
    	
    	if(isOpen(i,j)){
    		System.out.println("already opened");
    		return;
    	}
    	curr = xyTo1D(i, j, Nsize);
        status[xyTo1D(i, j, Nsize)] = true;
        //union new open site to the one next to it vertically and horizontally
        if(inBounds(i-1, j)){ //check the if the site above ours is in bounds
        	//if the above site is open, then union our new site with the above site
        	if(isOpen(i-1,j)) {
        		wuf.union(xyTo1D(i, j, Nsize), xyTo1D(i-1, j, Nsize));
        	}
        }
        if(inBounds(i+1, j)){ //check the if the site below ours is in bounds
        	//if the above site is open, then union our new site with the below site
        	if(isOpen(i+1,j)) {
        		wuf.union(xyTo1D(i, j, Nsize), xyTo1D(i+1, j, Nsize));
        	}
        }
        if(inBounds(i, j-1)){ //check the if the site left of ours is in bounds
        	//if the above site is open, then union our new site with the left site
        	if(isOpen(i,j-1)) {
        		wuf.union(xyTo1D(i, j, Nsize), xyTo1D(i, j-1, Nsize));
        	} 
        }
        if(inBounds(i, j+1)){ //check the if the site right of ours is in bounds
        	//if the right site is open, then union our new site with the above site
        	if(isOpen(i,j+1)) {
        		wuf.union(xyTo1D(i, j, Nsize), xyTo1D(i, j+1, Nsize));
        	}
        }
    }
    
    //check that all the indexes that are the same as our (i,j) that needs to change 
    //the status open must also be true
    
    private void unionAll(int og, int otheri, int otherj){
    	int other = wuf.find(xyTo1D(otheri, otherj, Nsize));
    	for(int i = 0; i < Nsize*Nsize; i++){
    		
    		int x = wuf.find(i);
    		System.out.println(x);
    		if(wuf.find(i) == other){
    			
    		}
    	}
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j){
    	int idx = xyTo1D(i, j, Nsize);
    	if(status[idx]){
    		return true;
    	}
        return false;
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
    	int idx = xyTo1D(i, j, Nsize);
    	if(status[idx] && wuf.find(idx) == 0){ //if status = open and our idx element's parent is the virtual root at the top 
    		return true;
    	} else {
    		return false;
    	}
    }
    // does the system percolate?
    public boolean percolates() {
    	//if both the top and bottom virtual sites have the same root (one of them is the root of the other) then they are connected
    	//if(wuf.find(0) == wuf.find(Nsize*Nsize +1)) return true; 
    	if(wuf.connected(0, curr)){
    		if(curr <= Nsize*Nsize+1 && curr > Nsize * Nsize - Nsize || percs){ //if curr is on the last row, and they are connected
    			percs = true; //percs keeps track of if any element has percalated already
    			return true;
    		}
    	}
        return false;
        
    }
    
    private int xyTo1D(int r, int c, int N){
    	int arrayIdx = (r-1) * N + c;
    	return arrayIdx; // return the number in the array that this will belong too
    }
    
    private boolean inBounds(int r, int c){
    	if(r > Nsize || r < 1) return false;
		if(c > Nsize || c < 1) return false;
		return true;
    }
    
    /*public static void main(String[] args){
        Percolation perc = new Percolation(4);
        perc.open(0, 0);
        boolean need = perc.isFull(0, 0);
        System.out.println(need);
        perc.open(0, 1);
        perc.open(1, 0);
        perc.open(1, 2);
        boolean need1 =perc.isFull(1, 2);
        System.out.println(need1);
        perc.open(2, 2);
        perc.open(0, 2);
        boolean need2 =perc.isFull(1, 2);
        System.out.println(need2);
        perc.open(2, 3);
        perc.open(3, 3);
        System.out.println(perc.percolates());
    } */
}
