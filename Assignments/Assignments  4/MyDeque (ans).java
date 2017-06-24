// Exercise 1.3.33
package algs13;
import stdlib.*;
import java.util.NoSuchElementException;

/**
 * This is a skeleton file for your homework.
 * Edit the sections marked TODO.
 * You may also edit the function "main" to test your code.
 *
 * You should not need to add any new functions or fields.
 *
 * You must not add static variables.
 * As always, you must not change the declaration of any method.
 * Do not change any of the methods I have provided, such as "toString" and "check".
 */
public class MyDeque {

    Node first = null;
    Node last = null;
    int N = 0;

    static class Node {
        public Node() { }
        public int item;
        public Node next;
        public Node prev;
    }

    public boolean isEmpty () {
        return first == null;
    }
    public int size () {
        return N;
    }
    public void pushLeft (int item) {
    	Node leftPush = new Node(); //create new node left 
    	leftPush.item = item; // left first item   

    	// if case
    	if (isEmpty()) 
    	{ //set both first and last to point to the new node
    		first = leftPush; 
    		last = leftPush;
    	} else { // insert on the left side;
    		leftPush.next = first; // make the new node point to the old first
    		first.prev = leftPush; // make the first.prev point to the new first
    		first = leftPush; // update first
    	}
    	N++;
    	checkInvariants ();
    }
    public void pushRight (int item) {
    	Node rightPush = new Node(); // created new node
    	rightPush.item = item; // last item

    	if (isEmpty())
    	{
    		last = rightPush;
    		first = rightPush;
    	}
    	else 
    	{
    		rightPush.prev = last;
    		last.next = rightPush;
    		last = rightPush;
    	}
    	N++;
    	checkInvariants ();
    }
    public int popLeft () 
    {
    	if (first==null)
    		throw new NoSuchElementException();	
    	Node leftPop = first;
    	first = first.next;
    	if (first == null)
    		last = null;
    	else{
    		first.prev = null;	
    	}
    	N--;
    	checkInvariants ();
    	return leftPop.item;
    }

    
    public int popRight () 
    {
    	if (last==null) throw new NoSuchElementException();	
    	Node rightPop = last;
    	last = rightPop.prev;

    	if (last == null)
    		first = null;
    	else{
    		last.next = null;
    	}
    	N--;
    	checkInvariants (); 
    	return rightPop.item;
    }
    // exercise 1.3.47 
    //
    // The concat method should take the Nodes from "that"
    // after execution, "that" should be empty.
    // See the tests in the main program.
    //
    // This method should create no new Nodes;
    // therefore it should not call pushLeft or pushRight.
    // Do not use a loop or a recursive definition.
    //
    public void concat (MyDeque that) {
    	//TODO -- see comments above
    	if (that.isEmpty()){
    		return; 
    	}
    	if(this.isEmpty()){
    		this.first = that.first;
    		this.last = that.last;
    		this.N = that.N;
    		return;
    	}
    	else{
    		this.last.next = that.first;
    		that.first.prev = this.last;
    		this.last = that.last;
    		this.N  +=  that.N;
    		that.first =null;
    		that.last = null;
    		that.N = 0;
    	}
    }
    // Delete should delete and return the kth element from the left.
    // See the tests in the main program.
    //
    // This method should create no new Nodes;
    // therefore it should not call pushLeft or pushRight.
    // You can use a loop or a recursive definition here.
    //
    public int delete (int k){
    	 if ( k < 0 || k > N) 
    		 throw new Error ();	
         Node curr = first;
         int count;
         int j = 0;
         while (j<k){
             curr = curr.next;
             j++;
         }
         count = curr.item;
         N--;
         if (N==0){
        	 first = null;
        	 last=null;
             
         } else if (k==1)
         {
             first = last;
             first.prev = null;
             return count;
                 
         } else if (curr==first)
         {
             first = first.next;
             first.prev = null;
             return count;
             
         } else if (curr==last)
         {
             last = last.prev;
             last.next = null;
         } else 
         {
             curr.prev.next=curr.next;
             curr.next.prev=curr.prev;
         }
         checkInvariants ();
         return count;
    }
       
    public String toString () {
        if (first == null) return "[]";
        StringBuilder sb = new StringBuilder ("[");
        sb.append (first.item);
        for (Node cursor = first.next; cursor != null; cursor = cursor.next) {
            sb.append (" ");
            sb.append (cursor.item);
        }
        sb.append ("]");
        return sb.toString ();
    }
    private void checkInvariants () {
        if (N < 0) throw new Error ();
        if (N == 0) {
            if (first != null || last != null) throw new Error ();
        } else {
            if (first == null || last == null) throw new Error ();
        }
        if (N > 0) {
            Node prev = null;
            Node current = first;
            for (int i = 0; i < N; i++) {
                //StdOut.println ("checking " + current.item);
                if (current.prev != prev) throw new Error ();
                prev = current;
                current = current.next;
            }
            if (current != null) throw new Error ();
            Node next = null;
            current = last;
            for (int i = 0; i < N; i++) {
                //StdOut.println ("checking " + current.item);
                if (current.next != next) throw new Error ();
                next = current;
                current = current.prev;
            }
            if (current != null) throw new Error ();
        }
    }
    private void check (String expected) {
        checkInvariants ();
        if (expected != null) {
            if (!expected.equals (this.toString ())) throw new Error ("Expected \"" + expected + "\", got \"" + this + "\"");
        }
        StdOut.println (this);
    }
    private void check (int iExpected, int iActual, String expected) {
        if (iExpected != iActual) throw new Error ("Expected \"" + iExpected + "\", got \"" + iActual + "\"");
        check (expected);
    }

