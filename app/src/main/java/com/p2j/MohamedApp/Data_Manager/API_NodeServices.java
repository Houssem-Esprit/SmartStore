package com.p2j.MohamedApp.Data_Manager;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API_NodeServices {


    @FormUrlEncoded
    @POST("SignUp")
    Call<Object> Signup(
            @Field("cin") String cin,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("login") String login,
            @Field("password") String password,
            @Field("img") String img,
            @Field("role") int role,
            @Field("bluetoothMac") String bluetoothMac

    );

    @FormUrlEncoded
    @POST("login")
    Call<Object> Login(
            @Field("login") String login,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("getuserdetails")
    Call<Object> GetUserDetails(
            @Field("cin") String cin
    );


    @Multipart
    @POST("/uploadUserImg/{id}")
    Call<ResponseBody> uploadImageUser(@Part MultipartBody.Part image, @Part("upload") RequestBody name, @Path("id") String id);

    @Multipart
    @POST("/uploadArticleImg/{id}")
    Call<ResponseBody> uploadImageArticle(@Part MultipartBody.Part image, @Part("upload") RequestBody name, @Path("id") String id);


    @POST("getStores")
    Call<Object> GetStores(
    );


    @FormUrlEncoded
    @POST("addArticle")
    Call<Object> AddArticle(
            @Field("IdArticle") String IdArticle,
            @Field("name") String ArticleName,
            @Field("color") int ArticleColor,
            @Field("quantity") int ArticleQuantity,
            @Field("price") Double ArticlePrice,
            @Field("dispo") int ArticleAvailable,
            @Field("imgArticle") String ArticleImg,
            @Field("idUser") String idUser

    );

    @FormUrlEncoded
    @POST("getuserArticles")
    Call<Object> getStoreArticles(
            @Field("cin") String Cin
    );


    @FormUrlEncoded
    @POST("AddToPanier")
    Call<Object> AddToPanier(
            @Field("idClient") String idClient,
            @Field("ArticleName") String name,
            @Field("price") Double Price,
            @Field("quantity") int Quantity,
            @Field("imageArticle") String imgArticle,
            @Field("idarticle") String idArticle
    );


    @FormUrlEncoded
    @POST("getUserWishlist")
    Call<Object> getUserWishlist(
            @Field("idClient") String idClient
    );


    @FormUrlEncoded
    @POST("getUserTotalWishlist")
    Call<Object> getTotalWishlist(
            @Field("idClient") String idClient
    );

    @FormUrlEncoded
    @POST("DeleteWishList")
    Call<Object> DeleteWishList(
            @Field("idarticle") String idArticle
    );

    @FormUrlEncoded
    @POST("UpdateWishList")
    Call<Object> ModifyWishList(
            @Field("idarticle") String idArticle,
            @Field("quantity") int Quantity
    );


    @FormUrlEncoded
    @POST("getArticleByID")
    Call<Object> getArticleByID(
            @Field("IdArticle") String idArticle
    );

    @FormUrlEncoded
    @POST("getArticleByID2")
    Call<Object> getArticleByID_EditAr(
            @Field("IdArticle") String idArticle
    );


    @FormUrlEncoded
    @POST("UpdatArticle")
    Call<Object> ModifyArticle(
            @Field("idarticle") String idArticle,
            @Field("name") String name,
            @Field("color") int color,
            @Field("quantity") int quantity,
            @Field("price") Double price,
            @Field("dispo") int dispo

    );

    @POST("getTrends")
    Call<Object> getTrends(
    );


    @FormUrlEncoded
    @POST("UpdateStoreBT")
    Call<Object> UpdateStoreBT(
            @Field("cin") String cin,
            @Field("bluetoothMac") String bluetoothMac
    );


    @FormUrlEncoded
    @POST("getStoreBluetooth")
    Call<Object> getStoreBluetooth(
            @Field("cin") String cin
    );


    @FormUrlEncoded
    @POST("getNotifWishlist")
    Call<Object> getNotifWishlist(
            @Field("cinStore") String idStore,
            @Field("cinClient") String idClient

    );

    @FormUrlEncoded
    @POST("getTotalSinglewhishList")
    Call<Object> getTotalSinglewhishList(
            @Field("cinStore") String idStore,
            @Field("cinClient") String idClient
    );

}
