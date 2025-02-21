import java.util.*;
public class AppendingManager {
    private StatisticManager statisticManager;
    private GeneralRunway generalRunway1;
    private  GeneralRunway generalRunway2;
    private DepartureRunway departureRunway;
    private Set<Integer> set=new HashSet<>();
    private int queueId;

    public AppendingManager(GeneralRunway generalRunway1, GeneralRunway generalRunway2 , DepartureRunway departureRunway,
                            StatisticManager statisticManager){
        this.generalRunway1=generalRunway1;
        this.generalRunway2=generalRunway2;
        this.departureRunway=departureRunway;
        this.statisticManager=statisticManager;
    }
    public void enqueueLandingQueue(Plane newPlane,int id){
        GeneralRunway[] runways={generalRunway1,generalRunway2,generalRunway1,generalRunway2};
        int queueId=id<=2?1:2;
        runways[id-1].enqueue(queueId,newPlane);
    }
    public void enqueueDepartureQueue(Plane newPlane, int queueId){
        if(queueId==5){
            generalRunway1.enqueue(newPlane);
        }
        if(queueId==6){
            generalRunway2.enqueue(newPlane);
        }
        if(queueId==7){
            departureRunway.enqueue(newPlane);
        }
    }
    public int getOptimalLandingQueue() {
        int min=Integer.MAX_VALUE;
        int sizeOfQueue=0;
        int optimalId=0;

        GeneralRunway[] runways={generalRunway1,generalRunway2,generalRunway1,generalRunway2};

        for (int id=1;id<=4;id++) {
            sizeOfQueue=runways[id-1].getQueueSize(id<=2?1:2);

            if (sizeOfQueue<min) {
                min=sizeOfQueue;
                optimalId=id;
            }
        }
        return optimalId;
    }

    public int getOptimalDepartureQueueWithWeight(){
        int min=Integer.MAX_VALUE;
        int sizeOfQueue=0;
        int optimalId=0;

        int r12weight=3;
        //int r12weight=1;

        for(int i=1;i<=3;i++){
            if(i==1){
                sizeOfQueue=departureRunway.getQueueSize();
                queueId=7;
            }
            else if(i==2){
                sizeOfQueue=generalRunway1.getQueueSize();
                sizeOfQueue*=r12weight;
                queueId=5;
            }
            else if(i==3) {
                sizeOfQueue = generalRunway2.getQueueSize();
                queueId = 6;
                sizeOfQueue*=r12weight;
            }
            if(sizeOfQueue<min){
                min=sizeOfQueue;
                optimalId=queueId;
            }
        }
        return optimalId;
    }

    public void createLandingPlanes(int time,int MAXID){
        int num=(int)(Math.random()*4); //0~3
        for(int i=0;i<num;i++){
            int id=(int)(Math.random()*((0.5*MAXID))+1)*2; //0~MAXID 사이의 짝수 ID 생성
            if(!set.contains(id)){
                set.add(id);
                int fuelRatio=(int)(Math.random()*5)+1; //1~5
                int fuelInit=(int)(Math.random() * 41)+10;//10~50
                Plane newPlane=new Plane(id,fuelInit,fuelRatio,time);
                int queueId=getOptimalLandingQueue();
                enqueueLandingQueue(newPlane,queueId);
                statisticManager.setQIdValue(newPlane.getId(),queueId);
            }
            else{
                i-=1;
            }
        }
    }

    public void createDeparturePlanes(int time,int MAXID){
        int num=(int)(Math.random()*4); //0~3
        for(int i=0;i<num;i++){
            int id=(int)(Math.random()*((0.5*MAXID))+1)*2+1; //0~MAXID 사이의 홀수 ID 생성 ??
            if(!set.contains(id)){
                set.add(id);
                Plane newPlane=new Plane(id,time);
                int queueId=getOptimalDepartureQueueWithWeight();
                enqueueDepartureQueue(newPlane,queueId);
                statisticManager.setQIdValue(newPlane.getId(),queueId);
            }
            else{
                i-=1;
            }
        }
    }
}