    public static void main (String args[]) {
        // Here are some tests to get you started.
        // You can edit this all you like.
        MyDeque d1, d2, d3;
        Integer k;
        
        ////////////////////////////////////////////////////////////////////
        // push/pop tests
        ////////////////////////////////////////////////////////////////////
        d1 = new MyDeque ();
        d1.pushLeft (11);
        d1.check ("[11]");
        d1.pushLeft (12);
        d1.check ("[12 11]");
        d1.pushLeft (13);
        d1.check ("[13 12 11]");
        k = d1.popLeft ();
        d1.check (13, k, "[12 11]");
        k = d1.popLeft ();
        d1.check (12, k, "[11]");
        k = d1.popLeft ();
        d1.check (11, k, "[]");

        d1 = new MyDeque ();
        d1.pushRight (11);
        d1.check ("[11]");
        d1.pushRight (12);
        d1.check ("[11 12]");
        d1.pushRight (13);
        d1.check ("[11 12 13]");
        k = d1.popRight ();
        d1.check (13, k, "[11 12]");
        k = d1.popRight ();
        d1.check (12, k, "[11]");
        k = d1.popRight ();
        d1.check (11, k, "[]");
        
        ////////////////////////////////////////////////////////////////////
        //  test exceptions
        ////////////////////////////////////////////////////////////////////
        try {
            d1.popLeft ();
            throw new Error ("Expected exception");
        } catch (NoSuchElementException e) {}
        try {
            d1.popRight ();
            throw new Error ("Expected exception");
        } catch (NoSuchElementException e) {}
        
        ////////////////////////////////////////////////////////////////////
        // concat tests (and more push/pop tests)
        ////////////////////////////////////////////////////////////////////
        d1 = new MyDeque ();
        d1.concat (new MyDeque ());
        d1.check ("[]");
        d1.pushLeft (11);
        d1.concat (new MyDeque ());
        d1.check ("[11]");

        d1 = new MyDeque ();
        d2 = new MyDeque ();
        d2.pushLeft (11);
        d1.concat (d2);
        d1.check ("[11]");

        d1 = new MyDeque ();
        for (int i = 10; i < 15; i++) { d1.pushLeft (i); d1.checkInvariants (); }
        for (int i = 20; i < 25; i++) { d1.pushRight (i); d1.checkInvariants (); }
        d1.check ("[14 13 12 11 10 20 21 22 23 24]");
        d2 = new MyDeque ();
        d1.concat (d2);
        d1.check ("[14 13 12 11 10 20 21 22 23 24]");
        d2.check ("[]");

        for (int i = 30; i < 35; i++) { d2.pushLeft (i); d2.checkInvariants (); }
        for (int i = 40; i < 45; i++) { d2.pushRight (i); d2.checkInvariants (); }
        d2.check ("[34 33 32 31 30 40 41 42 43 44]");

        d3 = new MyDeque ();
        d2.concat (d3);
        d2.check ("[34 33 32 31 30 40 41 42 43 44]");
        d3.check ("[]");

        d1.concat (d2);
        d1.check ("[14 13 12 11 10 20 21 22 23 24 34 33 32 31 30 40 41 42 43 44]");
        d2.check ("[]");
        for (int i = 0; i < 20; i++) { d1.popLeft (); d1.checkInvariants (); }
        
        ////////////////////////////////////////////////////////////////////
        // delete tests
        ////////////////////////////////////////////////////////////////////
        d1 = new MyDeque ();
        d1.pushLeft (11);
        d1.delete (0);
        d1.check ("[]");
        for (int i = 10; i < 20; i++) { d1.pushRight (i); d1.checkInvariants (); }
        d1.delete (0);
        //System.out.println(d1);
        d1.check ("[11 12 13 14 15 16 17 18 19]");
        d1.delete (8);
        //System.out.println(d1);  
        d1.check ("[11 12 13 14 15 16 17 18]");
        //System.out.println(d1);  
        d1.delete (4);
        d1.check ("[11 12 13 14 16 17 18]");
        d1.delete (0);
        d1.check ("[12 13 14 16 17 18]");
        d1.delete (0);
        d1.check ("[13 14 16 17 18]");
        d1.delete (0);
        d1.check ("[14 16 17 18]");
        d1.delete (0);
        d1.check ("[16 17 18]");
        d1.delete (0);
        d1.check ("[17 18]");
        d1.delete (0);
        d1.check ("[18]");
        d1.delete (0);
    }
}
