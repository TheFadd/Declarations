package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Human {

    @SerializedName("id")
    private String id;
    @SerializedName("lastname")
    private String surname;
    @SerializedName("firstname")
    private String nameFatherName;
    @SerializedName("placeOfWork")
    private String placeOfWork;
    @SerializedName("position")
    private String position;
    @SerializedName("linkPDF")
    private String linkPDF;
    private boolean favorite;
    private String comment;

    public Human(String id, String surname, String nameFatherName, String placeOfWork, String position, String linkPDF, boolean favorite, String comment) {
        this.id = id;
        this.surname = surname;
        this.nameFatherName = nameFatherName;
        this.placeOfWork = placeOfWork;
        this.position = position;
        this.linkPDF = linkPDF;
        this.favorite = favorite;
        this.comment = comment;
    }

    public String getSurname() {
        return surname;
    }

    public String getNameFatherName() {
        return nameFatherName;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public String getPosition() {
        return position;
    }

    public String getLinkPDF() {
        return linkPDF;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public String getComment() {
        return comment;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNameFatherName(String nameFatherName) {
        this.nameFatherName = nameFatherName;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setLinkPDF(String linkPDF) {
        this.linkPDF = linkPDF;
    }

    public void setFavourite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Human{" +
                ", surname='" + surname + '\'' +
                ", nameFatherName='" + nameFatherName + '\'' +
                ", placeOfWork='" + placeOfWork + '\'' +
                ", position='" + position + '\'' +
                ", linkPDF='" + linkPDF + '\'' +
                ", favorite=" + favorite +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
