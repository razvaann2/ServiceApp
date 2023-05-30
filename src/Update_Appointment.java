import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
public class Update_Appointment extends JDialog{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel mechanic_idLabel;
    private JComboBox text_mechanic_id;
    private JLabel mecchanic_nameLabel;
    private JComboBox text_car_brand;
    private JComboBox text_car_model;
    private JLabel text_mechanic_name;
    private JTextField text_client_name;
    private JTextField text_client_phone;
    private JTextField text_service;
    private JDatePicker text_start_time;
    private JDatePicker text_end_time;
    private JTextField text_car_plate;
    private JComboBox text_start_hour;
    private JComboBox text_start_minute;
    private JComboBox text_end_minute;
    private JComboBox text_end_hour;
    private JTextField textappointmentid;


    public Update_Appointment()
    {
        setContentPane(contentPane);
        setModal(true);



        setMinimumSize(new Dimension(900,1045));
        Vector<Integer> MechanicIDs= Database.ReturnMechanicID();
        HashSet<String> Car_brands=Database.ReturnCarBrand();
        text_mechanic_name.setText("Name");
        for(int i=0;i<MechanicIDs.size();i++)
        {
            text_mechanic_id.addItem(MechanicIDs.get(i));
        }



        text_mechanic_id.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedItem = (int) text_mechanic_id.getSelectedItem();
                String aux=Database.ReturnName(selectedItem);
                text_mechanic_name.setText(aux);
                text_mechanic_name.repaint();
                text_mechanic_name.revalidate();

            }
        });


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
        int appointmentId = Integer.parseInt(textappointmentid.getText());
        int mechanicId = Integer.parseInt(text_mechanic_id.getSelectedItem().toString());
        String mechanicName = text_mechanic_name.getText();
        Database.updateAppointment(appointmentId, mechanicId, mechanicName);
        dispose();
    }




    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
