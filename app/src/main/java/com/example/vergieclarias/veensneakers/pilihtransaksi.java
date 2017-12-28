package com.example.vergieclarias.veensneakers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class pilihtransaksi extends AppCompatActivity {

    EditText jumlah,ukuran;
    Button add;
    final Context p=this;
    ListView list;
    DataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihtransaksi);
        dataBase=new DataBase(this);
        load();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c=dataBase.selectedbarang(id);
                final String sendId=c.getString(0);
                final String sendName=c.getString(1);
                final String sendHarga=c.getString(2);

                LayoutInflater layoutInflater=LayoutInflater.from(p);
                View mView =layoutInflater.inflate(R.layout.tambahtransaksi,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(p);
                alertDialogBuilderUserInput.setView(mView);

                ukuran=(EditText)mView.findViewById(R.id.edtukuran);

                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.insertDatatransaksi(sendName,Integer.parseInt(ukuran.getText().toString()),Integer.parseInt(sendHarga),Integer.parseInt(sendHarga));
                        Intent intent=new Intent(getApplicationContext(),Transaksi.class);
                        startActivity(intent);
                    }
                })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Add to Chart");
                alertDialog.show();

            }
        });


    }
    public void load(){
        Cursor cursor = null;
        try {
            cursor = dataBase.readAllbarang();
        } catch (Exception e) {
            Toast.makeText(this,"salah",Toast.LENGTH_LONG).show();
        }
        String[] from = new String[]{"nama_sepatu", "harga_sepatu"};
        int[] to = new int[]{R.id.menu, R.id.harga };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(pilihtransaksi.this,R.layout.listsepatu , cursor, from, to);
        adapter.notifyDataSetChanged();
        list = (ListView) findViewById(R.id.listpilihtransaksi);
        list.setAdapter(adapter);
    }
}
