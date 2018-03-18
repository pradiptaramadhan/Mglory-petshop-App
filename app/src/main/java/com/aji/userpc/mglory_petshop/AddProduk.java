package com.aji.userpc.mglory_petshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduk extends AppCompatActivity {

    EditText eTNama;
    EditText eTHarga;
    EditText eTDeskripsi;
    Spinner spinKategori;
    Button btnAddProduk;

    DatabaseReference databaseProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);

        databaseProduk = FirebaseDatabase.getInstance().getReference("Produk");

        eTNama = (EditText) findViewById(R.id.editTextNama);
        eTHarga = (EditText) findViewById(R.id.editTextHarga);
        eTDeskripsi = (EditText) findViewById(R.id.editTextDeskripsi);
        spinKategori = (Spinner) findViewById(R.id.spinnerKategori);
        btnAddProduk = (Button) findViewById(R.id.buttonAddProduk);

        btnAddProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduk();
            }
        });
    }

    private void addProduk() {
        //getting the values to save
        String nama = eTNama.getText().toString().trim();
        String harga = eTHarga.getText().toString().trim();
        String deskripsi = eTDeskripsi.getText().toString().trim();
        String kategori = spinKategori.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(nama)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseProduk.push().getKey();

            //creating an Artist Object
           Produk produk = new Produk (id,nama,harga,deskripsi,kategori);

            //Saving the Artist
            databaseProduk.child(id).setValue(produk);

            //setting edittext to blank again
            eTNama.setText("");

            //displaying a success toast
            Toast.makeText(this, "Produk added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
