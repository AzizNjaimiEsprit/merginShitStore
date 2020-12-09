package Beans;

import java.util.Objects;

public class Client extends User {
    private int idClient;
    private String address;
    private int zipCode;
    public static Client client=null;

    public Client(){
        super();
    }
    public Client(int id, String fullName, String email, String telephone, String login, String password,int idClient,String address, int zipCode) {
        super(id,fullName,email,telephone,login,password);
        this.idClient=idClient;
        this.address = address;
        this.zipCode = zipCode;
    }
    public Client(int id, String fullName, String email, String telephone, String login, String password,String address, int zipCode) {
        super(id,fullName,email,telephone,login,password);
        this.address = address;
        this.zipCode = zipCode;
    }
    public Client( String fullName, String email, String telephone, String login, String password, int role,String address, int zipCode) {
        super(fullName,email,telephone,login,password,role);
        this.address = address;
        this.zipCode = zipCode;
    }
    public Client( String fullName, String email, String telephone, String login, String password,String address, int zipCode) {
        super(fullName,email,telephone,login,password);
        this.address = address;
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return idClient == client.idClient &&
                zipCode == client.zipCode &&
                address.equals(client.address);
    }


    @Override
    public int hashCode() {
        return Objects.hash(idClient, address, zipCode);
    }

    @Override
    public String toString() {
        return "Client{" +
                "User='" + super.toString() + '\'' +
                "address='" + address + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}
