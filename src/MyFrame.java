package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JProgressBar satietyBar;
    private JProgressBar fatigueBar;
    public MyFrame(){
        super();

        setSize(515, 630);
        setLayout(new GridBagLayout()); // 레이아웃 매니저 변경

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/backgroundImg.png"); // 여기에 원하는 이미지 경로 입력
        backgroundPanel.setLayout(null);

        tamaManager = new TamaManager(this);
        String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
        tamaManager.createTama(nickname);
        if(nickname == null) {
            System.exit(1);
        }
        else if(nickname.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "닉네임을 반드시 입력해야 합니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }

        //밥 버튼
        ImageIcon eatButtonImg = new ImageIcon(new ImageIcon("src/img/eatImg.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        eatButton = new JButton(eatButtonImg);
        eatButton.setBounds(35, 500, 90, 80);
        backgroundPanel.add(eatButton);

        //재우기 버튼
        ImageIcon sleepButtonImg = new ImageIcon(new ImageIcon("src/img/sleepImg.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        sleepButton = new JButton(sleepButtonImg);
        sleepButton.setBounds(210, 500, 90, 80);
        backgroundPanel.add(sleepButton);

        //똥 치우기 버튼
        ImageIcon cleanButtonImg = new ImageIcon(new ImageIcon("src/img/cleanImg1.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        cleanButton = new JButton(cleanButtonImg);
        cleanButton.setBounds(380, 500, 90, 80);
        backgroundPanel.add(cleanButton);

        //이름 라벨
        nameLabel = new JLabel(nickname);
        nameLabel.setBounds(200, 0, 100, 50);
        backgroundPanel.add(nameLabel);
        setContentPane(backgroundPanel);

        //actionListener 생성 및 배치
        actionListener = e -> {
            if(e.getSource() == eatButton){
                tamaManager.feed();
                satietyBar.setValue(tamaManager.getTama().getSatiety());
                repaint();
            }
            else if(e.getSource() == sleepButton){
                tamaManager.sleep();
                fatigueBar.setValue(tamaManager.getTama().getFatigue());
                repaint();
            }
            else if(e.getSource() == cleanButton){
                tamaManager.clean();
                repaint();
            }
        };
        eatButton.addActionListener(actionListener);
        sleepButton.addActionListener(actionListener);
        cleanButton.addActionListener(actionListener);

        //포만감 게이지바
        satietyBar = new JProgressBar(0, 15);
        satietyBar.setValue(tamaManager.getTama().getSatiety());
        satietyBar.setStringPainted(true);
        satietyBar.setBounds(350, 100, 125, 20);
        backgroundPanel.add(satietyBar);

        // 피로도 게이지 초기화 및 설정
        fatigueBar = new JProgressBar(0, 15);
        fatigueBar.setValue(tamaManager.getTama().getFatigue());
        fatigueBar.setStringPainted(true);
        fatigueBar.setBounds(350, 130, 125, 20);
        backgroundPanel.add(fatigueBar);


        scheduler = Executors.newScheduledThreadPool(3);

        tracker = new MediaTracker(this);
        Image tamaImage = tamaManager.getTama().getImageIcon().getImage();
        tracker.addImage(tamaImage, 0);

        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!tracker.isErrorAny()) {
            // 이미지 로딩 완료
            repaint();
        }
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.levelUp();
            SwingUtilities.invokeLater(this::repaint);
        },10,5, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.gettingHungry();
            satietyBar.setValue(tamaManager.getTama().getSatiety());

            SwingUtilities.invokeLater(this::repaint);
        }, 10, 10, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.gettingSleepy();
            fatigueBar.setValue(tamaManager.getTama().getFatigue());

            SwingUtilities.invokeLater(this::repaint);
        }, 20, 20, TimeUnit.SECONDS);

        //첫 번째 똥이 생성되어도, 그 이후부터 같이 출력된다는 문제 발생. 해결 필요
        scheduler.scheduleAtFixedRate(() -> {
            tamaManager.createPoop();
            SwingUtilities.invokeLater(this::repaint);
        }, 5, 5, TimeUnit.SECONDS);

        setVisible(true);
    }
    public void gameClear() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        JOptionPane.showMessageDialog(null, "축하합니다! 게임을 클리어했습니다!", "게임 클리어", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    public void gameOver() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        // 게임 오버라는 메세지와, 재시작 버튼이 있는 팝업창을 띄워준다. 재시작 버튼을 누를 시, createTama를 호출한다.
        int restart = JOptionPane.showConfirmDialog(null, "게임 오버! 다시 시작하시겠습니까?", "게임 오버", JOptionPane.YES_NO_OPTION);
        if (restart == JOptionPane.YES_OPTION) {
            // 게임 재시작. 닉네임 재설정
            String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
            tamaManager.createTama(nickname);
            satietyBar.setValue(tamaManager.getTama().getSatiety());
            fatigueBar.setValue(tamaManager.getTama().getFatigue());
            scheduler = Executors.newScheduledThreadPool(3);
            scheduler.scheduleAtFixedRate(() -> {
                tamaManager.levelUp();
                SwingUtilities.invokeLater(this::repaint);
            },0,60, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() -> {
                tamaManager.gettingHungry();
                satietyBar.setValue(tamaManager.getTama().getSatiety());

                SwingUtilities.invokeLater(this::repaint);
            }, 10, 10, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() -> {
                tamaManager.gettingSleepy();
                fatigueBar.setValue(tamaManager.getTama().getFatigue());

                SwingUtilities.invokeLater(this::repaint);
            }, 20, 20, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(() -> {
                tamaManager.createPoop();
                SwingUtilities.invokeLater(this::repaint);
            }, 5, 5, TimeUnit.SECONDS);
            repaint();
        } else {
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

            //똥을 우선적으로 그리게
            for(Poop p: tamaManager.getPoops()) {
                drawables.add((Drawable) p);
                // 게이지 바도 drawables에 넣어서 그려주어야 함. 추후 추가
            }
            //똥->비석
            for(Tombstone t: tamaManager.getTombstones()) {
                drawables.add((Drawable) t);
            }
            //똥->비석->캐릭터
            drawables.add((Drawable) tamaManager.getTama());
            for(Drawable d: drawables) {
                d.display(g);
            }
            drawables.clear();
        }
    }
}

