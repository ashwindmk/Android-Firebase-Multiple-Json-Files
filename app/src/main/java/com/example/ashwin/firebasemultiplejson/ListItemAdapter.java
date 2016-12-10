package com.example.ashwin.firebasemultiplejson;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;

/**
 * Created by ashwin on 10/12/16.
 */

public class ListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Item> mItems;

    public interface ItemListener {
        public void onItemClicked(View view, int position);
    }

    private static ItemListener sItemListener;


    public ListItemAdapter(ItemListener itemListener, ArrayList<Item> items) {
        sItemListener = itemListener;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        viewHolder = new ListItemAdapter.ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.position = position;
            String itemString = mItems.get(position).toString();
            itemViewHolder.mItemTextView.setText(itemString);
        }
        else if (holder instanceof ProgressViewHolder)
        {
           ProgressViewHolder progressViewHolder;
            progressViewHolder = (ProgressViewHolder) holder;
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public CardView mItemCardView;
        public TextView mItemTextView;
        public int position;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            //item
            mItemCardView = (CardView) itemView.findViewById(R.id.itemCardView);
            mItemCardView.setOnClickListener(this);
            mItemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
        }

        @Override
        public void onClick(View v) {
            if (sItemListener != null) {
                sItemListener.onItemClicked(v, position);
            }

        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        private CircleProgressBar circleProgressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            circleProgressBar = (CircleProgressBar) itemView.findViewById(R.id.circleProgressBar);
        }
    }
}
