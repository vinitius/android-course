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

package br.com.marvel.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by vinitius on 8/28/16.
 */
public class MarvelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context
                , "Salvo com sucesso: "
                +intent.getStringExtra("NAME"), Toast.LENGTH_LONG)
                .show();

        Vibrator vibrator = (Vibrator)context
                .getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(1000);

    }
}
