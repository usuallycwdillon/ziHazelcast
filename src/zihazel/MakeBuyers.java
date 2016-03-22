/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zihazel;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import java.io.Serializable;
import java.util.List;
import static zihazel.ZIHTraders.maxBuyerValue;
import zihazel.ZIHTraders.*;

/**
 *
 * @author cw
 */
public class MakeBuyers implements Runnable, Serializable, HazelcastInstanceAware {
    private final long theseBuyers;
    
    private transient HazelcastInstance hz;
    
    public MakeBuyers(long theseBuyers) {
        this.theseBuyers = theseBuyers;
    }
    
    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
    
    @Override
    public void run() {
        IdGenerator idg = hz.getIdGenerator("newID");   // Data IDs
        IMap<Long, BuyerAgent> buyers = hz.getMap("buyers");
        List<Long> buyerKeys = hz.getList("buyerKeys");
        
        for(long l = 0; l < theseBuyers; l++) {
            long agentID = idg.newId();
            buyers.set(agentID, new BuyerAgent(maxBuyerValue, agentID));
            buyerKeys.add(agentID);
        }           
    }    
}