package com.allandroidprojects.buildsmart.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allandroidprojects.buildsmart.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDesc;
    public ImageView imageView;
    public ItemClickListener listener;
    public ProductViewHolder(View itemView)
    {super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.product_image);
        txtProductName=(TextView) itemView.findViewById(R.id.product_name);

        txtProductDesc=(TextView) itemView.findViewById(R.id.product_desc);

    }



    public void setOnClickListener(ItemClickListener listener)
    {
        this.listener =listener;
    }

    @Override
    public void onClick(View view){
    listener.onClick(view,getAdapterPosition(),false);
    }



}
