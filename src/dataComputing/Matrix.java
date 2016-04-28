package dataComputing;

/**
 * @author Ari Weiland
 */
public class Matrix {

    private final double[][] matrix;

    public Matrix(double[]... matrix) {
        this.matrix = matrix;
    }

    public double get(int i, int j) {
        return matrix[i][j];
    }

    public Vector getRow(int i) {
        return new Vector(matrix[i]);
    }

    public int rows() {
        return matrix.length;
    }

    public int cols() {
        return matrix[0].length;
    }

    public Matrix transpose() {
        int r = matrix.length;
        int c = matrix[0].length;
        double[][] t = new double[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                t[j][i] = matrix[i][j];
            }
        }
        return new Matrix(t);
    }

    public Matrix plus(Matrix other) {
        int r = matrix.length;
        int c = matrix[0].length;
        double[][] out = new double[r][c];
        for (int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                out[i][j] = matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix(out);
    }

    public Matrix minus(Matrix other) {
        int r = matrix.length;
        int c = matrix[0].length;
        double[][] out = new double[r][c];
        for (int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                out[i][j] = matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix(out);
    }

    public Matrix times(Matrix other) {
        int r = matrix.length;
        int c = matrix[0].length;
        double[][] out = new double[r][c];
        for (int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                out[i][j] = matrix[i][j] * other.matrix[i][j];
            }
        }
        return new Matrix(out);
    }

    public Vector dot(Vector other) {
        return other.dot(transpose());
    }

    public Matrix dot(Matrix other) {
        int r1 = rows();
        int c1 = cols();
        int r2 = other.rows();
        int c2 = other.cols();
        if (c1 != r2) {
            throw new IllegalArgumentException("Invalid matrix dimensions");
        }
        double[][] out = new double[r1][c2];
        for (int i=0; i<r1; i++) {
            for (int j=0; j<c2; j++) {
                for (int k=0; k<c1; k++) {
                    out[i][j] += get(i, k) * other.get(k, j);
                }
            }
        }
        return new Matrix(out);
    }

    public Vector getMaxValues() {
        double[] max = new double[cols()];
        for (double[] row : matrix) {
            for (int j = 0; j < cols(); j++) {
                if (row[j] > max[j]) {
                    max[j] = row[j];
                }
            }
        }
        return new Vector(max);
    }

    public void normalize() {
        normalize(getMaxValues());
    }

    public void normalize(Vector maxValues) {
        int rows = rows();
        int cols = cols();
        for (int j=0; j<cols; j++) {
            double max = maxValues.get(j);
            for (int i=0; i<rows; i++) {
                matrix[i][j] /= max;
            }
        }
    }
}
