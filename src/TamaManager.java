import java.util.ArrayList;
import java.util.Random;

public class TamaManager {
    private MyFrame myframe;
    private Tamagochi tama;
    private ArrayList<Poop> poops;
    private ArrayList<Tombstone> tombstones;
    private SatietyBar satietyBar;
    private FatigueBar fatigueBar;

    public TamaManager(MyFrame myframe){
        this.tama = null;
        poops = new ArrayList<Poop>();
        tombstones = new ArrayList<Tombstone>();
        satietyBar = null;
        fatigueBar = null;
        this.myframe = myframe;
    }
    public void createTama(String nickname){
        int x=200, y=310, size=110;
        tama = new Tamagochi(x, y, size, "src/img/tamagochiImg1.png", nickname);
        satietyBar = new SatietyBar(350, 100, 110, "src/img/Satiety"+tama.getSatiety()+".png");
        fatigueBar = new FatigueBar(350, 130, 110, "src/img/Fatigue"+tama.getFatigue()+".png");
    }
    public void feed(){
        Random random= new Random();
        
        // 만약 포만감이 10 이상인 경우, 일정 확률로 Tamagochi의 dieByEat() 메소드 호출. 즉 배부른 상태에서 밥을 먹이면 일정확률로 죽음.
        if(tama.getSatiety() > 9){
            switch(tama.getLevel()){
            	case 1: tama.setImgIcon("src/img/tama1_full.png");
            	break;
            	case 2: tama.setImgIcon("src/img/tama2_full.png");
            	break;
            	case 3: tama.setImgIcon("src/img/tama3_full.png");
            	break;
            	case 4: tama.setImgIcon("src/img/tama4_full.png");
            	break;
            }
            if(Math.random() < 0.4){
                tama.setImgIcon("src/img/tamaGhostImg.png");
                tombstones.add(tama.dieByEat("배부른 상태에서 먹다가 체해서(운없어서) 죽음", tombstones.size()));
                myframe.gameOver("배부른 상태에서 먹다가 체해서(운없어서) 죽음");
            }
        }
        else if(tama.getSatiety() > 3 && tama.getSatiety() <= 10) {
            switch(tama.getLevel()){
        	case 1: tama.setImgIcon("src/img/tamagochiImg1.png");
        	break;
        	case 2: tama.setImgIcon("src/img/tamagochiImg2.png");
        	break;
        	case 3: tama.setImgIcon("src/img/tamagochiImg3.png");
        	break;
        	case 4: tama.setImgIcon("src/img/tamagochiImg4.png");
        	break;
        	case 5: tama.setImgIcon("src/img/tamagochiImg5.png");
        	break;
            }
        }
        
        //밥 먹임. 랜덤하게 1~3만큼 포만감 증가
        tama.setSatiety(tama.getSatiety()+random.nextInt(3)+1);
        satietyBar.setImgIcon("src/img/Satiety"+tama.getSatiety()+".png");
        // 만약 포만감이 15를 초과했을 경우, Tamagochi의 dieByEat() 메소드 호출
        if(tama.getSatiety() >= 15){
            tama.setImgIcon("src/img/tamaGhostImg.png");
            tombstones.add(tama.dieByEat("죽을때까지 먹다가 배터져 죽음", tombstones.size()));
            myframe.gameOver("죽을때까지 먹다가 배터져 죽음");
        }
    }
    
