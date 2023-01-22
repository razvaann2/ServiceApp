import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Appointment_UI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox text_mechanic_id;
    private JLabel text_mechanic_name;
    private JComboBox text_car_brand;
    private JComboBox text_car_model;
    private JTextField text_client_name;
    private JTextField text_client_phone;
    private JTextField text_service;
    private JDatePicker text_start_time;
    private JDatePicker text_end_time;
    private JTextField text_car_plate;
    private JComboBox text_start_hour;
    private JComboBox text_end_hour;
    private JComboBox text_end_minute;
    private JComboBox text_start_minute;
    private JLabel mechanic_idLabel;
    private JLabel mecchanic_nameLabel;

    public Appointment_UI(JFrame parent,User user) {

        super(parent);
        setContentPane(contentPane);
        setModal(true);
        if(user.getRole().equals("mechanic"))
        {
            text_mechanic_id.setVisible(false);
            text_mechanic_name.setVisible(false);
            mechanic_idLabel.setVisible(false);
            mecchanic_nameLabel.setVisible(false);
        }
        for(int i=0;i<24;i++)
        {
            text_start_hour.addItem(i);
            text_end_hour.addItem(i);
        }
        for(int i =0;i<60;i++)
        {
            text_start_minute.addItem(i);
            text_end_minute.addItem(i);
        }

        setMinimumSize(new Dimension(900,1045));
        Vector<Integer> MechanicIDs= Database.ReturnMechanicID();
        HashSet<String> Car_brands=Database.ReturnCarBrand();
        text_mechanic_name.setText("Name");
        for(int i=0;i<MechanicIDs.size();i++)
        {
            text_mechanic_id.addItem(MechanicIDs.get(i));
        }
        Iterator<String> iterator = Car_brands.iterator();
        for(int i=0;i<Car_brands.size();i++)
        {
            String name=iterator.next();
            text_car_brand.addItem(name);
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
        text_car_brand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text_car_model.removeAllItems();
                String selectedItem=(String) text_car_brand.getSelectedItem();
                Vector<String> vector=Database.ReturnCarName(selectedItem);
                for(int i=0;i<vector.size();i++)
                {
                    text_car_model.addItem(vector.get(i));
                }
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });


        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               LocalDateTime currentTime = LocalDateTime.now();

                Date fromDate= ((Calendar) text_start_time.getModel().getValue()).getTime();
                int fromHours= (int) text_start_hour.getSelectedItem();
                int fromMinutes= (int) text_start_minute.getSelectedItem();
                Calendar calendarStart=  Calendar.getInstance();
                calendarStart.setTime(fromDate);
                calendarStart.set(Calendar.HOUR_OF_DAY,fromHours);
                calendarStart.set(Calendar.MINUTE,fromMinutes);
                calendarStart.set(Calendar.SECOND,0);
                SimpleDateFormat aux=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString=aux.format(calendarStart.getTime());
                Timestamp from=Timestamp.valueOf(dateString);



                Date toDate= ((Calendar) text_end_time.getModel().getValue()).getTime();
                int toHours= (int) text_end_hour.getSelectedItem();
                int toMinutes= (int) text_end_minute.getSelectedItem();
                Calendar calendarEnd= Calendar.getInstance();
                calendarEnd.setTime(toDate);
                calendarEnd.set(Calendar.HOUR_OF_DAY,toHours);
                calendarEnd.set(Calendar.MINUTE,toMinutes);
                calendarEnd.set(Calendar.SECOND,0);
                SimpleDateFormat aux1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStringEnd=aux1.format(calendarEnd.getTime());
                Timestamp to=Timestamp.valueOf(dateStringEnd);

                int mechanicId = (int) text_mechanic_id.getSelectedItem();


                if(user.getRole().equals("mechanic"))
                    Database.addAppointment(currentTime,user.getId_user(),user.getFirst_name()+user.getLast_name() ,(String)text_car_brand.getSelectedItem(),(String)text_car_model.getSelectedItem(),text_client_name.getText(),text_client_phone.getText(),text_service.getText(),from,to,text_car_plate.getText());

                else Database.addAppointment(currentTime,mechanicId, text_mechanic_name.getText(),(String)text_car_brand.getSelectedItem(),(String)text_car_model.getSelectedItem(),text_client_name.getText(),text_client_phone.getText(),text_service.getText(),from,to,text_car_plate.getText());
                dispose();
            }


        });
        setVisible(true);
    }}
