package main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ExternalPanelsAgent extends Thread{

    private final ElevatorCar elevatorCar;

    static class ExternalCall{
        private final int atFloor;
        private final boolean directionUp;

        ExternalCall(int atFloor,boolean directionUp){
            this.atFloor = atFloor;
            this.directionUp = directionUp;
        }
    }
    BlockingQueue<ExternalCall> input = new ArrayBlockingQueue<>(100);
    ExternalPanelsAgent(ElevatorCar elevatorCar){
        this.elevatorCar = elevatorCar;
    }

    public void run(){
        for(;;){
            ExternalCall ec = null;
            try {
                ec = input.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ignorujemy wezwanie na piętro, na którym winda się znajduje
            if(ec.atFloor==elevatorCar.getFloor())continue;
            // dodajemy do jednej z tablic zgłoszeń
            if(ec.directionUp){
                ElevatorStops.get().setLiftStopUp(ec.atFloor);
            }else{
                ElevatorStops.get().setLiftStopDown(ec.atFloor);
            }
        }
    }
}
