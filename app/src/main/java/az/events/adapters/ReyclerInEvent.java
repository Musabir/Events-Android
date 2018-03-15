package az.events.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import az.events.R;
import az.events.activities.ActivityEvent;
import az.events.datamodels.SubCategories;
import az.events.others.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReyclerInEvent extends RecyclerView.Adapter<ReyclerInEvent.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<SubCategories> data = Collections.emptyList();

    public ReyclerInEvent(Context context, List<SubCategories> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_subcategory_small,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.venue.setText(data.get(position).getVenue());
        holder.date.setText(data.get(position).getDate());
        holder.price.setText(data.get(position).getPrice());
        holder.going.setText(data.get(position).getGoing());

//        holder.name.setTypeface(Fonts.getTypeface(context, Fonts.helvetiacNeueLight));
//        holder.venue.setTypeface(Fonts.getTypeface(context, Fonts.helveticaNeue));
//        holder.date.setTypeface(Fonts.getTypeface(context, Fonts.helveticaNeueThin));
//        holder.price.setTypeface(Fonts.getTypeface(context, Fonts.helvetiacNeueLight));

        holder.icon.setImageResource(data.get(position).getIcon());
        holder.photo.setImageResource(data.get(position).getPhoto());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), data.get(position).getPhoto());
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                holder.shadow.setBackgroundColor(p.getDarkVibrantColor(Color.parseColor("#000000")));
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.like.getDrawable().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.MULTIPLY);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityEvent.class);
                intent.putExtra("transition", data.get(holder.getAdapterPosition()).getPhoto());
                intent.setType(Constants.FROM_EVENT);
                Activity activity = (Activity) context;
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, (View) holder.photo, "transition");
                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout shadow;
        private ImageView photo, like;
        private CircleImageView icon;
        private TextView name, venue, date, price, going;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            venue = (TextView) itemView.findViewById(R.id.venue);
            date = (TextView) itemView.findViewById(R.id.date);
            price = (TextView) itemView.findViewById(R.id.price);
            going = (TextView) itemView.findViewById(R.id.going);

            icon = (CircleImageView) itemView.findViewById(R.id.icon);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            like = (ImageView) itemView.findViewById(R.id.like);
            shadow = (FrameLayout) itemView.findViewById(R.id.shadow);
        }
    }
}
