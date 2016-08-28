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

package br.com.marvel.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.marvel.view.WelcomeFragment;

/**
 * Created by vinitius on 8/28/16.
 */
public class WelcomeAdapter extends FragmentPagerAdapter {

    private String[] tittles = {"Bem-vindo","Arraste para os lados","Fim"};
    private int[] colors = {Color.RED,Color.GREEN,Color.BLACK};

    public WelcomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putString("TEXT", tittles[position]);
        args.putInt("COLOR", colors[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
