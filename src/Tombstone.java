

import java.awt.*;

public class Tombstone extends Drawable{
    private String causeOfDeath;
    public Tombstone(int x, int y, int size, String imgURL, String causeOfDeath){
        super(x, y, size, imgURL);
        this.causeOfDeath = causeOfDeath;
    }
    public void display(Graphics g){
        Image img = Toolkit.getDefaultToolkit().getImage(getImgURL());
        g.drawImage(img, getX(), getY(), getSize(), getSize(), null);
    }
}
