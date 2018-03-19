package com.aji.userpc.mglory_petshop;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CrudProduk extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EditText eTNama;
    EditText eTHarga;
    EditText eTDeskripsi;
    Spinner spinKategori;
    Button btnAddProduk;
    ListView listViewProduk;
    DatabaseReference databaseProduk;

    List<Produk> produks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_produk);

        listViewProduk = (ListView) findViewById(R.id.listViewProduks);

        databaseProduk = FirebaseDatabase.getInstance().getReference("Produk");

        eTNama = (EditText) findViewById(R.id.editTextNama);
        eTHarga = (EditText) findViewById(R.id.editTextHarga);
        eTDeskripsi = (EditText) findViewById(R.id.editTextDeskripsi);
        spinKategori = (Spinner) findViewById(R.id.spinnerKategori);
        btnAddProduk = (Button) findViewById(R.id.buttonAddProduk);

        produks = new ArrayList<>();

        btnAddProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduk();
            }
        });

        listViewProduk.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Produk produk = produks.get(i);
                showUpdateDeleteDialog(produk.getId(), produk.getNamaProduk(),
                        produk.getHargaProduk(), produk.getKategoriProduk());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseProduk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                produks.clear();


                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {

                    Produk produk = produkSnapshot.getValue(Produk.class);
                    produks.add(produk);
                }

                ProdukAdapter produkAdapter = new ProdukAdapter(CrudProduk.this, produks);
                listViewProduk.setAdapter( produkAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "eror : .", databaseError.toException());

            }
        });
    }

    private void addProduk() {

        String nama = eTNama.getText().toString().trim();
        String harga = eTHarga.getText().toString().trim();
        String deskripsi = eTDeskripsi.getText().toString().trim();
        String kategori = spinKategori.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nama)) {

            String id = databaseProduk.push().getKey();
            Produk produk = new Produk (id,nama,harga,deskripsi,kategori);
            databaseProduk.child(id).setValue(produk);
            eTNama.setText("");
            Toast.makeText(this, "Produk ditambahkan", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "masukan nama ", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateProduk(String id, String nama, String harga, String kategori) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Produk").child(id);


        Produk produk = new Produk(id, nama, harga, kategori);
        dR.setValue(produk);
        Toast.makeText(getApplicationContext(), "Produk telah di Update", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteProduk(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Produk").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Produk Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private void showUpdateDeleteDialog(final String Id, String Name, String harga, String kategori) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextHarga =  (EditText) dialogView.findViewById(R.id.editTextHarga);
        final Spinner spinnerKategori = (Spinner) dialogView.findViewById(R.id.spinnerKategori);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduk);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduk);

        dialogBuilder.setTitle(Name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String harga = editTextHarga.getText().toString().trim();
                String kategori = spinnerKategori.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateProduk(Id, name,harga, kategori);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProduk(Id);
                b.dismiss();

            }
        });
    }


}
