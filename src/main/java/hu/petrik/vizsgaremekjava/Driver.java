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
}
