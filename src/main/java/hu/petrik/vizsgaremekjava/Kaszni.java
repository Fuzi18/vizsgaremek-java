package hu.petrik.vizsgaremekjava;

public class Kaszni {
    private int id;
    private String leiras;
    private String kasznikomponens;
    private int quantity;
    private int price;

    public Kaszni(int id, String leiras, String kasznikomponens, int quantity, int price) {
        this.id = id;
        this.leiras = leiras;
        this.kasznikomponens = kasznikomponens;
        this.quantity = quantity;
        this.price = price;
    }

    public Kaszni(String leiras, String kasznikomponens, int quantity, int price) {
        this.leiras = leiras;
        this.kasznikomponens = kasznikomponens;
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

    public String getKasznikomponens() {
        return kasznikomponens;
    }

    public void setKasznikomponens(String kasznikomponens) {
        this.kasznikomponens = kasznikomponens;
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
        return "Kaszni{" +
                "id=" + id +
                ", leiras='" + leiras + '\'' +
                ", kasznikomponens='" + kasznikomponens + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
