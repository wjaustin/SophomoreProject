package com.example.boban.classwiz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MainActivity extends ActionBarActivity {

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    String[] mDrawerListItems;
    //Test
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);
        mDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerListItems));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, AddHomework.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, MoodleLogin.class));
                        break;
                }
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Test
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        Set<String> set = VariableDB.getList(MainActivity.this);
        if (set != null) {
            ArrayList<String> mainList = new ArrayList<String>();
            mainList.addAll(set);
            ArrayList<Assignment> assignments = new ArrayList<Assignment>();

            for (int i = 0; i < mainList.size(); i++) {
                String full, name, info;
                int location;
                full = mainList.get(i);
                Toast.makeText(MainActivity.this, full, Toast.LENGTH_SHORT).show();
                location = full.indexOf("/");
                if (location < full.length() && location > -1) {
                    name = full.substring(0, location - 1);
                    info = full.substring(location + 1, full.length());
                    assignments.add(new Assignment(name, info));
                }
            }


            if (assignments.size() > 1) {
                for (int x = assignments.size()-1; x >=0; x--) {
                    for (int i = 0; i < x; i++) {
                        try {
                            String date = assignments.get(i+1).getDesc();
                            String date2 = assignments.get(i).getDesc();
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                            Date strDate = sdf.parse(date);
                            Date strDate2 = sdf.parse(date2);

                            if (strDate2.after(strDate)) {
                                Assignment temp = new Assignment(assignments.get(i).getTitle(), assignments.get(i).getDesc());
                                assignments.get(i).setDesc(assignments.get(i+1).getDesc());
                                assignments.get(i).setTitle(assignments.get(i+1).getTitle());

                                assignments.get(i+1).setDesc(temp.getDesc());
                                assignments.get(i+1).setTitle(temp.getTitle());
                            }

                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                    }
                }
            }




            mAdapter = new MyAdapter(assignments);
            mRecyclerView.setAdapter(mAdapter);
        }
        mDrawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


