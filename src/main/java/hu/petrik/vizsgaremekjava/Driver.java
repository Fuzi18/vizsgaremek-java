package hu.petrik.vizsgaremekjava;

public class Driver {
    private int id;
    private String nev;
    private int kor;
    private String nemzetiseg;
    private String csapat;
    private int szerzettpontok;
    private String kategoria;
    private int helyezes;

    public Driver(int id, String nev, int kor, String nemzetiseg, String csapat, int szerzettpontok, String kategoria, int helyezes) {
        this.id = id;
        this.nev = nev;
        this.kor = kor;
        this.nemzetiseg = nemzetiseg;
        this.csapat = csapat;
        this.szerzettpontok = szerzettpontok;
        this.kategoria = kategoria;
        this.helyezes = helyezes;
    }

    public Driver(String nev, int kor, String nemzetiseg, String csapat, int szerzettpontok, String kategoria, int helyezes) {
        this.nev = nev;
        this.kor = kor;
        this.nemzetiseg = nemzetiseg;
        this.csapat = csapat;
        this.szerzettpontok = szerzettpontok;
        this.kategoria = kategoria;
        this.helyezes = helyezes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getKor() {
        return kor;
    }

    public void setKor(int kor) {
        this.kor = kor;
    }

    public String getNemzetiseg() {
        return nemzetiseg;
    }

    public void setNemzetiseg(String nemzetiseg) {
        this.nemzetiseg = nemzetiseg;
    }

    public String getCsapat() {
        return csapat;
    }

    public void setCsapat(String csapat) {
        this.csapat = csapat;
    }

    public int getSzerzettpontok() {
        return szerzettpontok;
    }

    public void setSzerzettpontok(int szerzettpontok) {
        this.szerzettpontok = szerzettpontok;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public int getHelyezes() {
        return helyezes;
    }

    public void setHelyezes(int helyezes) {
        this.helyezes = helyezes;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", nev='" + nev + '\'' +
                ", kor=" + kor +
                ", nemzetiseg='" + nemzetiseg + '\'' +
                ", csapat='" + csapat + '\'' +
                ", szerzettpontok=" + szerzettpontok +
                ", kategoria='" + kategoria + '\'' +
                ", helyezes=" + helyezes +
                '}';
    }
}
