package com.example.diplompart2.analyze_fragments.static_analyze_2.save_text;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class SaveText {

    private final static String FILE_NAME = "part1.json";

    public void saveText(String json, Context context){

        FileOutputStream fos = null;
        try {

            //  File file = new File(Environment.getExternalStorageState())
            fos =context.openFileOutput (FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
            Toast.makeText(context, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void openText(Context context){

        FileInputStream fin = null;
      //  TextView textView =  findViewById(R.id.textViewJSON);
        try {
            fin = context.openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
          //  textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


}

