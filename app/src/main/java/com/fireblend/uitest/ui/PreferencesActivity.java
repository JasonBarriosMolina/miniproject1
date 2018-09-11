package com.fireblend.uitest.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PreferencesActivity extends AppCompatActivity {

    private static final String PREFS = "app.preferences";
    private SharedPreferences prefs;

    @BindView(R.id.save_button)
    Button save;
    @BindView (R.id.font_size)
    TextView font_size;
    @BindView (R.id.delete_contact)
    Switch delete_contact;
    @BindView (R.id.view_columns)
    Switch view_columns;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:

                return true;
            case R.id.action_settings:
                Intent myintent = new Intent(PreferencesActivity.this, PreferencesActivity.class);
                startActivity(myintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);

        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        boolean delete = prefs.getBoolean("delete", false);
        boolean columns = prefs.getBoolean("columns", false);
        int size = prefs.getInt("size",15);

        delete_contact.setChecked(delete);
        view_columns.setChecked(columns);
        font_size.setTextSize(size);

    }

    @OnClick(R.id.save_button)
    public void save() {

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("delete", delete_contact.isChecked());
        editor.putBoolean("columns", view_columns.isChecked());
        editor.putInt("size", Integer.parseInt((font_size.getText().toString() == "" ? "10" : font_size.getText().toString())));

        editor.commit();

        Toast.makeText(this,"Guardado!",Toast.LENGTH_LONG).show();
    }

}
