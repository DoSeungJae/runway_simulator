import javax.swing.*;
import java.util.*;

public class StatisticManager {
    private GeneralRunway generalRunway1;
    private GeneralRunway generalRunway2;
    private DepartureRunway departureRunway;
    private List<Integer> totalEmergencyPlanes=new ArrayList<>();
    private int totalAccidentCnt;
    private int totalLandingCnt;
    private int totalDepartureCnt;

    private int totalWaitingForLanding;
    private int totalWaitingForDeparture;
    private int totalLeftFlightableTime;
    private Plane r1;
    private Plane r2;
    private Plane r3;

    public void addTotalEmergencyList(int id){
        totalEmergencyPlanes.add(id);
    }
    public Boolean isContainEmergencyId(int id){
        return totalEmergencyPlanes.contains(id);
    }
    public void addTotalLandingCnt(int landingCnt){
        totalLandingCnt+=landingCnt;
    }
    public void addTotalDepartureCnt(int departureCnt){
        totalDepartureCnt+=departureCnt;
    }

    public void addTotalWaitingForDeparture(Plane plane){
        totalWaitingForDeparture+=(plane.getOutTime()-plane.getCreatedTime());
    }
    public void addTotalWaitingForLanding(Plane plane){
        totalWaitingForLanding+=(plane.getOutTime()-plane.getCreatedTime());
    }
    public void addTotalLeftFlightableTime(Plane plane,int curTime){
        if(curTime==-1){
            totalLeftFlightableTime+=0;
            return ;
        }
        totalLeftFlightableTime+=(plane.getCreatedTime()+plane.getFlightableTime()-curTime);
    }
    public void addTotalAccidentCnt(int plus){
        totalAccidentCnt+=plus;
    }

    public void emergencyListInfo(){
        Iterator<Integer> it=totalEmergencyPlanes.iterator();
        while(it.hasNext()){
            System.out.printf(it.next()+", ");
        }
        System.out.printf(" \n(emergency planes) size : "+totalEmergencyPlanes.size()+"\n");
        System.out.println("total landings: "+totalLandingCnt+", total departures :"+totalDepartureCnt);
        System.out.println("accident(s) :"+totalAccidentCnt);
        System.out.println("average waiting time for departure : "+(double)totalWaitingForDeparture/totalDepartureCnt);
        System.out.println("average waiting time for landing : "+(double)totalWaitingForLanding/totalLandingCnt);
        System.out.println("average of Left Flightable Time at Landing :"+(double)totalLeftFlightableTime/totalLandingCnt);
    }
    public void setPlaneId(Plane[] planes){
        int runway=0;
        for(int i=1;i<=3;i++){
            Plane plane=planes[i-1];
            if(plane==null){continue;}

            if(i==1){runway=1;}
            else if(i==2){runway=2;}
            else if(i==3){runway=3;}

            if(plane.getId()%2==0){//짝수 id -> 착륙
                setRunwayFrom(plane.getId(),runway,0);//0이면 착륙,1이면 이륙
            }
            else{//홀수 id -> 이륙
                setRunwayFrom(plane.getId(),runway,1);
            }
        }

    }
    public void setRunwayFrom(int planeId,int runwayId,int mode){//0이면 착륙, 1이면 이륙
        if(runwayId==1){
            r1s[mode].setText(String.valueOf(planeId));
        }
        else if(runwayId==2){
            r2s[mode].setText(String.valueOf(planeId));
        }
        else{ //runwayId==3
            r3Dep.setText(String.valueOf(planeId));
        }

    }




    JFrame frame=new JFrame("AirLine Simulator");
    JLabel timeValueLabel = new JLabel(String.valueOf(0));
    JLabel q1Value=new JLabel(String.valueOf(0));
    JLabel q2Value=new JLabel(String.valueOf(0));
    JLabel q3Value=new JLabel(String.valueOf(0));
    JLabel q4Value=new JLabel(String.valueOf(0));

    JLabel q5Value=new JLabel(String.valueOf(0));
    JLabel q6Value=new JLabel(String.valueOf(0));
    JLabel q7Value=new JLabel(String.valueOf(0));

