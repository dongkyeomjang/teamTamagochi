import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class Tamagochi extends Drawable{
    private int satiety;
    private int fatigue;
    private int level;

    private Timestamp createTime;
    private String nickname;
    private String imgURL;

    public Tamagochi(int x, int y, int size, String imgURL, String nickname) {
        super(x, y, size, imgURL);
        this.satiety = 8;
        this.fatigue = 0;
        this.level = 1;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.nickname = nickname;
        this.imgURL = "src/img/tamagochiImg1.png";
    }

    public void display(Graphics g){
        Image img = this.imgIcon.getImage();
        g.drawImage(img, getX(), getY(), getSize(), getSize(), null);
    }

    public Tombstone dieByEat(String causeOfDeath, int tombstoneNum){
        return switch (tombstoneNum) {
            case 0 -> new Tombstone(30, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 1 -> new Tombstone(110, 280, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 2 -> new Tombstone(202, 250, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 3 -> new Tombstone(294, 280, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 4 -> new Tombstone(374, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            default -> new Tombstone(30, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
        };
    }
    public Tombstone dieBySleep(String causeOfDeath, int tombstoneNum){
        return switch (tombstoneNum) {
            case 0 -> new Tombstone(30, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 1 -> new Tombstone(110, 280, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 2 -> new Tombstone(202, 250, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 3 -> new Tombstone(294, 280, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 4 -> new Tombstone(374, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            default -> new Tombstone(30, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
        };
    }

    public Tombstone dieByPoop(String causeOfDeath, int tombstoneNum){
        return switch (tombstoneNum) {
            case 0 -> new Tombstone(30, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 1 -> new Tombstone(110, 280, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 2 -> new Tombstone(202, 250, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 3 -> new Tombstone(294, 280, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            case 4 -> new Tombstone(374, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
            default -> new Tombstone(30, 350, 110, "src/img/tombstoneImg1.png", causeOfDeath, nickname);
        };
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

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
    
}
