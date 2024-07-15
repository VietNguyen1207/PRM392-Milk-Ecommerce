package com.hoangviet.prm392_milk_ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoangviet.prm392_milk_ecommerce.Interface.ItemClickListener;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.activity.ProductInformationActivity;
import com.hoangviet.prm392_milk_ecommerce.model.NewProduct;

import java.util.List;

public class SuabotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<NewProduct> listSuaBot;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public SuabotAdapter(Context context, List<NewProduct> listSuaBot) {
        this.context = context;
        this.listSuaBot = listSuaBot;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suabot, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            NewProduct suabot = listSuaBot.get(position);
            myViewHolder.productName.setText(suabot.getProduct_name());
            myViewHolder.productPrice.setText(String.valueOf(suabot.getProduct_price()));
            myViewHolder.productDescription.setText(suabot.getDescription());
            Glide.with(context).load(suabot.getProduct_image()).into(myViewHolder.productImage);
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick) {
                        Intent intent = new Intent(context, ProductInformationActivity.class);
                        intent.putExtra("productDetail", suabot);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listSuaBot.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return listSuaBot.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.item_suabot_name);
            productPrice = itemView.findViewById(R.id.item_suabot_price);
            productDescription = itemView.findViewById(R.id.item_suabot_description);
            productImage = itemView.findViewById(R.id.item_suabot_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
