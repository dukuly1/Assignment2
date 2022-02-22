import java.util.ArrayList;
/**
 * tack class implements the stackInterface
 * @author djusu
 *
 * @param <T>
 */
public class MyStack<T>implements StackInterface<T> {
	

	 private T store[];
	    private int magnitude;
	    private int length ;

	    /**
	     * @param sizes make two constructors
	     */
	    @SuppressWarnings("unchecked")
		public MyStack(int sizes) {
	        magnitude = sizes;
	        store =   (T[]) new Object[sizes];
	        length = 0;
	    }
	    //default constructor	
	    public MyStack( ) {
	        this(6);
	    }
	    /**
	     * return true if the method is empty otherwise false
	     */
	    @Override
	    public boolean isEmpty() {
	        if(length == 0) {
	        	return true;
	        }
			return false;
	    }

	    @Override
	    /**
	     * return true if the method is full otherwise false
	     */
	    public boolean isFull() {
	        if(length == magnitude) {
	        return true;	
	        }
			return false;
	    }
     /**
       * To delete and return element at the top if stack is not empty
       * if the stack is empty, throw StackUnderFlowException
       */
	    @Override
	    public T pop() throws StackUnderflowException {
	    	
	        if (!isEmpty()) {
	        	 return store[--length];
	        }
	        throw new StackUnderflowException("This stack is empty");
	    }
        /**
         * @param return element at the top of the stack
         * @return StackUnderflowException if the stack is empty
         */
	    @Override
	    public T top() throws StackUnderflowException {
	    	
	        if (isEmpty()) {
	            throw new StackUnderflowException("This stack is empty");
	        }
	        return store[length-1];
	    }
        /**
         * @return return the number of element in the stack
         */
	    @Override
	    public int size() {
	        return length;
	    }
        /**
         * @param element to the top of the stack
         * @return return true if added successfully otherwise false
         */
	    @Override
	    public boolean push(T e) throws StackOverflowException {
	        if (isFull()) {
	            throw new StackOverflowException("This stack is full");
	        }

	        store[length] = e;
	        length++;
	        return true;
	    }
        /**
         * @param
         * @return the String representation of the stack 
         * from bottom to top with element separated using delimiter
         */
	    @Override
	    public String toString(String delimiter) {
	         StringBuilder s1 = new StringBuilder();
	      
	        for( int p=0; p <length; p++) {
	            s1.append(store[p].toString());
	           
	            	        }
	        return s1.toString();
	    }
        /**
         * @param the String representation of the stack 
         *@return from bottom to top, the beginning of the string specified by toString()
         */
	    @Override
	    public String toString() {
	         StringBuilder pas = new StringBuilder();
	        for (int t=0; t<length; t++) {
	        pas.append(store[t].toString()); 
	        }
			return pas.toString();
	     
	  
	    }
	    
        /**
         * @param fill the stack with the elements of the arrayList
         */
	    @SuppressWarnings("unchecked")
		@Override
	    public void fill(ArrayList<T> getList) {
	        store =   (T[]) new Object[magnitude];
	        length = 0;
	        try {
	        for(T myList: getList ) {
	        	push(myList);
	        }
	        }
	        catch(StackOverflowException r) {
	        System.out.println(r);	
	        }
	        }
	    }



 