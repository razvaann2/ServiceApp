public class Mechanic {
   private  int id_mechanic;
    private String first_name,last_name;
    public Mechanic(String first_name, String last_name)
    {
    this.first_name=first_name;
    this.last_name=last_name;
    }
    public void setId_mechanic(int id_mechanic)
    {
        this.id_mechanic=id_mechanic;
    }
    public void setFirst_name(String first_name)
    {
        this.first_name=first_name;
    }
    public void setLast_name(String last_name)
    {
        this.last_name=last_name;
    }
    public int getId_mechanic()
    {
        return  id_mechanic;
    }
    public String getFirst_name()
    {
        return first_name;
    }
    public String getLast_name()
    {
        return last_name;
    }
}
