package com.example.myapplication.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.myapplication.Human;

@Entity(tableName = "human")
public class HumanEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "server_id")
    private String serverId;

    @ColumnInfo(name = "lastname")
    private String surname;

    @ColumnInfo(name = "firstname")
    private String nameFatherName;

    @ColumnInfo(name = "placeOfWork")
    private String placeOfWork;

    @ColumnInfo(name = "position")
    private String position;

    @ColumnInfo(name = "linkPDF")
    private String linkPDF;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @ColumnInfo(name = "comment")
    private String comment;

    public static HumanEntity from(Human human) {
        return new HumanEntity(human.getId(), human.getSurname(), human.getNameFatherName(), human.getPlaceOfWork(),
                human.getPosition(), human.getLinkPDF(), human.getFavorite(), human.getComment());
    }

    public HumanEntity(int id, String serverId, String surname, String nameFatherName, String placeOfWork, String position, String linkPDF, boolean favorite, String comment) {
        this.id = id;
        this.serverId = serverId;
        this.surname = surname;
        this.nameFatherName = nameFatherName;
        this.placeOfWork = placeOfWork;
        this.position = position;
        this.linkPDF = linkPDF;
        this.favorite = favorite;
        this.comment = comment;
    }

    public HumanEntity(String serverId, String surname, String namefathername, String placeOfWork, String position, String linkPDF, boolean favorite, String comment) {
        this(0, serverId, surname, namefathername, placeOfWork, position, linkPDF, favorite, comment);
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
        return "HumanEntity{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", nameFatherName='" + nameFatherName + '\'' +
                ", placeOfWork='" + placeOfWork + '\'' +
                ", position='" + position + '\'' +
                ", linkPDF='" + linkPDF + '\'' +
                ", favorite=" + favorite +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanEntity that = (HumanEntity) o;
        if (id != that.id) return false;
        if (favorite != that.favorite) return false;
        if (!serverId.equals(that.serverId)) return false;
        if (!surname.equals(that.surname)) return false;
        if (!nameFatherName.equals(that.nameFatherName)) return false;
        if (placeOfWork != null ? !placeOfWork.equals(that.placeOfWork) : that.placeOfWork != null)
            return false;
        if (position != null ? !position.equals(that.position) : that.position != null)
            return false;
        if (linkPDF != null ? !linkPDF.equals(that.linkPDF) : that.linkPDF != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + serverId.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + nameFatherName.hashCode();
        result = 31 * result + (placeOfWork != null ? placeOfWork.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (linkPDF != null ? linkPDF.hashCode() : 0);
        result = 31 * result + (favorite ? 1 : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}