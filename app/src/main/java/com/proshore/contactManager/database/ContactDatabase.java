package com.proshore.contactManager.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.proshore.contactManager.dao.ContactDao;
import com.proshore.contactManager.model.ContactModel;

@Database(entities = {ContactModel.class},version = 1,exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {
public abstract ContactDao getContactDao();
private static ContactDatabase contactDatabase;

public static ContactDatabase getInstance(Context context){
if (contactDatabase==null){
contactDatabase = buildDatabaseInstance(context);
}
return contactDatabase;
}

    private static ContactDatabase buildDatabaseInstance(Context context) {
return Room.databaseBuilder(context,
        ContactDatabase.class,
        "contactdb")
        .allowMainThreadQueries().build();
    }

}
