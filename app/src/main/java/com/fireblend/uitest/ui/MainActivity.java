package com.fireblend.uitest.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.fireblend.uitest.R;
import com.fireblend.uitest.db.ContactDB;
import com.fireblend.uitest.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView list;
    private static final String PREFS = "app.preferences";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (GridView)findViewById(R.id.lista_contactos);

        CreateContacts(this);

        setupList(this);
    }

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
                Intent myintent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(myintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupList(Context ctx) {
        final List<ContactDB> contacts;
        List<ContactDB> clist = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(ctx);
        Dao<ContactDB, Integer> contactDao = null;

        try
        {
            contactDao = helper.getContactDBDao();
            clist = contactDao.queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        contacts = clist;

        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        boolean columns = prefs.getBoolean("columns",false);
        list.setNumColumns((columns == true ? 2 : 1));

        list.setAdapter(new BaseAdapter() {
            @Override
            //Retorna el numero de elementos en la lista.
            public int getCount() {
                return contacts.size();
            }

            @Override
            //Retorna el elemento que pertenece a la posición especificada.
            public Object getItem(int position) {
                return contacts.get(position);
            }

            @Override
            //Devuelve un identificador único para cada elemento de la lista.
            //En nuestro caso, basta con devolver la posición del elemento en la lista.
            public long getItemId(int position) {
                return position;
            }

            @Override
            //Devuelve la vista que corresponde a cada elemento de la lista
            public View getView(int position, View convertView, ViewGroup parent) {

                prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                int size = prefs.getInt("size",10);

                LayoutInflater inflater = getLayoutInflater();
                View row = inflater.inflate(R.layout.contact_item, parent, false);

                final TextView name, age, phone, email;

                name = (TextView) row.findViewById(R.id.name);
                age = (TextView) row.findViewById(R.id.age);
                phone = (TextView) row.findViewById(R.id.phone);
                email = (TextView) row.findViewById(R.id.email);

                name.setTextSize(size);

                Button btn = (Button) row.findViewById(R.id.row_btn);

                //Basandonos en la posicion provista en este metodo, proveemos los datos
                //correctos para este elemento.
                final int pos = position;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, "Hola, "+contacts.get(pos).name, Toast.LENGTH_SHORT).show();
                        Intent myintent = new Intent(MainActivity.this, DisplayActivity.class);

                        myintent.putExtra("id",contacts.get(pos).contactId);
                        myintent.putExtra("name",name.getText().toString());
                        myintent.putExtra("age",age.getText().toString());
                        myintent.putExtra("phone",phone.getText().toString());
                        myintent.putExtra("email",email.getText().toString());

                        startActivity(myintent);
                    }
                });

                name.setText(contacts.get(position).name);
                age.setText(contacts.get(position).age+"");
                phone.setText(contacts.get(position).phone);
                email.setText(contacts.get(position).email);

                return row;
            }
        });
    }

    private void CreateContacts(Context ctx){

        final List<ContactDB> contacts = new ArrayList();
        DatabaseHelper helper = new DatabaseHelper(ctx);
        Dao<ContactDB, Integer> contactDao = null;

        try
        {
            contactDao = helper.getContactDBDao();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        contacts.add(new ContactDB("Sergio", 28, "sergiome@gmail.com", "88854764"));
        contacts.add(new ContactDB("JASON", 1, "alex@gmail.com", "88883644"));
        contacts.add(new ContactDB("Andrea", 2, "andrea@gmail.com", "98714764"));
        contacts.add(new ContactDB("Fabian", 3, "fabian@gmail.com", "12345678"));
        contacts.add(new ContactDB("Ivan", 4, "ivan@gmail.com", "87654321"));
        contacts.add(new ContactDB("Gabriela", 5, "gabriela@gmail.com", "09871234"));
        contacts.add(new ContactDB("Alex", 6, "sergiome@gmail.com", "43215678"));

        for (ContactDB contact : contacts) {
            try {
                contactDao.create(contact);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
