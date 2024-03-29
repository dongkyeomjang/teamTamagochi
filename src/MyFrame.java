import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyFrame extends JFrame {
    private MediaTracker tracker;
    private ScheduledExecutorService scheduler;
    private JButton eatButton;
    private JButton sleepButton;
    private JButton cleanButton;
    private JLabel nameLabel;
    private TamaManager tamaManager;
    private ActionListener actionListener;
    

    public MyFrame(){
        super();

        setSize(515, 630);
        setLayout(new GridBagLayout()); // 레이아웃 매니저 변경
        
        setLocationRelativeTo(null); // 화면 가운데 배치
        setResizable(false); // 창 크기 고정

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/backgroundImg.png"); // 여기에 원하는 이미지 경로 입력
        backgroundPanel.setLayout(null);

        tamaManager = new TamaManager(this);
        String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
        
        if(nickname == null) {
            System.exit(1);
        }
        while (nickname.trim().isEmpty()) {
        	JOptionPane.showMessageDialog(null, "닉네임을 반드시 입력해야 합니다.", "경고", JOptionPane.WARNING_MESSAGE);
        	nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
        }
        
        tamaManager.createTama(nickname);
        
        

        //밥 버튼
        ImageIcon eatButtonImg = new ImageIcon(new ImageIcon("src/img/eatImg.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        eatButton = new JButton(eatButtonImg);
        eatButton.setBounds(35, 500, 90, 80);
        backgroundPanel.add(eatButton);

        //재우기 버튼
        ImageIcon sleepButtonImg = new ImageIcon(new ImageIcon("src/img/sleepImg.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        sleepButton = new JButton(sleepButtonImg);
        sleepButton.setBounds(210, 500, 90, 80);
        backgroundPanel.add(sleepButton);

        //똥 치우기 버튼
        ImageIcon cleanButtonImg = new ImageIcon(new ImageIcon("src/img/cleanImg1.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        cleanButton = new JButton(cleanButtonImg);
        cleanButton.setBounds(380, 500, 90, 80);
        backgroundPanel.add(cleanButton);

        //이름 라벨
        nameLabel = new JLabel(nickname);
        nameLabel.setBounds(245, 0, 100, 50);
        backgroundPanel.add(nameLabel);
        setContentPane(backgroundPanel);

        //actionListener 생성 및 배치
        actionListener = e -> {
            if(e.getSource() == eatButton){
                tamaManager.feed();
                repaint();
            }
            else if(e.getSource() == sleepButton){
            	// 10초, 20초, 30초 중 랜덤 시간 선택
                Random rand = new Random();
            	int[] times = {10000, 20000, 30000}; // 밀리초 단위
            	int randomTime = times[rand.nextInt(times.length)];

            	
            	// 랜덤 시간에 따른 피로도 감소
                int fatigueReduction = randomTime / 10000; // 1, 2 또는 3으로 설정

                tamaManager.sleep(fatigueReduction);
                repaint();
            	
            	// 버튼을 비활성화            	 
            	 sleepButton.setEnabled(false);
                 eatButton.setEnabled(false);
             	

                // 랜덤 시간 후에 버튼을 다시 활성화
                Timer timer = new Timer(randomTime, ev -> {  
                    sleepButton.setEnabled(true);
                    eatButton.setEnabled(true);             
                });
                
                timer.setRepeats(false);
                timer.start();            
            }
            	
            
            else if(e.getSource() == cleanButton){
                tamaManager.clean();
                repaint();
            }
        };

        eatButton.addActionListener(actionListener);
        sleepButton.addActionListener(actionListener);
        cleanButton.addActionListener(actionListener);

        scheduler = Executors.newScheduledThreadPool(5);

        tracker = new MediaTracker(this);
        Image tamaImage = tamaManager.getTama().getImageIcon().getImage();
        tracker.addImage(tamaImage, 0);

        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!tracker.isErrorAny()) {
            repaint();
        }
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.levelUp();
            SwingUtilities.invokeLater(this::repaint);
        }, 60, 60, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.gettingHungry();

            SwingUtilities.invokeLater(this::repaint);
        }, 10, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.gettingSleepy();

            SwingUtilities.invokeLater(this::repaint);
        }, 20, 20, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.createPoop();
            SwingUtilities.invokeLater(this::repaint);
        }, 5, 5, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() ->{
            tamaManager.move();
            SwingUtilities.invokeLater(this::repaint);
        }, 0, 500, TimeUnit.MILLISECONDS);

        setVisible(true);
    }
    public boolean isSleepButtonEnabled() {
        return sleepButton.isEnabled();
    }
	public void gameClear() {
        if (scheduler != null) {            	
        	scheduler.shutdown();   
        	repaint();
        }
        String[] options = {"재도전", "닫기"};
        int clear = JOptionPane.showOptionDialog(null, "축하합니다! 게임을 클리어했습니다! \n 다시 시작하시겠습니까?", "게임 클리어",   JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null,
                options,
                options[0]);
        if(clear == JOptionPane.YES_OPTION) {
        	String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
            tamaManager.createTama(nickname);
            nameLabel.setText(nickname);

            // 똥 배열 비우기
            tamaManager.clean();
            
            sleepButton.setEnabled(true);
            eatButton.setEnabled(true);
            
            scheduler = Executors.newScheduledThreadPool(5);
            scheduler.scheduleAtFixedRate(() -> {
            	tamaManager.levelUp();
            	SwingUtilities.invokeLater(this::repaint);
            }, 60, 60, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() -> {
            	tamaManager.gettingHungry();
            	SwingUtilities.invokeLater(this::repaint);
            }, 10, 10, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() -> {
            	tamaManager.gettingSleepy();
            	SwingUtilities.invokeLater(this::repaint);
            }, 20, 20, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() -> {
            	tamaManager.createPoop();
            	SwingUtilities.invokeLater(this::repaint);
            }, 5, 5, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() ->{
            	tamaManager.move();
                SwingUtilities.invokeLater(this::repaint);
            }, 500, 500, TimeUnit.MILLISECONDS);
            repaint();
		}else {
			System.exit(0);
		}
        

        
    }
    public void gameOver(String causeOfDeath) {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        repaint();
        if(tamaManager.getTombstones().size() <= 5) {
        	// 게임 오버라는 메세지와, 재시작 버튼이 있는 팝업창을 띄워준다. 재시작 버튼을 누를 시, createTama를 호출한다.
        	int restart = JOptionPane.showConfirmDialog(null, "게임 오버! 다시 시작하시겠습니까?\n사인:"+causeOfDeath, "게임 오버", JOptionPane.YES_NO_OPTION);
        	if (restart == JOptionPane.YES_OPTION) {
        		if (tamaManager.getTombstones().size() <=5) {
        			// 게임 재시작. 닉네임 재설정
                    String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
                    
                    if(nickname == null) {
                        System.exit(1);
                    }
                    while (nickname.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "닉네임을 반드시 입력해야 합니다.", "경고", JOptionPane.WARNING_MESSAGE);
                    	nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
                    }
                    
                    tamaManager.createTama(nickname);
                    nameLabel.setText(nickname);
    
                    // 똥 배열 비우기
                    tamaManager.clean();
                    
                    // 게임 재실행 후 버튼 활성화
                    sleepButton.setEnabled(true);
                    eatButton.setEnabled(true);
            
                    scheduler = Executors.newScheduledThreadPool(5);
                    scheduler.scheduleAtFixedRate(() -> {
                    	tamaManager.levelUp();
                    	SwingUtilities.invokeLater(this::repaint);
                    },10,10, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(() -> {
                    	tamaManager.gettingHungry();

                    	SwingUtilities.invokeLater(this::repaint);
                    }, 2, 2, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(() -> {
                    	tamaManager.gettingSleepy();

                    	SwingUtilities.invokeLater(this::repaint);
                    }, 4, 4, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(() -> {
                    	tamaManager.createPoop();
                    	SwingUtilities.invokeLater(this::repaint);
                    }, 5, 5, TimeUnit.SECONDS);
                    scheduler.scheduleAtFixedRate(() ->{
                    	tamaManager.move();
                    	SwingUtilities.invokeLater(this::repaint);
                    }, 500, 500, TimeUnit.MILLISECONDS);
                    
                    repaint();
        		}
        	}
        if(restart == JOptionPane.NO_OPTION) {
        	System.exit(0); 
        }
        }else {
	   JOptionPane.showMessageDialog(null, " 목숨을 모두 소진했습니다! 게임을 종료합니다. \n 사인: "+causeOfDeath , "게임 종료", JOptionPane.INFORMATION_MESSAGE);
	   System.exit(0);       
    }         
}
    


    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        // 배경 이미지를 받는 생성자
        public BackgroundPanel(String fileName) {
            backgroundImage = new ImageIcon(fileName).getImage();
        }

        // 배경 이미지 그리기
        // 타마고치 그리기, 먼저 그리는게 밑으로 가는 형식
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ArrayList<Drawable> drawables = new ArrayList<>();
            //배경
            g.drawImage(backgroundImage, 0, 50, 500, 550, null);

            //비석을 우선적으로 그리게
            for(Tombstone t: tamaManager.getTombstones()) {
                drawables.add((Drawable) t);
            }
            //비석->케릭터
            drawables.add((Drawable) tamaManager.getTama());
            
            //비석->케릭터->똥(머리위만 올라감)
            for(Poop p: tamaManager.getPoops()) {
                drawables.add((Drawable) p);
            }        
            //비석->똥->캐릭터->바
            drawables.add((Drawable) tamaManager.getSatietyBar());
            drawables.add((Drawable) tamaManager.getFatigueBar());
            for(Drawable d: drawables) {
                d.display(g);
            }
            drawables.clear();
        }
    }
}

