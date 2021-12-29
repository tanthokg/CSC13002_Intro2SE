package com.example.sunshine.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sunshine.R;
import com.example.sunshine.database.Book;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public List<Book> bookList;
    private Context context;

    public HomeAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(bookList.get(position).getImageUri()).into(holder.imgBookAvatar);
        holder.txtBookName.setText(bookList.get(position).getName());

        holder.imgBookAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserMainActivity)context).fromFragmentToMain("HOME", "POST", bookList.get(holder.getAdapterPosition()).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBookAvatar;
        TextView txtBookName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookAvatar = (ImageView) itemView.findViewById(R.id.imgBookAvatar);
            txtBookName = (TextView) itemView.findViewById(R.id.txtBookName);
        }
    }
}
