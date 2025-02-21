import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GeneralRunway {

    private StatisticManager statisticManager;

    private Queue<Plane> landingQueue1=new LinkedList<>(); //1
    private Queue<Plane> landingQueue2=new LinkedList<>(); //2
    private Queue<Plane> departureQueue=new LinkedList<>();
    private Queue[] queues={departureQueue,landingQueue1,landingQueue2};

    private int emergencyId=-1;

    public int getEmergencyId(){return this.emergencyId;}
    public void setEmergencyId(int emergencyId){this.emergencyId=emergencyId;}


    public GeneralRunway(){}
    public void setStatisticManager(StatisticManager statisticManager){this.statisticManager=statisticManager;}

    public int getQueueSize(int queueId){
        int size=0;
        return queues[queueId].size();
    }
    public int getQueueSize(){
        return departureQueue.size();
    }

    public void enqueue(int queueId,Plane newPlane){
        queues[queueId].add(newPlane);
    }
    public void enqueue(Plane newPlane){departureQueue.add(newPlane);}
    public Plane dequeue(int time){

        double landingWeight=1.0/5;
        double departureWeight=1.0/4;

        //double landingWeight=1.0;
        //double departureWeight=1.0;

        //높은 순대로 dequeue, 우선순위가 같은 경우 큐 번호가 높은 것 부터
        double priority=0.0;
        double maxPri=Double.MIN_VALUE;
        int optimalId=0;
        Plane handledPlane=null;


        if(emergencyId!=-1){
            emergencyId=-1;
        }

        else{
            for(int i=1;i<=3;i++){
                if(i==1){//이륙
                    priority=(double)departureQueue.size()*departureWeight;
                }
                else if(i==2){//착륙2
                    priority=(double)landingQueue2.size()*landingWeight;
                }
                else if(i==3)//착륙1
                    priority=(double)landingQueue1.size()*landingWeight;
                if(priority>maxPri){
                    maxPri=priority;
                    optimalId=i;
                }
            }

            if(optimalId==1&&!departureQueue.isEmpty()){
                handledPlane=departureQueue.remove();
                statisticManager.addTotalDepartureCnt(1);
            }
            else if(optimalId==2&&!landingQueue2.isEmpty()){
                handledPlane=landingQueue2.remove();
                statisticManager.addTotalLandingCnt(1);
                statisticManager.addTotalLeftFlightableTime(handledPlane,time);
            }
            else if(optimalId==3&&!landingQueue1.isEmpty()){//i==3
                handledPlane=landingQueue1.remove();
                statisticManager.addTotalLandingCnt(1);
                statisticManager.addTotalLeftFlightableTime(handledPlane,time);
            }

            if(handledPlane!=null && handledPlane.getOutTime()==0){
                handledPlane.setOutTime(time);
                statisticManager.addTotalWaitingForLanding(handledPlane);
            }

            if(handledPlane!=null){
                if(statisticManager.isContainEmergencyId(handledPlane.getId())){
                    System.out.println("already handled at emergency");
                    return dequeue(time);
                }
            }

        }
        return handledPlane;
    }
    public Iterator<Plane> getQueueIterator(int queueId){
        return queues[queueId].iterator();
    }


}
