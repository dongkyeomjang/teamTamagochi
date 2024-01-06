package src;

import javax.swing.*;
import java.awt.*;

public abstract class Drawable {
    private int x;
    private int y;
    private int size;
    ImageIcon imgIcon;
    public Drawable(int x, int y, int size, String imgURL) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.imgIcon = new ImageIcon(imgURL);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSize() {
        return size;
    }
    public void setImgIcon(String imgURL) {
        this.imgIcon = new ImageIcon(imgURL);
    }
    public ImageIcon getImageIcon() {
        return this.imgIcon;
    }
    public abstract void display(Graphics g);
}
