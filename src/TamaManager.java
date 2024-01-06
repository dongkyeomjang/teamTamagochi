
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class TamaManager {
    private Tamagochi tama;
    private ArrayList<Poop> poops;
    private ArrayList<Tombstone> tombstones;
    
    Random rand = new Random();
    
	public TamaManager(){
        this.tama = null;
        poops = new ArrayList<Poop>();
        tombstones = new ArrayList<Tombstone>();
    }
    public void createTama(String nickname){
        int x=50, y=50, size=50;
        tama = new Tamagochi(x, y, size, "src/img/tamagochiImg.png", nickname);
    }
    
    public void feed(Timer hungryTimer, Timer sleepyTimer){
        Random random= new Random();
        
        String[] buttons = {"종료", "재실행"};
        
        //밥 먹임. 랜덤하게 1~3만큼 포만감 증가
        tama.setSatiety(tama.getSatiety()+random.nextInt(3)+1);
        
        // 포만감이 11 이상인 경우, 일정 확률로 Tamagochi의 dieByEating() 메소드 호출 => 배부른 상태에서 밥 먹이면 50% 확률로 죽음
        if(tama.getSatiety() >= 11) {
        	if(Math.random() < 0.5) {
                tombstones.add(tama.dieByEating("죽을때까지 먹다가 배터져 죽음"));
                System.out.println("죽음");
                hungryTimer.stop();
                sleepyTimer.stop();
                
            	int result = JOptionPane.showOptionDialog(null, "사망했습니다.", "사망 알림창", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "재실행");
            	System.out.println(result);
            	if(result == 0) {
            		System.exit(1);
            	}
        	}
        }
        // 만약 포만감이 15를 초과했을 경우, Tamagochi의 dieByEating() 메소드 호출
        if(tama.getSatiety() >= 15){
            tombstones.add(tama.dieByEating("죽을때까지 먹다가 배터져 죽음"));
            System.out.println("죽음");
            hungryTimer.stop();
            sleepyTimer.stop();
            
        	int result = JOptionPane.showOptionDialog(null, "사망했습니다.", "사망 알림창", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "재실행");
        	System.out.println(result);
        	if(result == 0) {
        		System.exit(1);
        	}
        }
    }
    
    public void sleep(){
        Random random= new Random();
        tama.setFatigue(tama.getFatigue()-random.nextInt(4));
    }
    public void clean(){
        // poop를 모두 제거
        poops.clear();
    }
    public void createPoop(){
        // poop 생성
        int x=50, y=50, size=50;
        Poop poop = new Poop(x, y, size, "src/img/poop.png");
        poops.add(poop);
        // 만약 poop가 10개라면, Tamagochi의 dieByPoop() 메소드 호출. 즉 poop이 10개가 되면 죽음.
        if(poops.size() >= 10){
            tombstones.add(tama.dieByPoop("똥독 올라 죽음"));
        }
    }
    public void gettingHungry(Timer hungryTimer, Timer sleepyTimer){
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '10초'마다 포만감을 1씩 감소시킴
        tama.setSatiety(tama.getSatiety()-1);

        String[] buttons = {"종료", "재실행"};
        
        // 포만감이 4 이하인 경우 50% 확률로 Tamagochi의 dieByEating() 메소드 호출하여 죽게 함
        if(tama.getSatiety() <= 4) {
        	if(Math.random() < 0.5) {
                tombstones.add(tama.dieByEating("굶어 죽음"));
                System.out.println("죽음");
                hungryTimer.stop();
                sleepyTimer.stop();
                
            	int result = JOptionPane.showOptionDialog(null, "사망했습니다.", "사망 알림창", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "재실행");
            	System.out.println(result);
            	if(result == 0) {
            		System.exit(1);
            	}
        	}
        }
        
        // 포만감이 0일 때는 Tamagochi의 dieByEating() 메소드를 호출이여 무조건 죽게 함
        if(tama.getSatiety() == 0) {
            tombstones.add(tama.dieByEating("굶어 죽음"));
            System.out.println("아사");
            hungryTimer.stop();
            sleepyTimer.stop();

        	int result = JOptionPane.showOptionDialog(null, "사망했습니다.", "사망 알림창", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "재실행");
        	System.out.println(result);
        	if(result == 0) {
        		System.exit(1);
        	}
        }
    }
    public void gettingSleepy() {
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '20초'마다 피로도를 1씩 증가시킨다.
        // 만약 피로도가 10이 넘어가면, 일정 확률로 Tamagochi의 dieBySleep() 메소드 호출.
        // 피로도가 15에 도달하면 그냥 dieBySleep() 호출. 즉 피로도가 15가 되면 죽음.
        tama.setFatigue(tama.getFatigue() + 1);
        if (tama.getFatigue() >= 10) {
            if (tama.getFatigue() == 15) {
                tombstones.add(tama.dieBySleeping("피곤에 찌들어 죽"));
            } else if (Math.random() < 0.4) {
                tombstones.add(tama.dieBySleeping("피곤해서 죽음"));
            }
        }
    }
    public Tamagochi getTama(){
        return tama;
    }
    
}
