
import java.awt.*;

public abstract class Drawable {
    private int x;
    private int y;
    private int size;
    private String imgURL;
    
    public Drawable(int x, int y, int size, String imgURL) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.imgURL = imgURL;
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
    
    public String getImgURL() {
        return imgURL;
    }
    
    public abstract void display(Graphics g);
    
}
