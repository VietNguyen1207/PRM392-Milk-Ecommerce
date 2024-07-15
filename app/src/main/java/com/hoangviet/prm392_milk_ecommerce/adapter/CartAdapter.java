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
import com.hoangviet.prm392_milk_ecommerce.Interface.IImageClickListener;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.model.Cart;
import com.hoangviet.prm392_milk_ecommerce.model.EventBus.CalTotalCartEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> listCart;

    public CartAdapter(Context context, List<Cart> listCart) {
        this.context = context;
        this.listCart = listCart;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtCartProductName,
                txtCartProductPrice,
                txtCartProductQuantity,
                txtCartProductTotalPrice;
        ImageView cartProductImage,
                imgDecreaseProduct,
                imgIncreaseProduct;

        IImageClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCartProductName = (TextView) itemView.findViewById(R.id.item_cart_productName);
            txtCartProductPrice = (TextView) itemView.findViewById(R.id.item_cart_productPrice);
            txtCartProductQuantity = (TextView) itemView.findViewById(R.id.item_cart_quantity);
            txtCartProductTotalPrice = (TextView) itemView.findViewById(R.id.item_cart_productTotalPrice);
            cartProductImage = (ImageView) itemView.findViewById(R.id.item_cart_image);
            imgDecreaseProduct = (ImageView) itemView.findViewById(R.id.cart_decrease_product);
            imgIncreaseProduct = (ImageView) itemView.findViewById(R.id.cart_increase_product);

            imgDecreaseProduct.setOnClickListener(this);
            imgIncreaseProduct.setOnClickListener(this);
        }

        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(view == imgDecreaseProduct){
                listener.onImageClick(view, getAdapterPosition(), 1);
            }else if(view == imgIncreaseProduct){
                listener.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = listCart.get(position);
        holder.txtCartProductName.setText(cart.getProductName());
        holder.txtCartProductQuantity.setText(cart.getQuantity() + "");
        Glide.with(context).load(cart.getProductImage()).into(holder.cartProductImage);
        holder.txtCartProductPrice.setText(String.valueOf(cart.getProductPrice()));
        float totalProductPrice = cart.getProductPrice() * cart.getQuantity();
        holder.txtCartProductTotalPrice.setText(String.valueOf(totalProductPrice));
        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int position, int value) {
                if (value == 1){
                    if(listCart.get(position).getQuantity() > 1){
                        int newQuantity = listCart.get(position).getQuantity() -1;
                        listCart.get(position).setQuantity(newQuantity);
                    }
                }else if(value ==2){
                    if(listCart.get(position).getQuantity() < 11){
                        int newQuantity = listCart.get(position).getQuantity() + 1;
                        listCart.get(position).setQuantity(newQuantity);
                    }
                }
                holder.txtCartProductQuantity.setText(listCart.get(position).getQuantity() + " ");
                float totalProductPrice = listCart.get(position).getProductPrice() * listCart.get(position).getQuantity();
                holder.txtCartProductTotalPrice.setText(String.valueOf(totalProductPrice));
                EventBus.getDefault().postSticky(new CalTotalCartEvent());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }
}
