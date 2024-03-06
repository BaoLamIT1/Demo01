package com.example.demo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etName;
    private EditText etPhoneNumber;
    private Button btnAdd, btnAddImg, btnCancle;
    private CheckBox checkBox;
    private ImageView imageView;
    private String imgPath;

    private String imageUri = "https://images.app.goo.gl/Ye3hL1QmqKcmBDNQ8" ;

    // private Uri imgUri; // khai bao dia chi imgView

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        etId = findViewById(R.id.edtId);
        etName = findViewById(R.id.edtName);
        etPhoneNumber = findViewById(R.id.edtPhone);
        imageView = findViewById(R.id.imageView);
        btnAdd = findViewById(R.id.btnAdd);
        btnAddImg = findViewById(R.id.btnAddImg);
        btnCancle = findViewById(R.id.btnCancle);
        checkBox = findViewById(R.id.checkBox);



        // registerResult();

        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,130);
            }
        });  // Lay anh tu library

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lay du lieu va gui ve cho MainActivity
                try {
                    int id = Integer.parseInt(etId.getText().toString());
                    String name = etName.getText().toString();
                    String phone = etPhoneNumber.getText().toString();
                    boolean status = checkBox.isChecked();

                    // kiem tra xem cac truong co rong ko ?
                    if (name.isEmpty() || phone.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Missing data field", Toast.LENGTH_SHORT).show();
                    }   else {
                        // khai bao intent
                        Intent intent = new Intent(SubActivity.this, MainActivity.class);
                        // Bundle is used to encapsulate data and send to another Activity from SubActivity via Intent
                        Bundle b = new Bundle();
                        b.putInt("id",id);
                        b.putString("Name",name);
                        b.putString("PhoneNumber",phone);
                        b.putBoolean("Status",status);
                        b.putString("image",imageUri);
                        intent.putExtras(b);
                        //intent.putExtra("additem",b);
                        setResult(150,intent);
                        finish();

                    }
                }catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),"Please input Id",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 130 && resultCode == RESULT_OK && data != null){
            Uri ImageUri = data.getData();
            imageUri = ImageUri.toString();
            imageView.setImageURI(ImageUri);
        }
    }
    //    private void registerResult() {
//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult o) {
//                        try {
//                            Uri imgUri = o.getData().getData();
//                            imageView.setImageURI(imgUri);
//                            imgPath = imgUri.toString();
//                        } catch (Exception e) {
//                            Toast.makeText(SubActivity.this, "No image was selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//    }

}