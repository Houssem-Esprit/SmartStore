package com.p2j.MohamedApp.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.p2j.MohamedApp.Adapters.AdapterInsideStore;
import com.p2j.MohamedApp.Adapters.AdapterStore;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.Article;
import com.p2j.MohamedApp.Model.User;
import com.p2j.MohamedApp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Store_Fragment extends Fragment {

   RecyclerView StoreArticleRecyclerView;
   AdapterInsideStore adapterArticleStore;
   String StoreName;
   String StoreImg;
   String StoreCin;
   CircleImageView StoreImage;
   TextView StoreNameWelcome;
   FloatingActionButton AddArticlefloating_button;
    View viewroot;
   View v;
    ImageView welcomeImg;
    View view;
    public Store_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Action bar menu enable navigation
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
         viewroot= inflater.inflate(R.layout.fragment_store_, container, false);
//        AddArticlefloating_button = getActivity().findViewById(R.id.addArticlefloating_button);
//        AddArticlefloating_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(view);
//                navController.navigate(R.id.action_store_Fragment_to_addArticleFragment);
//            }
//        });
        welcomeImg = getActivity().findViewById(R.id.welcomeImg);
        view = getActivity().findViewById(R.id.GradiantMesh);
        welcomeImg.setImageResource(0);
        welcomeImg.setImageResource(R.drawable.entryway);
        welcomeImg.setBackgroundResource(R.color.M_colorPrimary);
        welcomeImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setBackgroundResource(0);
        view.setBackgroundResource(R.drawable.homepage_titlebackground);

        StoreArticleRecyclerView= viewroot.findViewById(R.id.ArticleStoreRecycler);
        StoreNameWelcome = viewroot.findViewById(R.id.StoreNameWelcome);
        StoreImage = viewroot.findViewById(R.id.StoreImage);
        StoreName = getArguments().getString("StoreName");
        StoreImg = getArguments().getString("StoreImg");
        StoreNameWelcome.setText(StoreName);
        String url = "http://localhost:3001/uploads/users/"+StoreImg;
        Glide.with(getContext()).load(url).into(StoreImage);

        loadStoresArticle storesArticle = new loadStoresArticle();
        storesArticle.execute();

        return viewroot;
    }






    public class loadStoresArticle extends AsyncTask<String, Article, List<Article>> {

        private List<Article> ArticleList = new ArrayList<>();
        String StoreCin = getArguments().getString("StoreCin");


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected List<Article> doInBackground(String... strings) {
            // *********************************************************



            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .getStoreArticles(StoreCin);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    try {

                        JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                        String msg=jsonResp.getString("articleInformations");
                        if(msg.equals("User articles retrieved"))
                        {
                                JSONArray article=jsonResp.getJSONArray("articles");

                            for (int i = 0 ; i < article.length(); i++) {
                                JSONObject obj = article.getJSONObject(i);
                                Article e=new Article();
                                e.setIdArticle(obj.getString("IdArticle"));
                                e.setName(obj.getString("name"));
                                e.setColor(obj.getInt("color"));
                                e.setPrice(obj.getDouble("price"));
                                e.setQuantity(obj.getInt("quantity"));
                                e.setImgArticle(obj.getString("imgArticle"));
                                e.setDispo(obj.getInt("dispo"));
                                e.setIdUser(obj.getString("idUser"));

                                ArticleList.add(e);
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
            return  ArticleList;
        }

        @Override
        protected void onProgressUpdate(Article ... store) {
            super.onProgressUpdate(store);

        }

        @Override
        protected void onPostExecute(List<Article> o) {
            super.onPostExecute(o);
            // This is your code



            StoreArticleRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

                adapterArticleStore = new AdapterInsideStore(getContext(),o);
            StoreArticleRecyclerView.setAdapter(adapterArticleStore);
        }
    }



}