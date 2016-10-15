package matrix;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Matrix implements Cloneable {
    private int amountStrings;
    private int amountColumns;
    public double[][] matrix;


    public Matrix() {
        amountStrings = 5;
        amountColumns = 6;
        matrix = new double[amountStrings][amountColumns];

    }

    public Matrix(int amountStrings, int amountColumns) {
        this.amountStrings = amountStrings;
        this.amountColumns = amountColumns;
        matrix = new double[amountStrings][amountColumns];
    }

    public Matrix(Matrix other) {
        this(other.amountStrings, other.amountColumns);
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountColumns; j++) {
                this.matrix[i][j] = other.matrix[i][j];
            }
        }
    }


    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Your full matrix: ");
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountColumns; j++) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        System.out.println();
    }

}

