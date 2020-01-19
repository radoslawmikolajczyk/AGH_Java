package main;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mean {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<>(100);
    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        initArray(128_000_000);
        for(int cnt:new int[]{1,2,4,8,16,32,64,128}){
            parallelMean(cnt);
        }
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end){
            this.start = start;
            this.end=end;
        }
        public void run(){
            // ??? liczymy średnią
            double suma = 0;
            for (int a = start; a < end; a++){
                suma += array[a];
            }
            mean = suma/(end-start);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
        }
    }

    static void parallelMean(int cnt) throws InterruptedException {
        // utwórz tablicę wątków
        MeanCalc[] threads =new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        // załóż, że array.length dzieli się przez cnt)

        int div = array.length/cnt;
        for (int b = 0; b < cnt; b++){
            threads[b] = new MeanCalc(b*div,div*(b+1));
        }
        double t1 = System.nanoTime()/1e6;
        //uruchom wątki
        for (MeanCalc mc: threads){
            mc.start();
        }
        double t2 = System.nanoTime()/1e6;
        // czekaj na ich zakończenie używając metody ''join''
        /*
        for(MeanCalc mc:threads) {
            mc.join();
        }
        */

        double mean = 0;
        double sum2 = 0;
        for (int c = 0; c < threads.length; c++){
            sum2 += results.take();
        }
        // oblicz średnią ze średnich
        /*for ( MeanCalc mc : threads){
            sum2 += mc.mean;
        }*/
        mean = sum2/threads.length;
        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }
}
