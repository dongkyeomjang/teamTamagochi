import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class FatigueBar extends Drawable{
	 private boolean isSleep = false;
    public FatigueBar(int x, int y, int size, String imgURL) {
        super(x,y,size,imgURL);
    }
    public void display(Graphics g) {
    	if (!isSleep) {
        Image img = this.imgIcon.getImage();
        g.drawImage(img, getX(), getY(), 125, 20, null);
    }
    }
    public void pause() {
    	isSleep= true;
    }

    public void resume() {
    	isSleep = false;
    }
}


