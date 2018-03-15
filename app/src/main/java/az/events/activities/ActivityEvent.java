package az.events.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import az.events.R;
import az.events.adapters.ReyclerInEvent;
import az.events.datamodels.SubCategories;
import az.events.others.Constants;

public class ActivityEvent extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title;
    private CollapsingToolbarLayout collapsing;
    private ImageView imageView;
    private TextView info, event1, event2, event3, event4, event5;
    private RecyclerView recyclerView;
    private List<SubCategories> data = new ArrayList<>();
    private FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        //title.setTypeface(Fonts.getTypeface(this, Fonts.helvetiacNeueLight));
        title.setText("Event #1");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsing.setTitleEnabled(false);
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(getIntent().getIntExtra("transition",R.drawable.redbull));
        info = (TextView) findViewById(R.id.info);
        //info.setTypeface(Fonts.getTypeface(this, Fonts.helveticaNeueThin));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getIntent().getIntExtra("transition",R.drawable.redbull));
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsing.setContentScrimColor(palette.getDarkMutedColor(Color.parseColor("#000000")));
                collapsing.setStatusBarScrimColor(palette.getDarkMutedColor(Color.parseColor("#000000")));
                ((LinearLayout) findViewById(R.id.color)).setBackgroundColor(palette.getDarkMutedColor(Color.parseColor("#5F22BB")));
            }
        });

        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent().getType().equals(Constants.FROM_CATEGORY)){
            recyclerView.setAdapter(new ReyclerInEvent(this,data));
        }
        else
        if (getIntent().getType().equals(Constants.FROM_EVENT)){
            recyclerView.setAdapter(null);
        }

        event1 = (TextView) findViewById(R.id.event1);
        event2 = (TextView) findViewById(R.id.event2);
        event3 = (TextView) findViewById(R.id.event3);
        event4 = (TextView) findViewById(R.id.event4);
        event5 = (TextView) findViewById(R.id.event5);

//        event1.setTypeface(Fonts.getTypeface(this, Fonts.helveticaNeueBold));
//        event2.setTypeface(Fonts.getTypeface(this, Fonts.helveticaNeueThin));
//        event3.setTypeface(Fonts.getTypeface(this, Fonts.helveticaNeueThin));
//        event4.setTypeface(Fonts.getTypeface(this, Fonts.helvetiacNeueLight));
//        event5.setTypeface(Fonts.getTypeface(this, Fonts.helveticaNeueBold));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addData(){
        SubCategories d1 = new SubCategories();
        SubCategories d2 = new SubCategories();
        d1.setIcon(R.drawable.party);
        d1.setPhoto(R.drawable.party1);
        d1.setName("I took a pill in Ibiza");
        d1.setDate("2016 Summer, 12:30");
        d1.setGoing("10+ going to event");
        d1.setVenue("Baku Boulevard Yacht, Neftchilar avenue, Azerbaijan");
        d1.setPrice("$200");

        d2.setIcon(R.drawable.party1);
        d2.setPhoto(R.drawable.party);
        d2.setName("I can feel your heartbeat");
        d2.setDate("Friday night!");
        d2.setGoing("20+ going to event");
        d2.setVenue("Yacht");
        d2.setPrice("Free");

        data.add(d1);
        data.add(d2);
        data.add(d1);
        data.add(d2);
        data.add(d1);
    }
}
