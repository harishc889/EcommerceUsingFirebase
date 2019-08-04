package com.allandroidprojects.buildsmart.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.allandroidprojects.buildsmart.DisplayHome.modell;
import com.allandroidprojects.buildsmart.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SearchProductActivity extends AppCompatActivity {


    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView SearchList;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.button);
        SearchList = findViewById(R.id.searchlist);
        SearchList.setLayoutManager(new LinearLayoutManager(SearchProductActivity.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent i=new Intent(SearchProductActivity.this,TextDetector.class);
               startActivityForResult(i,1);

                onStart();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1&&requestCode==1)
        {
            SearchInput=data.getStringExtra("input");
            inputText.setText(SearchInput);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products").child("Laptop");
        FirebaseRecyclerOptions<modell> options = new FirebaseRecyclerOptions.Builder<modell>()
                .setQuery(reference.orderByChild("name")
                .startAt(SearchInput),modell.class).build();

        FirebaseRecyclerAdapter<modell, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<modell, ProductViewHolder>(options)
                {


            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.product_items_layout, viewGroup, false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(ProductViewHolder holder, int position, modell model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductDesc.setText(model.getDescription());

                Uri uri = Uri.parse(model.getDownloadurl());
                Picasso.get().load(uri).into(holder.imageView);
                //holder.imageView.setImageURI(uri);
//                holder.itemView.setOnClickListener(new View.onClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(SearchProductActivity.this, Produc)
//                        intent.putExtra("pid", model.getPid());
//                        startActivity(intent);
//                    }
//                });

            }


        };
        SearchList.setAdapter(adapter);
        adapter.startListening();
    }
}