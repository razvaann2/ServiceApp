import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JDialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.crypto.Data;


public class MainPage extends JDialog{
    private JPanel HomePage1;
    private JPanel HomePage2;
    private JLabel JLabel1;
    private JLabel JLabel2;
    private JTable tabel_programari;
    private JDatePicker DateFrom;
    private JButton ButtonOK;
    private JButton ButtonCancel;
    private JDatePicker DateTo;
    private JLabel Text;
    private JTextField textClient;
    private JTextField textClient1;

    public MainPage(JFrame parent,User user) {
        super(parent);
        setTitle("Homepage");
        setContentPane(HomePage2);
        setMinimumSize(new Dimension(1920, 1080));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //tabel_programari.setPreferredSize(new Dimension(50,60));
        Text.setVisible(false);
        DateFrom.setVisible(false);
        DateTo.setVisible(false);
        ButtonOK.setVisible(false);
        ButtonCancel.setVisible(false);
        textClient.setVisible(false);
        textClient1.setVisible(false);

        JLabel1.setText("Signed up as:" + user.getFirst_name() + " " + user.getLast_name());
        JLabel2.setText("Role:" + user.getRole());
        JMenuBar menuBar = new JMenuBar();
        if (user.getRole().equals("owner")) {

            JMenu ownerMenu = new JMenu("Owner"); // MENIU OWNER
            menuBar.add(ownerMenu);






            JMenuItem addMechanicItem = new JMenuItem("AddMechanic"); // ADAUGARE MECHANIC CA USER
            addMechanicItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddMechanic_UI window = new AddMechanic_UI();


                }
            });
            JMenuItem deleteAppointmentItem = new JMenuItem("DeleteAppointment"); // STERGERE PROGRAMARE
            deleteAppointmentItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tabel_programari.getModel().getColumnName(0) .equals("id_appointment"))
                    {
                        int selectedRow= tabel_programari.getSelectedRow();
                        if(selectedRow!=-1)
                        {
                            int id = (int) tabel_programari.getValueAt(selectedRow, 0);
                            Database.deleteAppointment(id);
                            DefaultTableModel model = Database.show_table_Owner();
                            model.removeRow(selectedRow);
                            OwnerTable();
                        }
                        else JOptionPane.showMessageDialog(MainPage.this,"SELECT AN APPOINTMENT","WARNING",JOptionPane.ERROR_MESSAGE);

                    } else JOptionPane.showMessageDialog(MainPage.this,"WRONG TABLE","WARNING",JOptionPane.ERROR_MESSAGE);
                }
            });
            ownerMenu.add(deleteAppointmentItem);


            JMenuItem ShowTableStartingDate=new JMenuItem("Show Appointments Date"); // VEZI PROGRAMARILE DE LA DATA DE PANA LA DATA DE
            ShowTableStartingDate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Text.setText("Select Date from to:");
                    Text.setVisible(true);
                    textClient.setVisible(false);
                    DateFrom.setVisible(true);
                    DateTo.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.setVisible(true);
                    textClient1.setVisible(false);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Calendar calendar=  (Calendar) DateFrom.getModel().getValue();
                            java.util.Date utilDate = calendar.getTime();
                            Timestamp from= new Timestamp(utilDate.getTime());
                            calendar=(Calendar) DateTo.getModel().getValue();
                            utilDate = calendar.getTime();
                            Timestamp to= new Timestamp(utilDate.getTime());
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Owner_from_to(from,to);
                            tabel_programari.setModel(model);
                            DateFrom.setVisible(false);
                            DateTo.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DateFrom.setVisible(false);
                            DateTo.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });

                }
            });

            ownerMenu.add(ShowTableStartingDate);

            JMenuItem ShowTableClientName=new JMenuItem("Show Appointments From client_name"); // VEZI PROGRAMARILE CLIENTULUI CU NUMELE X
            ShowTableClientName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    Text.setText("Write name:");
                    Text.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           DefaultTableModel model=new DefaultTableModel();
                           model= Database.show_table_Owner_client_name(textClient.getText());
                           tabel_programari.setModel(model);
                           textClient.setVisible(false);
                           ButtonOK.setVisible(false);
                           ButtonCancel.setVisible(false);
                           Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });
            ownerMenu.add(ShowTableClientName);

            JMenuItem ShowTableItem=new JMenuItem("Show Table");
            ShowTableItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultTableModel model=new DefaultTableModel();
                    model=Database.show_table_Owner();
                    tabel_programari.setModel(model);
                }
            });
            ownerMenu.add(ShowTableItem);

            JMenuItem ShowTableMechanics=new JMenuItem("show mechanics");//     VEZI MECANICII
            ShowTableMechanics.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    textClient.setVisible(false);
                    textClient1.setVisible(false);
                    ButtonOK.setVisible(false);
                    ButtonCancel.setVisible(false);
                    DefaultTableModel model=new DefaultTableModel();
                    model=Database.show_table_Mechanics();
                    tabel_programari.setModel(model);
                }
            });
            ownerMenu.add(ShowTableMechanics);

            JMenuItem ShowTablecars=new JMenuItem("show cars");//     VEZI masinile
            ShowTablecars.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    textClient.setVisible(false);
                    textClient1.setVisible(false);
                    ButtonOK.setVisible(false);
                    ButtonCancel.setVisible(false);
                    DefaultTableModel model=new DefaultTableModel();
                    model=Database.show_table_cars();
                    tabel_programari.setModel(model);
                }
            });
            ownerMenu.add(ShowTablecars);


            JMenuItem ShowTableClientPhone=new JMenuItem("Show Appointments From phone"); // VEZI PROGRAMARILE CLIENTULUI CU NUMARUL DE TELEFON X
            ShowTableClientPhone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    Text.setText("Write Phone number:");
                    Text.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Owner_client_phone(textClient.getText());
                            tabel_programari.setModel(model);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });
            ownerMenu.add(ShowTableClientPhone);

            JMenuItem ShowTableClientPlate=new JMenuItem("Show Appointments From number plate"); // VEZI PROGRAMARILE CLIENTULUI CU NUMARUL DE INMATRICULARE X
            ShowTableClientPlate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    Text.setText("Write the number plate in format like AB 12 CDE OR AB 123 CDE:");
                    Text.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Owner_client_number_plate(textClient.getText());
                            tabel_programari.setModel(model);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });
            ownerMenu.add(ShowTableClientPlate);



            JMenuItem deleteCarItem = new JMenuItem("DeleteCar"); // STERGERE Masina
            deleteCarItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tabel_programari.getModel().getColumnName(0) .equals("car_name")) {
                        int selectedRow = tabel_programari.getSelectedRow();
                        if (selectedRow != -1) {
                            String id = (String) tabel_programari.getValueAt(selectedRow, 0);
                            Database.deleteCar(id);
                            DefaultTableModel model = Database.show_table_Owner();
                            //model.removeRow(selectedRow);
                            OwnerTable();
                        }
                        else JOptionPane.showMessageDialog(MainPage.this,"SELECT A CAR","WARNING",JOptionPane.ERROR_MESSAGE);
                    }else JOptionPane.showMessageDialog(MainPage.this,"WRONG TABLE","WARNING",JOptionPane.ERROR_MESSAGE);
                }
            });
            ownerMenu.add(deleteCarItem);


            JMenuItem deleteMechanicItem = new JMenuItem("DeleteMechanic"); // STERGERE Mechanic
            deleteMechanicItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(tabel_programari.getModel().getColumnName(0) .equals("id_user"))
                    {
                    int selectedRow= tabel_programari.getSelectedRow();
                    if(selectedRow!=-1)
                    {
                        int id=(int) tabel_programari.getValueAt(selectedRow,0);
                        Database.deleteMechanic(id);
                        DefaultTableModel model= Database.show_table_Owner();
                        model.removeRow(selectedRow);
                        OwnerTable();
                    }
                    else JOptionPane.showMessageDialog(MainPage.this,"SELECT A MECHANIC","WARNING",JOptionPane.ERROR_MESSAGE);
                }
                    else JOptionPane.showMessageDialog(MainPage.this,"WRONG TABLE","WARNING",JOptionPane.ERROR_MESSAGE);
                }
            });
            ownerMenu.add(deleteMechanicItem);

            JMenuItem InsertCaritem = new JMenuItem("Insert a new car");
            InsertCaritem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    textClient1.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    Text.setVisible(true);
                    Text.setText("insert car brand and then model:");

                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nume=textClient.getText();
                            String brand= textClient1.getText();
                            Database.insertCar( nume,brand);
                            DateFrom.setVisible(false);
                            DateFrom.setVisible(false);
                            textClient1.setVisible(false);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DateFrom.setVisible(false);
                            DateFrom.setVisible(false);
                            textClient1.setVisible(false);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });
            ownerMenu.add(InsertCaritem);

            JMenuItem addAppointmentitem = new JMenuItem("AddAppointment"); // ADAUGARE PROGRAMARE
            addAppointmentitem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    textClient.setVisible(false);
                    textClient1.setVisible(false);
                    //Database.addAppointment();
                   // setVisible(false);
                    Appointment_UI appointmentWindow= new Appointment_UI(null,user);

                    OwnerTable();
                    setVisible(true);

                }
            });
            ownerMenu.add(addAppointmentitem);
            OwnerTable();

        }
        if (user.getRole().equals("mechanic")) {
            JMenu mechanicMenu = new JMenu("Mechanic");
            menuBar.add(mechanicMenu);
            MechanicTable(user.getId_user());

            JMenuItem deleteAppointmentItem = new JMenuItem("DeleteAppointment"); // STERGERE PROGRAMARE
            deleteAppointmentItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tabel_programari.getModel().getColumnName(0) .equals("id_appointment"))
                    {
                        int selectedRow= tabel_programari.getSelectedRow();
                        if(selectedRow!=-1)
                        {
                            int id = (int) tabel_programari.getValueAt(selectedRow, 0);
                            Database.deleteAppointment(id);
                            DefaultTableModel model = Database.show_table_Mechanic(user.getId_user());
                            //model.removeRow(selectedRow);
                            MechanicTable(user.getId_user());
                        }
                        else JOptionPane.showMessageDialog(MainPage.this,"SELECT AN APPOINTMENT","WARNING",JOptionPane.ERROR_MESSAGE);

                    } else JOptionPane.showMessageDialog(MainPage.this,"WRONG TABLE","WARNING",JOptionPane.ERROR_MESSAGE);
                }
            });
            mechanicMenu.add(deleteAppointmentItem);

            JMenuItem ShowTableItem=new JMenuItem("Show Table");
            ShowTableItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultTableModel model=new DefaultTableModel();
                    model= Database.show_table_Mechanic( user.getId_user());
                    tabel_programari.setModel(model);
                }
            });
            mechanicMenu.add(ShowTableItem);




            JMenuItem ShowTableStartingDate=new JMenuItem("Show Appointments Date"); // VEZI PROGRAMARILE DE LA DATA DE PANA LA DATA DE
            ShowTableStartingDate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Text.setText("Select Date from to:");
                    Text.setVisible(true);
                    textClient.setVisible(false);
                    DateFrom.setVisible(true);
                    DateTo.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.setVisible(true);
                    textClient1.setVisible(false);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Calendar calendar=  (Calendar) DateFrom.getModel().getValue();
                            java.util.Date utilDate = calendar.getTime();
                            Timestamp from= new Timestamp(utilDate.getTime());
                            calendar=(Calendar) DateTo.getModel().getValue();
                            utilDate = calendar.getTime();
                            Timestamp to= new Timestamp(utilDate.getTime());
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Mechanic_from_to(from,to, user.getId_user());
                            tabel_programari.setModel(model);
                            DateFrom.setVisible(false);
                            DateTo.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DateFrom.setVisible(false);
                            DateTo.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });

                }
            });

            mechanicMenu.add(ShowTableStartingDate);
            JMenuItem ShowTableClientPlate=new JMenuItem("Show Appointments From number plate"); // VEZI PROGRAMARILE CLIENTULUI CU NUMARUL DE INMATRICULARE X
            ShowTableClientPlate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    Text.setText("Write the number plate in format like AB 12 CDE OR AB 123 CDE:");
                    Text.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Mechanic_client_number_plate(textClient.getText(),user.getId_user());
                            String aux= textClient.getText();
                            tabel_programari.setModel(model);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });
            mechanicMenu.add(ShowTableClientPlate);

            JMenuItem ShowTableClientName=new JMenuItem("Show Appointments From name"); // VEZI PROGRAMARILE CLIENTULUI CU NUMELE X
            ShowTableClientName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    Text.setText("Write name:");
                    Text.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Mechanic_client_name(textClient.getText(), user.getId_user());
                            tabel_programari.setModel(model);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });
            mechanicMenu.add(ShowTableClientName);
            JMenuItem ShowTableClientPhone=new JMenuItem("Show Appointments From phone"); // VEZI PROGRAMARILE CLIENTULUI CU NUMARUL DE TELEFON X
            ShowTableClientPhone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    Text.setText("Write Phone number:");
                    Text.setVisible(true);
                    textClient.setVisible(true);
                    ButtonOK.setVisible(true);
                    ButtonCancel.setVisible(true);
                    ButtonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel model=new DefaultTableModel();
                            model= Database.show_table_Mechanic_client_phone(textClient.getText(), user.getId_user());
                            tabel_programari.setModel(model);
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                    ButtonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textClient.setVisible(false);
                            ButtonOK.setVisible(false);
                            ButtonCancel.setVisible(false);
                            Text.setVisible(false);
                        }
                    });
                }
            });

            JMenuItem ShowTablecars=new JMenuItem("show cars");//     VEZI masinile
            ShowTablecars.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    textClient.setVisible(false);
                    textClient1.setVisible(false);
                    ButtonOK.setVisible(false);
                    ButtonCancel.setVisible(false);
                    DefaultTableModel model=new DefaultTableModel();
                    model=Database.show_table_cars();
                    tabel_programari.setModel(model);
                }
            });
           mechanicMenu.add(ShowTablecars);

            mechanicMenu.add(ShowTableClientPhone);
            JMenuItem addAppointmentitem = new JMenuItem("AddAppointment"); // ADAUGARE PROGRAMARE
            addAppointmentitem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateFrom.setVisible(false);
                    DateTo.setVisible(false);
                    textClient.setVisible(false);
                    textClient1.setVisible(false);
                    //Database.addAppointment();
                    //setVisible(false);
                    Appointment_UI appointmentWindow= new Appointment_UI(null,user);

                    MechanicTable(user.getId_user());
                    //setVisible(true);

                }
            });
            mechanicMenu.add(addAppointmentitem);
        }

        setSize(400, 300);
        setJMenuBar(menuBar);



    }

    public void OwnerTable()
    {
        //Clock clock = Clock.systemDefaultZone();
        //Instant instant = clock.instant();
        //String currentTime = instant.toString();
        //String[] columnNames = {"data_created","Mechanic","Car_model","client_name","client_phone","service","start_time","end_time","car_plate"};
        //Object[][] data={ {currentTime,"Aurel Turbina","a4","Khal Drogos","0734691950","Revizie","1/9/2023 10:00","1/9/2023 11:00","BZ 20 VIO"}

        //};
        //tabel_programari.setModel(new DefaultTableModel(data,columnNames));
        DefaultTableModel model=new DefaultTableModel();
        model= Database.show_table_Owner();
        tabel_programari.setModel(model);



    }
    public void MechanicTable(int id)
    {
        //Clock clock = Clock.systemDefaultZone();
        //Instant instant = clock.instant();
        //String currentTime = instant.toString();
        //String[] columnNames = {"data_created","Mechanic","Car_model","client_name","client_phone","service","start_time","end_time","car_plate"};
        //Object[][] data={ {currentTime,"Aurel Turbina","a4","Khal Drogos","0734691950","Revizie","1/9/2023 10:00","1/9/2023 11:00","BZ 20 VIO"}

        //};
        //tabel_programari.setModel(new DefaultTableModel(data,columnNames));
        DefaultTableModel model=new DefaultTableModel();
        model= Database.show_table_Mechanic(id);
        tabel_programari.setModel(model);



    }

}
