package com.p2j.MohamedApp.Model;

public class Store {

    private int IdStore;
    private String StoreName;
    private int StoreImg;

    public Store() {
    }

    public Store(int idStore, String storeName, int storeImg) {
        IdStore = idStore;
        StoreName = storeName;
        StoreImg = storeImg;
    }

    public int getIdStore() {
        return IdStore;
    }

    public void setIdStore(int idStore) {
        IdStore = idStore;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public int getStoreImg() {
        return StoreImg;
    }

    public void setStoreImg(int storeImg) {
        StoreImg = storeImg;
    }

    @Override
    public String toString() {
        return "Store{" +
                "IdStore=" + IdStore +
                ", StoreName='" + StoreName + '\'' +
                ", StoreImg=" + StoreImg +
                '}';
    }
}
