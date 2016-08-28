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

package br.com.marvel.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.marvel.MainActivity;
import br.com.marvel.R;
import br.com.marvel.adapter.CharacterAdapter;
import br.com.marvel.connection.RestClient;
import br.com.marvel.listener.OnCharacterSelectListener;
import br.com.marvel.model.Character;
import br.com.marvel.model.ResponseWrapper;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by vinitius on 8/20/16.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private ListView list;
    private SwipeRefreshLayout swipe;
    private OnCharacterSelectListener listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCharacterSelectListener){
            listener = (OnCharacterSelectListener)context;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).getToolbar().setTitle("Marvel Acervo");
        ((MainActivity)getActivity()).getToolbar().setTitleTextColor(Color.WHITE);

        list = (ListView)view.findViewById(R.id.list);
        swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipe);

        swipe.setOnRefreshListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onCharacterSelect((Character) adapterView.getItemAtPosition(i));
            }
        });

        setHasOptionsMenu(true);

        onRefresh();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fav,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.fav){
            loadFavorites();
        }

        return true;
    }

    private void loadFavorites(){
        try {
            Dao<Character,String> dao = ((MainActivity)getActivity())
                    .getDbHelper().getDao(Character.class);
            List<Character> characters = dao.queryForAll();
            CharacterAdapter adapter = new
                    CharacterAdapter(characters,getActivity());
            list.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onRefresh() {
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
        RestClient restClient = new RestAdapter.Builder()
                .setEndpoint(RestClient.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .build()
                .create(RestClient.class);

        HashMap<String,String> params = new HashMap<>();
        long ts = new Date().getTime();
        params.put("ts", String.valueOf(ts));
        params.put("hash", md5(ts + RestClient.PRIVATE_KEY + RestClient.PUBLIC_KEY));

        restClient.loadCharacters(params, new Callback<ResponseWrapper>() {
            @Override
            public void success(ResponseWrapper responseWrapper, Response response) {

                List<Character> characters = responseWrapper.getData().getResults();
                CharacterAdapter adapter =
                        new CharacterAdapter(characters, getActivity());
                list.setAdapter(adapter);
                swipe.post(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                });


            }

            @Override
            public void failure(RetrofitError error) {
                swipe.post(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                });
                error.printStackTrace();
                Toast.makeText(getActivity()
                        , "Erro na conexão."
                        , Toast.LENGTH_LONG).show();

            }
        });

    }
}
