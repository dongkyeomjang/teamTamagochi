package src;

import java.util.ArrayList;
import java.util.Random;

public class TamaManager {
    private Tamagochi tama;
    private ArrayList<Poop> poops;
    private ArrayList<Tombstone> tombstones;

    public TamaManager(){
        this.tama = null;
        poops = new ArrayList<Poop>();
        tombstones = new ArrayList<Tombstone>();
    }
    public void createTama(String nickname){
        int x=50, y=50, size=50;
        tama = new Tamagochi(x, y, size, "src/img/tama.png", nickname);
    }
    public Tombstone feed(){
        // 만약 포만감이 10이상인 경우, 일정 확률로 Tamagochi의 dieByEat() 메소드 호출. 즉 배부른 상태에서 밥을 먹이면 일정확률로 죽음.
        // 만약 포만감이 14인 상태에서 밥을 먹인 경우, Tamagochi의 dieByEat() 메소드 호출
        if(tama.getSatiety() == 14){
            return tama.dieByEat("죽을때까지 먹다가 배터져 죽음");
        }
        else if(tama.getSatiety() >= 10){
            if(Math.random() < 0.4){
                return tama.dieByEat("배부른 상태에서 먹다가 체해서(운없어서) 죽음");
            }
        }
        // 아직 최대 포만감을 설정하진 않은 상태임. 추가 코딩 필요.
        tama.setSatiety(tama.getSatiety()+1);
        return null;
    }
    public void sleep(){
        Random random= new Random();
        tama.setFatigue(tama.getFatigue()-random.nextInt(4));
    }
    public void clean(){
        // poop를 모두 제거
        poops.clear();
    }
    public Tombstone createPoop(){
        // poop 생성
        int x=50, y=50, size=50;
        Poop poop = new Poop(x, y, size, "src/img/poop.png");
        poops.add(poop);
        // 만약 poop가 10개라면, Tamagochi의 dieByPoop() 메소드 호출. 즉 poop이 10개가 되면 죽음.
        if(poops.size() >= 10){
            return tama.dieByPoop("똥독 올라 죽음");
        }
        return null;
    }
    private void gettingHungry(){
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '10초'마다 포만감을 1씩 감소시킨다.
        // 만약 포만감이 0이 되면, Tamagochi의 dieByEat() 메소드 호출. 즉 배고픔이 0이 되면 죽음.
    }
    private void gettingSleepy(){
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '20초'마다 피로도를 1씩 증가시킨다.
        // 만약 피로도가 10이 넘어가면, 일정 확률로 Tamagochi의 dieBySleep() 메소드 호출.
        // 피로도가 15에 도달하면 그냥 dieBySleep() 호출. 즉 피로도가 15가 되면 죽음.
    }

}
