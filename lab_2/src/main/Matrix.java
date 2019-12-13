package main;

import java.util.Random;

public class Matrix {
    public double [] data;
    public int rows;
    public int cols;

    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols= cols;
        data = new double[rows*cols];
    }

    // 2.1
    Matrix(double [][] d){
        this.rows = d.length;
        this.cols = d[0].length;
        for (double[] doubles : d)
            if (doubles.length > cols)
                this.cols = doubles.length;

        data = new double[rows*cols];
        for (int i = 0; i < d.length; i++)
            for (int j = 0; j < d[i].length; j++)
                 this.data[i*cols + j] = d[i][j];
    }

    //2.2
    public double [][] asArray(){
        double [][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = data[i*cols+j];
        return result;
    }

    //2.3
    public double get(int r, int c){
        return data[this.cols*(r-1) + (c-1)];  //user can choose which row and column he want to get without knowledge about indexing from 0.
    }
    public void set(int r, int c, double value){
        this.data[this.cols*(r-1) + (c-1)] = value;
    }

    //2.4
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < rows; i++){
            s.append("|");
            for (int j = 0; j < cols; j++){
                s.append(data[i*cols + j]).append(", ");
            }
            s.delete(s.length()-2,s.length());
            s.append("|").append("\n");
        }
        s.delete(s.length() -1,s.length());

        return s.toString();
    }

    //2.5
    public void reshape(int rows, int cols) {
        if (this.rows * this.cols != rows * cols) {
            throw new RuntimeException(
                    String.format("%d x %d matrix can't be reshaped to %d x %d", this.rows, this.cols, rows, cols)
            );
        }
        this.rows = rows;
        this.cols = cols;
    }

    //2.6
    public int [] shape(){
        return new int[]{this.rows, this.cols};
    }

    //2.7
    public Matrix add(Matrix m) throws Exception {
        Matrix result = new Matrix(this.rows,this.cols);
        if (m.rows != this.rows || m.cols != this.cols)
            throw new Exception("Matrix has different size and can't be added");
        else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    result.data[i*cols + j] = m.get(i+1,j+1) + get(i+1,j+1);
                }
            }
        }
        return result;
    }

    //2.8
    public Matrix sub(Matrix m) throws Exception {
        Matrix result = new Matrix(this.rows,this.cols);
        if (m.rows != this.rows || m.cols != this.cols)
            throw new Exception("Matrix has different size and can't be subtractioned");
        else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    result.data[i*cols + j] = get(i+1,j+1) - m.get(i+1,j+1);
                }
            }
        }
        return result;
    }
    public Matrix mul(Matrix m) throws Exception {
        Matrix result = new Matrix(this.rows,this.cols);
        if (m.rows != this.rows || m.cols != this.cols)
            throw new Exception("Matrix has different size and can't be subtractioned");
        else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    result.data[i*cols + j] = get(i+1,j+1) * m.get(i+1,j+1);
                }
            }
        }
        return result;
    }
    public Matrix div(Matrix m) throws Exception {
        Matrix result = new Matrix(this.rows,this.cols);
        if (m.rows != this.rows || m.cols != this.cols)
            throw new Exception("Matrix has different size and can't be subtractioned");
        else {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    result.data[i*cols + j] = get(i+1,j+1) / m.get(i+1,j+1);
                }
            }
        }
        return result;
    }
    public Matrix add(double w){
        Matrix result = new Matrix(this.rows,this.cols);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                result.data[i*cols + j] = w + get(i+1,j+1);
            }
        }
        return result;
    }
    public Matrix sub(double w){
        Matrix result = new Matrix(this.rows,this.cols);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                result.data[i*cols + j] = get(i+1,j+1) - w;
            }
        }
        return result;
    }
    public Matrix mul(double w){
        Matrix result = new Matrix(this.rows,this.cols);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                result.data[i*cols + j] = w * get(i+1,j+1);
            }
        }
        return result;
    }
    public Matrix div(double w) throws Exception {
        if (w == 0)
            throw new Exception("Dividing by 0!");
        Matrix result = new Matrix(this.rows,this.cols);
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                result.data[i*cols + j] = get(i+1,j+1)/w;
            }
        }
        return result;
    }

    //2.9
    public Matrix dot(Matrix m) throws Exception {
        Matrix result = new Matrix(this.rows, m.cols);
        if (this.cols != m.rows)
            throw new Exception("Matrix has different size and can't be multiplied");
        else {
            for (int i = 0; i < result.rows; i++) {
                for (int j = 0; j < result.cols; j++) {
                    result.set(i + 1, j + 1, 0);
                    for (int k = 0; k < m.rows; k++) {
                        result.set(i + 1, j + 1, result.get(i + 1, j + 1) + this.get(i + 1, k + 1) * m.get(k + 1, j + 1));
                    }
                }
            }
        }
        return result;
    }

    //2.10
    public double frobenius(){
        double [] power = new double[data.length];
        double result = 0;
        for (int i = 0; i < data.length;i++){
            power[i] = data[i]*data[i];
            result += power[i];
        }
        return Math.sqrt(result);
    }

    //2.12
    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                m.set(i+1,j+1,r.nextDouble());
        return m;
    }
    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
       for (int i =0; i < n; i++){
           m.set(i+1,i+1,1);
       }
        return m;
    }

    //Transposition of matrix
    public Matrix getTransposition() {
        Matrix result = new Matrix(this.cols, this.rows);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.set(j+1, i+1, this.get(i+1, j+1));
            }
        }

        return result;
    }
}
