package dataComputing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Ari Weiland
 */
public class NeuralNetwork {

    private static final Random RANDOM = new Random(1);

    private final Function function;
    private final Function derivative;
    private final int dimension;
    private Vector weights;

    public NeuralNetwork(Function function, Function derivative, int dimension) {
        this.function = function;
        this.derivative = derivative;
        this.dimension = dimension;
        this.weights = Vector.gaussian(dimension, RANDOM);
    }

    public int getDimension() {
        return dimension;
    }

    public Vector getWeights() {
        return weights;
    }

    public Vector train(Matrix x, Vector y) {
        return train(x, y, 10000);
    }

    public Vector train(Matrix x, Vector y, int iterations) {
        Vector out = null;
        for (int i=0; i<iterations; i++) {
            Vector temp = x.dot(weights);
            out = function.value(temp);
            Vector error = y.minus(out);
            Vector deriv = derivative.value(temp);
            Vector delta = error.times(deriv);
            weights = weights.plus(x.transpose().dot(delta));
        }
        return out;
    }

    public double classify(Vector v) {
        return function.value(v.dot(weights));
    }

    public Vector classify(Matrix m) {
        return function.value(m.dot(weights));
    }

    public static Matrix readDataCSV(String file, int... columns) {
        List<String> lines = new ArrayList<>();
        String line = null;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(file));
            while ((line = fileReader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error at \'" + line + "\'", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ignored) {
                }
            }
        }
        boolean allCols = (columns.length == 0);

        int rows = lines.size();
        double[][] data = new double[rows][];
        for (int i=0; i<rows; i++) {
            String[] split = lines.get(i).split(",");
            int cols = allCols ? split.length : columns.length;
            data[i] = new double[cols];
            for (int j=0; j<cols; j++) {
                data[i][j] = Double.parseDouble(split[allCols ? j : columns[j]]);
            }
        }
        return new Matrix(data);
    }

    public static Vector readResultCSV(String file) {
        List<String> lines = new ArrayList<>();
        String line = null;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(file));
            while ((line = fileReader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error at \'" + line + "\'", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ignored) {
                }
            }
        }

        int rows = lines.size();
        double[] results = new double[rows];
        for (int i=0; i<rows; i++) {
            results[i] = Double.parseDouble(lines.get(i));
        }
        return new Vector(results);
    }

    public static void main(String[] args) {
        Matrix trainingMatrix = readDataCSV("src/data/nhlforwards-trainingdata.csv");
        Vector maxValues = trainingMatrix.getMaxValues();
        trainingMatrix.normalize(maxValues);
        Vector trainingVector = readResultCSV("src/data/nhlforwards-trainingresults.csv");

        NeuralNetwork nhl = new NeuralNetwork(Function.SIGMOID, Function.D_SIGMOID, trainingMatrix.cols());
        nhl.train(trainingMatrix, trainingVector);

        System.out.println("Weights: " + nhl.getWeights());
        System.out.println();

        Matrix testingMatrix = readDataCSV("src/data/nhlforwards-testingdata.csv");
        testingMatrix.normalize(maxValues);
        Vector testingVector = readResultCSV("src/data/nhlforwards-testingresults.csv");

        System.out.println("=== Testing data ===");
        Vector test = nhl.classify(testingMatrix);
        int[][] confusion = new int[2][2];
        for (int i=0; i<testingMatrix.rows(); i++) {
            double actual = testingVector.get(i);
            double classified = test.get(i);
            boolean isStar = actual > 0.5;
            boolean isStarClassified = classified > 0.5;
            confusion[isStar ? 0 : 1][isStarClassified ? 0 : 1]++;
            String s = String.format("%.2f\t%.2f\t" + (isStar == isStarClassified), actual, classified);
            System.out.println(s);
        }

        System.out.println();
        System.out.println(confusion[0][0] + "\t" + confusion[1][0]);
        System.out.println(confusion[0][1] + "\t" + confusion[1][1]);
    }
}
