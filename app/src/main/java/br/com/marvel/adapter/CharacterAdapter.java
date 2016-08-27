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

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.marvel.R;
import br.com.marvel.model.Character;

/**
 * Created by vinitius on 8/27/16.
 */
public class CharacterAdapter extends BaseAdapter {

    private List<br.com.marvel.model.Character> characters;
    private Context context;

    public CharacterAdapter(List<Character> characters, Context context) {
        this.characters = characters;
        this.context = context;
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Character getItem(int i) {
        return characters.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CharacterHolder holder;

        if (view == null){
            view = ((Activity)context).getLayoutInflater()
                    .inflate(R.layout.character_list_item,null);

            holder = new CharacterHolder(view);
            view.setTag(holder);
        }else{
            holder = (CharacterHolder)view.getTag();

        }

        holder.name.setText(getItem(i).getName());

        String url = getItem(i).getThumbnail().getPath()
                + "."+getItem(i).getThumbnail().getExtension();

        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img);

        return view;
    }



    public static class CharacterHolder{
        TextView name;
        ImageView img;

        public CharacterHolder(View view){
            name = (TextView)view.findViewById(R.id.name);
            img = (ImageView)view.findViewById(R.id.img);

        }

    }
}