    public void sleep(int fatigueReduction){
        tama.setFatigue(tama.getFatigue() - fatigueReduction);
        
        if(myframe.isSleepButtonEnabled()) {
            switch(tama.getLevel()){
        	case 1: tama.setImgIcon("src/img/tama1_sleeping.png");
        	break;
        	case 2: tama.setImgIcon("src/img/tama2_sleeping.png");
        	break;
        	case 3: tama.setImgIcon("src/img/tama3_sleeping.png");
        	break;
        	case 4: tama.setImgIcon("src/img/tama4_sleeping.png");
        	break;
            }
        }
        
        if(tama.getFatigue() < 0){
            tama.setFatigue(0);
        }
        fatigueBar.setImgIcon("src/img/Fatigue"+tama.getFatigue()+".png");
    }
    public void clean(){
        // poop를 모두 제거
        poops.clear();
    }
    //move에서 움직임을 url로 변경해서 url을 통한 변경을 위해 setImgURL을 거친 이미지 변경 
    public void levelUp(){
        tama.setLevel(tama.getLevel()+1);
        switch (tama.getLevel()){
            case 2:
                tama.setImgURL("src/img/tamagochiImg2.png");
                break;
            case 3:
                tama.setImgURL("src/img/tamagochiImg3.png");
                break;
            case 4:
                tama.setImgURL("src/img/tamagochiImg4.png");
                break;
            case 5:
                tama.setImgURL("src/img/tamagochiImg5.png");
                break;
            default:
                tama.setImgURL("src/img/tamagochiImg1.png");
                break;
        }
        if(tama.getLevel()==6){
            myframe.gameClear();
        }
    }
    public void createPoop(){
        // poop 생성
    	
        Random random = new Random();
        int x, y;
        int size = 50;

        if (poops.isEmpty()) {
            // 첫 번째 똥은 타마고치 머리 부근에 위치
            x = 230; // 타마고치의 너비 범위 내에서 랜덤
            y = 280; // 타마고치의 y 좌표
        } else {
            // 그 이후의 똥은 랜덤 위치
            do {
                x = random.nextInt(350) + 50;
                y = random.nextInt(80) + 330;
            } while (x < 310 && x + size > 200 && y < 420 && y + size > 310); // 타마고치와 겹치지 않도록 함
        }

        Poop poop = new Poop(x, y, size, "src/img/poopImg.png");
        poops.add(poop);
        
        // 만약 poop가 10개라면, Tamagochi의 dieByPoop() 메소드 호출. 즉 poop이 10개가 되면 죽음.
        if(poops.size() >= 10){
            tombstones.add(tama.dieByPoop("똥독 올라 죽음", tombstones.size()));
            tama.setImgIcon("src/img/tamaGhostImg.png");
            myframe.gameOver("똥독 올라 죽음");
        }
    }
    
    public void move() {
    	if(tama.getSatiety() >= 5 && tama.getSatiety() <= 10 && tama.getFatigue() <= 10 && myframe.isSleepButtonEnabled()) {
        	switch(tama.getLevel()) {
        	case 1:
        		if(tama.getImgURL()=="src/img/tamagochiImg1.png") {
        			tama.setImgURL("src/img/tamagochiImg12.png");
        		}else if(getTama().getImgURL()=="src/img/tamagochiImg12.png") {
        			tama.setImgURL("src/img/tamagochiImg1.png");
        		}
        		tama.setImgIcon(tama.getImgURL());
        		break;
        	case 2:
        		if(tama.getImgURL()=="src/img/tamagochiImg2.png") {
        			tama.setImgURL("src/img/tamagochiImg22.png");
        		}else if(tama.getImgURL()=="src/img/tamagochiImg22.png") {
        			tama.setImgURL("src/img/tamagochiImg2.png");
        		}
        		tama.setImgIcon(tama.getImgURL());
        		break;
        	case 3:
        		if(tama.getImgURL()=="src/img/tamagochiImg3.png") {
        			tama.setImgURL("src/img/tamagochiImg32.png");
        		}else if(tama.getImgURL()=="src/img/tamagochiImg32.png") {
        			tama.setImgURL("src/img/tamagochiImg3.png");
        		}
        		tama.setImgIcon(tama.getImgURL());
        		break;
        	case 4:
        		if(tama.getImgURL()=="src/img/tamagochiImg4.png") {
        			tama.setImgURL("src/img/tamagochiImg42.png");
        		}else if(tama.getImgURL()=="src/img/tamagochiImg42.png") {
        			tama.setImgURL("src/img/tamagochiImg4.png");
        		}
        		tama.setImgIcon(tama.getImgURL());
        		break;
        	case 5:
        		if(tama.getImgURL()=="src/img/tamagochiImg5.png") {
        			tama.setImgURL("src/img/tamagochiImg52.png");
        		}else if(tama.getImgURL()=="src/img/tamagochiImg52.png") {
        			tama.setImgURL("src/img/tamagochiImg5.png");
        		}
        		tama.setImgIcon(tama.getImgURL());
        		break;  
        	}
    	}
    }
    
