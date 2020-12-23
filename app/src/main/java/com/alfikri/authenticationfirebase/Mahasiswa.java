package com.alfikri.authenticationfirebase;

public class Mahasiswa {
    private String key;

    private String nama;
    private String fakultas;
    private String jurusan;
    private String semester;

    public Mahasiswa(){
    }

    public Mahasiswa(String nama, String fakultas, String jurusan, String semester) {
        this.nama = nama;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
