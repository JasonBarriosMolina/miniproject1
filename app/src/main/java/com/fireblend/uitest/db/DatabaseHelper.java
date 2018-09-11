package com.fireblend.uitest.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Dao<ContactDB, Integer> contactDao = null;

    public DatabaseHelper(Context context) {
        super(context, "contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
    {
        try {
            TableUtils.createTable(connectionSource, ContactDB.class);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try {
            TableUtils.dropTable(connectionSource, ContactDB.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    public Dao<ContactDB, Integer> getContactDBDao() throws SQLException {
        if (contactDao == null) {
            contactDao = getDao(ContactDB.class);
        }
        return contactDao;
    }
    @Override public void close() { contactDao = null;
        super.close();
    }
}
