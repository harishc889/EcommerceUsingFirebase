package com.allandroidprojects.buildsmart.options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allandroidprojects.buildsmart.R;
import com.allandroidprojects.buildsmart.payu_de.MainActivity2;
import com.allandroidprojects.buildsmart.payu_de.StartPaymentActivity;
import com.allandroidprojects.buildsmart.product.ItemDetailsActivity;
import com.allandroidprojects.buildsmart.startup.MainActivity;
import com.allandroidprojects.buildsmart.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import static com.allandroidprojects.buildsmart.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.allandroidprojects.buildsmart.fragments.ImageListFragment.STRING_IMAGE_URI;
import static com.allandroidprojects.buildsmart.product.ItemDetailsActivity.total;

public class CartListActivity extends AppCompatActivity {
    private static Context mContext;
    private static TextView disptotal;
    String phone="9756423780";
    int pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mContext = CartListActivity.this;

        disptotal=(TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewPayment = (TextView)findViewById(R.id.text_action_bottom2);
        disptotal.setText(total+"");

        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        ArrayList<String> cartlistImageUri =imageUrlUtils.getCartListImageUri();
        ArrayList<String> cartlistName =imageUrlUtils.getpnameCartList();
        ArrayList<String> cartlistPrice =imageUrlUtils.getppriceCartList();
        //Show cart layout based on items
        setCartLayout();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri,cartlistName,cartlistPrice));


        textViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
//                MainActivity.notificationCountCart++;
//                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
//                startActivity(new Intent(getApplicationContext(), CartListActivity.class));


                Intent in=new Intent(getApplicationContext(), StartPaymentActivity.class);

                in.putExtra("amount",total+"");
                in.putExtra("phone",phone);
                startActivity(in);
            }
        });


    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<String> mCartlistImageUri;
        private ArrayList<String> pnameCartList;
        private ArrayList<String> ppriceCartList;

        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final TextView pname,pprice;
            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                pname=(TextView) view.findViewById(R.id.itemname);
                pprice=(TextView) view.findViewById(R.id.itemprice);

                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<String> wishlistImageUri,ArrayList<String> a,ArrayList<String> b) {
            mCartlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
            pnameCartList=a;
            ppriceCartList=b;


        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Uri uri = Uri.parse(mCartlistImageUri.get(position));
            holder.mImageView.setImageURI(uri);
            holder.pname.setText(pnameCartList.get(position));
            holder.pprice.setText(ppriceCartList.get(position));
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,mCartlistImageUri.get(position));
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);



                }
            });

           //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                   total=imageUrlUtils.removeCartListImageUri(position,total);
                   disptotal.setText(total+"");
                    notifyDataSetChanged();
                    //Decrease notification count
                    MainActivity.notificationCountCart--;

                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCartlistImageUri.size();
        }
    }

    protected void setCartLayout(){
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        if(MainActivity.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }







    }
}