    JLabel q11Value=new JLabel(String.valueOf("id"));
    JLabel q22Value=new JLabel(String.valueOf("id"));
    JLabel q33Value=new JLabel(String.valueOf("id"));
    JLabel q44Value=new JLabel(String.valueOf("id"));

    JLabel q55Value=new JLabel(String.valueOf("id"));
    JLabel q66Value=new JLabel(String.valueOf("id"));
    JLabel q77Value=new JLabel(String.valueOf("id"));


    JLabel r1Dep=new JLabel("id");
    JLabel r1Lan=new JLabel("id");
    JLabel[] r1s={r1Lan,r1Dep};


    JLabel r2Dep=new JLabel("id");
    JLabel r2Lan=new JLabel("id");
    JLabel[] r2s={r2Lan,r2Dep};

    JLabel r3Dep=new JLabel("id");


    JLabel r1Emer=new JLabel("id");
    JLabel r2Emer=new JLabel("id");
    JLabel r3Emer=new JLabel("id");
    JLabel[] emers={r3Emer,r1Emer,r2Emer};


    JLabel waitingTimeForDepartureValue=new JLabel("0");
    JLabel waitingTimeForLandingValue=new JLabel("0");
    JLabel timeLeftValue=new JLabel("0");


    JLabel[] qValues={q1Value,q2Value,q3Value,q4Value,q5Value,q6Value,q7Value};

    JLabel[] qIdValues={q11Value,q22Value,q33Value,q44Value,q55Value,q66Value,q77Value};

    JLabel accidentValue=new JLabel("0");
    JLabel emergencyCntValue=new JLabel("0");

    public StatisticManager(){}
    public StatisticManager(GeneralRunway generalRunway1, GeneralRunway generalRunway2,DepartureRunway departureRunway){
        this.generalRunway1=generalRunway1;
        this.generalRunway2=generalRunway2;
        this.departureRunway=departureRunway;
    }


