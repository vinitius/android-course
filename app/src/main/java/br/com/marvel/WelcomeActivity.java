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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.marvel.model.User;


public class WelcomeActivity extends AppCompatActivity {

    private TextView text;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        text = (TextView)findViewById(R.id.text);
        list = (ListView)findViewById(R.id.list);

        final User user = (User)getIntent().getSerializableExtra("KEY");


        text.setText("Bem-vindo , " + user.getName());

        ArrayAdapter<User> adapter =
                new ArrayAdapter<User>(this,android.R.layout.simple_list_item_1,generateUsers());

        list.setAdapter(adapter);


    }


    private List<User> generateUsers(){
        final List<User> array = new ArrayList<>();
        for(int i =0; i< 30 ; i++){
            User user = new User();
            user.setName("USUARIO "+i);
            array.add(user);
        }


        return array;


    }
}
