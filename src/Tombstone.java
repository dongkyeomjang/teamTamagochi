import java.awt.*;

public class Tombstone extends Drawable{
    private String causeOfDeath;
	private String nickname;
    public Tombstone(int x, int y, int size, String imgURL,String causeOfDeath, String nickname){
        super(x, y, size, imgURL);
        this.causeOfDeath = causeOfDeath;
        this.nickname = nickname;
    }
    public void display(Graphics g){
        Image img = this.imgIcon.getImage();
        g.drawImage(img, getX(), getY(), getSize(), getSize(), null);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("이름: " +nickname, getX() + 27, getY() + 30);
        
        g.setColor(Color.BLACK);
        g.drawString("사인: " +causeOfDeath  , getX() + 27, getY() + 45);
 
    }
    
   
    
    
}
