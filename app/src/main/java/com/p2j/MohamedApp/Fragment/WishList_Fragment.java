package com.p2j.MohamedApp.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.p2j.MohamedApp.Adapters.AdapterInsideStore;
import com.p2j.MohamedApp.Adapters.AdapterWishList;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.Article;
import com.p2j.MohamedApp.Model.Wishlist;
import com.p2j.MohamedApp.R;
import com.p2j.MohamedApp.Utils.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishList_Fragment extends Fragment {
    RecyclerView WishList_RecyclerView;
    AdapterWishList WishListAdapter;
    FloatingActionButton AddArticlefloating_button;
    TextView TotalwishList;


    public WishList_Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewroot = inflater.inflate(R.layout.fragment_wish_list_, container, false);
//        AddArticlefloating_button=getActivity().findViewById(R.id.addArticlefloating_button);
//        AddArticlefloating_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(view);
//                navController.navigate(R.id.action_wishList_Fragment_to_addArticleFragment);
//            }
//        });

        //********************************** retrieve the total ***************************************
        TotalwishList = viewroot.findViewById(R.id.TotalWishlist);

        Call call = RetrofitClient
                .getInstance()
                .getApi_nodeservices()
                .getTotalWishlist(Session.getInstance().getUser().getCin());
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
                        TotalwishList.setText(obj.getString("Total")+'\t'+"DT");




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

          WishList_RecyclerView = viewroot.findViewById(R.id.WishListRecyler);
          loadWishList wishList = new loadWishList();
          wishList.execute();


        return  viewroot;
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
                    .getUserWishlist(Session.getInstance().getUser().getCin());
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



            WishList_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            WishListAdapter = new AdapterWishList(getContext(),o);
            WishList_RecyclerView.setAdapter(WishListAdapter);
        }
    }




}