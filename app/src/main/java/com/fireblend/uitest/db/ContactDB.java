package com.fireblend.uitest.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class ContactDB {
    @DatabaseField(generatedId = true, columnName = "contactId")
    public int contactId;

    @DatabaseField(columnName = "name", canBeNull = false)
    public String name;

    @DatabaseField(columnName = "age")
    public int age;

    @DatabaseField(columnName = "phone")
    public String phone;

    @DatabaseField(columnName = "email")
    public String email;

    public ContactDB(String name, int age, String email, String phone)
    {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }

    public ContactDB()
    {

    }
}