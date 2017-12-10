package com.crazyhitty.chdev.ks.firebasechat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.models.Book;

import java.util.List;


public class BooksDisplayAdapter extends RecyclerView.Adapter<BooksDisplayAdapter.MyViewHolder> {
    List<Book> bookList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookCategory.setText(book.getCategory());
        holder.bookUser.setText(book.getUser());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookDescription.setText(book.getDescription());
        holder.bookPrice.setText(book.getPrice());
        holder.bookName.setText(book.getName());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName, bookCategory, bookDescription, bookAuthor, bookPrice, bookUser;


        public MyViewHolder(View view) {
            super(view);

            bookName = (TextView) view.findViewById(R.id.bookName);
            bookCategory = (TextView) view.findViewById(R.id.bookCategory);
            bookDescription = (TextView) view.findViewById(R.id.bookDescription);
            bookAuthor = (TextView) view.findViewById(R.id.bookAuthor);
            bookPrice = (TextView) view.findViewById(R.id.bookPrice);
            bookUser = (TextView) view.findViewById(R.id.bookUser);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    System.out.println("LongClick: ");
                    return true;// returning true instead of false, works for me
                }
            });
        }

    }

    public BooksDisplayAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }
}
