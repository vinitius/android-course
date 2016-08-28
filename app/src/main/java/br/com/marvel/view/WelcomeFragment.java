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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.marvel.R;

/**
 * Created by vinitius on 8/28/16.
 */
public class WelcomeFragment extends Fragment {

    private TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (TextView)view.findViewById(R.id.text);
        text.setText(getArguments().getString("TEXT"));
        text.setBackgroundColor(getArguments().getInt("COLOR"));

    }
}
