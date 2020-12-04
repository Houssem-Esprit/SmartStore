package com.p2j.MohamedApp.Adapters;

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
import com.p2j.MohamedApp.Data_Manager.RetrofitClient;
import com.p2j.MohamedApp.Model.Wishlist;
import com.p2j.MohamedApp.R;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterNotifWishList extends RecyclerView.Adapter<AdapterNotifWishList.myViewHolder> {

    Context mContext;
    List<Wishlist> mData;



    public AdapterNotifWishList(Context mContext, List<Wishlist> mData){
        this.mContext=mContext;
        this.mData=mData;
    }


    @NonNull
    @Override
    public AdapterNotifWishList.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View v=inflater.inflate(R.layout.notif_wishlist_card,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotifWishList.myViewHolder holder, final int position) {

        String url = "http://localhost:3001/uploads/articles/"+mData.get(position).getImgProduct();
        Glide.with(mContext).load(url).into(holder.ArticleImage);
        holder.ArticleName.setText(mData.get(position).getArticleName().toString());
        holder.ArticlePrice.setText(mData.get(position).getPrice().toString()+'\n'+"DT");
        holder.ArticleQuantity.setText(""+mData.get(position).getQuantity()+'\n'+"U");
        try {
            Double price = mData.get(position).getPrice();
            int quantity = mData.get(position).getQuantity();
            Double total = price * quantity;
            if (total!= null){
                holder.TotalSingleWishList.setText(total.toString());

            }
        }catch (Exception e){e.printStackTrace();}






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
        TextView TotalSingleWishList;

        View view;

        public myViewHolder( View itemView) {
            super(itemView);
            view=itemView;
            ArticleImage = itemView.findViewById(R.id.notif_ProductImg);
            ArticleName= itemView.findViewById(R.id.notif_ProductName);
            ArticlePrice= itemView.findViewById(R.id.notif_ProductPrice);
            ArticleQuantity= itemView.findViewById(R.id.notif_ProductQuantity);
            TotalSingleWishList=itemView.findViewById(R.id.totalSingleWishlist);

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
