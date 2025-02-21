
public class Plane {
    private int id;
    private int fuelRatio;
    private int fuelInit;
    private int flightableTime;
    private int createdTime;
    private int outTime=0;

    public Plane(int id,int fuelInit,int fuelRatio, int createdTime){
        this.id=id;
        this.fuelInit=fuelInit;
        this.fuelRatio=fuelRatio;
        this.flightableTime=fuelInit/fuelRatio;
        this.createdTime=createdTime;
    }
    public Plane(int id,int createdTime){
        this.id=id;
        this.createdTime=createdTime;
    }

    public int getId(){
        return this.id;
    }

    public int getCreatedTime(){
        return this.createdTime;
    }

    public int getOutTime(){
        return this.outTime;
    }

    public void setOutTime(int outTime){this.outTime=outTime;}

    public int getFlightableTime(){
        return flightableTime;
    }

}
