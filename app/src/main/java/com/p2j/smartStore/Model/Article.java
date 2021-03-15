package com.p2j.smartStore.Model;

public class Article {

    private String IdArticle;
    private String name;
    private int color;
    private int quantity;
    private Double price;
    private int dispo;
    private String imgArticle;
    private String idUser;

    public Article() {
    }

    public Article(String idArticle, String name, int color, int quantity, Double price, int dispo, String imgArticle, String idUser) {
        IdArticle = idArticle;
        this.name = name;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.dispo = dispo;
        this.imgArticle = imgArticle;
        this.idUser = idUser;
    }

    public String getIdArticle() {
        return IdArticle;
    }

    public void setIdArticle(String idArticle) {
        IdArticle = idArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    public String getImgArticle() {
        return imgArticle;
    }

    public void setImgArticle(String imgArticle) {
        this.imgArticle = imgArticle;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }


    @Override
    public String toString() {
        return "Article{" +
                "IdArticle='" + IdArticle + '\'' +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", quantity=" + quantity +
                ", price=" + price +
                ", dispo=" + dispo +
                ", imgArticle='" + imgArticle + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}
