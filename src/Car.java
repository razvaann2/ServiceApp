public class Car {
    private String car_name,car_brand;
    public Car(String car_name,String car_brand)
    {
        this.car_name=car_name;
        this.car_brand=car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public String getCar_name() {
        return car_name;
    }
}
