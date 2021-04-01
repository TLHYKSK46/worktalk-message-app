package com.example.worktalk.Models;

public class SirketModel {
    private String sirketKey,sirketAdi,sirketUnvani,sirketTanim,sirketFaliyetAlani,sirketadresi;

    public SirketModel() {

    }

    public SirketModel(String sirketKey, String sirketAdi, String sirketUnvani, String sirketTanim, String sirketFaliyetAlani, String sirketadresi) {
        this.sirketKey = sirketKey;
        this.sirketAdi = sirketAdi;
        this.sirketUnvani = sirketUnvani;
        this.sirketTanim = sirketTanim;
        this.sirketFaliyetAlani = sirketFaliyetAlani;
        this.sirketadresi = sirketadresi;
    }

    public String getSirketKey() {
        return sirketKey;
    }

    public void setSirketKey(String sirketKey) {
        this.sirketKey = sirketKey;
    }

    public String getSirketAdi() {
        return sirketAdi;
    }

    public void setSirketAdi(String sirketAdi) {
        this.sirketAdi = sirketAdi;
    }

    public String getSirketUnvani() {
        return sirketUnvani;
    }

    public void setSirketUnvani(String sirketUnvani) {
        this.sirketUnvani = sirketUnvani;
    }

    public String getSirketTanim() {
        return sirketTanim;
    }

    public void setSirketTanim(String sirketTanim) {
        this.sirketTanim = sirketTanim;
    }

    public String getSirketFaliyetAlani() {
        return sirketFaliyetAlani;
    }

    public void setSirketFaliyetAlani(String sirketFaliyetAlani) {
        this.sirketFaliyetAlani = sirketFaliyetAlani;
    }

    public String getSirketadresi() {
        return sirketadresi;
    }

    public void setSirketadresi(String sirketadresi) {
        this.sirketadresi = sirketadresi;
    }

    @Override
    public String toString() {
        return "SirketModel{" +
                "sirketKey='" + sirketKey + '\'' +
                ", sirketAdi='" + sirketAdi + '\'' +
                ", sirketUnvani='" + sirketUnvani + '\'' +
                ", sirketTanim='" + sirketTanim + '\'' +
                ", sirketFaliyetAlani='" + sirketFaliyetAlani + '\'' +
                ", sirketadresi='" + sirketadresi + '\'' +
                '}';
    }
}
