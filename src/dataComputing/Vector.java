package dataComputing;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Ari Weiland
 */
public class Vector {

    private final double[] vector;

    public Vector(double... values) {
        this.vector = values;
    }

    public double get(int i) {
        return vector[i];
    }

    public int length() {
        return vector.length;
    }

    public Vector plus(Vector other) {
        double[] out = new double[length()];
        for (int i=0; i<length(); i++) {
            out[i] = vector[i] + other.vector[i];
        }
        return new Vector(out);
    }

    public Vector minus(Vector other) {
        double[] out = new double[length()];
        for (int i=0; i<length(); i++) {
            out[i] = vector[i] - other.vector[i];
        }
        return new Vector(out);
    }

    public Vector times(Vector other) {
        double[] out = new double[length()];
        for (int i=0; i<length(); i++) {
            out[i] = vector[i] * other.vector[i];
        }
        return new Vector(out);
    }

    public double dot(Vector other) {
        return dot(other.asColumnMatrix()).get(0);
    }

    public Vector dot(Matrix other) {
        return asRowMatrix().dot(other).getRow(0);
    }

    public Matrix asRowMatrix() {
        return new Matrix(vector);
    }

    public Matrix asColumnMatrix() {
        return asRowMatrix().transpose();
    }

    public static Vector gaussian(int length) {
        return gaussian(length, new Random());
    }

    public static Vector gaussian(int length, Random random) {
        double[] temp = new double[length];
        for (int i=0; i<length; i++) {
            temp[i] = random.nextGaussian();
        }
        return new Vector(temp);
    }

    @Override
    public String toString() {
        return Arrays.toString(vector);
    }
}
