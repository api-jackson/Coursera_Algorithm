import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;
    public Deque() 
    {
        // TODO Auto-generated constructor stub
        first = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty() 
    {
        if (size == 0)
            return true;
        return false;
    }
    
    public int size() 
    {
        return size;
    }
    
    public void addFirst(Item item) 
    {
        if (item == null)
            throw new java.lang.NullPointerException("You should not add a null item!");
        Node node = new Node();
        node.item = item;
        if (isEmpty())
        {
            first = node;
            last = node;
            first.previous = null;
            last.next = null;
        }
        else 
        {
            node.previous = null;
            node.next = first;
            first.previous = node;
            first = first.previous;
        }
        size++;
    }
    
    public void addLast(Item item) 
    {
        if (item == null)
            throw new java.lang.NullPointerException("The item is null!");
        Node node = new Node();
        node.item = item;
        if (isEmpty()) {
            first = node;
            last = node;
            first.previous = null;
            last.next = null;
        }
        else {
            node.next = null;
            node.previous = last;
            last.next = node;
            last = last.next;
        }
        size++;
    }
    
    public Item removeFirst() 
    {
        if (this.isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty!");
        
        Node node = first;
        first = first.next;
        if (first == null)
        { 
        	last = null;
        }
        else {
            first.previous = null;
            node.next = null;
        }
        
        size--;
        
        Item item = node.item;
        node = null;
        return item;

    }
    
    public Item removeLast() 
    {
        if (this.isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty!");
        
        Node node = last;
        last = last.previous;
        if (last == null)
        {
        	first = null;
        }
        else
        {
            last.next = null;
            node.previous = null;
        }
        
        size--;
        
        Item item = node.item;
        node = null;
        return item;
    }
    
    @Override
    public Iterator<Item> iterator() 
    {
        // TODO Auto-generated method stub
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>
    {
        private Node current;

        private DequeIterator() {
            // TODO Auto-generated constructor stub
            current = first;
        }
        
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (current == null)
                throw new java.util.NoSuchElementException("There are no more items to return!");
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() 
        {
            throw new java.lang.UnsupportedOperationException("The operation remove in Iterator has not been SUPPORTED!");
        }
    }
    
    private class Node
    {
        private Node previous;
        private Item item;
        private Node next;
    }
    

    public static void main(String[] args)
    {
//        Deque<Integer> deque = new Deque();
//        deque.addFirst(1);
//        deque.addFirst(2);
//        System.out.println(deque.size());
//        deque.removeLast();
////        deque.removeLast();
//        Iterator<Integer> it = deque.iterator();
//        System.out.println(it.hasNext());
//        it.next();
//        System.out.println(it.hasNext());
    }
}


