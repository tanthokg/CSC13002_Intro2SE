package com.example.sunshine.user;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;

import java.util.ArrayList;
import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_category_list_post);
        String value = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(value);


        rcvCategory = (RecyclerView) findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rcvCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);
    }

    private List<Category> getListCategory()
    {
        List<Category> list = new ArrayList<>();

        /*int resourceID, String bookName,
            String bookContent, int upvoteCount, int downvoteCount, int viewCount*/

        Category cat1 = new Category(R.drawable.bookimg1, "Harry Potter and the Philosophy Stone", "This is the best book ever",
                190, 30);

        Category cat2 = new Category(R.drawable.bookimg2, "Divergent", "One choice can transform you",
                110, 35);

        Category cat3 = new Category(R.drawable.bookimg8, "Twilight", "The international best seller romance book",
                98, 16);

        Category cat4 = new Category(R.drawable.bookimg7, "Daphne du Maurier", "One of the most influential novels of the XX century",
                90, 12);

        Category cat5 = new Category(R.drawable.bookimg7, "The host", "The most thrilling book I've ever read",
                80, 5);

        list.add(cat1);
        list.add(cat2);
        list.add(cat3);
        list.add(cat4);
        list.add(cat5);
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
