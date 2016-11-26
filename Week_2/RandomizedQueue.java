import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int head;
    private int tail;
    private Item[] items;
    
    public RandomizedQueue() 
    {
        // TODO Auto-generated constructor stub
        items = (Item[]) new Object[2];
        head = 0;
        tail = 0;
    }
    
    private void resizeQueue(int capacity)
    {
        
    	if (capacity < 2)
    		capacity = 2;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i=0, j=head; j < tail; i++, j++)
        {
        	temp[i] = items[j];
        }
        tail = tail-head;
        head = 0;
        items = temp;

    }
    
    public boolean isEmpty() 
    {
    	return (tail - head) == 0;
    }
    
    public int size() 
    {
        return (tail - head);
    }
    
    public void enqueue(Item item) 
    {
        if (item == null)
            throw new java.lang.NullPointerException("The Item has not been initialized");
        if ((tail) == (items.length))
        {
            resizeQueue((tail-head)*2);
        }
        items[tail++] = item;
    }
    
    public Item dequeue() 
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException("The Queue is Empty!");
        
        int random = StdRandom.uniform(head, tail);
        Item item = items[random];
        
        items[random] = items[tail-1];
        items[tail-1] = null;

        tail--;
        
        if ((tail - head) > 0 && (tail - head) == (items.length/4))
        {
            resizeQueue(items.length/2);
        }
        return item;
    }
    
    public Item sample() 
    {
    	if (isEmpty())
    		throw new NoSuchElementException("The Queue is Empty!");
    	
        int randNum = StdRandom.uniform((tail - head));
        return items[head+randNum];
    }
    
    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new RandomQueueIterator(items);
    }
    
    private class RandomQueueIterator implements Iterator<Item>
    {
        private int current;
        private Item[] itItems;

        private RandomQueueIterator(Item[] items) {
            // TODO Auto-generated constructor stub
            itItems = (Item[]) new Object[tail-head];
            System.arraycopy(items, head, itItems, 0, (tail - head));
            StdRandom.shuffle(itItems);
            current = 0;
        }
        
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current < itItems.length;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!(current < itItems.length))
                throw new java.util.NoSuchElementException("The Queue has no next element");
            Item item = itItems[current++];
            return item;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("The Method REMOVE has not been completed!");
        }
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    	RandomizedQueue<Integer> random = new RandomizedQueue<>();
    	random.enqueue(1);
    	random.enqueue(2);
    	random.enqueue(3);
    	random.enqueue(4);
    	random.enqueue(5);
    }

}
