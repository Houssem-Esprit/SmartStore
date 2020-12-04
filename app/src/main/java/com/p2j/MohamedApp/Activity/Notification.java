package com.p2j.MohamedApp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.p2j.MohamedApp.Adapters.AdapterNotifWishList;
import com.p2j.MohamedApp.Adapters.AdapterWishList;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.Wishlist;
import com.p2j.MohamedApp.R;
import com.p2j.MohamedApp.Utils.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Notification extends AppCompatActivity {
    Bundle extra;
    String StoreID;
    TextView StoreName;
    TextView NotifDate;
    CircleImageView StoreImg;
    TextView TotalPanierbyStore;
    RecyclerView Notif_wishlist_recyclerView;
    AdapterNotifWishList adapterNotifWishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Notif_wishlist_recyclerView = findViewById(R.id.notif_wishlist_recyclerView);
        StoreImg = findViewById(R.id.notif_StoreImage);
        StoreName= findViewById(R.id.notif_storeName);
        NotifDate= findViewById(R.id.notif_date);
        TotalPanierbyStore = findViewById(R.id.Totalpanier);
        //*************** get the intent extra input *******************
        extra = getIntent().getExtras();
        if (extra != null){
            StoreID= extra.getString("StoreID");
            getStore();
            loadWishList LoadNotifWishList = new loadWishList();
            LoadNotifWishList.execute();

            //********************************** retrieve the total ***************************************

            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .getTotalSinglewhishList(StoreID,Session.getInstance().getUser().getCin());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("TotalwishlistInformations");
                        if(msg.equals("Total Wishlist retrieved"))
                        {
                            JSONArray article=jsonResp.getJSONArray("Total");

                            JSONObject obj = article.getJSONObject(0);
                            TotalPanierbyStore.setText(obj.getString("Total")+'\t'+"DT");




                            //   shimmerFrameLayout.stopShimmer();
                            //shimmerFrameLayout.setVisibility(View.GONE);
                        }
                        else{}



                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

            //********************************** retrieve the total ***************************************//
        }

    }


     public void getStore(){

        try {

            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .GetUserDetails(StoreID);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {
                        JSONObject jsonResp =new JSONObject(new Gson().toJson(response.body()));
                        String msg = jsonResp.getString("userInformations");
                        if (msg.equals("User retrieved")){

                            //Toast.makeText(getApplicationContext(),"Loading..",Toast.LENGTH_LONG).show();
                            //JSONArray store=jsonResp.getJSONArray("user");
                            JSONObject obj = jsonResp.getJSONObject("user");
                            StoreName.setText(obj.getString("firstName"));
                            Toast.makeText(Notification.this, ""+obj.getString("firstName"), Toast.LENGTH_SHORT).show();
                            String url = "http://localhost:3001/uploads/users/"+ obj.getString("img");
                            Glide.with(getApplicationContext()).load(url).into(StoreImg);
                            Date currentTime = Calendar.getInstance().getTime();
                            NotifDate.setText(currentTime.toString());


                        }  else
                        {
                            Toast.makeText(getApplicationContext(),"Error !",Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e){
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("onFailure msg",": "+t.getMessage());
                    // Toast.makeText(getApplicationContext(), "onFailure zone", Toast.LENGTH_LONG).show();
                }
            });


        }catch(Exception e){e.printStackTrace();}

     }



    public class loadWishList extends AsyncTask<String, Wishlist, List<Wishlist>> {

        private List<Wishlist> WishList = new ArrayList<>();


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected List<Wishlist> doInBackground(String... strings) {
            // *********************************************************



            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .getNotifWishlist(StoreID,Session.getInstance().getUser().getCin());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("wishlistInformations");
                        if(msg.equals("Wishlist retrieved"))
                        {
                            JSONArray article=jsonResp.getJSONArray("wishlist");

                            for (int i = 0 ; i < article.length(); i++) {
                                JSONObject obj = article.getJSONObject(i);
                                Wishlist e=new Wishlist();
                                e.setIdArticel(obj.getString("idarticle"));
                                e.setArticleName(obj.getString("ArticleName"));
                                e.setPrice(obj.getDouble("price"));
                                e.setQuantity(obj.getInt("quantity"));
                                e.setImgProduct(obj.getString("imageArticle"));
                                e.setIdClient(obj.getString("idClient"));

                                WishList.add(e);
                            }

                            //   shimmerFrameLayout.stopShimmer();
                            //shimmerFrameLayout.setVisibility(View.GONE);
                        }
                        else{}



                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
            return  WishList;
        }

        @Override
        protected void onProgressUpdate(Wishlist ... store) {
            super.onProgressUpdate(store);

        }

        @Override
        protected void onPostExecute(List<Wishlist> o) {
            super.onPostExecute(o);
            // This is your code



            Notif_wishlist_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
            adapterNotifWishList = new AdapterNotifWishList(getApplicationContext(),o);
            Notif_wishlist_recyclerView.setAdapter(adapterNotifWishList);
        }
    }


}