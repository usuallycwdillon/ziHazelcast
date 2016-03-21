package zihazel;

/**
 * @author CW Dillon
 * This ZI Hazelcast Traders simulation is intended to be an executer-style 
 * implementation of Rob Axtell's 2005 ZI Traders code, used in his CSS 610
 * course on Models of Social Complexity at George Mason University; adapted 
 * to run a small Hazelcast cluster with many (20-80) executor JVMs. 
 *  
 */
import com.hazelcast.config.*;
import com.hazelcast.core.*;
import java.util.Map;


public class ZIHTraders {
    /**
    * @param  
    */
    static  long    numberOfBuyers  = 1_000L;
    static  long    numberOfSellers = 1_000L;    
    static  double  maxBuyerValue   = 30.0;
    static  double  maxSellerCost   = 30.0;
    static  long    maxNumberTrades = 10_000L;
    static  int     executionThreads= 40;
    
//    double     tempN      = 0;
//    double     tempAvg    = 0;
//    double     tempStdDev = 0;
//    public int sample     = 500;
    
    
    public static void main(String[] args) {
        Config cfg = new Config();
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);
        IdGenerator idg = hz.getIdGenerator("newId"); // Agent IDs
        IdGenerator dig = hz.getIdGenerator("did");   // Data IDs
        
        IMap<Long, BuyerAgent> buyers    = hz.getMap("buyers");
        IMap<Long, SellerAgent> sellers  = hz.getMap("sellers");
        Map<Long, Double> data = hz.getMap("data");
        
        Config config = new Config();
        ExecutorConfig executorConfig = config.getExecutorConfig("exec");
        executorConfig.setPoolSize(10).setQueueCapacity(100).setStatisticsEnabled(true);
        IExecutorService execService = hz.getExecutorService("exec");
        
        
        // Generate IMaps of BuyerAgents and SellerAgents
        for (long l = 0; l < numberOfBuyers; l++) {
            buyers.set(idg.newId(), new BuyerAgent(maxBuyerValue));                
        }
            
        for(long l = 0; l < numberOfSellers; l++) {
            sellers.set(idg.newId(), new SellerAgent(maxSellerCost));
        }
        
        long trades = maxNumberTrades / (long)executionThreads;
        for (int i = 1; i< executionThreads; i++){
            System.out.println("Starting trading round: " + i);
            execService.execute(new DoTrading(trades));
        }
        
        System.out.println("Trading is complete.");
        
    }
    
//    public void takeStats(int newN, double newAvg, double newStdDev) {
//        tempN = tempN + newN;
//        tempAvg = tempAvg + newAvg;
//        tempStdDev = tempStdDev + newStdDev;
//
//        System.out.println (";" + (tempN / sample) + ";" + (tempAvg / sample) + ";" + (tempStdDev / sample));
//    }

}