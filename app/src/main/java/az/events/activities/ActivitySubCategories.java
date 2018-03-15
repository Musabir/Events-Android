package az.events.activities;

import android.app.DatePickerDialog;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import az.events.R;
import az.events.adapters.RecyclerFilter;
import az.events.adapters.RecyclerFragmentFirst;
import az.events.adapters.RecyclerSubCategories;
import az.events.datamodels.SubCategories;

public class ActivitySubCategories extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private RecyclerView recyclerView, recyclerViewFilter;
    private List<SubCategories> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private String type = "";
    private FloatingActionMenu fam;
    private FloatingActionButton date;
    private int mYear, mMonth, mDay;
    private TextInputEditText inputSearch;

    private List<String> filters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subitmes);

        type = getIntent().getType();
        Toast.makeText(this,type,Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        //toolbarTitle.setTypeface(Fonts.getTypeface(this, Fonts.helvetiacNeueLight));
        toolbarTitle.setText(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        addData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSub);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerSubCategories(ActivitySubCategories.this,data, type));
        inputSearch = (TextInputEditText) findViewById(R.id.subinputSearch);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence constraint, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //   MainActivity.this.adapter.getFilter().filter(cs);
                List<SubCategories> copy_data = new ArrayList<>();


                for(int i=0;i<data.size();i++){
                    String s = data.get(i).getName().toLowerCase();
                    String c = constraint.toString().toLowerCase();
                    if(s.contains(c))
                    {
                        copy_data.add(data.get(i));
                    }
                }
                recyclerView.setAdapter(new RecyclerSubCategories(ActivitySubCategories.this,copy_data, type));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }
        });
        fam = (FloatingActionMenu) findViewById(R.id.menu_labels_right);
        fam.setClosedOnTouchOutside(true);
        date = (FloatingActionButton) findViewById(R.id.action_b);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivitySubCategories.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Toast.makeText(getApplicationContext(),dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
                fam.close(true);
            }
        });

        recyclerViewFilter = (RecyclerView) findViewById(R.id.recyclerViewFilter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFilter.setLayoutManager(manager);
        filters.add("1Konsert");
        filters.add("2Teatr");
        filters.add("3Party");
        filters.add("4Restoran");
        filters.add("5Azerbaijan");
        filters.add("6Konsert");
        filters.add("7Teatr");
        filters.add("8Party");
        filters.add("9Restoran");
        filters.add("10Azerbaijan");
        filters.add("11Konsert");
        filters.add("12Teatr");
        filters.add("13Party");
        filters.add("14Restoran");
        filters.add("15Azerbaijan");
        recyclerViewFilter.setAdapter(new RecyclerFilter(ActivitySubCategories.this,filters));
    }

    private void addData(){
        SubCategories d1 = new SubCategories();
        SubCategories d2 = new SubCategories();
        d1.setIcon(R.drawable.party);
        d1.setPhoto(R.drawable.party1);
        d1.setName("Tomorrow will explode");
        d1.setDate("2016 Summer, 12:30");
        d1.setGoing("10+ going to event");
        d1.setVenue("Baku Boulevard Yacht, Neftchilar avenue, Azerbaijan");
        d1.setPrice("$200");

        d2.setIcon(R.drawable.party1);
        d2.setPhoto(R.drawable.party);
        d2.setName("Tomorrow will explode");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
