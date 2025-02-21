
public class Main {
    public static void main(String[] args){

        GeneralRunway generalRunway1=new GeneralRunway();
        GeneralRunway generalRunway2=new GeneralRunway();
        DepartureRunway departureRunway=new DepartureRunway();
        StatisticManager statisticManager=new StatisticManager(generalRunway1,generalRunway2,departureRunway);
        EmergencyManager emergencyManager=new EmergencyManager(generalRunway1,generalRunway2,departureRunway,statisticManager);
        AppendingManager appendingManager=new AppendingManager(generalRunway1,generalRunway2,departureRunway,statisticManager);


        generalRunway1.setStatisticManager(statisticManager);
        generalRunway2.setStatisticManager(statisticManager);
        departureRunway.setStatisticManager(statisticManager);


        try{
            Thread.sleep(50);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        statisticManager.initGUI();

        int MAXTIME=100;
        for(int time=0;time<MAXTIME;time++){
            appendingManager.createDeparturePlanes(time,6*MAXTIME);
            appendingManager.createLandingPlanes(time,6*MAXTIME);
            statisticManager.setTimeValueLabel(time);
            System.out.println(time);

            statisticManager.setSizeOfQueues();


            try{
                Thread.sleep(0);
            }catch(InterruptedException e){
                e.printStackTrace();
            }



            emergencyManager.checkEmergency(time); //비상 착륙에도 outTime 설정해야햄

            Plane planeFromR1=generalRunway1.dequeue(time);
            Plane planeFromR2=generalRunway2.dequeue(time);
            Plane planeFromR3=departureRunway.dequeue(time);
            Plane[] planes={planeFromR1,planeFromR2,planeFromR3};

            statisticManager.setPlaneId(planes);
            statisticManager.setAverages();
            statisticManager.setGUIofEmerAndAcci();

            System.out.println();

        }
        statisticManager.emergencyListInfo();
    }
}
