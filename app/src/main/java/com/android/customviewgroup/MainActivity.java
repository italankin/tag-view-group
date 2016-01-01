package com.android.customviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnLongClickListener {

    private static final String KEY_DATA = "data";

    private CustomLayout mLayout;
    private Random mRandom = new Random();
    private List<String> mData = new ArrayList<>(0);
    private String[] mArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (CustomLayout) findViewById(R.id.tag_list);
        mArray = getResources().getStringArray(R.array.data);
        if (savedInstanceState == null) {
            addNewView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                addNewView();
                return true;
            }
            case R.id.action_add_more: {
                for (int i = 0; i < 10; i++) {
                    addNewView();
                }
                return true;
            }
            case R.id.action_remove_all: {
                String s = getResources().getQuantityString(R.plurals.toast_removed, mData.size(), mData.size());
                mData.clear();
                mLayout.removeAllViews();
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String[] data = mData.toArray(new String[mData.size()]);
        outState.putStringArray(KEY_DATA, data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String[] data = savedInstanceState.getStringArray(KEY_DATA);
        if (data != null) {
            mData = new ArrayList<>(Arrays.asList(data));
            for (String s : mData) {
                TextView textView = new TextView(this, null, 0, R.style.Tag);
                textView.setText(s);
                textView.setOnClickListener(this);
                textView.setOnLongClickListener(this);
                mLayout.addView(textView);
            }
        }
    }

    private void addNewView() {
        TextView textView = new TextView(this, null, 0, R.style.Tag);
        String s = mArray[mRandom.nextInt(mArray.length)];
        textView.setText(s);
        textView.setOnClickListener(this);
        textView.setOnLongClickListener(this);
        mData.add(s);
        mLayout.addView(textView);
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        TextView tv = (TextView) v;
        String s = tv.getText().toString();
        mLayout.removeView(tv);
        Toast.makeText(this, getString(R.string.toast_item_removed, s), Toast.LENGTH_SHORT).show();
        mData.remove(s);
        return true;
    }

}
