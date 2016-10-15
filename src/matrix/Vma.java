package matrix;

import java.util.Scanner;

public class Vma {
    private Matrix eq;
    private int amountStrings;
    private int amountColumns;
    private double determinant;
    double[] x;
    int[] order;
    double[][] reverseMatrix;
    double[][] matrixE;

    public Vma() {
        amountStrings = 5;
        amountColumns = 6;
        eq = new Matrix(amountStrings, amountColumns);
        determinant = 1;
        x = new double[amountStrings];
        order = new int[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            order[i] = i;
        }
        reverseMatrix = new double[amountStrings][amountStrings];
        matrixE = new double[amountStrings][amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                if (i == j) {
                    matrixE[i][j] = 1;
                } else {
                    matrixE[i][j] = 0;
                }
            }
        }
    }

    private void output(double[][] matrix, int strings, int columns) {
        for (int i = 0; i < strings; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%-15f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public double[][] transpose() {
        double[][] transposeMatrix = new double[amountColumns][amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountColumns; j++) {
                transposeMatrix[j][i] = eq.matrix[i][j];
            }
        }
        System.out.println("Your transpose matrix: ");
        output(transposeMatrix, amountStrings, amountColumns);
        return transposeMatrix;
    }

    private void changeColumns(int k, int columnMax) {
        double tempDouble = 0;
        int tempInt = 0;
        for (int i = 0; i < amountStrings; i++) {
            tempDouble = eq.matrix[i][k];
            eq.matrix[i][k] = eq.matrix[i][columnMax];
            eq.matrix[i][columnMax] = tempDouble;
        }
        tempInt = order[k];
        order[k] = order[columnMax];
        order[columnMax] = tempInt;
    }

    private void mainElement(int k) {
        int columnMax = k;
        for (int j = k; j < amountStrings; j++) {
            if (Math.abs(eq.matrix[k][j]) > Math.abs(eq.matrix[k][columnMax])) {
                columnMax = j;
            }
        }
        if (columnMax != k) {
            determinant *= -1;
            changeColumns(k, columnMax);
        }
    }

    private void directWay() {
        for (int k = 0; k < amountStrings; k++) {
            mainElement(k);
            determinant *= eq.matrix[k][k];
            double tempKK = eq.matrix[k][k];
            for (int j = amountStrings; j >= k; j--) {
                eq.matrix[k][j] /= eq.matrix[k][k];
            }
            for (int i = 0; i < amountStrings; i++) {
                matrixE[k][i] /= tempKK;
            }


            for (int i = k + 1; i < amountStrings; i++) {
                double tempMain = eq.matrix[i][k];
                for (int j = amountStrings; j >= k; j--) {
                    eq.matrix[i][j] -= eq.matrix[k][j] * eq.matrix[i][k];
                }
                for (int h = 0; h < amountStrings; h++) {
                    matrixE[i][h] -= matrixE[k][h] * tempMain;
                }
            }

        }
    }

    private void backwardWay() {
        for (int i = 0; i < amountStrings; i++) {
            x[i] = eq.matrix[i][amountStrings];
            for (int j = 0; j < amountStrings; j++) {
                reverseMatrix[j][i] = matrixE[j][i];
            }
        }

        for (int i = amountStrings - 2; i >= 0; i--) {
            for (int j = i + 1; j < amountStrings; j++) {
                x[i] -= x[j] * eq.matrix[i][j];
                for (int k = 0; k < amountStrings; k++) {
                    reverseMatrix[i][k] -= reverseMatrix[j][k] * eq.matrix[i][j];
                }
            }
        }
    }


    private void printSolution() {
        System.out.println("Your solution: ");
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                if (order[j] == i) {
                    System.out.println("x" + (i + 1) + " = " + x[j]);
                    break;
                }
            }
        }
        System.out.println("Reverse matrix: ");
        output(reverseMatrix, amountStrings, amountStrings);
        System.out.println("Determinant of A-matrix: " + determinant);
    }

    private void checkEqualization(Matrix check) {
        double[] r = new double[amountStrings];
        for (double item : r) {
            item = 0;
        }
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i] += check.matrix[i][j] * x[j];
            }
            r[i] -= check.matrix[i][amountStrings];
        }
        System.out.println("r = A * x - b: ");
        for (double item : r) {
            System.out.printf("%E", item);
            System.out.println();
        }
        double max = r[0];
        for (int i = 1; i < amountStrings; i++) {
            if (Math.abs(max) < Math.abs(r[i])) {
                max  = r[i];
            }
        }
        System.out.println("||r|| = " + max);
    }

    private double[][] multiplyMatrix(double[][] a, double[][] b) {
        double[][] result = new double[amountStrings][amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                for (int k = 0; k < amountStrings; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    private void checkReverse(Matrix check) {
        double[][] r = new double[amountStrings][amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                if (i == j) {
                    matrixE[i][j] = 1;
                } else {
                    matrixE[i][j] = 0;
                }
            }
        }
        r = multiplyMatrix(check.matrix, reverseMatrix);
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i][j] -= matrixE[i][j];
            }
        }
        System.out.println("R = A * A^(-1) - E: ");
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                System.out.printf("%E  ", r[i][j]);
            }
            System.out.println();
        }
        double[] tempR = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            tempR[i] = 0;
        }
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                tempR[i] += Math.abs(r[i][j]);
            }
        }
        double maxR = tempR[0];
        for (int i = 1; i < amountStrings; i++) {
            if (maxR < tempR[i]) {
                maxR = tempR[i];
            }
        }
        System.out.println("||R|| = " + maxR);
    }

    private void conditionalNumber(Matrix mtr) {
        double[] tempA = new double[amountStrings];
        double[] tempReverse = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            tempA[i] = 0;
            tempReverse[i] = 0;
        }
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                tempA[i] += Math.abs(mtr.matrix[i][j]);
                tempReverse[i] += Math.abs(reverseMatrix[i][j]);
            }
        }
        double maxA = tempA[0];
        double maxReverse = tempReverse[0];
        for (int i = 1; i < amountStrings; i++) {
            if (maxA < tempA[i]) {
                maxA = tempA[i];
            }
            if (maxReverse < tempReverse[i]) {
                maxReverse = tempReverse[i];
            }
        }
        System.out.println("Conditional number ||A||*||A^(-1)|| is: " + (maxA * maxReverse));
    }

    public void solveGauss() {
        eq.input();
        Matrix check = new Matrix(eq);
        directWay();
        backwardWay();
        printSolution();
        checkEqualization(check);
        checkReverse(check);
        conditionalNumber(check);
        for (int item : order)
            System.out.println(item);
    }
}
