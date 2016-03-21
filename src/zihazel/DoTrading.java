/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zihazel;

/**
 *
 * @author cw
 */
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import static zihazel.ZIHTraders.*;


public class DoTrading implements Runnable, Serializable, HazelcastInstanceAware {
    private final long numberOfTradesHere;
    
    private transient HazelcastInstance hz;
        
    public DoTrading(long numberOfTradesHere) {
        this.numberOfTradesHere = numberOfTradesHere;
    }
    
    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
    
    
    
    @Override
    public void run() {
        IdGenerator dig = hz.getIdGenerator("newID");   // Data IDs
        IMap<Long, BuyerAgent> buyers = hz.getMap("buyers");
        IMap<Long, SellerAgent> sellers = hz.getMap("sellers");
        Map<Long, Double> data = hz.getMap("data");
       
        long        buyerKey, sellerKey;
        double      bidPrice, askPrice, transactionPrice;
        //DecimalFormat twoDigits = new DecimalFormat("00.00");

        for (long counter = 1; counter < numberOfTradesHere; counter++) {
            // Pick a buyer and a seller at random             
            buyerKey  = ThreadLocalRandom.current().nextLong(numberOfBuyers);
            sellerKey = ThreadLocalRandom.current().nextLong(numberOfSellers);
            
            // if they haven't traded, create a copy of them and see if they'll trade
            if(!buyers.get(buyerKey).traded && !sellers.get(sellerKey).traded) {
                BuyerAgent potentialBuyer = buyers.get(buyerKey);
                SellerAgent potentialSeller = sellers.get(sellerKey);
                
                bidPrice = potentialBuyer.formBidPrice();
                askPrice = potentialSeller.formAskPrice();
                
                if(bidPrice > askPrice){
                    transactionPrice = askPrice + Math.random() * (bidPrice - askPrice);
                    potentialBuyer.traded = true;
                    potentialBuyer.price = transactionPrice;
                    potentialSeller.traded = true;
                    potentialSeller.price = transactionPrice;
                    buyers.set(buyerKey, potentialBuyer);
                    sellers.set(sellerKey, potentialSeller);
                    
                    data.put(dig.newId(), transactionPrice);
                    
                }   // fi bidPrice > askPrice                   
            }       // fi Agents haven't already traded
        }           // for each potential trade
    }               // end run    
}
