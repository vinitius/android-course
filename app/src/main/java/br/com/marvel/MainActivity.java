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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.marvel.listener.OnUserSelectListener;
import br.com.marvel.model.User;
import br.com.marvel.view.DetailsFragment;
import br.com.marvel.view.MainFragment;


public class MainActivity extends AppCompatActivity implements OnUserSelectListener{


    private MainFragment mainFragment;
    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentTransaction mainTransaction =
                getSupportFragmentManager().beginTransaction();

        mainFragment = new MainFragment();

        mainTransaction.replace(R.id.content, mainFragment);
        mainTransaction.commit();

        if (getResources().getBoolean(R.bool.is_landscape)){
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this,"Menu selecionado.", Toast.LENGTH_LONG).show();
                break;
            default:
                break;

        }


        return true;
    }

    @Override
    public void onUserSelect(User user) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        DetailsFragment details = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER", user);
        details.setArguments(bundle);

        if (getResources().getBoolean(R.bool.is_landscape)) {
            transaction.replace(R.id.contentDetails, details);
        }else {
            transaction.replace(R.id.content, details);
            transaction.addToBackStack("DETAILS");
        }

        transaction.commit();


    }
}
