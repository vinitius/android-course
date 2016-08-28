/*
 *
 *  Copyright (c) 2016 Speakgame
 *  All rights reserved.
 *
 *  This software is a confidential and proprietary information of Speakgame.
 *  ("Confidential Information").  You shall not disclose such
 *  Confidential Information and shall use it only in accordance with the terms
 *  of the license agreement you entered into with Speakgame.
 * /
 *
 */

package br.com.marvel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import br.com.marvel.db.DatabaseHelper;
import br.com.marvel.listener.OnCharacterSelectListener;
import br.com.marvel.view.DetailsFragment;
import br.com.marvel.view.MainFragment;


public class MainActivity extends AppCompatActivity implements OnCharacterSelectListener {


    private MainFragment mainFragment;
    private DetailsFragment detailsFragment;
    private Toolbar toolbar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        dbHelper = new DatabaseHelper(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        if (i == 0){
                            Intent intent = new
                                    Intent(MainActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                })
                .build()
                .addItem(new PrimaryDrawerItem().withName("Tutorial"));



        FragmentTransaction mainTransaction =
                getSupportFragmentManager().beginTransaction();

        mainFragment = new MainFragment();

        mainTransaction.replace(R.id.content, mainFragment);
        mainTransaction.commit();

        if (getResources().getBoolean(R.bool.is_landscape)) {
            FragmentTransaction detailsTransaction = getSupportFragmentManager()
                    .beginTransaction();

            detailsFragment = new DetailsFragment();

            detailsTransaction.replace(R.id.contentDetails, detailsFragment);
            detailsTransaction.commit();

        }








    }



//    @Override
//    public void onBackPressed() {
//        final AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(R.string.hello_world)
//                .setMessage("Deseja mesmo sair?")
//                .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MainActivity.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .create();
//
//        dialog.show();
//
//    }


    @Override
    public void onCharacterSelect(br.com.marvel.model.Character character) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        DetailsFragment details = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("CHARACTER", character);
        details.setArguments(bundle);

        if (getResources().getBoolean(R.bool.is_landscape)) {
            transaction.replace(R.id.contentDetails, details, "DETAILS");
        }else {
            transaction.replace(R.id.content, details);
            transaction.addToBackStack("DETAILS");
        }

        transaction.commit();


    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