    public void initGUI(){
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(null);

        JLabel timeLabel = new JLabel("시간 : ");  // 레이블 생성
        timeLabel.setBounds(10, 10, 50, 20);  // 레이블의 위치와 크기 지정: x=50, y=50, width=100, height=20
        frame.add(timeLabel);

        timeValueLabel.setBounds(50, 10, 100, 20);
        frame.add(timeValueLabel);
        setTimeValueLabel(-1);


        JLabel Qsitu=new JLabel("대기 큐 길이");
        Qsitu.setBounds(50,50,110,20);
        frame.add(Qsitu);


        JLabel runway1=new JLabel("활주로 1");
        runway1.setBounds(100,70,50,20);
        frame.add(runway1);

        JLabel runway2=new JLabel("활주로 2");
        runway2.setBounds(150+60,70,50,20);
        frame.add(runway2);

        JLabel runway3=new JLabel("활주로 3");
        runway3.setBounds(170+50+100,70,50,20);
        frame.add(runway3);

        JLabel landing=new JLabel("착륙");
        landing.setBounds(50,90+10,50,20);
        frame.add(landing);

        JLabel q1=new JLabel("Q1:");
        q1.setBounds(100,100,50,20);
        frame.add(q1);

        q1Value.setBounds(120,100,50,20);
        frame.add(q1Value);

        JLabel q2=new JLabel("Q2:");
        q2.setBounds(150,100,50,20);
        frame.add(q2);

        q2Value.setBounds(170,100,50,20);
        frame.add(q2Value);

        JLabel q3=new JLabel("Q3:");
        q3.setBounds(210,100,50,20);
        frame.add(q3);

        q3Value.setBounds(230,100,50,20);
        frame.add(q3Value);

        JLabel q4=new JLabel("Q4:");
        q4.setBounds(260,100,50,20);
        frame.add(q4);

        q4Value.setBounds(280,100,50,20);
        frame.add(q4Value);


        JLabel departure=new JLabel("이륙");
        departure.setBounds(50,130,50,20);
        frame.add(departure);


        JLabel q5=new JLabel("Q5:");
        q5.setBounds(100,130,50,20);
        frame.add(q5);

        q5Value.setBounds(120,130,50,20);
        frame.add(q5Value);

        JLabel q6=new JLabel("Q6:");
        q6.setBounds(210,130,50,20);
        frame.add(q6);

        q6Value.setBounds(230,130,50,20);
        frame.add(q6Value);

        JLabel q7=new JLabel("Q7:");
        q7.setBounds(320,130,50,20);
        frame.add(q7);

        q7Value.setBounds(340,130,50,20);
        frame.add(q7Value);

        JLabel QArrive=new JLabel("큐 도착 현황");
        QArrive.setBounds(50,170,100,20);
        frame.add(QArrive);

        JLabel runway11=new JLabel("활주로 1");
        runway11.setBounds(100,190,50,20);
        frame.add(runway11);

        JLabel runway22=new JLabel("활주로 2");
        runway22.setBounds(210,190,50,20);
        frame.add(runway22);

        JLabel runway33=new JLabel("활주로 3");
        runway33.setBounds(320,190,50,20);
        frame.add(runway33);
//1
        JLabel landing_=new JLabel("착륙");
        landing_.setBounds(50,220,50,20);
        frame.add(landing_);


        JLabel q11=new JLabel("Q1:");
        q11.setBounds(100,220,50,20);
        frame.add(q11);

        q11Value.setBounds(120,220,50,20);
        frame.add(q11Value);

        JLabel q22=new JLabel("Q2:");
        q22.setBounds(150,220,50,20);
        frame.add(q22);

        q22Value.setBounds(170,220,50,20);
        frame.add(q22Value);

        JLabel q33=new JLabel("Q3:");
        q33.setBounds(210,220,50,20);
        frame.add(q33);

        q33Value.setBounds(230,220,50,20);
        frame.add(q33Value);

        JLabel q44=new JLabel("Q4:");
        q44.setBounds(260,220,50,20);
        frame.add(q44);

        q44Value.setBounds(280,220,50,20);
        frame.add(q44Value);



        JLabel departure_=new JLabel("이륙");
        departure_.setBounds(50,250,50,20);
        frame.add(departure_);

        JLabel q55=new JLabel("Q5:");
        q55.setBounds(100,250,50,20);
        frame.add(q55);

        q55Value.setBounds(120,250,50,20);
        frame.add(q55Value);


        JLabel q66=new JLabel("Q6:");
        q66.setBounds(210,250,50,20);
        frame.add(q66);

        q66Value.setBounds(230,250,50,20);
        frame.add(q66Value);


        JLabel q77=new JLabel("Q7:");
        q77.setBounds(320,250,50,20);
        frame.add(q77);

        q77Value.setBounds(340,250,50,20);
        frame.add(q77Value);


        JLabel airLineSituation=new JLabel("활주로 상황");
        airLineSituation.setBounds(50,310,100,20);
        frame.add(airLineSituation);

        JLabel runway111=new JLabel("활주로 1");
        runway111.setBounds(100,340,50,20);
        frame.add(runway111);
//1
        JLabel runway222=new JLabel("활주로 2");
        runway222.setBounds(210,340,50,20);
        frame.add(runway222);

        JLabel runway333=new JLabel("활주로 3");
        runway333.setBounds(320,340,50,20);
        frame.add(runway333);

        JLabel averageWaitingTime=new JLabel("평균 대기 시간");
        averageWaitingTime.setBounds(430,340,100,20);
        frame.add(averageWaitingTime);

        waitingTimeForDepartureValue.setBounds(466,370,60,20);
        frame.add(waitingTimeForDepartureValue);

        waitingTimeForLandingValue.setBounds(466,390,60,20);
        frame.add(waitingTimeForLandingValue);


        JLabel averageTimeLeftAtLanding=new JLabel("착륙 시 남아있는 제한 시간의 평균");
        averageTimeLeftAtLanding.setBounds(540,340,200,20);
        frame.add(averageTimeLeftAtLanding);

        timeLeftValue.setBounds(630,360,70,20);
        frame.add(timeLeftValue);

        JLabel departure__=new JLabel("이륙");
        departure__.setBounds(50,370,50,20);
        frame.add(departure__);

        JLabel landing__=new JLabel("착륙");
        landing__.setBounds(50,390,50,20);
        frame.add(landing__);

        JLabel emergencyLanding=new JLabel("비상 착륙");
        emergencyLanding.setBounds(38,420,80,20);
        frame.add(emergencyLanding);


        r1Dep.setBounds(110,370,50,20);
        frame.add(r1Dep);


        r2Dep.setBounds(220,370,50,20);
        frame.add(r2Dep);

        r3Dep.setBounds(330,370,50,20);
        frame.add(r3Dep);

        //1

        r1Lan.setBounds(110,390,50,20);
        frame.add(r1Lan);


        r2Lan.setBounds(220,390,50,20);
        frame.add(r2Lan);

        r1Emer.setBounds(110,420,50,20);
        frame.add(r1Emer);

        r2Emer.setBounds(220,420,50,20);
        frame.add(r2Emer);

        r3Emer.setBounds(330,420,50,20);
        frame.add(r3Emer);

        JLabel accident=new JLabel("사고");
        accident.setBounds(430,50,70,20);
        frame.add(accident);

        accidentValue.setBounds(430,70,70,20);
        frame.add(accidentValue);

        JLabel emergencyCnt=new JLabel("긴급 착륙");
        emergencyCnt.setBounds(540,50,70,20);
        frame.add(emergencyCnt);

        emergencyCntValue.setBounds(540,70,70,20);
        frame.add(emergencyCntValue);





        frame.setVisible(true);


    }




