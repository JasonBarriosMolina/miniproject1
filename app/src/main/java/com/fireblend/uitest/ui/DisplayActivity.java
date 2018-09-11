package com.fireblend.uitest.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.R;
import com.fireblend.uitest.db.ContactDB;
import com.fireblend.uitest.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayActivity extends AppCompatActivity {

    private static final String PREFS = "app.preferences";
    private SharedPreferences prefs;

    @BindView(R.id.delete_button)
    Button delete;
    @BindView (R.id.name)
    TextView  lblname;
    @BindView (R.id.age)
    TextView lblage;
    @BindView (R.id.phone)
    TextView lblphone;
    @BindView (R.id.email)
    TextView lblemail;

    Context ctx;
    int contactId;

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
                Intent myintent = new Intent(DisplayActivity.this, PreferencesActivity.class);
                startActivity(myintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ButterKnife.bind(this);

        lblname.setText("Nombre : " + getIntent().getStringExtra("name"));
        lblage.setText("Edad : " + getIntent().getStringExtra("age"));
        lblphone.setText("Teléfono : " + getIntent().getStringExtra("phone"));
        lblemail.setText("Correo : " + getIntent().getStringExtra("email"));

        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (prefs.getBoolean("delete", false) == true)
            delete.setVisibility(View.GONE);

        this.contactId = getIntent().getIntExtra("id",0);
        this.ctx = this;
    }

    @OnClick(R.id.delete_button)
    public void delete() {

        new AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("Desea eliminar el contacto ?")
            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHelper helper = new DatabaseHelper(ctx);
                    Dao<ContactDB, Integer> contactDao = null;
                    try {
                        contactDao = helper.getContactDBDao();
                        ContactDB d = new ContactDB();
                        d.contactId = contactId;
                        contactDao.delete(d);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ctx,"Eliminado!",Toast.LENGTH_LONG).show();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .show();


    }
}
