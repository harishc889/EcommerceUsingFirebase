package com.allandroidprojects.buildsmart.product;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.allandroidprojects.buildsmart.R;
import com.allandroidprojects.buildsmart.fragments.ImageListFragment;
import com.allandroidprojects.buildsmart.fragments.ViewPagerActivity;
import com.allandroidprojects.buildsmart.notification.NotificationCountSetClass;
import com.allandroidprojects.buildsmart.options.CartListActivity;
import com.allandroidprojects.buildsmart.startup.MainActivity;
import com.allandroidprojects.buildsmart.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    public static int total=0;
    String stringImageUri;
    String stringImageName;
    String stringImagePrice;
    TextView pname,pprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        SimpleDraweeView mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);


        pname=findViewById(R.id.productName);
        pprice=findViewById(R.id.productPrice);




        //Getting image uri from previous screen
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_URI,0);
            stringImageName=getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_NAME);
            stringImagePrice=getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_PRICE);
        }

        pname.setText(stringImageName);
        pprice.setText(stringImagePrice);
        Uri uri = Uri.parse(stringImageUri);
        mImageView.setImageURI(uri);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                    intent.putExtra("position", imagePosition);
                    startActivity(intent);

            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                total=imageUrlUtils.addCartListImageUri(stringImageUri,stringImageName,stringImagePrice,total);

                Toast.makeText(ItemDetailsActivity.this,"Item added to cart.",Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                total=imageUrlUtils.addCartListImageUri(stringImageUri,stringImageName,stringImagePrice,total);
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));

            }
        });

    }
}
