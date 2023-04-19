package hu.petrik.vizsgaremekjava;

public class Motor {
    private int id;
    private String leiras;
    private String motorkomponens;
    private int quantity;
    private int price;

    public Motor(int id, String leiras, String motorkomponens, int quantity, int price) {
        this.id = id;
        this.leiras = leiras;
        this.motorkomponens = motorkomponens;
        this.quantity = quantity;
        this.price = price;
    }

    public Motor(String leiras, String motorkomponens, int quantity, int price) {
        this.leiras = leiras;
        this.motorkomponens = motorkomponens;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getMotorkomponens() {
        return motorkomponens;
    }

    public void setMotorkomponens(String motorkomponens) {
        this.motorkomponens = motorkomponens;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Motor{" +
                "id=" + id +
                ", leiras='" + leiras + '\'' +
                ", motorkomponens='" + motorkomponens + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
