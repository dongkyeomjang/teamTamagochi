import java.awt.*;

public class SatietyBar extends Drawable {
    public SatietyBar(int x, int y, int size, String imgURL) {
        super(x,y,size,imgURL);
    }
    public void display(Graphics g) {
        Image img = this.imgIcon.getImage();
        g.drawImage(img, getX(), getY(), 125, 20, null);
    }
}
