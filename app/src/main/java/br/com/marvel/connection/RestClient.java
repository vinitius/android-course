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

package br.com.marvel.connection;

import java.util.Map;

import br.com.marvel.model.ResponseWrapper;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by vinitius on 8/20/16.
 */
public interface RestClient {

    String BASE_URL = "http://gateway.marvel.com:80/v1/public";

    String PRIVATE_KEY = "5d7926a0d59f3409df891e26a2219c7240e4e820";
    String PUBLIC_KEY = "d932150383390c4326fd11bc14308ed7";

    @GET("/characters?limit=5&offset=5&apikey=d932150383390c4326fd11bc14308ed7")
    void loadCharacters(@QueryMap Map<String,String> params, Callback<ResponseWrapper> cb);

}
