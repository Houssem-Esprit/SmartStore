package com.p2j.MohamedApp.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.p2j.MohamedApp.Fragment.Store_Fragment;
import com.p2j.MohamedApp.Model.Article;
import com.p2j.MohamedApp.Model.User;
import com.p2j.MohamedApp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterInsideStore extends RecyclerView.Adapter<AdapterInsideStore.myViewHolder> {

    Context mContext;
    List<Article> mData;


    public AdapterInsideStore(Context mContext, List<Article> mData){
        this.mContext=mContext;
        this.mData=mData;


    }


    @NonNull
    @Override
    public AdapterInsideStore.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View v=inflater.inflate(R.layout.article_item_card,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInsideStore.myViewHolder holder, final int position) {

        String url = "http://localhost:3001/uploads/articles/"+mData.get(position).getImgArticle();
        Glide.with(mContext).load(url).into(holder.ArticleImage);
        holder.ArticleName.setText(mData.get(position).getName());

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article article =mData.get(position);
                final Bundle bundle= new Bundle();
                bundle.putString("idA",article.getIdArticle());
                bundle.putString("nameA",article.getName());
                bundle.putInt("colorA",article.getColor());
                bundle.putInt("quantityA",article.getQuantity());
                bundle.putDouble("priceA",article.getPrice());
                bundle.putString("imgA",article.getImgArticle());
                bundle.putInt("dispoA",article.getDispo());
                bundle.putString("idUserA",article.getIdUser());
                bundle.putString("jeton2","no it's me");
                final NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_store_Fragment_to_article_Fragment,bundle);

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


        View view;

        public myViewHolder( View itemView) {
            super(itemView);
            view=itemView;
            ArticleImage = itemView.findViewById(R.id.ArticleImageItem);
            ArticleName= itemView.findViewById(R.id.ArticleNameItem);
        }
        public View getView()
        {
            return view;
        }
    }
}
