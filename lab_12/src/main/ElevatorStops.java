package main;

public class ElevatorStops {
    static final int FLOORS = 10;
    static final int MIN_FLOOR = 0;
    static final int MAX_FLOOR=FLOORS-1;
    boolean stopsDown[] = new boolean[FLOORS];
    boolean stopsUp[] = new boolean[FLOORS];

    public final static ElevatorStops INSTANCE = new ElevatorStops();

    private ElevatorStops(){}

    void setLiftStopUp(int floor){
        stopsUp[floor]=true;
    }
    void setLiftStopDown(int floor){
        stopsDown[floor]=true;
    }
    void clearStopUp(int floor){
        stopsUp[floor]=false;
    }
    void clearStopDown(int floor){
        stopsDown[floor]=false;
    }


    boolean hasStopAbove(int floor){
        for(int i=floor+1;i<MAX_FLOOR;i++){
            if(stopsUp[i] || stopsDown[i])return true;
        }
        return false;
    }
    boolean hasStopBelow(int floor){
        for(int i=floor-1;i>=MIN_FLOOR;i--){
            if(stopsUp[i] || stopsDown[i])return true;
        }
        return false;

    }

    int getMaxSetFloor(){
        for(int i=MAX_FLOOR-1;i>=0;i--){
            if(stopsUp[i]||stopsDown[i])return i;
        }
        return 0;
    }
    int getMinSetFloor(){
        for(int i=0;i<MAX_FLOOR;i++){
            if(stopsUp[i]||stopsDown[i])return i;
        }
        return 0;
    }

    boolean whileMovingDownSholudStopAt(int floor){
        return stopsDown[floor];
    }
    boolean whileMovingUpSholudStopAt(int floor){
        return stopsUp[floor];
    }
    static ElevatorStops get(){
        return INSTANCE;
    }
}
