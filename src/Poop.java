import java.awt.*;

public class Poop extends Drawable {
    public Poop(int x, int y, int size, String imgURL) {
        super(x, y, size, imgURL);
    }
    public void display(Graphics g) {
        Image img = this.imgIcon.getImage();
        g.drawImage(img, getX(), getY(), getSize(), getSize(), null);
    }
}
