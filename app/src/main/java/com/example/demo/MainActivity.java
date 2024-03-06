package com.example.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // khai bao doi tuong luu tru danh sach cac contact
    private ArrayList<Contact> ContactList;
    private Adapter ContactAdapter;
    private EditText etSearch;
    private ListView lstViewContact;
    private Button btnAdd, btnDelete;
    private int SelectedItemId;

    IntentFilter intentFilter;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle b = data.getExtras();
        // Lay du lieu tu Bundle
        int id = b.getInt("id");
        String name = b.getString("name");
        String phoneNumber = b.getString("phoneNumber");

        boolean status = b.getBoolean("status");

        String ImgUri = b.getString("image");

        Contact newcontact = new Contact(id, name, phoneNumber, ImgUri,false);
        if (requestCode == 100 && resultCode ==RESULT_OK)
        {
            // THEM
            ContactList.add(newcontact);
            ContactAdapter.notifyDataSetChanged();

        } else if (resultCode == RESULT_CANCELED) {
            Log.v("Error","SomeThing error");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactList = new ArrayList<>();
        ContactList.add(new Contact(1, "Mot", "34567", "@drawable/vol33", true));
        ContactList.add(new Contact(2, "Hai", "0987", "@drawable/vol33", false));
        ContactList.add(new Contact(1, "Ba", "56789", "@drawable/vol33", false));

        etSearch = findViewById(R.id.etName);
        lstViewContact = findViewById(R.id.lstContact);
        btnAdd = findViewById(R.id.btnThem);
        btnDelete = findViewById(R.id.btnXoa);

        // Create Adapter
        ContactAdapter = new Adapter(ContactList, this);
        lstViewContact.setAdapter(ContactAdapter);

        // btnThem
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Create Intent to open MainActivity2
                Intent intent = new Intent(MainActivity.this, SubActivity.class);

                // 2. Transmit data to SubActivity by using bundle if necessary
                // 3. Open SubActivity by using function startactivity or startactivityforresult
                //startActivity(intent);
                startActivityForResult(intent, 100);

            }
        });

        // btnXoa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Do you want to delete this item?");
                builder.setMessage("Delete");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < ContactList.size(); i++) {
                            if (ContactList.get(i).isStatus()) ContactList.remove(i);
                        }
                        ContactAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO", null);
                builder.create().show();

            }
        });
    }
    // End OnCreate



}