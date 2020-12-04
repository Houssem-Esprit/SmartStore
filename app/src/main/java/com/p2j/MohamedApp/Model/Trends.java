package com.p2j.MohamedApp.Model;

public class Trends {

    private  String idarticle;
    private String nameArticle;
    private String imgArticle;
    private String firstName;
    private String img;

    public Trends() {
    }

    public Trends(String idarticle, String nameArticle, String imgArticle, String firstName, String img) {
        this.idarticle = idarticle;
        this.nameArticle = nameArticle;
        this.imgArticle = imgArticle;
        this.firstName = firstName;
        this.img = img;
    }

    public String getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(String idarticle) {
        this.idarticle = idarticle;
    }

    public String getNameArticle() {
        return nameArticle;
    }

    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getImgArticle() {
        return imgArticle;
    }

    public void setImgArticle(String imgArticle) {
        this.imgArticle = imgArticle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Trends{" +
                "idarticle='" + idarticle + '\'' +
                ", nameArticle='" + nameArticle + '\'' +
                ", imgArticle='" + imgArticle + '\'' +
                ", firstName='" + firstName + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
