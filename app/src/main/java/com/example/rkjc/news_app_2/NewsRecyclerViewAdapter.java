package com.example.rkjc.news_app_2;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    //Context context;
    private ArrayList<NewsItem> mArticles;
    final private ListItemClickListener mOnClickListener;


    public NewsRecyclerViewAdapter(ArrayList<NewsItem> mArticles, ListItemClickListener mOnClickListener){
        this.mArticles = mArticles;
        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( NewsItemViewHolder articleViewHolder, int position) {
        NewsItem currentNewsItem = mArticles.get(position);
        articleViewHolder.title.setText("Title: " + currentNewsItem.getTitle());
        articleViewHolder.description.setText("Description: " + currentNewsItem.getDescription());
        articleViewHolder.date.setText("Date: " + mArticles.get(position).getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{


        private TextView title;
        private TextView description;
        private TextView date;


        public NewsItemViewHolder(View itemView) {
            // Stores itemView as a public final member variable that can be accessed in any ViewHolder
            super(itemView);
            // Fill
            title = (TextView) itemView.findViewById(R.id.news_article_title);
            description = (TextView) itemView.findViewById(R.id.news_article_description);
            date = (TextView) itemView.findViewById(R.id.news_article_date);
            // Sets on click for itemView.
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


}
