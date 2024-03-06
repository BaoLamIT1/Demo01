package com.example.demo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter  {

    private ArrayList<Contact> data;   // Du lieu cho Adapter lay tu Contact
    private  Activity context ; // ngu canh cua ung dung

    private LayoutInflater inflater; // doi tuong de phan tich layout
    public Adapter(ArrayList<Contact> data, Activity context){
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        if (v == null)
            v = inflater.inflate(R.layout.activity_contactitem,null);
        // Lay du lieu ben contactitem.xml va set = gia tri ben Contact
        TextView textViewName = v.findViewById(R.id.tvName);
        textViewName.setText(data.get(position).getName());

        TextView textViewPhone = v.findViewById(R.id.tvPhone);
        textViewPhone.setText(data.get(position).getPhoneNumber());

        CheckBox checkBox = v.findViewById(R.id.chbItem);
        checkBox.setChecked(data.get(position).isStatus());

        ImageView imageView = v.findViewById(R.id.imageView_contact);


        Uri imgUri = Uri.parse(data.get(position).getImage());
        imageView.setImageURI(imgUri);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setStatus(checkBox.isChecked());
            }
        });

            return v;
    }
}
