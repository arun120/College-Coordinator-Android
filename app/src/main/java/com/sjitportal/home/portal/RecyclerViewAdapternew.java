package com.sjitportal.home.portal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alagesh on 01-02-2016.
 */
public class RecyclerViewAdapternew extends RecyclerView.Adapter<RecyclerViewAdapternew.ItemHolder> implements RecyclerView.OnItemTouchListener {

    private List<String> itemsName;
    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private List<Integer> itemsValue;


    public RecyclerViewAdapternew(Context context){
        layoutInflater = LayoutInflater.from(context);
        itemsName = new ArrayList<String>();
        itemsValue=new ArrayList<Integer>();
    }

    @Override
    public RecyclerViewAdapternew.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView itemcard=(CardView)layoutInflater.inflate(R.layout.card_resource,parent,false);
        return new ItemHolder(itemcard,this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapternew.ItemHolder holder, int position) {
        holder.setItemName(itemsName.get(position));
        holder.setItemValue(itemsValue.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private GestureDetector gestureDetector;
    private RecyclerViewAdapternew.ClickListener clickListener;

    public RecyclerViewAdapternew(Context context, final RecyclerView recyclerView, final RecyclerViewAdapternew.ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }




    public interface OnItemClickListener{
        public void onItemClick(ItemHolder item, int position);
    }

    public interface OnItemLongClickListener{
        public void onItemLongClick(ItemHolder item, int position);
    }

    public void add(int location, String iName, int iValue){
        itemsName.add(location, iName);
        itemsValue.add(location,iValue);
        Log.i("value",String.valueOf(iValue));
        notifyItemInserted(location);
    }

    public void remove(int location){
        if(location >= itemsName.size())
            return;

        itemsName.remove(location);
        notifyItemRemoved(location);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public static int[] images={R.drawable.unk,R.drawable.pdf,R.drawable.doc,R.drawable.jpg,R.drawable.txt,R.drawable.xls,R.drawable.rar,R.drawable.zip,R.drawable.png};
        private RecyclerViewAdapternew parent;
        TextView textItemName;
        TextView textItemValue;
        CardView card;
        ImageView img;

        public ItemHolder(CardView itemView, RecyclerViewAdapternew parent) {
            super(itemView);
            itemView.setOnClickListener(this);
            card=itemView;
            this.parent = parent;
            textItemName = (TextView) card.findViewById(R.id.text_card_l);
            textItemValue = (TextView) card.findViewById(R.id.text_card_m);
            img= (ImageView) card.findViewById(R.id.image_card_list);

        }

        public void setItemName(CharSequence name){
            textItemName.setText(name);
        }


        public void setItemValue(int val){

            img.setImageResource(images[val]);
        }

        public CharSequence getItemName(){
            return textItemName.getText();
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                listener.onItemClick(this, getPosition());
            }


        }

    }



}
