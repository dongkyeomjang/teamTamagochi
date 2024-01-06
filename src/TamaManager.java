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
        int x=200, y=310, size=110;
        tama = new Tamagochi(x, y, size, "src/img/tamagochiImg.png", nickname);
    }
    public void feed(){
        Random random= new Random();
        // 만약 포만감이 10이상인 경우, 일정 확률로 Tamagochi의 dieByEat() 메소드 호출. 즉 배부른 상태에서 밥을 먹이면 일정확률로 죽음.
        if(tama.getSatiety() >= 10){
            if(Math.random() < 0.4){
                tombstones.add(tama.dieByEat("배부른 상태에서 먹다가 체해서(운없어서) 죽음", tombstones.size()));
                tama.setImgURL("src/img/tamaGhostImg.png");
            }
        }
        //밥 먹임. 랜덤하게 1~3만큼 포만감 증가
        tama.setSatiety(tama.getSatiety()+random.nextInt(3)+1);
        // 만약 포만감이 15를 초과했을 경우, Tamagochi의 dieByEat() 메소드 호출
        if(tama.getSatiety() >= 15){
            tombstones.add(tama.dieByEat("죽을때까지 먹다가 배터져 죽음", tombstones.size()));
            tama.setImgURL("src/img/tamaGhostImg.png");
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
        Random random = new Random();
        // 화면 크기에 맞추어 랜덤 좌표 생성 (예시: 화면 크기가 500x500)
        int x = random.nextInt(350)+100; // 화면 너비에서 poop 크기(50)를 뺀 범위
        int y = random.nextInt(100)+350; // 화면 높이에서 poop 크기(50)를 뺀 범위

        int size=50;
        Poop poop = new Poop(x, y, size, "src/img/poopImg.png");
        poops.add(poop);
        // 만약 poop가 10개라면, Tamagochi의 dieByPoop() 메소드 호출. 즉 poop이 10개가 되면 죽음.
        if(poops.size() >= 10){
            tombstones.add(tama.dieByPoop("똥독 올라 죽음", tombstones.size()));
            tama.setImgURL("src/img/tamaGhostImg.png");
        }
    }
    public void gettingHungry(){
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '10초'마다 포만감을 1씩 감소시킨다.
        // 만약 포만감이 0이 되면, Tamagochi의 dieByEat() 메소드 호출. 즉 배고픔이 0이 되면 죽음.
        tama.setSatiety(tama.getSatiety()-1);
        if(tama.getSatiety() <= 0){
            tombstones.add(tama.dieByEat("배고파서 죽음", tombstones.size()));
            tama.setImgURL("src/img/tamaGhostImg.png");
        }
    }
    public void gettingSleepy() {
        // 시스템은 tama의 createTime과 현재 시간을 비교하여 시간의 경과를 측정하고, '20초'마다 피로도를 1씩 증가시킨다.
        // 만약 피로도가 10이 넘어가면, 일정 확률로 Tamagochi의 dieBySleep() 메소드 호출.
        // 피로도가 15에 도달하면 그냥 dieBySleep() 호출. 즉 피로도가 15가 되면 죽음.
        tama.setFatigue(tama.getFatigue() + 1);
        if (tama.getFatigue() >= 10) {
            if (tama.getFatigue() == 15) {
                tombstones.add(tama.dieBySleep("피곤에 찌들어 죽음", tombstones.size()));
                tama.setImgURL("src/img/tamaGhostImg.png");
            } else if (Math.random() < 0.4) {
                tombstones.add(tama.dieBySleep("피곤해서 죽음", tombstones.size()));
                tama.setImgURL("src/img/tamaGhostImg.png");
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
}
