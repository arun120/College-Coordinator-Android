package com.sjitportal.home.portal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alagesh on 22-01-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemHolder> {


    private OnItemClickListener onItemClickListener;
    private List<String> itemsName;
    private List<String> itemsValue;
    private LayoutInflater layoutInflater;
    Button btns;
    private Context context;

    public RecyclerViewAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        itemsName = new ArrayList<String>();
        itemsValue = new ArrayList<String>();
    }

    @Override
    public RecyclerViewAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView itemcard=(CardView)layoutInflater.inflate(R.layout.cardview_resources,parent,false);
        return new ItemHolder(itemcard,this);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ItemHolder holder, int position) {

        holder.setItemName(itemsName.get(position));
        holder.setItemValue(itemsValue.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Downloading",Toast.LENGTH_LONG).show();
            }
        });
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

    public interface OnItemClickListener{
        public void onItemClick(ItemHolder item, int position);
    }

    public void add(int location, String iName, String iValue) {
        itemsName.add(location, iName);
        itemsValue.add(location, iValue);
        notifyItemInserted(location);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewAdapter parent;
        private CardView card;
        TextView textItemName;
        TextView textItemValue;
        ImageView imgview;
        Button btn;


        public ItemHolder(CardView cards, RecyclerViewAdapter parent) {
            super(cards);
            card = cards;
            this.parent = parent;

            textItemName = (TextView) card.findViewById(R.id.text_card_l);
            textItemValue = (TextView) card.findViewById(R.id.text_card_m);
            imgview= (ImageView) card.findViewById(R.id.image_card);
            btn= (Button) card.findViewById(R.id.button_card);


        }

        public void setItemName(CharSequence name){
            textItemName.setText(name);
        }


        public void setItemValue(CharSequence val){
            textItemValue.setText(val);
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
