package src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class FatigueBar extends Drawable{
    public FatigueBar(int x, int y, int size, String imgURL) {
        super(x,y,size,imgURL);
    }
    public void display(Graphics g) {
        Image img = Toolkit.getDefaultToolkit().getImage(getImgURL());
        g.drawImage(img, getX(), getY(), getSize(), getSize(), null);
    }
}
