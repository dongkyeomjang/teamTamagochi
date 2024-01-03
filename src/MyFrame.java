package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class MyFrame extends JFrame {
    private JButton eatButton;
    private JButton sleepButton;
    private JButton cleanButton;
    private JPanel menuPanel;
    private JPanel characterPanel;
    private JPanel poopPanel;
    private JPanel tombstonePanel;
    private JPanel gagePanel;
    private JPanel namePanel;
    private Timer hungryTimer;
    private Timer sleepyTimer;
    private TamaManager tamaManager;
    private ActionListener actionListener;
    private JProgressBar satietyBar;
    private JProgressBar fatigueBar;
    public MyFrame(){
        super();

        setSize(500, 600);
        setLayout(new GridBagLayout()); // 레이아웃 매니저 변경

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/backgroundImg.jpg"); // 여기에 원하는 이미지 경로 입력
        this.setContentPane(backgroundPanel);
        this.setLayout(new GridBagLayout()); // 배경 패널 위에 다른 컴포넌트들을 올리기 위해 레이아웃 재설정
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;

        tamaManager = new TamaManager();
        String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요");
        tamaManager.createTama(nickname);
        ImageIcon tamaIcon = new ImageIcon(tamaManager.getTama().getImgURL());
        // 이미지 크기 조정
        Image image = tamaIcon.getImage();
        Image resizedImage = image.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        tamaIcon = new ImageIcon(resizedImage);
        //menuPanel 구성 요소 생성 및 배치
        ImageIcon eatIcon = new ImageIcon(new ImageIcon("src/img/eatImg.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon sleepIcon = new ImageIcon(new ImageIcon("src/img/sleepImg.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon cleanIcon = new ImageIcon(new ImageIcon("src/img/cleanImg1.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        eatButton = new JButton(eatIcon);
        sleepButton = new JButton(sleepIcon);
        cleanButton = new JButton(cleanIcon);
        //actionListener 생성 및 배치
        actionListener = e -> {
            if(e.getSource() == eatButton){
                tamaManager.feed();
                repaint();
            }
            else if(e.getSource() == sleepButton){
                tamaManager.sleep();
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
        menuPanel = new JPanel();
        menuPanel.add(eatButton);
        menuPanel.add(sleepButton);
        menuPanel.add(cleanButton);
        // menuPanel을 하단에 배치
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        menuPanel.setOpaque(false);
        add(menuPanel, gbc);


        //namePanel 구성 요소 생성 및 배치
        namePanel = new JPanel();
        // namePanel을 상단에 배치
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1; // 적은 공간 할당
        namePanel.setOpaque(false);
        add(namePanel, gbc);

        //gagePanel 구성 요소 생성 및 배치
        gagePanel = new JPanel();
        // gagePanel을 namePanel 아래에 배치
        gbc.gridy = 1;
        gagePanel.setOpaque(false);
        add(gagePanel, gbc);
        // 포만감 게이지 초기화 및 설정
        satietyBar = new JProgressBar(0, 15); // 최대 포만감을 15로 가정
        satietyBar.setValue(tamaManager.getTama().getSatiety());
        satietyBar.setStringPainted(true); // 숫자로 현재 값 표시

        // 피로도 게이지 초기화 및 설정
        fatigueBar = new JProgressBar(0, 15); // 최대 피로도를 15로 가정
        fatigueBar.setValue(tamaManager.getTama().getFatigue());
        fatigueBar.setStringPainted(true);

        // gagePanel에 게이지 추가
        gagePanel.setLayout(new GridLayout(2, 1)); // 레이아웃 설정
        gagePanel.add(satietyBar);
        gagePanel.add(fatigueBar);

        //characterPanel 구성 요소 생성 및 배치
        characterPanel = new JPanel();
        poopPanel = new JPanel();
        poopPanel.setOpaque(false);
        tombstonePanel= new JPanel();
        tombstonePanel.setOpaque(false);
        // 이미지를 JLabel에 설정하고 characterPanel에 추가
        JLabel tamaLabel = new JLabel(tamaIcon);
        characterPanel.setOpaque(false);
        characterPanel.add(tamaLabel);
        characterPanel.add(poopPanel);
        characterPanel.add(tombstonePanel);
        // characterPanel을 gagePanel 아래, menuPanel 위에 배치
        gbc.gridy = 2;
        gbc.weighty = 0.01; // 필요한 공간만큼만 할당
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(characterPanel, gbc);

        repaint();
        setVisible(true);
    }
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        // 배경 이미지를 받는 생성자
        public BackgroundPanel(String fileName) {
            backgroundImage = new ImageIcon(fileName).getImage();
        }

        // 배경 이미지 그리기
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
