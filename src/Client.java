public class Client {
    private String client_name,client_phone;
    private int id_client;
    public Client(String client_name, String client_phone)
    {
        this.client_name=client_name;
        this.client_phone=client_phone;
    }
    public Client(String client_name)
    {
        this.client_name=client_name;
        client_phone="not available";
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
    public void setId_client(int id_client)
    {
        this.id_client=id_client;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public int getId_client() {
        return id_client;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getClient_phone() {
        return client_phone;
    }
}
