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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.marvel.R;
import br.com.marvel.connection.RestClient;
import br.com.marvel.listener.OnUserSelectListener;
import br.com.marvel.model.Character;
import br.com.marvel.model.ResponseWrapper;
import br.com.marvel.model.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by vinitius on 8/20/16.
 */
public class MainFragment extends Fragment {


    private ListView list;
    private OnUserSelectListener listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserSelectListener){
            listener = (OnUserSelectListener)context;
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

        list = (ListView)view.findViewById(R.id.list);


        RestClient restClient = new RestAdapter.Builder()
                .setEndpoint(RestClient.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .build()
                .create(RestClient.class);

        HashMap<String,String> params = new HashMap<>();
        long ts = new Date().getTime();
        params.put("ts", String.valueOf(ts));
        params.put("hash",md5(ts+RestClient.PRIVATE_KEY+RestClient.PUBLIC_KEY));

        restClient.loadCharacters(params, new Callback<ResponseWrapper>() {
            @Override
            public void success(ResponseWrapper responseWrapper, Response response) {

                List<Character> characters = responseWrapper.getData().getResults();
                ArrayAdapter<br.com.marvel.model.Character> adapter =
                        new ArrayAdapter<Character>(getActivity()
                                , android.R.layout.simple_list_item_1
                                , characters);
                list.setAdapter(adapter);

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Toast.makeText(getActivity()
                        , "Erro na conexão. Verifique sua internet."
                        , Toast.LENGTH_LONG).show();

            }
        });

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
}
