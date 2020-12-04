package com.p2j.MohamedApp.Model;

public class Wishlist {

    private String idClient;
    private String ArticleName;
    private Double price;
    private int quantity;
    private String imgProduct;
    private String idArticel;

    public Wishlist() {
    }

    public Wishlist(String idClient,String ArticleName, Double price, int quantity, String idArticel, String imgProduct) {
        this.idClient = idClient;
        this.ArticleName= ArticleName;
        this.price = price;
        this.quantity = quantity;
        this.imgProduct=imgProduct;
        this.idArticel = idArticel;

    }

    public String getArticleName() {
        return ArticleName;
    }

    public void setArticleName(String articleName) {
        ArticleName = articleName;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIdArticel() {
        return idArticel;
    }

    public void setIdArticel(String idArticel) {
        this.idArticel = idArticel;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "idClient='" + idClient + '\'' +
                ", ArticleName='" + ArticleName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", imgProduct='" + imgProduct + '\'' +
                ", idArticel='" + idArticel + '\'' +
                '}';
    }
}
