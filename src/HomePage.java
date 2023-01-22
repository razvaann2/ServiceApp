import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JDialog{
    private JTextField txt;
    private JPasswordField pass;
    private JButton butonok;
    private JButton cancelButton;
    private JPanel LoginPanel;
    public User user;
    public HomePage(JFrame parent)
    {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(800 ,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        butonok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database.connectionDb();
                String username= txt.getText();
                String password= String.valueOf(pass.getPassword());
                   user=Database.getAuthenticatedUser(username,password);
                    if(user!=null){
                        //LoginPanel.setVisible(false);
                        dispose();
                        MainPage mainPage=new MainPage(null,user);

                    mainPage.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(HomePage.this,"Warning","Wrong creditals",JOptionPane.ERROR_MESSAGE);
                    }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    }

