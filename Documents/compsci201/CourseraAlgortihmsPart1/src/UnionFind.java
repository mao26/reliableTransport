
public class UnionFind {

	private int[] id;
	private int[] sz;
	
	//set id of each object in the array equal to itself
	//N array accesses 
	public void createUF(int N){
		id = new int[N];
		sz = new int[N];
		for(int i = 0; i < N; i++){
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	//check that p and q are in the same component
	//2 array accesses;
	public boolean connectedQuickFind(int p, int q){
		return id[p] == id[q];
	}
	
	//change all entries with id[p] to id[q]
	//add the component p and all of it's connected components to all of the components connected to q by going through the array, getting the element
	//at id[p] and then checking all array elements. if the array element is equal to id[p] change it to id[q]. go through the array. 
	//at most 2N + 2 array accesses
	public void unionQuickFind(int p, int q){
		int idp = id[p];
		int idq = id[q];
		for(int i = 0; i < id.length; i++){
			if(id[p] == idp) id[p] = idq;
		}
	}

	
	//take element p and make it a child of q by placing in id[p] --> int q, where we find the root of each one
	//change root of p to point to root of q
	public void unionQuickUnion(int p, int q){
		int i = root(p);
		int j = root(q);
		id[i] = j;
	}
	
	//check that p and q have the same root--- also what find is for weighted quick union
	public boolean connectedQuickUnion(int p, int q){
		return root(q) == root(p);
	} // this is also the algorithm used for weighted union find
	
	//chase parent pointers until you reach the root
		public int root(int i){
			while(i != id[i]){
				id[i] = id[id[i]]; //this line compresses it, so every other node in the path points to its grandparent (halving the path length)
				//i = id[i];
			}
			return i;
		}
	/*
	 * 
	 * The best alternative for quick union(depth of p tree) and quick find (worst N^2) is to use weighted tree
	 * balance by linking the root of the smaller tree to the root of the larger tree
	 * 
	 */
	public void unionWeightedQuickUnion(int p, int q){ // takes constant time given root == depth at most of any node in the tree is log N
		int i = root(p);
		int j = root(q);
		if(i == j) return;
		if(sz[i] < sz[j]){
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
	}
	
}
