package services;

import android.content.Context;
import android.content.Intent;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.ID_CHAT;
import static constants.IntentParameters.MESSAGES_SIZE;

public class IntentShowChat {
   Intent intent = new Intent("ru.matrosov.vladimir.diplom.client.service");

   public void setPackage(Context context){
       this.intent.setPackage(context.getPackageName());
   }

    public void setData(int idChat, String email, int messages_size){
        this.intent.putExtra(ID_CHAT, idChat);
        this.intent.putExtra(EMAIL, email);
        this.intent.putExtra(MESSAGES_SIZE, messages_size);
    }

    public int getInt(String name){
        return intent.getIntExtra(name, -1);
    }

    public String getStr(String name){
        return intent.getStringExtra(name);
    }

    public boolean getBool(String name){
        return intent.getBooleanExtra(name, false);
    }

    public Intent getIntent() {
        return intent;
    }

}
