package com.example.worktalk.Models;

public class MesajlarımModel {
    private String adSoyad,profilFoto,time,enSonMesaj;
    boolean seen;



    public MesajlarımModel(String adSoyad ,String enSonMesaj, String profilFoto, String time, boolean seen) {
        this.adSoyad = adSoyad;
        this.enSonMesaj = enSonMesaj;
        this.profilFoto = profilFoto;
        this.time = time;
        this.seen = seen;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }


    public String getEnSonMesaj() {
        return enSonMesaj;
    }

    public void setEnSonMesaj(String enSonMesaj) {
        this.enSonMesaj = enSonMesaj;
    }

    public String getProfilFoto() {
        return profilFoto;
    }

    public void setProfilFoto(String profilFoto) {
        this.profilFoto = profilFoto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public MesajlarımModel() {

    }

    @Override
    public String toString() {
        return "MesajlarımModel{" +
                "adSoyad='" + adSoyad + '\'' +
                ", profilFoto='" + profilFoto + '\'' +
                ", time='" + time + '\'' +
                ", sonGelenMesaj='" + enSonMesaj + '\'' +
                ", seen=" + seen +
                '}';
    }
}
