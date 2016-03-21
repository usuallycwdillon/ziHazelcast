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

    public BuyerAgent(double maxBuyerValue) {
        value  = Math.random() * maxBuyerValue;
        traded = false;
    }

    public double formBidPrice() {
        return Math.random() * value;
    }
    
    public void doneTrading() {
        traded = true;
    }
}

