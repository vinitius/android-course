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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import br.com.marvel.MainActivity;
import br.com.marvel.R;
import br.com.marvel.model.Character;

/**
 * Created by vinitius on 8/20/16.
 */
public class DetailsFragment extends Fragment {


    private TextView text;
    private ImageView image;
    private br.com.marvel.model.Character character;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (TextView)view.findViewById(R.id.text);
        image = (ImageView)view.findViewById(R.id.img);

        if (getArguments() != null){
            character = (Character) getArguments().getSerializable("CHARACTER");
            ((MainActivity)getActivity())
                    .getToolbar().setTitle(character.getName());
            text.setText(character.getDescription().isEmpty()
                    ? "- Sem Descrição -"
                    : character.getDescription());

            String url = character.getThumbnail() != null
                    ?character.getThumbnail().getPath()
                    +"."+character.getThumbnail().getExtension()

                    :character.getImageUrl();

            Glide.with(getActivity()).load(url).into(image);
        }


        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.phone){
            Intent intent = new Intent(Intent.ACTION_DIAL
                    , Uri.parse("tel:"+character.getPhone()));
            startActivity(intent);

        }else if (item.getItemId() == R.id.photo){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 111);
        }else if (item.getItemId() == R.id.save){
            try {
                Dao<Character,String> dao =
                        ((MainActivity)getActivity())
                                .getDbHelper()
                                .getDao(Character.class);
                dao.createOrUpdate(character);
                Toast.makeText(getActivity()
                ,"Salvo com sucesso!",Toast.LENGTH_LONG)
                        .show();
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(getActivity()
                        ,"Falha ao gravar registro!",Toast.LENGTH_LONG)
                        .show();
            }

        }



        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111){
            if (resultCode == Activity.RESULT_OK){
                Bitmap bitmap =  (Bitmap)data.getExtras().get("data");
                image.setImageBitmap(bitmap);
            }else{

            }
        }

    }
}
