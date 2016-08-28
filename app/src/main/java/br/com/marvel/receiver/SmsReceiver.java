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
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by vinitius on 8/28/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getExtras() != null){

            if (intent.getExtras().get("pdus") != null){
                Object[] pdus = (Object[])intent.getExtras().get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];

                for(int i=0; i< messages.length; i++){
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }

                Toast.makeText(context,messages[0].getDisplayMessageBody()
                        +" \nDE:"+messages[0].getOriginatingAddress(), Toast.LENGTH_LONG).show();



            }
        }



    }
}
