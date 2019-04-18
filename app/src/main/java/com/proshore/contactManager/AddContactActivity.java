package com.proshore.contactManager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.proshore.contactManager.model.ContactModel;
import com.proshore.contactManager.utils.ContactRepo;

import java.util.List;
import java.util.regex.Pattern;

public class AddContactActivity extends AppCompatActivity {
Button btnAddContact;
TextInputEditText etContactName,etContactNumber;
ContactRepo contactRepo;
Boolean validation;
ContactModel contactModels;
Boolean status= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        getSupportActionBar().setTitle("Add Contacts");
        contactModels = (ContactModel) getIntent().getSerializableExtra("object");
        status = getIntent().getBooleanExtra("status",false);

        btnAddContact = findViewById(R.id.btn_add_contact);
        etContactName = findViewById(R.id.et_contact_name);
        etContactNumber = findViewById(R.id.et_contact_number);
        contactRepo = new ContactRepo(getApplicationContext());
        if (status){
            etContactNumber.setText(contactModels.getContactNumber());
            etContactName.setText(contactModels.getContactName());
            btnAddContact.setText("Update Contact");

        }

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation = true;
                String firstLetter= etContactNumber.getText().toString().substring(0,1);
                if (etContactName.getText().toString().length()<1){
                    etContactName.setError("Contact name Required");
                    etContactName.requestFocus();
                    validation=false;
                }
                if (etContactNumber.getText().toString().length()<10||etContactNumber.getText().toString().length()>14){
                    etContactNumber.setError("Contact number length invalid");
                    etContactNumber.requestFocus();
                    validation=false;
                }
                if (!firstLetter.equals("+")){
                    etContactNumber.setError("first number should include + ");
                    etContactNumber.requestFocus();
                    validation=false;
                }

                if (validation){
                    if (!status) {
                        insertContacts();
                    }
                    else {
                        updateContacts();
                    }
                }


            }
        });



    }

    private void updateContacts() {
        ContactModel contactModel = new ContactModel();
        contactModel.setContactName(etContactName.getText().toString());
        contactModel.setContactNumber(etContactNumber.getText().toString());
        contactModel.setContactId(contactModels.getContactId());
        contactRepo.updateContacts(contactModel);
        Toast.makeText(getApplicationContext(),"Contact Updated Successfully",Toast.LENGTH_LONG).show();


        finish();
    }

    private void insertContacts() {
        ContactModel contactModel = new ContactModel();
        contactModel.setContactName(etContactName.getText().toString());
        contactModel.setContactNumber(etContactNumber.getText().toString());
        contactRepo.insertContacts(contactModel);
        Toast.makeText(getApplicationContext(),"Contact Inserted Successfully",Toast.LENGTH_LONG).show();
        finish();
    }
}
