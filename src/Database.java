import com.sun.source.tree.StatementTree;

import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Vector;

public class Database {
    private static Connection connection;

    static public void connectionDb() {
        connection = null;
        String host = "localhost";
        String port = "5432";
        String db_name = "service";
        String username = "postgres";
        String password = "qwe123";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":" + port + "/" + db_name,
                    username,
                    password
            );
            if (connection != null) {
                System.out.println("Connection OK");
            } else {
                System.out.println("Connection failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void insertClient(String client_name, String client_phone) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("INSERT INTO public.client (client_name,client_phone) VALUES ('" + client_name + "','" + client_phone + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void insertCar(String car_name, String car_brand) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("INSERT INTO public.car (car_name,car_brand) VALUES ('" + car_name + "','" + car_brand + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static public void addSchedule(int id_mechanic, LocalDateTime from, LocalDateTime to) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("INSERT INTO public.Schedule (id_mechanic,from,to) VALUES ('" + id_mechanic + "','" + from + "','" + to + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void addAppointment(LocalDateTime data_created1, int mechanic_id, String mechanic_name, String car_model, String car_brand, String client_name, String client_phone, String service, Timestamp start_time, Timestamp end_time, String car_plate) {
        Statement stmt;
        try {
            Timestamp data_created = Timestamp.valueOf(data_created1);
            //Timestamp start_time=Timestamp.valueOf(start_time1);
            //Timestamp end_time=Timestamp.valueOf(end_time1);
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("INSERT INTO public.Appointment (date_created,mechanic_id,mechanic_name,car_model,car_brand,client_name,client_phone,service,start_time,end_time,car_plate) VALUES ('" + data_created + "','" + mechanic_id + "','" + mechanic_name + "','" + car_model + "','" + car_brand + "','" + client_name + "','" + client_phone + "','" + service + "','" + start_time + "','" + end_time + "','" + car_plate + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void updateAppointment(int appointment_id,  int mechanic_id, String mechanic_name) {
        try {
            Statement stmt = connection.createStatement();
            String query = "UPDATE public.appointment SET " +
                    "mechanic_id = '" + mechanic_id + "', " +
                    "mechanic_name = '" + mechanic_name + "' " +
                    "WHERE id_appointment = " + appointment_id;
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void updateMechanic(int mechanic_id, String mechanic_name, String password) {
        try {

            Statement stmt = connection.createStatement();
            String query = "UPDATE public.user SET " +
                    "username = '" + mechanic_name + "', " +
                    "password = '" + password +"' " +
                    "WHERE id_user = " + mechanic_id;
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    static public void deleteUser(int user_id) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("DELETE FROM public.user WHERE id_user='" + user_id + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void deleteAppointment(int Appointment_id) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("DELETE FROM public.appointment WHERE id_Appointment='" + Appointment_id + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void deleteCar(String carname) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("DELETE FROM public.car WHERE car_name='" + carname + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void deleteMechanic(int id) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate
                    ("DELETE FROM public.user WHERE id_user='" + id + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        static public DefaultTableModel show_table_Owner_mechanic() {
            Statement stmt;
            DefaultTableModel model = null;
            try {
                stmt = connection.createStatement();
                model = new DefaultTableModel();
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE role=mechanic");
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] columnNames = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = metaData.getColumnName(i);
                }
                model.setColumnIdentifiers(columnNames);
                while (resultSet.next()) {
                    Object[] rowData=new Object[columnCount];
                    for (int col = 0; col < columnCount; col++) {
                        rowData[col] = resultSet.getObject(col + 1);
                    }
                    model.addRow(rowData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return model;
        }


        static public User getAuthenticatedUser(String username, String password) {
        User user = null;
        Statement stmt;
        try {
            stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.user WHERE username='" + username + "' AND password = '" + password + "'");
            if (resultSet.next()) {
                user = new User();
                user.setFirst_name(resultSet.getString("first_name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setId_user(resultSet.getInt("id_user"));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    static public DefaultTableModel show_table_Owner() {
        Statement stmt;
        DefaultTableModel model = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_Owner_client_name(String name) {
        Statement stmt;
        DefaultTableModel model = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE client_name='"+name+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_Mechanics() {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT id_user,first_name,last_name FROM public.user WHERE role='mechanic' ");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_cars() {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT car_name,car_brand FROM public.car");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    static public DefaultTableModel show_table_Mechanic_client_name(String name,int id) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE client_name='"+name+"' AND mechanic_id='"+id+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_Owner_client_phone(String name) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE client_phone='"+name+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    static public DefaultTableModel show_table_Mechanic_client_phone(String name,int id) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE client_phone='"+name+"' AND mechanic_id='"+id+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    static public DefaultTableModel show_table_Owner_client_number_plate(String name) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE car_plate='"+name+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_Mechanic_client_number_plate(String name,int id) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE car_plate='"+name+"' AND mechanic_id='"+id+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_Owner_from_to(Timestamp from,Timestamp to) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE start_time>='"+from+"' AND start_time<='"+to+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    static public DefaultTableModel show_table_Mechanic_from_to(Timestamp from,Timestamp to,int id) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment WHERE start_time>='"+from+"' AND start_time<='"+to+"' AND mechanic_id='"+id+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
    static public DefaultTableModel show_table_Mechanic(int mechanic_id) {
        Statement stmt;
        DefaultTableModel model = null;
        try {
            stmt = connection.createStatement();
            model = new DefaultTableModel();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public.appointment where mechanic_id='"+mechanic_id+"'");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);
            while (resultSet.next()) {
                Object[] rowData=new Object[columnCount];
                for (int col = 0; col < columnCount; col++) {
                    rowData[col] = resultSet.getObject(col + 1);
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    static public void addMechanic(String first_name,String last_name,String username, String password) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.execute("INSERT INTO public.user (first_name, last_name, username, password,role) VALUES ('"+first_name+"','"+last_name+"','"+username+"','"+password+"' ,'mechanic');");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void updateCar(String car_model,String car_brand) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.execute("UPDATE public.car SET car_brand = '" + car_brand + "' WHERE car_name = '" + car_model + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public Vector<Integer>ReturnMechanicID()
    { Vector<Integer> vector=new Vector<>();
        Statement stmt;
        try{
            stmt= connection.createStatement();
           ResultSet resultSet= stmt.executeQuery("SELECT id_user from public.user WHERE role = 'mechanic'");
            while (resultSet.next())
            {
            vector.add(resultSet.getInt("id_user"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return  vector;
    }

    static public HashSet<String> ReturnCarBrand()
    { HashSet<String> vector=new HashSet<>();
        Statement stmt;
        try{
            stmt= connection.createStatement();
            ResultSet resultSet= stmt.executeQuery("SELECT car_brand from public.car ");
            while (resultSet.next())
            {
                vector.add(resultSet.getString("car_brand"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return vector;
    }
    static public Vector<String>ReturnCarName(String brand)
    { Vector<String> vector=new Vector<>();
        Statement stmt;
        try{
            stmt= connection.createStatement();
            ResultSet resultSet= stmt.executeQuery("SELECT car_name from public.car WHERE car_brand = '"+brand+"'");
            while (resultSet.next())
            {
                vector.add(resultSet.getString("car_name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return  vector;
    }
    static public String ReturnName(int id)
    {   String aux = null;
        Statement stmt;
        try{

            stmt= connection.createStatement();
            ResultSet resultSet= stmt.executeQuery("SELECT first_name from public.user WHERE id_user= '"+id+"'");
            while(resultSet.next())
            {
            aux=resultSet.getString("first_name");
            }
            resultSet=stmt.executeQuery("SELECT last_name from public.user WHERE id_user='"+id+"'");
            aux=aux+" ";
            while (resultSet.next())
            {
                aux=aux+ resultSet.getString("last_name");
            }


        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return  aux;
    }
}





