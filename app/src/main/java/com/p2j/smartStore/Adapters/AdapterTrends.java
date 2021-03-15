package com.p2j.smartStore.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.p2j.smartStore.Model.Trends;
import com.p2j.smartStore.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTrends extends RecyclerView.Adapter<AdapterTrends.myViewHolder> {

    Context mContext;
    List<Trends> mData;


    public AdapterTrends(Context mContext, List<Trends> mData){
        this.mContext=mContext;
        this.mData=mData;


    }


    @NonNull
    @Override
    public AdapterTrends.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View v=inflater.inflate(R.layout.trends_card,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrends.myViewHolder holder, final int position) {

        //********************* Trends Info ************************
        String url2 = "http://localhost:3001/uploads/users/"+mData.get(position).getImg();
        Glide.with(mContext).load(url2).into(holder.StoreTrendImg);
        String url1 = "http://localhost:3001/uploads/articles/"+mData.get(position).getImgArticle();
        Glide.with(mContext).load(url1).into(holder.trendsImg);
        holder.trendsName.setText(mData.get(position).getFirstName());

       holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trends trend=mData.get(position);
                final Bundle bundle= new Bundle();
                bundle.putString("ArticleId",trend.getIdarticle());
                bundle.putString("Jeton","it's me");

                final NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_homePage_Fragment_to_article_Fragment,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder{

        View view;
        ImageView trendsImg;
        CircleImageView StoreTrendImg;
        TextView trendsName;

        public myViewHolder( View itemView) {
            super(itemView);
            view=itemView;
            //************************
            trendsImg= itemView.findViewById(R.id.img_article);
            StoreTrendImg= itemView.findViewById(R.id.storeImggg);
            trendsName= itemView.findViewById(R.id.store_name);
        }
        public View getView()
        {
            return view;
        }
    }
}
