package com.example.boban.classwiz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddHomework extends ActionBarActivity {

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    String[] mDrawerListItems;
    private Spinner hour, minute, month, day, year;
    private EditText assignmentName;
    private Button addAssignment;
    private String h, min, mon, d, y;

    public double[] hours, minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        //XML TO JAVA
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(android.R.id.list);
        mDrawerListItems = getResources().getStringArray(R.array.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerListItems));

        //Drawer List to do stuff
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int editedPosition = position + 1;
                Toast.makeText(AddHomework.this, "You selected item " + editedPosition, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        startActivity(new Intent(AddHomework.this, MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AddHomework.this, AddHomework.class));
                        break;
                }
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        //Toolbar
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
        //Assignment Name/Info
        assignmentName = (EditText) findViewById(R.id.assignmentName);

        //SPINNERS =D
        //Month
        month = (Spinner) findViewById(R.id.month);
        String[] monthArray = new String[12];
        for (int i = 0; i < 12; i++) {
            monthArray[i] = String.valueOf(i + 1);
        }

        month.setAdapter(new ArrayAdapter<String>(AddHomework.this, android.R.layout.simple_spinner_dropdown_item, monthArray));
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position < 10) {
                    mon = "0" + String.valueOf(position + 1);
                } else {
                    mon = String.valueOf(position + 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Day
        day = (Spinner) findViewById(R.id.day);
        String[] dayArray = new String[31];
        for (int i = 0; i < 31; i++) {
            dayArray[i] = String.valueOf(i + 1);
        }

        day.setAdapter(new ArrayAdapter<String>(AddHomework.this, android.R.layout.simple_spinner_dropdown_item, dayArray));
        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 10) {
                    d = "0" + String.valueOf(position + 1);
                } else {
                    d = String.valueOf(position + 1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Year
        year = (Spinner) findViewById(R.id.year);
        String[] yearArray = new String[6];
        for (int i = 0; i < 6; i++) {
            yearArray[i] = String.valueOf(i + 2014);
        }

        year.setAdapter(new ArrayAdapter<String>(AddHomework.this, android.R.layout.simple_spinner_dropdown_item, yearArray));
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                y = String.valueOf(position + 2014);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Hour
        hour = (Spinner) findViewById(R.id.hour);
        String[] hourArray = new String[24];
        for (int i = 0; i < 24; i++) {
            hourArray[i] = String.valueOf(i);
        }

        hour.setAdapter(new ArrayAdapter<String>(AddHomework.this, android.R.layout.simple_spinner_dropdown_item, hourArray));
        hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                h = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Minutes
        minute = (Spinner) findViewById(R.id.minute);
        String[] minuteArray = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteArray[i] = String.valueOf(i);
        }
        minute.setAdapter(new ArrayAdapter<String>(AddHomework.this, android.R.layout.simple_spinner_dropdown_item, minuteArray));
        minute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Button
        addAssignment = (Button) findViewById(R.id.addAssignment);
        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean after;
                String checkDate = mon + "/" + d + "/" + y + " " + h + ":" + min;
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                    Date strDate = sdf.parse(checkDate);
                    if (new Date().after(strDate)) {
                        Toast.makeText(AddHomework.this, "That day and time is in the past please try again", Toast.LENGTH_SHORT).show();
                    }else{

                        if (assignmentName.getText().toString().equals("")) {
                            Toast.makeText(AddHomework.this, "Cannot leave fields blank", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(AddHomework.this, "Assignment Added", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(AddHomework.this, "Something went wrong please try again.", Toast.LENGTH_SHORT).show();
                }


            }
        });

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
