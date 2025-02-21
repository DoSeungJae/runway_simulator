import java.util.LinkedList;
import java.util.Queue;

public class DepartureRunway {
    private StatisticManager statisticManager;
    private Queue<Plane> departureQueue=new LinkedList<>();
    private int emergencyId=-1;
    public DepartureRunway(){}
    public void setStatisticManager(StatisticManager statisticManager){
        this.statisticManager=statisticManager;
    }
    public int getEmergencyId(){return this.emergencyId;}
    public void setEmergencyId(int emergencyId){
        this.emergencyId=emergencyId;
    }
    public int getQueueSize(){
        return departureQueue.size();
    }
    public void enqueue(Plane newPlane){departureQueue.add(newPlane);}
    public Plane dequeue(int outTime){
        if(emergencyId!=-1){ //-1은 id가 지정되지 않은 상태 ; false
            //이미 EmergencyManager에서 비상상황이 처리되었으므로 비상상황을 초기화하는 것만 필요함.
            emergencyId=-1;
        }
        if(emergencyId==-1 &&!departureQueue.isEmpty()){
            Plane planeAfterDeparture=departureQueue.remove();
            planeAfterDeparture.setOutTime(outTime);
            statisticManager.addTotalDepartureCnt(1);
            statisticManager.addTotalWaitingForDeparture(planeAfterDeparture);
            return planeAfterDeparture;
        }
        return null;
    }

}
