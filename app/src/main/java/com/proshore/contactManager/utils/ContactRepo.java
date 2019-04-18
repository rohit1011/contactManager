package com.proshore.contactManager.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.proshore.contactManager.dao.ContactDao;
import com.proshore.contactManager.database.ContactDatabase;
import com.proshore.contactManager.model.ContactModel;

import java.util.List;

public class ContactRepo {
ContactDatabase contactDatabase;
ContactDao contactDao;
Context context;
List<ContactModel> contactModels;

    public ContactRepo(Context context) {
        this.context = context;
        contactDatabase =ContactDatabase.getInstance(context);
        contactDao =contactDatabase.getContactDao();
        contactModels =contactDao.getContacts();
    }
public List<ContactModel> getAllContacts(){
return contactModels;
}

public void insertContacts(ContactModel contactModel){
        new insertContactAsync(contactDao).execute(contactModel);

}
    public void updateContacts(ContactModel contactModel){
        new updateContactsAsync(contactDao).execute(contactModel);

    }

    public void deleteContacts(int pos){
contactDatabase.getContactDao().deleteContact(contactModels.get(pos));
    }


    private class updateContactsAsync extends AsyncTask<ContactModel,Void,Void> {
        private ContactDao contactDao;
        public updateContactsAsync(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(ContactModel... contactModels) {
            contactDao.updateContact(contactModels[0]);
            return null;
        }
    }

    private class insertContactAsync extends AsyncTask<ContactModel,Void,Void> {
        private ContactDao contactDao;
        public insertContactAsync(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(ContactModel... contactModels) {
        contactDao.insertContact(contactModels[0]);
            return null;
        }
    }
}
