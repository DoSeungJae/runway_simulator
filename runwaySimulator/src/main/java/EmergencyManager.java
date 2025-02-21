import java.util.*;

public class EmergencyManager {
    private GeneralRunway generalRunway1;
    private  GeneralRunway generalRunway2;
    private DepartureRunway departureRunway;
    private StatisticManager statisticManager;
    public EmergencyManager(GeneralRunway generalRunway1, GeneralRunway generalRunway2,DepartureRunway departureRunway,StatisticManager statisticManager){
        this.generalRunway1=generalRunway1;
        this.generalRunway2=generalRunway2;
        this.departureRunway=departureRunway;
        this.statisticManager=statisticManager;
    }
    public void checkEmergency(int curTime){
        Queue<Plane> emergencyQueue=new LinkedList<Plane>();

        Iterator<Plane> q1=generalRunway1.getQueueIterator(1);
        Iterator<Plane> q2=generalRunway1.getQueueIterator(2);
        Iterator<Plane> q3=generalRunway2.getQueueIterator(1);
        Iterator<Plane> q4=generalRunway2.getQueueIterator(2);

        @SuppressWarnings("unchecked")
        Iterator<Plane>[] iterators = (Iterator<Plane>[])new Iterator<?>[4];
        iterators[0] = q1;
        iterators[1] = q2;
        iterators[2] = q3;
        iterators[3] = q4;

        while((iterators[0].hasNext() || iterators[1].hasNext() || iterators[2].hasNext() || iterators[3].hasNext())){ //모든 큐를 다 조회할 때 까지
            //조회하지 않은 큐(아이터레이터)가 하나라도 있다면 while문 실행
            for(int i=1;i<5;i++){
                Iterator<Plane> it=iterators[i-1];
                if(it.hasNext()){
                    Plane plane=it.next();
                    if(plane.getCreatedTime()+plane.getFlightableTime()==curTime){
                        plane.setOutTime(curTime);
                        emergencyQueue.add(plane);
                    }
                }
            }
        }
        setRunwayForEmergency(emergencyQueue);
    }

    public void setRunwayForEmergency(Queue<Plane> emergencyQueue){
        int emergencyCnt=emergencyQueue.size();
        int emergencyId=-1;
        for(int i=0;i<3;i++){//활주로 3이 우선적으로 비상착륙 활주로로 지정되도록 작성

            if(i==0 && departureRunway.getEmergencyId()==-1 && emergencyCnt>0){
                Plane emergencyPlane=emergencyQueue.remove();
                emergencyId=emergencyPlane.getId();
                departureRunway.setEmergencyId(emergencyId);
                System.out.println("emergency landing at departureRunway");
                statisticManager.addTotalEmergencyList(emergencyId);
                statisticManager.addTotalWaitingForLanding(emergencyPlane);
                statisticManager.setGUIofEmergencies(3,emergencyId);
            }
            else if(i==1 && generalRunway1.getEmergencyId()==-1 && emergencyCnt>0 ){
                Plane emergencyPlane=emergencyQueue.remove();
                emergencyId=emergencyPlane.getId();
                generalRunway1.setEmergencyId(emergencyId);
                System.out.println("emergency landing at generalRunway1");
                statisticManager.addTotalEmergencyList(emergencyId);
                statisticManager.addTotalWaitingForLanding(emergencyPlane);
                statisticManager.setGUIofEmergencies(1,emergencyId);
            }
            else if(i==2 && generalRunway2.getEmergencyId()==-1 && emergencyCnt>0){
                Plane emergencyPlane=emergencyQueue.remove();
                emergencyId=emergencyPlane.getId();
                generalRunway2.setEmergencyId(emergencyId);
                System.out.println("emergency landing at generalRunway2");
                statisticManager.addTotalEmergencyList(emergencyId);
                statisticManager.addTotalWaitingForLanding(emergencyPlane);
                statisticManager.setGUIofEmergencies(2,emergencyId);
            }
            emergencyCnt-=1;

        }
        int accidents=emergencyQueue.size();
        if(accidents>0){//사고
            statisticManager.addTotalAccidentCnt(accidents);
            System.out.println("사고 발생 건수 : "+accidents);
        }

    }

}
