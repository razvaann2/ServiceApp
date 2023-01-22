import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;

public class appointment {
    Clock clock = Clock.systemDefaultZone();
    Instant instant = clock.instant();
    public String date_created;
    public int id_appointment;
    public int mechanic_id;
    public String car_model;
    public String car_brand;
    public int client_id;
    public String client_name;
    public String client_phone;
    public Timestamp start_time;
    public Timestamp end_time;
    public String car_plate;
    public appointment(int id_appointment,int mechanic_id, String car_model,String car_brand,int client_id,String client_name,String client_phone,Timestamp start_time,Timestamp end_time)
    {
        this.id_appointment=id_appointment;
        this.mechanic_id=mechanic_id;
        this.car_model=car_model;
        this.car_brand=car_brand;
        this.client_id=client_id;
        this.client_name=client_name;
        this.client_phone=client_phone;
        this.start_time=start_time;
        this.end_time=end_time;
    }
    public void setId_appointment(int id_appointment) {
        this.id_appointment = id_appointment;
    }

    public int getId_appointment() {
        return id_appointment;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setMechanic_id(int mechanic_id) {
        this.mechanic_id = mechanic_id;
    }

    public int getMechanic_id() {
        return mechanic_id;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }
}

