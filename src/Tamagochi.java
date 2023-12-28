package src;

import java.awt.*;
import java.sql.Timestamp;

public class Tamagochi extends Drawable{
    private int satiety;
    private int fatigue;

    //poop는 매니저에 넣는게 맞는 것 같아서 뺐음.
    private int level;

    private Timestamp createTime;
    private String nickname;

    public Tamagochi(int x, int y, int size, String imgURL, String nickname) {
        super(x, y, size, imgURL);
        this.satiety = 8;
        this.fatigue = 0;
        this.level = 1;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.nickname = nickname;
    }

    public void display(Graphics g){
        Image img = Toolkit.getDefaultToolkit().getImage(getImgURL());
        g.drawImage(img, getX(), getY(), getSize(), getSize(), null);
    }

    public Tombstone dieByEat(String causeOfDeath){
        // 사인 작성, 비석 생성
        return new Tombstone(getX(), getY(), getSize(), "src/img/tombstone.png",causeOfDeath);
    }
    public Tombstone dieBySleep(String causeOfDeath){
        // 사인 작성, 비석 생성
        return new Tombstone(getX(), getY(), getSize(), "src/img/tombstone.png",causeOfDeath);
    }

    public Tombstone dieByPoop(String causeOfDeath){
        // 사인 작성, 비석 생성
        return new Tombstone(getX(), getY(), getSize(), "src/img/tombstone.png",causeOfDeath);
    }

    //-------------------------getter, setter-------------------------
    public int getSatiety() {
        return satiety;
    }
    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }
    public int getFatigue() {
        return fatigue;
    }
    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }
    public String getNickname() {
        return nickname;
    }
}
