package zihazel;

/*Data class
 *
 * CSS 610
 * George Mason University
 * Spring 2005
 *
 * Rob Axtell
 */

// TODO: rewrite as a map-reduce process within Hazelcast

public class Data {
    private int N;
    private double min, max, sum, sum2;

    public Data() {
        N    = 0;
        min  = 1_000_000_000.0; //billion
        max  = 0.0;
        sum  = 0.0;
        sum2 = 0.0;
    }

    public void AddDatuum(double Datuum) {
        N++;

        if (Datuum < min) {
            min = Datuum;
        }

        if (Datuum > max) {
            max = Datuum;
        }

        sum  += Datuum;
        sum2 += Datuum * Datuum;
    }

    public int GetN() {
        return N;
    }

    public double GetMin() {
        return min;
    }

    public double GetMax() {
        return max;
    }

    public double GetAverage() {
        if (N > 0) {
            return sum / N;
        } else {
            return 0.0;
        }
    }

    public double GetVariance() {
        double avg, arg;

        if (N > 1) {
            avg = GetAverage();
            arg = sum2 - N * avg * avg;

            return arg / (N - 1);
        } else {
            return 0.0;
        }
    }

    public double GetStdDev() {
        return Math.sqrt(GetVariance());
    }
}