    public void gettingHungry(){
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '10초'마다 포만감을 1씩 감소시킨다.
        // 만약 포만감이 0이 되면, Tamagochi의 dieByEat() 메소드 호출. 즉 배고픔이 0이 되면 죽음.
        tama.setSatiety(tama.getSatiety()-1);
        satietyBar.setImgIcon("src/img/Satiety"+tama.getSatiety()+".png");
        if (myframe.isSleepButtonEnabled()) {
            if(tama.getSatiety() <= 4) {
                switch(tama.getLevel()){
                    case 1: tama.setImgIcon("src/img/tama1_hungry.png");
                    break;
                    case 2: tama.setImgIcon("src/img/tama2_hungry.png");
                    break;
                    case 3: tama.setImgIcon("src/img/tama3_hungry.png");
                    break;
                    case 4: tama.setImgIcon("src/img/tama4_hungry.png");
                    break;
                }
            }
            if(tama.getSatiety() > 3 && tama.getSatiety() <= 10) {
                switch(tama.getLevel()){
               case 1: tama.setImgIcon("src/img/tamagochiImg1.png");
               break;
               case 2: tama.setImgIcon("src/img/tamagochiImg2.png");
               break;
               case 3: tama.setImgIcon("src/img/tamagochiImg3.png");
               break;
               case 4: tama.setImgIcon("src/img/tamagochiImg4.png");
               break;
               case 5: tama.setImgIcon("src/img/tamagochiImg5.png");
               break;
                }
            }
        }
        
        if(tama.getSatiety() <= 0){
            tama.setImgIcon("src/img/tamaGhostImg.png");
            tombstones.add(tama.dieByEat("배고파서 죽음", tombstones.size()));
            myframe.gameOver("배고파서 죽음");
        }
    }
    
    public void gettingSleepy() {
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '20초'마다 피로도를 1씩 증가시킨다.
        // 만약 피로도가 10이 넘어가면, 일정 확률로 Tamagochi의 dieBySleep() 메소드 호출.
        // 피로도가 15에 도달하면 그냥 dieBySleep() 호출. 즉 피로도가 15가 되면 죽음.
        tama.setFatigue(tama.getFatigue() + 1);
        fatigueBar.setImgIcon("src/img/Fatigue" + tama.getFatigue() + ".png");
        if (tama.getFatigue() >= 11) {
            switch(tama.getLevel()){
        	case 1: tama.setImgIcon("src/img/tama1_sleepy.png");
        	break;
        	case 2: tama.setImgIcon("src/img/tama2_sleepy.png");
        	break;
        	case 3: tama.setImgIcon("src/img/tama3_sleepy.png");
        	break;
        	case 4: tama.setImgIcon("src/img/tama4_sleepy.png");
        	break;
            }
            if (tama.getFatigue() == 15) {
                tama.setImgIcon("src/img/tamaGhostImg.png");
                tombstones.add(tama.dieBySleep("피곤에 찌들어 죽음", tombstones.size()));
                myframe.gameOver("피곤에 찌들어 죽음");
            } else if (Math.random() < 0.4) {
                tama.setImgIcon("src/img/tamaGhostImg.png");
                tombstones.add(tama.dieBySleep("피곤해서 죽음", tombstones.size()));
                myframe.gameOver("피곤해서 죽음");
            }
        }
    }
    
    public Tamagochi getTama(){
        return tama;
    }
    public ArrayList<Poop> getPoops(){
        return poops;
    }
    public ArrayList<Tombstone> getTombstones(){
        return tombstones;
    }
    public SatietyBar getSatietyBar(){
        return satietyBar;
    }
    public FatigueBar getFatigueBar(){
        return fatigueBar;
    }
}
