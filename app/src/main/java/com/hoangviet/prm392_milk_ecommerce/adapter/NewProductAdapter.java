package com.hoangviet.prm392_milk_ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoangviet.prm392_milk_ecommerce.Interface.ItemClickListener;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.activity.ProductInformationActivity;
import com.hoangviet.prm392_milk_ecommerce.model.NewProduct;

import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewHolder> {
    Context context;
    List<NewProduct> listNewProducts;

    public NewProductAdapter(Context context, List<NewProduct> listNewProducts) {
        this.context = context;
        this.listNewProducts = listNewProducts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_prodcut, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewProduct newProduct = listNewProducts.get(position);
        holder.txtNewProductName.setText(newProduct.getProduct_name());
        holder.txtNewProductPrice.setText(String.valueOf(newProduct.getProduct_price()));
        Glide.with(context).load(newProduct.getProduct_image()).into(holder.newProductImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, ProductInformationActivity.class);
                    intent.putExtra("productDetail", newProduct);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNewProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNewProductName;
        TextView txtNewProductPrice;
        ImageView newProductImage;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNewProductPrice = (TextView) itemView.findViewById(R.id.newproductpricee);
            txtNewProductName = (TextView) itemView.findViewById(R.id.newproductname);
            newProductImage = (ImageView) itemView.findViewById(R.id.newproductimg);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onClick(view, getAdapterPosition(), false);
            }
        }
    }
}
