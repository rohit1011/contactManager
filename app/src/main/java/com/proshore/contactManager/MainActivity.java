package com.proshore.contactManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.proshore.contactManager.adapter.ContactAdapter;
import com.proshore.contactManager.model.ContactModel;
import com.proshore.contactManager.utils.ContactRepo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private RecyclerView rvContacts;
private FloatingActionButton fabContacts;
private List<ContactModel> contactModel;
    RecyclerView.LayoutManager layoutManager;
    ContactAdapter contactAdapter ;
    ContactRepo contactRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabContacts = findViewById(R.id.fabAddContact);
        rvContacts = findViewById(R.id.rvContacts);

        fabContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddContactActivity.class));

            }
        });
    }
        private void getAllnotes() {
            contactRepo = new ContactRepo(getApplicationContext());
            contactModel = new ArrayList<>();

            contactModel = contactRepo.getAllContacts();
            contactAdapter = new ContactAdapter(getApplicationContext(), contactModel, new ContactAdapter.onContactClickListner() {
                @Override
                public void onContactClick(View v, int pos) {


                    Intent intent = new Intent(getApplicationContext(),AddContactActivity.class);
                    intent.putExtra("id",pos);
                    intent.putExtra("object",contactModel.get(pos));
                    intent.putExtra("status",true);
                    startActivity(intent);
                    contactAdapter.notifyDataSetChanged();

                }
            }, new ContactAdapter.onContactClickListner() {
                @Override
                public void onContactClick(View v, int pos) {

                openDeleteDialog(contactModel,pos);
                }
            });
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            rvContacts.setLayoutManager(layoutManager);
            rvContacts.setHasFixedSize(true);
            rvContacts.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();
        }


    private void openDeleteDialog(final List<ContactModel> contactModels, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle(""+contactModels.get(pos).getContactName());
        builder.setMessage("Are you sure, you want to Delete?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactRepo.deleteContacts(pos);
                contactModel.remove(pos);
                contactAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Contact delete successfully" , Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog =builder.create();
        dialog.show();
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.RED);
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.RED);
    }
        @Override
        protected void onResume() {
            super.onResume();
            getAllnotes();
            contactAdapter.notifyDataSetChanged();
        }

    }