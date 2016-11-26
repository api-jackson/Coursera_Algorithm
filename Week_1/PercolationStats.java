import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	
    // N = size of Percolation
    private int N;
    // T is repeated computation test times of percolation experiment
    private int T;
    private double mean = -1;
    private double stddev = -1;
    private double confidenceLo = -1;
    private double confidenceHi = -1;

    public PercolationStats(int N, int T) 
    {
        // TODO Auto-generated constructor stub
    	if (N<= 0 || T <= 0)
    		throw new IllegalArgumentException("N or T is illegal");
    	
        this.T = T;
        this.N = N;
        
        double[] openPercentage = new double[T];

        getOpenPercentageInPercolation(openPercentage);
        
        /****************************/
        // calculate mean
        double totalOpenSites = 0.0;
        for (int i = 0; i < T; i++)
        {
            totalOpenSites += openPercentage[i];
        }
        mean = ((totalOpenSites)/((double) T));
        
        /****************************/
        
        /****************************/
        // calculate stddev
        double sum = 0.0;
        for (int i = 0; i < T; i++)
        {
            sum += (openPercentage[i]-mean)*(openPercentage[i]-mean);
        }
        stddev = Math.sqrt(sum /((double) (T-1)));
        
        /****************************/
        
    }
    
    private void getOpenPercentageInPercolation(double[] openPercentage) 
    {
        int randomI;
        int randomJ;
        for (int i = 0; i < T; i++)
        {
            int j = 0;
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()) {
                // generate random num for open the percolation
                randomI = StdRandom.uniform(1, N+1);
                randomJ = StdRandom.uniform(1, N+1);
                if (percolation.isOpen(randomI, randomJ))
                    continue;
                else 
                {
                    percolation.open(randomI, randomJ);
                    j++;
                }    
            }
            openPercentage[i] = (double) j/(double) (N*N);
//            System.out.println(i+":"+j);
        }
    }
    

    
    public double mean() 
    {
        return mean;
    }
    
    public double stddev() 
    {
        if (T == 1)
            return Double.NaN;
        return stddev;
    }
    
    public double confidenceLo() 
    {
        if (confidenceLo != -1)
            return confidenceLo;
        confidenceLo = mean-(1.96*stddev/Math.sqrt(T));
        return confidenceLo;
    }
    
    public double confidenceHi() 
    {
        if (confidenceHi != -1)
            return confidenceHi;
        confidenceHi = mean+(1.96*stddev/Math.sqrt(T));
        return confidenceHi;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PercolationStats stats = new PercolationStats(200, 100);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("cofidenceLo = " + stats.confidenceLo());
        System.out.println("cofidenceHi = " + stats.confidenceHi());
    }

}
