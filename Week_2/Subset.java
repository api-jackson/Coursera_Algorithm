import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) 
    {
        // TODO Auto-generated method stub
    	
    	RandomizedQueue<String> random = new RandomizedQueue<>();
    	int k = Integer.parseInt(args[0]);
    	if (k == 0) return;
    	int count = 0;
    	
    	// TODO read String from StdIn
    	/***************************************************/
		while (!StdIn.isEmpty()) 
		{
			if (random.size() == k) 
			{
				int randomNum = StdRandom.uniform(count+1);
				if (randomNum < k)
				{
					random.dequeue();
					random.enqueue(StdIn.readString());
				}
				else {
					StdIn.readString();
				}
			} 
			else 
			{
				random.enqueue(StdIn.readString());
			}
			count++;
		}

		while (!random.isEmpty())
			StdOut.println(random.dequeue());
		/***************************************************/
					
//    	while (!StdIn.isEmpty())
//    		random.enqueue(StdIn.readString());
//    	
//    	for (int i = 0; i < k; i++)
//    		StdOut.println(random.dequeue());

	}
}