    public void setTimeValueLabel(int time){
        timeValueLabel.setText(String.valueOf(time));
    }

    public void setQValue(int totalQueueId,int value){
        qValues[totalQueueId-1].setText(String.valueOf(value));
    }

    public void setQIdValue(int planeId, int totalQueueId){qIdValues[totalQueueId-1].setText(String.valueOf(planeId)); }


    public void setSizeOfQueues(){ //return type : List<Integer>
        int sizeQ1=generalRunway1.getQueueSize(1);
        setQValue(1,sizeQ1);

        int sizeQ2=generalRunway1.getQueueSize(2);
        setQValue(2,sizeQ2);

        int sizeQ3=generalRunway2.getQueueSize(1);
        setQValue(3,sizeQ3);

        int sizeQ4=generalRunway2.getQueueSize(2);
        setQValue(4,sizeQ4);


        int sizeQ5=generalRunway1.getQueueSize();
        setQValue(5,sizeQ5);

        int sizeQ6=generalRunway2.getQueueSize();
        setQValue(6,sizeQ6);

        int sizeQ7=departureRunway.getQueueSize();
        setQValue(7,sizeQ7);

    }
    public void setAverages(){

        double average;
        average=(double)totalWaitingForDeparture/totalDepartureCnt;
        average=Math.round(average*100.0)/100.0;
        waitingTimeForDepartureValue.setText(String.valueOf(average));

        average=(double)totalWaitingForLanding/totalLandingCnt;
        average=Math.round(average*100.0)/100.0;
        waitingTimeForLandingValue.setText(String.valueOf(average));

        average=(double)totalLeftFlightableTime/totalLandingCnt;
        average=Math.round(average*100.0)/100.0;
        timeLeftValue.setText(String.valueOf(average));

    }

    public void setGUIofEmergencies(int runwayId,int emergencyId){
        if(runwayId==3){runwayId=0;}
        emers[runwayId].setText(String.valueOf(emergencyId));

        if(runwayId==0){
            r3Dep.setText("!!!!!!");
        }
        if(runwayId==1){
            r1Lan.setText("!!!!!!");
        }
        if(runwayId==2){
            r2Lan.setText("!!!!!!");
            //+String.valueOf(emergencyId)
        }

    }

    public void setGUIofEmerAndAcci(){
        double percent;
        percent=(double)totalEmergencyPlanes.size()/totalLandingCnt;
        percent=Math.round(percent*100.0);
        emergencyCntValue.setText(String.valueOf(totalEmergencyPlanes.size())+"("+String.valueOf(percent)+"%)");

        percent=(double)totalAccidentCnt/totalLandingCnt;
        percent=Math.round(percent*100.0);
        accidentValue.setText(String.valueOf(totalAccidentCnt)+"("+String.valueOf(percent)+"%)");
    }

}
