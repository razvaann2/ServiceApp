import javax.swing.*;
import java.awt.event.*;

public class Update_Car extends JDialog {
    private JTextField textCarModel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textCarBrand;
    private JPanel contentPane;

    public Update_Car()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    private void onOK() {
        Database.updateCar(textCarModel.getText(),textCarBrand.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
