package zihazel;

/*SellerAgent class
 *
 * CSS 610
 * George Mason University
 * Spring 2005
 *
 * @author Rob Axtell
 * extended for use with Hazelcast by:
 * @author CW Dillon
 */
import java.io.Serializable;

public class SellerAgent implements Serializable {
    private static double maxCost;
    private double        cost;
    public double         price;
    public boolean        traded;

    public SellerAgent(double maxSellerCost) {
        this.cost    = Math.random() * maxSellerCost;
        this.maxCost = maxSellerCost;
        this.traded  = false;
    }

    public double formAskPrice() {
        return cost + Math.random() * (maxCost - cost);
    }    
}
