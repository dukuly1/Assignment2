
import java.util.*;
 /**
  * The MyQueue class implements the QueueInterface
  * @author djusu
  *
  * @param <T>
  */

public class MyQueue<T> implements QueueInterface<T> {

    private T sas[];
    protected int first, last;
    protected int sizes, length;

    /**
     * will take an int as the size of the queue
     */
    @SuppressWarnings("unchecked")
	public MyQueue(int sizes) {
    	 //copy constructor
        this.sizes = sizes;
        //initialized the length
        length = 0;
        sas = (T[]) new Object[sizes];
        first = -1;
        last = -1;
     
    }

    // uses a default as the size of the queue
    public MyQueue() {
        this(5);
    }

    @Override
    /**
     * @param true if the queue is empty otherwise return false
     */
    public boolean isEmpty() {
       if(first == -1){
    	   return true;
       }
	return false;
    }
    /**
     * @param tell whether the queue is full
     * @return true if it is full otherwise false
     */
    @Override
    public boolean isFull() {
     if (first == 0 && last == sizes - 1) {
        	return true;
        }
        return false;
    }
    /**
     * @param delete and return element at the front of the queue
     */
    @Override
    public T dequeue() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException("Will cause QueueUnderflowException");
        } else {
            length--;
            T element = sas[first];
            if (first == last) {
                first = -1;
                last = -1;
            } else {
                first++;
            }
            return element;
        }
    }
    /**
     * @return return the length
     */
    @Override
    public int size() {
        return length;
    }
    /**
     * @param element to the end of the stack
     * @return true if successfully added or otherwise
     */
    @Override
    public boolean enqueue(T e) throws QueueOverflowException {

        if (last == -1) {
            first = 0;
            last = 0;
            sas[last] = e;

        } else if (last + 1 >= sizes) {
            throw new QueueOverflowException("Overflow Exception");
        } else if (last + 1 < sizes) {
            sas[++last] = e;
        }
        length++;
        return true;
    }
    /**
     * @param string representation of the element in the string 
     * @return by the delimiter
     */
    @Override
    public String toString(String delimiter) {
        StringBuilder hold = new StringBuilder();
    
        for(int y=first; y < last; y++) {
            hold.append(sas[y].toString());
           
            if(y<last)
            hold.append(delimiter);
        
        }
        
        return hold.toString();
    }
    /**
     * @param string representation of elements in the queue
     */
    @Override
    public String toString( ) {
        StringBuilder s = new StringBuilder();
        for (int i = first; i <= last; i++) {
            s.append(sas[i].toString());
             
        }
        return s.toString();
    }
    
    /**
     * @param the queue withe element in the arrayList
     */
    @SuppressWarnings("unchecked")
	@Override
    public void fill(ArrayList<T> list) {
        length = 0;
        sas = (T[]) new Object[sizes];
        first = -1;
        last = -1;
        try {
            for (T t : list) {

                enqueue(t);

            }
        } catch (QueueOverflowException ex) {
            System.out.println(ex.toString());
        }
    }

}

