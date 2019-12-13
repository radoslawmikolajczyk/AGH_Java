package main;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @org.junit.jupiter.api.Test
    void asArray() {
        double [][] data = new double[][]{{1,2}, {1,2},{1,2}};
        Matrix m = new Matrix(data);
        assertArrayEquals(m.asArray(),data);
    }

    @org.junit.jupiter.api.Test
    void get() {
        double [][] data = new double[][]{{1,2,3},{1,2,3},{1,2,3}};
        Matrix mGet = new Matrix(data);
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length;j++)
                assertEquals(data[i][j], mGet.get(i+1,j+1)); // I explained why i use i+1 and j+1 in the Matrix class.
    }

    @org.junit.jupiter.api.Test
    void set() {
        double [][] data = new double[][] {{1,2,3},{0,0,0},{4,5,6}};
        Matrix mSet = new Matrix(data);
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j <data[i].length; j++)
                mSet.set(i+1,j+1,1.0);
        double [] data2 = new double[] {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
        assertArrayEquals(mSet.data,data2);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String s= "[[1.0,2.3,4.56], [12.3,  45, 21.8]]";
        s= s.replaceAll("(\\[|\\]|\\s)+","");
        String[] t = s.split("(,)+");
        for(String x:t){
            System.out.println(String.format("\'%s\'",x ));
        }

        double[]d=new double[t.length];
        for(int i=0;i<t.length;i++) {
            d[i] = Double.parseDouble(t[i]);
        }

        double arr[][]=new double[1][];
        arr[0]=d;

        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                System.out.println(arr[i][j]);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void reshape() {
        double [][] data = new double[][]{{1,2,3},{1,2,3},{1,2,3}};
        Matrix mReshape = new Matrix(data);
        try{
            mReshape.reshape(3,2);
        } catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        fail("Something went wrong");
    }

    @org.junit.jupiter.api.Test
    void shape() {
        double [][] data = {{1,2},{1,2},{1,2}};
        Matrix mShape = new Matrix(data);
        int [] arr = {3,2};
        assertArrayEquals(mShape.shape(), arr);
    }

    @org.junit.jupiter.api.Test
    void add() throws Exception {
        double [][] data = {{0,0,0},{0,0,0}};
        Matrix m1 = new Matrix(data);
        Matrix m2 = Matrix.random(2,3);
        Matrix summary = m1.add(m2);
        assertArrayEquals(m2.asArray(),summary.asArray());
    }

    @org.junit.jupiter.api.Test
    void sub() throws Exception {
        Matrix m1 = new Matrix(new double[][]{{1,2,3},{1,2,3},{1,2,3}});
        Matrix m2 = new Matrix(new double[][]{{1,2,3},{1,2,3},{1,2,3}});
        Matrix sub = m1.sub(m2);
        assertEquals(0.0,sub.frobenius());
    }

    @org.junit.jupiter.api.Test
    void mul() throws Exception {
        Matrix m1 = new Matrix(new double[][]{{1,1,1},{2,2,2}});
        Matrix m2 = new Matrix(new double[][]{{0,0,0},{1,1,1}});
        Matrix expected = new Matrix(new double[][]{{0,0,0},{2,2,2}});
        Matrix mul = m1.mul(m2);
        assertArrayEquals(mul.asArray(), expected.asArray());
    }

    @org.junit.jupiter.api.Test
    void div() throws Exception {
        Matrix m1 = new Matrix(new double[][]{{1,1,1},{2,2,2}});
        Matrix m2 = new Matrix(new double[][]{{1,1,1},{2,2,2}});
        Matrix expected = new Matrix(new double[][]{{1,1,1},{1,1,1}});
        Matrix div = m1.div(m2);
        assertArrayEquals(div.asArray(),expected.asArray());
    }

    @org.junit.jupiter.api.Test
    void testAdd() {
        Matrix m1 = new Matrix(new double[][]{{0,0,0}, {1,2,3}});
        Matrix expected = new Matrix(new double[][]{{2,2,2}, {3,4,5}});
        assertArrayEquals(m1.add(2).asArray(),expected.asArray());
    }

    @org.junit.jupiter.api.Test
    void testSub() {
        Matrix m1 = new Matrix(new double[][]{{1, 1}, {1, 1}});
        Matrix expected = m1.sub(1);
        assertEquals(0.0,expected.frobenius());
    }

    @org.junit.jupiter.api.Test
    void testMul() {
        Matrix m1 = new Matrix(new double[][]{{1,3},{1,5}});
        Matrix expected = m1.mul(0);
        assertEquals(0.0,expected.frobenius());
    }

    @org.junit.jupiter.api.Test
    void testDiv() throws Exception {
        Matrix m1 = new Matrix(new double[][]{{2,2},{4,4}});
        Matrix expected = m1.div(2);
        Matrix m2 = new Matrix(new double[][]{{1,1},{2,2}});
        assertArrayEquals(m2.asArray(),expected.asArray());
    }

    @org.junit.jupiter.api.Test
    void dot() throws Exception {
        Matrix m1 = new Matrix(new double[][]{{1,1},{2,2}});
        Matrix m2 = new Matrix(new double[][]{{1,2},{3,4}});
        Matrix expected = new Matrix(new double[][]{{4,6},{8,12}});
        assertArrayEquals(m1.dot(m2).asArray(),expected.asArray());
    }

    @org.junit.jupiter.api.Test
    void frobenius() {
        Matrix m1 = new Matrix(new double[][]{{2,2},{2,2}});
        double result = 4.0;
        assertEquals(result,m1.frobenius());
    }

    @org.junit.jupiter.api.Test
    void random() throws Exception {
        Matrix rand = Matrix.random(2,2);
        int exresult = 4;
        assertEquals(4,rand.rows*rand.cols);
    }

    @org.junit.jupiter.api.Test
    void eye() {
        Matrix ee = Matrix.eye(4);
        assertArrayEquals(ee.asArray(),ee.getTransposition().asArray());
        assertEquals(2.0,ee.frobenius());

    }

    @org.junit.jupiter.api.Test
    void getTransposition() {
        Matrix m1 = new Matrix(new double[][]{{1,3},{4,6}});
        Matrix expected = new Matrix(new double[][]{{1,4},{3,6}});
        assertArrayEquals(expected.asArray(),m1.getTransposition().asArray());
    }
}