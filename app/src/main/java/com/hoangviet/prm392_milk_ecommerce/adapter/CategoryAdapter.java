package com.hoangviet.prm392_milk_ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoangviet.prm392_milk_ecommerce.R;
import com.hoangviet.prm392_milk_ecommerce.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    List<Category> array;
    Context context;

    public CategoryAdapter(Context context, List<Category> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return array.get(i); // Corrected to return the actual item
    }

    @Override
    public long getItemId(int i) {
        return i; // Return the index as the ID
    }

    public class ViewHolder{
        TextView categoryname;
        ImageView categoryimage;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_category, null);
            viewHolder.categoryname = view.findViewById(R.id.item_categoryname);
            viewHolder.categoryimage = view.findViewById(R.id.item_categorytimage);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.categoryname.setText(array.get(i).getCategoryname());
        Glide.with(context).load(array.get(i).getCategory_image()).into(viewHolder.categoryimage);

        return view;
    }
}
