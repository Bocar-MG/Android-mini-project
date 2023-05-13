package com.groupe.eshop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Article implements Parcelable {

    private String Nom;
    private String Description;
    private Double Prix;
    private String image_url;
     public Article()
     {

     }
    public Article(String nom, String description, Double prix, String image_url) {
        Nom = nom;
        Description = description;
        Prix = prix;
        this.image_url = image_url;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrix() {
        return Prix;
    }

    public void setPrix(Double prix) {
        Prix = prix;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return this.Nom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
         parcel.writeString(Nom);
        parcel.writeString(Description);
        parcel.writeDouble(Prix);
        parcel.writeString(image_url);


    }
    protected Article(Parcel in) {
        Nom = in.readString();
        Description = in.readString();
        Prix = in.readDouble();
        image_url = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
