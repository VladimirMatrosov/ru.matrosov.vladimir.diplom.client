package services;

import android.content.Context;
import android.content.Intent;

public class IntentShowChat {
   Intent intent = new Intent("ru.matrosov.vladimir.diplom.client.service");

   public void setPackage(Context context){
       this.intent.setPackage(context.getPackageName());
   }

    public void setInt(String name, int count){
        this.intent.putExtra(name, count);
    }

    public void setStr(String name, String str){
        this.intent.putExtra(name, str);
    }

    public void setBool(String name, Boolean bool){
        this.intent.putExtra(name, bool);
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
