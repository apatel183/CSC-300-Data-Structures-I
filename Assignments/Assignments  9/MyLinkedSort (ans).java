package algs13;
import stdlib.*;

// PROBLEM 2.2.17
public class MyLinkedSort {
    static class Node {
        public Node() { }
        public double item;
        public Node next;
    }

    int N;
    Node first;
    
    public MyLinkedSort () {
        first = null;
        N = 0;
        checkInvariants ();
    }

    private void myassert (String s, boolean b) { if (!b) throw new Error ("Assertion failed: " + s); }
    private void checkInvariants() {
        myassert("Empty <==> first==null", (N == 0) == (first == null));
        Node x = first;
        for (int i = 0; i < N; i++) {
            if (x==null) {
                throw new Error ("List too short!");
            }
            x = x.next;
        }
        myassert("EndOfList == null", x == null);
    }

    public boolean isEmpty () { return first == null; }
    public int size () { return N; }
    public void add (double item) {
        Node newfirst = new Node ();
        newfirst.item = item;
        newfirst.next = first;
        first = newfirst;
        N++;
    }

    private static void print (String s, MyLinkedSort b) {
        StdOut.print (s + ": ");
        for (Node x = b.first; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println ();
    }
    private static void print (String s, MyLinkedSort b, double i) {
        StdOut.print (s + ": ");
        for (Node x = b.first; x != null; x = x.next)
            StdOut.print (x.item + " ");
        StdOut.println (": " + i);
    }

    private void sort(){ 
    	mergeSort(this); 
    	 }
    private static MyLinkedSort split(MyLinkedSort splitINtwo){
    	Node temp = splitINtwo.first; // front node very first part
    	int size = splitINtwo.size()/2;
    	for (int i = 0; i < size; i++)
    		temp = temp.next; //move the pointer next
    	//now list for second half
    	MyLinkedSort secondhalf = new MyLinkedSort(); // create new list start at mid point
    	secondhalf.first = temp.next;// new list we starting at second half of the orginal 
    	temp.next = null; // cutting in half 
    	splitINtwo.N = splitINtwo.size()/2; // update the size 
    	return secondhalf;
    	// this function is split half. divide by 2
    }

    private static Node merge(Node left, Node right){ 
    	if(left == null)
    		return right;
    	if (right == null)
    		return left;
    	Node temp = null; 
    	if (left.item <= right.item){  // left item is less than right item 
    		temp= left; // update the left
    		temp.next=merge(left.next,right); // recur calling the function 
    	}else{
    		temp =right;
    		temp.next = merge(left, right.next);
    	}
    	return temp;	
    }
    private static void mergeSort(MyLinkedSort inputlist){
    	if (inputlist.isEmpty() || inputlist.first.next==null)
    		return;
    	MyLinkedSort list = split(inputlist); // split in half 
    	mergeSort(inputlist);
    	mergeSort(list);
    	inputlist.first = merge(inputlist.first, list.first);	
    }
    public static void main (String args[]) {
        int[] a1 = new int[20];
		for (int i = 0; i < a1.length; i++)
			a1[i] = i;
		StdRandom.shuffle (a1);
        MyLinkedSort b1 = new MyLinkedSort ();
        for (int i:a1) b1.add(i);
        MyLinkedSort.print("before sort",b1);
        b1.sort();
        MyLinkedSort.print("after sort",b1);

        int[] a2 = new int[200];
		for (int i = 0; i < a2.length; i++)
			a2[i] = i;
		StdRandom.shuffle (a2);
        MyLinkedSort b2 = new MyLinkedSort ();
        for (int i:a2) b2.add(i);
        MyLinkedSort.print("before sort",b2);
        b2.sort();
        MyLinkedSort.print("after sort",b2);
         
    }
}


