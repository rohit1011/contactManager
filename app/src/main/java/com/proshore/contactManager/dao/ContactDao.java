package com.proshore.contactManager.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.proshore.contactManager.model.ContactModel;

import java.util.List;
@Dao
public interface ContactDao {
@Query("SELECT * FROM contact")
    List<ContactModel> getContacts();

@Insert
long insertContact(ContactModel contactModel);

@Update
    void updateContact(ContactModel contactUpdate);
@Delete
void deleteContact(ContactModel contactModel);

}
