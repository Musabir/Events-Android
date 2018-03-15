package az.events.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

import az.events.R;
import az.events.activities.ActivitySubCategories;
import az.events.datamodels.SubCategories;
import az.events.others.Constants;
import az.events.others.CustomImageView;

public class RecyclerFragmentFirst extends RecyclerView.Adapter<RecyclerFragmentFirst.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> photos = Collections.emptyList();
    private List<String> icons = Collections.emptyList();
    private List<String> names = Collections.emptyList();
    private List<String> ids = Collections.emptyList();

    public RecyclerFragmentFirst(Context context, List<String> ids, List<String> names, List<String> photos){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.names = names;
        this.photos = photos;
        this.ids = ids;
        this.icons = icons;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_fragment_first,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView.setText(names.get(position));
        holder.textView.setTag(ids.get(position));

        Glide.with(context).load(R.drawable.party)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(new ColorDrawable(Color.parseColor("#ffccaa")))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        Glide.with(context).load("")
                .thumbnail(0.5f)
                .crossFade()
                //.placeholder(new ColorDrawable(Color.parseColor("#aaccff")))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icon);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivitySubCategories.class);
                intent.setType(Constants.FROM_TIMELINE);
                intent.putExtra("title",holder.textView.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CustomImageView imageView;
        private ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imageView = (CustomImageView) itemView.findViewById(R.id.photo);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
