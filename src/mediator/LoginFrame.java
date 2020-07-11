package mediator;

import components.ColleagueButton;
import components.ColleagueTextField;
import model.UserReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener, Mediator {
    private ColleagueTextField textUser;
    private ColleagueTextField textUserId;
    private ColleagueButton buttonOk;

    public LoginFrame(String title) {
        super(title);

        setSize(400, 600);
        // 真ん中に位置
        setLocationRelativeTo(null);
        // 閉じるボタンでアプリケーション終了
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        createColleagues();

        // スペース用JPanel
        JPanel spacePanel = new JPanel();
        spacePanel.setLayout(new BorderLayout());
        JLabel subject = new JLabel("めっちゃ使いやすい" + title);
        subject.setFont(new Font("Arial", Font.PLAIN, 20));
        spacePanel.add(subject);
        spacePanel.add(Box.createVerticalStrut(50), BorderLayout.SOUTH);

        // 文字入力用JPanel
        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(300, 100));

        GridLayout inputLayout = new GridLayout(3, 2);
        inputLayout.setHgap(10);
        inputPanel.setLayout(inputLayout);

        inputPanel.add(new JLabel("氏名:"));
        inputPanel.add(textUser);
        inputPanel.add(new JLabel("会員ID:"));
        inputPanel.add(textUserId);

        // OKボタン用JPanel
        JPanel submitPanel = new JPanel();
        submitPanel.setPreferredSize(new Dimension(300, 50));

        submitPanel.setLayout(new GridLayout(1, 1));
        submitPanel.add(buttonOk);

        Container contentPane = getContentPane();
        contentPane.add(spacePanel);
        contentPane.add(inputPanel);
        contentPane.add(submitPanel);

        setVisible(true);
    }

    public void createColleagues() {

        textUser = new ColleagueTextField("", 10);
        textUserId = new ColleagueTextField("", 10);
        buttonOk = new ColleagueButton("ログイン");

        textUser.setMediator(this);
        textUserId.setMediator(this);
        buttonOk.setMediator(this);

        buttonOk.addActionListener(buttonOk);
    }

    public void colleagueChanged() {
        // ログイン
        if (UserReader.isCorrectUser(textUser.getText(), textUserId.getText())) {
            JOptionPane.showMessageDialog(this, "ログイン成功", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "ログイン失敗", "Warn", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        System.exit(0);
    }
}
