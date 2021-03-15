package com.p2j.smartStore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.p2j.smartStore.Data_Manager.RetrofitClient;
import com.p2j.smartStore.R;
import com.p2j.smartStore.Utils.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Article_Fragment extends Fragment {
    CircleImageView S_articleImg;
    TextView S_articleName;
    CheckBox  S_articleColor;
    CheckBox  S_articleDispo;
    TextView S_articlePrice;
    TextView S_articleQuantity;
    ImageView welcomeImg;
    View view;
    FloatingActionButton editArticle;
    FloatingActionButton deleteArticle;
    FloatingActionButton addToPanier;
    String S_idArticle;
    Double  S_priceArticle;
    int S_QuantityArticle;
    int Quantity;
    String S_idUserArticle;
    String S_imgArticle;
    String S_Name;
    NavController navController;
    FloatingActionButton AddArticlefloating_button;



    public Article_Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewroot=  inflater.inflate(R.layout.fragment_article_, container, false);

//        AddArticlefloating_button = getActivity().findViewById(R.id.addArticlefloating_button);
//        AddArticlefloating_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController navController = Navigation.findNavController(view);
//                navController.navigate(R.id.action_article_Fragment_to_addArticleFragment);
//            }
//        });
        welcomeImg = getActivity().findViewById(R.id.welcomeImg);
        view = getActivity().findViewById(R.id.GradiantMesh);
        welcomeImg.setImageResource(0);
        welcomeImg.setImageResource(R.drawable.shopping);
        welcomeImg.setBackgroundResource(R.color.M_colorSecond);
        welcomeImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setBackgroundResource(0);
        view.setBackgroundResource(R.drawable.homepage_titlebackground_blue);
        S_articleImg = viewroot.findViewById(R.id.ArticleSpecImg);
        S_articleName = viewroot.findViewById(R.id.ArticleSpecName);
        S_articleColor = viewroot.findViewById(R.id.ArticleSpecColor);
        S_articleDispo = viewroot.findViewById(R.id.ArticleSpecDispo);
        S_articlePrice = viewroot.findViewById(R.id.ArticleSpecPrice);
        S_articleQuantity = viewroot.findViewById(R.id.ArticleSpecQuantity);

        //************************* find out where the data came from *************************
        String Jeton1 = getArguments().getString("Jeton");
        String Jeton2 = getArguments().getString("Jeton2");

         S_Name = getArguments().getString("nameA");
         S_idArticle = getArguments().getString("idA");
        int S_colorArticle = getArguments().getInt("colorA");
          S_priceArticle = getArguments().getDouble("priceA");
          S_QuantityArticle = getArguments().getInt("quantityA");
         S_imgArticle = getArguments().getString("imgA");
        int S_dispoArticle = getArguments().getInt("dispoA");
        S_idUserArticle= getArguments().getString("idUserA");



        if (Jeton1!= null){
            getArticleFromServer();
        }else{
            String url = "http://localhost:3001/uploads/articles/"+S_imgArticle;
            Glide.with(getContext()).load(url).into(S_articleImg);
            S_articleName.setText(S_Name);
            S_articleQuantity.setText(""+S_QuantityArticle+'\n'+"U");
            S_articlePrice.setText(S_priceArticle.toString()+'\n'+"DT");
            if (S_colorArticle==1){
                S_articleColor.setChecked(true);
                S_articleColor.setEnabled(false);
            }else {
                S_articleColor.setChecked(false);
                S_articleColor.setEnabled(false);}

            if (S_dispoArticle == 1){
                S_articleDispo.setChecked(true);
                S_articleDispo.setEnabled(false);
            }else{
                S_articleDispo.setChecked(false);
                S_articleDispo.setEnabled(false);
            }
        }

        // Floating actions buttons events

         editArticle = viewroot.findViewById(R.id.editArticle);
        deleteArticle =viewroot.findViewById(R.id.deleteArticle);
         addToPanier =viewroot.findViewById(R.id.addToPanier);
         Log.d("Tell the hell","idUserSession : "+Session.getInstance().getUser().getCin()+'\n'+S_idUserArticle);

         if ( Session.getInstance().getUser().getRole()==1 || !(Session.getInstance().getUser().getRole() == 0 && Session.getInstance().getUser().getCin().equals(S_idUserArticle) ) || !Session.getInstance().getUser().getCin().equals(S_idUserArticle)){

             deleteArticle.setEnabled(false);
             deleteArticle.setFocusable(false);
             deleteArticle.setClickable(false);
             deleteArticle.setVisibility(View.INVISIBLE);
             editArticle.setEnabled(false);
             editArticle.setFocusable(false);
             editArticle.setClickable(false);
             editArticle.setVisibility(View.INVISIBLE);
             //******************************************************
            addToPanier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                            Quantity = Integer.parseInt(S_articleQuantity.getText().toString()) ;
                        if (Quantity == 0) {
                            Toast.makeText(getContext(), "please specify the quantity", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        System.out.println(e);
                    }



                    Call call = RetrofitClient
                            .getInstance()
                            .getApi_nodeservices()
                            .AddToPanier(Session.getInstance().getUser().getCin(),S_Name,S_priceArticle,Quantity,S_imgArticle, S_idArticle);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {

                            try {
                                JSONObject jsonResp =new JSONObject(new Gson().toJson(response.body()));
                                String msg = jsonResp.getString("panierInformations");
                                if (msg.equals("Article added to wishlist")){

                                    Toast.makeText(getContext(),"Article added to wishlist successfully !",Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(SignUp.this,SignIn_Activity.class));
                                }  else
                                {
                                    Toast.makeText(getContext(),"Error !",Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                System.out.println(e);
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("onFailure msg",": "+t.getMessage());
                            // Toast.makeText(getApplicationContext(), "onFailure zone", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });




           //*********************************************************
         } else {
             addToPanier.setEnabled(false);
             addToPanier.setVisibility(View.INVISIBLE);
             editArticle = viewroot.findViewById(R.id.editArticle);
             editArticle.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     final Bundle bundle= new Bundle();
                     bundle.putString("M_itemID",S_idArticle);
                     bundle.putString("M_itemImg",S_imgArticle);
                     NavController navController = Navigation.findNavController(view);
                     navController.navigate(R.id.action_article_Fragment_to_modifyArticle,bundle);
                 }
             });

         }









        return viewroot;
    }

    public void getArticleFromServer(){

        //********************************* get Infos ******************************
        String ID=  getArguments().getString("ArticleId");

        Call call = RetrofitClient
                .getInstance()
                .getApi_nodeservices()
                .getArticleByID_EditAr(ID);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {

                    JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                    String msg=jsonResp.getString("articleInformations");
                    if(msg.equals("article retrieved"))
                    {
                        JSONArray article=jsonResp.getJSONArray("article");
                        JSONObject obj = article.getJSONObject(0);
                        String url = "http://localhost:3001/uploads/articles/"+obj.getString("imgArticle");
                        Glide.with(getContext()).load(url).into(S_articleImg);
                        S_articleName.setText(obj.getString("name"));
                        S_articlePrice.setText(""+obj.getDouble("price"));
                        S_articleQuantity.setText(""+obj.getInt("quantity"));
                        int color = obj.getInt("color");
                        if (color == 1){
                            S_articleColor.setChecked(true);
                        }else { S_articleColor.setChecked(false);}
                        int dispo =obj.getInt("dispo");
                        if (dispo == 1){
                            S_articleDispo.setChecked(true);
                        }else { S_articleDispo.setChecked(false);}

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

        //********************************* get infos *******************************
    }
}