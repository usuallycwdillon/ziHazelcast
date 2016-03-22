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
import static zihazel.ZIHTraders.maxSellerCost;


/**
 *
 * @author cw
 */
public class MakeSellers implements Runnable, Serializable, HazelcastInstanceAware {
    private final long theseSellers;
    
    private transient HazelcastInstance hz;
    
    public MakeSellers(long theseSellers) {
        this.theseSellers = theseSellers;
    }
    
    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
    
    @Override
    public void run() {
        IdGenerator idg = hz.getIdGenerator("newID");   // Data IDs
        IMap<Long, SellerAgent> sellers = hz.getMap("sellers");
        List<Long> sellerKeys = hz.getList("sellerKeys");

        
        for(long l = 0; l < theseSellers; l++) {
            long agentID = idg.newId();
            sellers.set(agentID, new SellerAgent(maxSellerCost, agentID));
            sellerKeys.add(agentID);
        }           
    }    
}
