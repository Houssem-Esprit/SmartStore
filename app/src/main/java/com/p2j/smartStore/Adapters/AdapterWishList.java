package com.p2j.smartStore.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.p2j.smartStore.Data_Manager.RetrofitClient;
import com.p2j.smartStore.Model.Wishlist;
import com.p2j.smartStore.R;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterWishList extends RecyclerView.Adapter<AdapterWishList.myViewHolder> {

    Context mContext;
    List<Wishlist> mData;



    public AdapterWishList(Context mContext, List<Wishlist> mData){
        this.mContext=mContext;
        this.mData=mData;
    }


    @NonNull
    @Override
    public AdapterWishList.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View v=inflater.inflate(R.layout.wishlist_card,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWishList.myViewHolder holder, final int position) {

        String url = "http://localhost:3001/uploads/articles/"+mData.get(position).getImgProduct();
        Glide.with(mContext).load(url).into(holder.ArticleImage);
        holder.ArticleName.setText(mData.get(position).getArticleName().toString());
        holder.ArticlePrice.setText(mData.get(position).getPrice().toString()+'\n'+"DT");
        holder.ArticleQuantity.setText(""+mData.get(position).getQuantity()+'\n'+"U");


        holder.DeletePanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
           builder.setTitle(R.string.alerte_title);
           builder.setMessage(R.string.alerte_support_text);
           builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {


                   Wishlist wish = mData.get(position);
                   //********************** Delete Service **************************

                   try {


                       Call call = RetrofitClient
                               .getInstance()
                               .getApi_nodeservices()
                               .DeleteWishList(wish.getIdArticel());
                       call.enqueue(new Callback() {
                           @Override
                           public void onResponse(Call call, Response response) {

                               try {

                                   JSONObject jsonResp=new JSONObject(new Gson().toJson(response.body()));
                                   String msg=jsonResp.getString("wishlistInformations");
                                   if(msg.equals("Article removed from wishlist"))
                                   {
                                       Toast.makeText(mContext, "Article removed from wishlist", Toast.LENGTH_SHORT).show();
                                       removeAt(position);

                                   }
                                   else{}
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }
                           @Override
                           public void onFailure(Call call, Throwable t) {
                               Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                   }catch (Exception e){e.printStackTrace();}

                   //********************** Delete Service **************************//

               }
           });

           builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {


               }
           });

           builder.show();

            }
        });

        holder.ModifyPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Wishlist wishlist =mData.get(position);
                final Bundle bundle= new Bundle();
                bundle.putString("idArticle",wishlist.getIdArticel());
                bundle.putString("nameArticle",wishlist.getArticleName());
                bundle.putInt("quantityArticle",wishlist.getQuantity());
                bundle.putDouble("priceArticle",wishlist.getPrice());
                bundle.putString("ImageArticle",wishlist.getImgProduct());

                final NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_wishList_Fragment_to_modifyWhishList,bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder{
        ImageView ArticleImage;
        TextView ArticleName;
        TextView ArticlePrice;
        TextView ArticleQuantity;
        ImageButton ModifyPanier;
        ImageButton DeletePanier;



        View view;

        public myViewHolder( View itemView) {
            super(itemView);
            view=itemView;
            ArticleImage = itemView.findViewById(R.id.ProductImg);
            ArticleName= itemView.findViewById(R.id.ProductName);
            ArticlePrice= itemView.findViewById(R.id.ProductPrice);
            ArticleQuantity= itemView.findViewById(R.id.ProductQuantity);
             ModifyPanier=itemView.findViewById(R.id.modifyPanier);
             DeletePanier=itemView.findViewById(R.id.DeletePanier);
        }
        public View getView()
        {
            return view;
        }
    }


    public void removeAt(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }
}
