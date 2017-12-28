package com.example.vergieclarias.veensneakers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DataBarang extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    DataBase dataBase;
    final Context p=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBase=new DataBase(this);
        load();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=LayoutInflater.from(p);
                View mView =layoutInflater.inflate(R.layout.inputdata,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(p);
                alertDialogBuilderUserInput.setView(mView);

                final EditText nama=(EditText)mView.findViewById(R.id.inputmenu);
                final EditText harga=(EditText)mView.findViewById(R.id.inputharga);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.insertDatabarang(nama.getText().toString(),Integer.parseInt(harga.getText().toString()));
                        load();
                    }
                })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Input Barang");
                alertDialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c=dataBase.selectedbarang(id);
                final String sendId=c.getString(0);
                final String sendName=c.getString(1);
                final String sendHarga=c.getString(2);

                LayoutInflater layoutInflater=LayoutInflater.from(p);
                View mView =layoutInflater.inflate(R.layout.detilbarang,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(p);
                alertDialogBuilderUserInput.setView(mView);

                final EditText nama=(EditText)mView.findViewById(R.id.textnama);
                final EditText harga=(EditText)mView.findViewById(R.id.textharga);
                final Button delete=(Button)mView.findViewById(R.id.btndelete);

                nama.setText(sendName);
                harga.setText(sendHarga);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBase.deletebarang(Long.parseLong(sendId));
                        load();
                    }
                });
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.updatebarang(Long.parseLong(sendId),nama.getText().toString(),Integer.parseInt(harga.getText().toString()));
                    load();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_barang) {
            Intent intent=new Intent(getApplicationContext(),DataBarang.class);
            startActivity(intent);
        } else if (id == R.id.nav_transaksi) {
            Intent intent=new Intent(getApplicationContext(),Transaksi.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(DataBarang.this,R.layout.listsepatu , cursor, from, to);
        adapter.notifyDataSetChanged();
        list = (ListView) findViewById(R.id.listvsepatu);
        list.setAdapter(adapter);
    }
}
