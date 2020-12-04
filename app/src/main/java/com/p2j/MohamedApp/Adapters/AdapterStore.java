package com.p2j.MohamedApp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.p2j.MohamedApp.Activity.MainActivity;
import com.p2j.MohamedApp.Fragment.Store_Fragment;
import com.p2j.MohamedApp.Model.User;
import com.p2j.MohamedApp.R;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterStore extends RecyclerView.Adapter<AdapterStore.myViewHolder> {

    Context mContext;
    List<User> mData;


    public AdapterStore(Context mContext, List<User> mData){
        this.mContext=mContext;
        this.mData=mData;


    }


    @NonNull
    @Override
    public AdapterStore.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mContext);
        View v=inflater.inflate(R.layout.store_card,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStore.myViewHolder holder, final int position) {

        String url = "http://localhost:3001/uploads/users/"+mData.get(position).getImg();
        Glide.with(mContext).load(url).into(holder.StoreImg);
        holder.StoreName.setText(mData.get(position).getFirstName());

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=mData.get(position);
                final Bundle bundle= new Bundle();
                bundle.putString("StoreName",user.getFirstName());
                bundle.putString("StoreImg",user.getImg());
                bundle.putString("StoreCin",user.getCin());

                ;
                final NavController navController = Navigation.findNavController(view);

                navController.navigate(R.id.action_homePage_Fragment_to_store_Fragment,bundle);



            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder{
        ImageView StoreImg;
        TextView StoreName;
        View view;

        public myViewHolder( View itemView) {
            super(itemView);
            view=itemView;
            StoreImg = itemView.findViewById(R.id.StoreImg);
            StoreName= itemView.findViewById(R.id.StoreName);
        }
        public View getView()
        {
            return view;
        }
    }
}
