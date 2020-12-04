package com.p2j.MohamedApp.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.p2j.MohamedApp.Adapters.AdapterStore;
import com.p2j.MohamedApp.Adapters.AdapterTrends;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.Trends;
import com.p2j.MohamedApp.Model.User;
import com.p2j.MohamedApp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePage_Fragment extends Fragment {

    RecyclerView StoreRecyclerView;
    AdapterStore adapterStore;
    LinearLayoutManager StoreLinearLayoutManager;
    //***************** Trends attr ************

    RecyclerView TrendsRecyclerView;
    AdapterTrends adapterTrends;
    LinearLayoutManager TrendsLinearLayoutManager;

    public HomePage_Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewroot = inflater.inflate(R.layout.fragment_home_page_, container, false);

        // ******************** Store Logic ****************************
        StoreRecyclerView=viewroot.findViewById(R.id.StoreRecyclerView);
        TrendsRecyclerView = viewroot.findViewById(R.id.TrendsRecyclerView);
        // load Stores data
        loadTrends trends = new loadTrends();
        trends.execute();
        loadStores stores = new loadStores();
        stores.execute();
        //********************* WishList Logic ***************************
        TrendsRecyclerView = viewroot.findViewById(R.id.TrendsRecyclerView);


        return viewroot;
    }









    public class loadStores extends AsyncTask<String,User,List<User>> {

        private List<User> StoreList = new ArrayList<>();


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected List<User> doInBackground(String... strings) {
            // *********************************************************
            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .GetStores();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("storeInformation");
                        if(msg.equals("Stores retrieved"))
                        {
                            JSONArray store=jsonResp.getJSONArray("stores");

                            for (int i = 0 ; i < store.length(); i++) {
                                JSONObject obj = store.getJSONObject(i);
                                User e=new User();
                                e.setCin(obj.getString("cin"));
                                e.setFirstName(obj.getString("firstName"));
                                e.setImg(obj.getString("img"));

                                StoreList.add(e);
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
            return  StoreList;
        }

        @Override
        protected void onProgressUpdate(User ... store) {
            super.onProgressUpdate(store);

        }

        @Override
        protected void onPostExecute(List<User> o) {
            super.onPostExecute(o);
            // This is your code


            StoreLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            StoreRecyclerView.setLayoutManager(StoreLinearLayoutManager);

            adapterStore = new AdapterStore(getContext(),o);
            StoreRecyclerView.setAdapter(adapterStore);
        }
    }

    public class loadTrends extends AsyncTask<String,Trends,List<Trends>> {

        private List<Trends> TrendsList = new ArrayList<>();


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected List<Trends> doInBackground(String... strings) {
            // *********************************************************
            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .getTrends();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("TrendsInformations");
                        if(msg.equals("articles trends retrieved"))
                        {
                            JSONArray trend=jsonResp.getJSONArray("Trends");

                            for (int i = 0 ; i < trend.length(); i++) {
                                JSONObject obj = trend.getJSONObject(i);
                                Trends e=new Trends();
                                e.setIdarticle(obj.getString("idarticle"));
                                e.setNameArticle(obj.getString("name"));
                                e.setImgArticle(obj.getString("imgArticle"));
                                e.setFirstName(obj.getString("firstName"));
                                e.setImg(obj.getString("img"));



                                TrendsList.add(e);
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
            return  TrendsList;
        }

        @Override
        protected void onProgressUpdate(Trends ... trend) {
            super.onProgressUpdate(trend);

        }

        @Override
        protected void onPostExecute(List<Trends> o) {
            super.onPostExecute(o);
            // This is your code


            TrendsLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            TrendsRecyclerView.setLayoutManager(TrendsLinearLayoutManager);

            adapterTrends = new AdapterTrends(getContext(),o);
            TrendsRecyclerView.setAdapter(adapterTrends);
        }
    }





}