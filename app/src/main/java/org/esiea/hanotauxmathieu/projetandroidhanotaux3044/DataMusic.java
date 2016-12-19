package org.esiea.hanotauxmathieu.projetandroidhanotaux3044;

/**
 * Created by mat on 18/12/2016.
 */

public class DataMusic {
    private String nomMusic="";
    private String nomAuteur="";
    private String nomAlbum="";
    private String lienBigImg="";
    private String lienPetitImg="";
    private String ID="";
    private int duree = 0;
    private int popularity = 0;

    public DataMusic(String nomMusic, String nomAuteur, String nomAlbum, String lienPetitImg, String ID) {
        this.nomMusic = nomMusic;
        this.nomAuteur = nomAuteur;
        this.nomAlbum = nomAlbum;
        this.lienPetitImg = lienPetitImg;
        this.ID = ID;
    }

    public DataMusic (DataMusic music){
        this.nomMusic = music.nomMusic;
        this.nomAuteur = music.nomAuteur;
        this.nomAlbum = music.nomAlbum;
        this.lienPetitImg = music.lienPetitImg;
        this.ID = music.ID;
    }

    public DataMusic() {

    }

    @Override
    public String toString (){
        return "Nom musique : " + nomMusic;
    }

    public String getNomMusic() {
        return nomMusic;
    }

    public void setNomMusic(String nomMusic) {
        this.nomMusic = nomMusic;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getNomAlbum() {
        return nomAlbum;
    }

    public void setNomAlbum(String nomAlbum) {
        this.nomAlbum = nomAlbum;
    }

    public String getLienBigImg() {
        return lienBigImg;
    }

    public void setLienBigImg(String lienBigImg) {
        this.lienBigImg = lienBigImg;
    }

    public String getLienPetitImg() {
        return lienPetitImg;
    }

    public void setLienPetitImg(String lienPetitImg) {
        this.lienPetitImg = lienPetitImg;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
