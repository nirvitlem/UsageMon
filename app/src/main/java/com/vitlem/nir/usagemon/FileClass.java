package com.vitlem.nir.usagemon;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import static android.content.Context.MODE_PRIVATE;

public class FileClass extends Activity {

    static final int READ_BLOCK_SIZE = 100;

    // write text to file
    public void WriteBtn(String t) {
        // add-write text into file
        try {
            FileOutputStream fileout= openFileOutput("Usgafile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(t);
            outputWriter.close();

            //display file saved message
            //
            Log.i( "File saved successfully!","File saved successfully");

        } catch (Exception e) {
            Log.i("Error Saving file",e.getMessage());
        }
    }

    // Read text from file
    public long ReadBtn() {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("Usgafile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Log.i("Read File ",s );
            return (Long.valueOf(s));


        } catch (Exception e) {
            Log.i("error Read File ",e.getMessage() );
            return 0;
        }
    }
}
