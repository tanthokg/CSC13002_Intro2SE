package com.example.sunshine.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshine.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> list)
    {
        this.categoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cat = categoryList.get(position);

        if (cat == null)
            return;

        holder.bookAva.setImageResource(cat.getResourceID());
        holder.bookName.setText(cat.getBookName());
        holder.content.setText(cat.getBookContent());



        holder.upvoteCount.setText(Integer.toString(cat.getUpvoteCount()));
        holder.downvoteCount.setText(Integer.toString(cat.getDownvoteCount()));
        //holder.viewCount.setText(Integer.toString(cat.getViewCount()));

    }

    @Override
    public int getItemCount() {
        if (categoryList != null)
            return categoryList.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookAva;
        private TextView bookName;
        private TextView content;

        private Button upvoteCount;
        private Button downvoteCount;
        //private Button viewCount;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            bookAva = (ImageView) itemView.findViewById(R.id.bookAva);
            bookName = (TextView) itemView.findViewById(R.id.bookName);
            content = (TextView) itemView.findViewById(R.id.bookContent);

            upvoteCount = (Button) itemView.findViewById(R.id.btnUpvoteCount);
            downvoteCount = (Button) itemView.findViewById(R.id.btnDownvoteCount);
            //viewCount = (Button) itemView.findViewById(R.id.view);


        }
    }
}
