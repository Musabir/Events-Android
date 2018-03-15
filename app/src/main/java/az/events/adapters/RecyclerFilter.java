package az.events.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import az.events.R;
import az.events.others.Fonts;

public class RecyclerFilter extends RecyclerView.Adapter<RecyclerFilter.MyViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<String> filters = Collections.emptyList();
    private List<Integer> colorsCard = new ArrayList<>();
    private List<Integer> colorsText = new ArrayList<>();

    public RecyclerFilter(Context context, List<String> filters){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.filters = filters;
        for (int i=0;i<filters.size();i++) {
            colorsCard.add(Color.parseColor("#37286D"));
            colorsText.add(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_filter,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.cardText.setText(filters.get(position));
        holder.card.setCardBackgroundColor(colorsCard.get(position));
        holder.cardText.setTextColor(colorsText.get(position));
        //holder.cardText.setTypeface(Fonts.getTypeface(context, Fonts.helveticaNeueMedium));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(holder.card.getTag().equals("0")){
//                    holder.card.setCardBackgroundColor(Color.parseColor("#ffffff"));
//                    holder.cardText.setTextColor(Color.parseColor("#37286D"));
//                    holder.card.setTag("1");
//                }
//                else {
//                    holder.card.setCardBackgroundColor(Color.parseColor("#37286D"));
//                    holder.cardText.setTextColor(Color.parseColor("#ffffff"));
//                    holder.card.setTag("0");
//                }
                colorsCard.set(position,Color.parseColor("#ffffff"));
                colorsText.set(position,Color.parseColor("#37286D"));
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cardText;
        private CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card);
            cardText = (TextView) itemView.findViewById(R.id.cardText);
        }
    }
}
