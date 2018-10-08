package com.example.farzad.ecommerce;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farzad.ecommerce.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder>{
    private static final String TAG="RecylerViewAdapter";
    private ArrayList<Product> mData;
    private Activity mActivity;

    public RecylerViewAdapter(ArrayList<Product> data,Activity activity){
        mData=data;
        this.mActivity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Product product=mData.get(position);

        viewHolder.setName(product.getName());
        viewHolder.setAddress(product.getAddress());
        viewHolder.setImageView(product.getImageUri());


    }

    @Override
    public int getItemCount() {
        if(mData==null)
        return 0;
        else
            return   mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.image);
            title=(TextView) itemView.findViewById(R.id.title);
            description=(TextView) itemView.findViewById(R.id.description);
        }
        public void setName(String title){
            this.title.setText(title);
        }
        public void setAddress(String address){
            this.description.setText(address);
        }
        public void setImageView(String url){

//            this.imageView.
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

        }
    }
}
