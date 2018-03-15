package az.events.activities;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import az.events.R;
import az.events.adapters.ViewPagerAdapterTabs;
import az.events.fragments.FragmentSecond;
import az.events.fragments.FragmentFirst;
import az.events.fragments.FragmentFourth;
import az.events.fragments.FragmentThird;
import az.events.others.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionMenu fam;

    private int[] tabsActive = {
            R.drawable.ic_action_e_icon,
            R.drawable.tabactive3,
            R.drawable.ic_action_tag,
            R.drawable.tabactive4
    };

    private List<Integer> actionBarColor = new ArrayList<>();
    private List<Integer> statusBarColor = new ArrayList<>();
    private FloatingActionButton a,b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fam = (FloatingActionMenu) findViewById(R.id.menu_labels_right);
        fam.setClosedOnTouchOutside(true);
        a = (FloatingActionButton) findViewById(R.id.action_a);
        b = (FloatingActionButton) findViewById(R.id.action_b);
        c = (FloatingActionButton) findViewById(R.id.action_c);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ViewPagerAdapterTabs adapter = new ViewPagerAdapterTabs(getSupportFragmentManager());
        adapter.addFragment(new FragmentFirst());
        adapter.addFragment(new FragmentSecond());
        adapter.addFragment(new FragmentThird());
        adapter.addFragment(new FragmentFourth());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabsActive[0]).getIcon().mutate().setAlpha(255);
        tabLayout.getTabAt(1).setIcon(tabsActive[1]).getIcon().mutate().setAlpha(192);
        tabLayout.getTabAt(2).setIcon(tabsActive[2]).getIcon().mutate().setAlpha(192);
        tabLayout.getTabAt(3).setIcon(tabsActive[3]).getIcon().mutate().setAlpha(192);


        actionBarColor.add(R.color.colorPrimary);
        actionBarColor.add(Color.parseColor("#EB5D4A"));
        actionBarColor.add(Color.parseColor("#A99CD2"));
        actionBarColor.add(Color.parseColor("#28B99D"));

        statusBarColor.add(R.color.colorPrimaryDark);
        statusBarColor.add(Color.parseColor("#BC5143"));
        statusBarColor.add(Color.parseColor("#8A80A9"));
        statusBarColor.add(Color.parseColor("#289580"));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < actionBarColor.size()-1 && position < adapter.getCount()-1) {
                    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                    int acolor = (Integer) argbEvaluator.evaluate(positionOffset,actionBarColor.get(position),actionBarColor.get(position+1));
                    int scolor = (Integer) argbEvaluator.evaluate(positionOffset,statusBarColor.get(position),statusBarColor.get(position+1));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(scolor);
                    }
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(acolor));
                    tabLayout.setBackgroundColor(acolor);
                    //fam.setMenuButtonColorNormal(acolor);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position==0)
                        fam.setVisibility(View.VISIBLE);
                else
                    fam.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().mutate().setAlpha(255);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().mutate().setAlpha(192);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.action_a:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.setType(Constants.FROM_CATEGORY);
                intent.putExtra("title",a.getLabelText());
                startActivity(intent);
                break;
            case R.id.action_b:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.setType(Constants.FROM_CATEGORY);
                intent.putExtra("title",b.getLabelText());
                startActivity(intent);
                break;
            case R.id.action_c:
                intent = new Intent(MainActivity.this, ActivitySubCategories.class);
                intent.setType(Constants.FROM_CATEGORY);
                intent.putExtra("title",c.getLabelText());
                startActivity(intent);
                break;
        }
    }
}
