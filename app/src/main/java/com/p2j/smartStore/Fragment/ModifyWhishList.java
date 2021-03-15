package com.p2j.smartStore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.p2j.smartStore.Data_Manager.RetrofitClient;
import com.p2j.smartStore.R;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyWhishList extends Fragment {

    String ModifArtId;
    String ModifArtName;
    int ModifArtQuanity;
    Double ModifArtPrice;
    String ModifArtImg;
    CircleImageView ArtImg;
    TextView ArtPrice;
    TextView ArtName;
    EditText ArtQuantity;
    CheckBox ArtColor;
    CheckBox ArtDispo;
    Button ModifyButtonn ;
    int Quantity;
    View view;


    public ModifyWhishList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_modify_whish_list, container, false);

        ArtImg = view.findViewById(R.id.ModifArticleImg);
        ArtPrice = view.findViewById(R.id.ModifArticlePrice);
        ArtName = view.findViewById(R.id.ModifArticleName);
        ArtQuantity = view.findViewById(R.id.ModifArticleQuantity);
        ArtColor = view.findViewById(R.id.ModifArticleColor);
        ArtDispo = view.findViewById(R.id.ModifArticleDispo);
        ModifArtId= getArguments().getString("idArticle");
        ModifArtName= getArguments().getString("nameArticle");
        ModifArtQuanity= getArguments().getInt("quantityArticle");
        ModifArtPrice= getArguments().getDouble("nameApriceArticle");
        ModifArtImg= getArguments().getString("ImageArticle");
        //

        String url = "http://localhost:3001/uploads/articles/"+ModifArtImg;
        Glide.with(getContext()).load(url).into(ArtImg);
        //
        ArtName.setText(ModifArtName);
        ArtPrice.setText(ModifArtPrice.toString());
        ArtQuantity.setText(""+ModifArtQuanity);
        //********************* retrieve Some data ****************
        try {


            Call call = RetrofitClient
                    .getInstance()
                    .getApi_nodeservices()
                    .getArticleByID(ModifArtId);
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
                                int color = Integer.parseInt(obj.getString("color"));
                                int disp =Integer.parseInt(obj.getString("dispo"));
                            if (color==1){
                                ArtColor.setChecked(true);
                                ArtColor.setEnabled(false);
                            }else {
                                ArtColor.setChecked(false);
                                ArtColor.setEnabled(false);}

                            if (disp == 1){
                                ArtDispo.setChecked(true);
                                ArtDispo.setEnabled(false);
                            }else{
                                ArtDispo.setChecked(false);
                                ArtDispo.setEnabled(false);
                            }

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

        }catch (Exception e){e.printStackTrace();}


        ModifyButtonn = view.findViewById(R.id.ModifyButton);
        ModifyButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View viewT) {
                /************** Modify service******************/


                    Quantity = Integer.parseInt(ArtQuantity.getText().toString()) ;
                    if (Quantity == 0) {
                        Toast.makeText(getContext(), "please specify the quantity", Toast.LENGTH_SHORT).show();
                    }

                    Call call = RetrofitClient
                            .getInstance()
                            .getApi_nodeservices()
                            .ModifyWishList(ModifArtId,Quantity);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {

                            try {

                                JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                                String msg=jsonResp.getString("wishlistInformations");
                                if(msg.equals("Wishlist article updated with success"))
                                {

                                    Toast.makeText(getContext(), "Wishlist article updated with success", Toast.LENGTH_SHORT).show();
                                    final NavController navController = Navigation.findNavController(viewT);
                                    navController.navigate(R.id.action_wishList_Fragment_to_modifyWhishList);
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


        return  view;
    }
}