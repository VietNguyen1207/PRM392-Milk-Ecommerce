package com.hoangviet.prm392_milk_ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.model.NewProduct;

import java.util.List;

public class SuabotAdapter extends RecyclerView.Adapter<SuabotAdapter.MyViewHolder> {
    Context context;
    List<NewProduct> listSuaBot;

    public SuabotAdapter(Context context, List<NewProduct> listSuaBot) {
        this.context = context;
        this.listSuaBot = listSuaBot;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suabot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewProduct suabot = listSuaBot.get(position);
        holder.productName.setText(suabot.getProduct_name());
        holder.productPrice.setText(suabot.getProduct_price());
        holder.productDescription.setText(suabot.getDescription());
        Glide.with(context).load(suabot.getProduct_image()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return listSuaBot.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.item_suabot_name);
            productPrice = itemView.findViewById(R.id.item_suabot_price);
            productDescription = itemView.findViewById(R.id.item_suabot_description);
            productImage = itemView.findViewById(R.id.item_suabot_image);

        }
    }
}
