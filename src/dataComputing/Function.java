package dataComputing;

/**
 * @author Ari Weiland
 */
public abstract class Function {

    public abstract double value(double x);

    public Vector value(Vector v) {
        double[] out = new double[v.length()];
        for (int i=0; i<v.length(); i++) {
            out[i] = value(v.get(i));
        }
        return new Vector(out);
    }

    public static final Function SIGMOID = new Function() {
        @Override
        public double value(double x) {
            return 1 / (1 + Math.exp(-(x-4)));
        }
    };

    public static final Function D_SIGMOID = new Function() {
        @Override
        public double value(double x) {
            double sig = SIGMOID.value(x);
            return sig * (1 - sig);
        }
    };
}
