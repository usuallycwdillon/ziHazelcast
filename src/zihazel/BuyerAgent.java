package zihazel;

/**BuyerAgent class
 *
 * CSS 610
 * George Mason University
 * Spring 2005
 * @author Rob Axtell
 *
 * extended for use with Hazelcast by:
 * @author CW Dillon
 */
import java.io.Serializable;

public class BuyerAgent implements Serializable {
    public double  price;
    public boolean traded;
    private double value;
    public long agentID;

    public BuyerAgent(double maxBuyerValue, long agentID) {
        this.value  = Math.random() * maxBuyerValue;
        this.traded = false;
        this.agentID = agentID;
    }

    public double formBidPrice() {
        return Math.random() * value;
    }
    
    public void doneTrading() {
        this.traded = true;
    }
}

