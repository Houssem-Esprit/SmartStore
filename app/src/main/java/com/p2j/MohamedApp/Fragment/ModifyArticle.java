package com.p2j.MohamedApp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.Article;
import com.p2j.MohamedApp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyArticle extends Fragment {
    CircleImageView Modify_Ar_Img;
    TextInputEditText Modify_Ar_Name;
    TextInputEditText Modify_Ar_ID;
    TextInputEditText Modify_Ar_Price;
    TextInputEditText Modify_Ar_Quantity;
    SwitchMaterial Modify_Ar_Color;
    SwitchMaterial Modify_Ar_Availability;
    Button Modify_Ar_Button;
    String M_IDItem;
    String M_ItemImg;
    int color;
    int dispo;

    public ModifyArticle() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewroot= inflater.inflate(R.layout.fragment_modify_article, container, false);
        Modify_Ar_ID= viewroot.findViewById(R.id.Modify_Ar_ID_Item);
        Modify_Ar_Img= viewroot.findViewById(R.id.Modify_Ar_ArticleImg);
        Modify_Ar_Name= viewroot.findViewById(R.id.Modify_Ar_Item_name);
        Modify_Ar_Price= viewroot.findViewById(R.id.Modify_Ar_PriceInput);
        Modify_Ar_Quantity= viewroot.findViewById(R.id.Modify_Ar_QuantityInput);
        Modify_Ar_Color= viewroot.findViewById(R.id.Modify_Ar_colorSwitch);
        Modify_Ar_Availability= viewroot.findViewById(R.id.Modify_Ar_productSwitch);
        Modify_Ar_Button= viewroot.findViewById(R.id.Modify_Ar_AddArticle);
        M_IDItem = getArguments().getString("M_itemID");
        M_ItemImg = getArguments().getString("M_itemImg");
        String url = "http://localhost:3001/uploads/articles/"+M_ItemImg;
        Glide.with(getContext()).load(url).into(Modify_Ar_Img);

       //********************************* get Infos ******************************

        Call call = RetrofitClient
                .getInstance()
                .getApi_nodeservices()
                .getArticleByID_EditAr(M_IDItem);
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
                            //e.setIdArticle(obj.getString("IdArticle"));
                        Modify_Ar_ID.setText(M_IDItem);
                        Modify_Ar_Name.setText(obj.getString("name"));
                        Modify_Ar_Price.setText(""+obj.getDouble("price"));
                        Modify_Ar_Quantity.setText(""+obj.getInt("quantity"));
                        int color = obj.getInt("color");
                        if (color == 1){
                            Modify_Ar_Color.setChecked(true);
                        }else { Modify_Ar_Color.setChecked(false);}
                            int dispo =obj.getInt("dispo");
                        if (dispo == 1){
                            Modify_Ar_Availability.setChecked(true);
                        }else { Modify_Ar_Availability.setChecked(false);}



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

        //********************************* get infos *******************************

        Modify_Ar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                /************** Modify service******************/
                String name = Modify_Ar_Name.getText().toString();
                Double price = Double.parseDouble(Modify_Ar_Price.getText().toString());
                int quantity = Integer.parseInt(Modify_Ar_Quantity.getText().toString());
                if (Modify_Ar_Color.isChecked() == true) {
                 color =1; } else { color =0;}
                if (Modify_Ar_Availability.isChecked() == true) {
                    dispo =1; } else { dispo =0;}

                Call call = RetrofitClient
                        .getInstance()
                        .getApi_nodeservices()
                        .ModifyArticle(M_IDItem,name,color,quantity,price,dispo);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        try {

                            JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                            String msg=jsonResp.getString("ArticleInformations");
                            if(msg.equals("article updated with success"))
                            {

                                Toast.makeText(getContext(), "article updated with success", Toast.LENGTH_SHORT).show();
                                //final NavController navController = Navigation.findNavController(view);
                               // navController.navigate(R.id.action_modifyArticle_to_store_Fragment);
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

                /************** Modify service******************/
            }
        });

        return viewroot;
    }
}