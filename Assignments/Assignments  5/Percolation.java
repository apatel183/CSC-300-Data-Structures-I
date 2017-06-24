
package algs15.perc;

//import stdlib.*; 
import algs15.*;

// Uncomment the import statements above.
// You can test this using InteractivePercolationVisualizer and PercolationVisualizer
// All methods should make at most a constant number of calls to the UF data structure,
// except percolates(), which may make up to N calls to the UF data structure.


public class Percolation {

	private boolean[][] gridISOpened;; // create empty grid
	private WeightedUF unionFind; // WeightedUF class for unionFind
	
	
	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		gridISOpened = new boolean[N][N]; // checking for the not correct gird
		//create a UF data structure of size N*N + 2
		unionFind = new WeightedUF(N * N + 2); // created new instance for unionFind
	}
	// open site (row i, column j) if it is not already
	public void open(int i, int j) 
	{
		//TODO
		//set the grid to boolean true. which will look like something like this v[i][j]=true.
		gridISOpened[i][j] = true;
		// new int for grid slot
		int gridslot = j + 1 + (i * gridISOpened.length);
		//below will be all if cases for the grid and it will check everything. 
		if (gridslot - gridISOpened.length <= 0)
			unionFind.union(0, gridslot);// this code is going to check top girdeslots
		// case for below
		if (i - 1 >= 0 && isFull(i - 1, j))
			unionFind.union(gridslot - gridISOpened.length, gridslot);// this code is going to check below grideslots	
		// case for left slots
		if (j - 1 >= 0 && isFull(j - 1,i))
			unionFind.union(gridslot - 1, gridslot); // this code is going to check left gridslots. 
		// case for right slots
		if (j + 1 < gridISOpened.length && isFull(j + 1,i))
			unionFind.union(gridslot + 1, gridslot);// this code going to check right gridslots. 
		//case for above slots
		if (i + 1 < gridISOpened.length && isFull(i+1,j))
			unionFind.union(gridslot + gridISOpened.length, gridslot);// this code going to check above gridslots.
		// case for bottom slots													
		if (gridslot + gridISOpened.length > gridISOpened.length * gridISOpened.length)
			unionFind.union(gridISOpened.length * gridISOpened.length + 1, gridslot); // this code going to check bottom gridslots.
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j)
	{
		//TODO
		return gridISOpened[i][j];// return position of the i,j. 
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) 
	{
		//TODO
		return gridISOpened[i][j];  // return the status of gridstatus of i , j(full). 
	}
	// does the system percolate?
	public boolean percolates() 
	{
		//TODO
		return unionFind.connected(0, gridISOpened.length*gridISOpened.length + 1); //connects grid
	}
}
