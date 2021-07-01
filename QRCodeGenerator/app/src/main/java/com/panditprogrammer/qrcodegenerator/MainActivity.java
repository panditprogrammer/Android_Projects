package com.panditprogrammer.qrcodegenerator;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    //initialize variables
    EditText text;
    Button generate;
    ImageView image;
    Button Saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        granting the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},00);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning variables
        text = findViewById(R.id.input);
        generate = findViewById(R.id.generate);
        image = findViewById(R.id.qr_img);
        Saved = findViewById(R.id.save_gallery);


        //event handling
        generate.setOnClickListener(v -> {
//                getting input from inputbox
            String input_string = text.getText().toString().trim();

//                initializing multiformat writer
            MultiFormatWriter writer = new MultiFormatWriter();

            try {
//                bit matrix
                BitMatrix matrix = writer.encode(input_string, BarcodeFormat.QR_CODE,350,350);

//                    barcode format encoder
                BarcodeEncoder encoder = new BarcodeEncoder();

//                    initializing for generating images
                Bitmap bitmap = encoder.createBitmap(matrix);
//                    showing on imageview
                image.setImageBitmap(bitmap);

//                    initialize input manager
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    hiding the keyboard
                manager.hideSoftInputFromWindow(text.getApplicationWindowToken(),0);


//                showing the toast message
                Toast.makeText(this, "QR CODE Generated Successfully", Toast.LENGTH_SHORT).show();

            } catch (WriterException e) {
                e.printStackTrace();
            }


        });


//        saving qr code in gallery
        Saved.setOnClickListener(v -> {
            saveToGallery();
        });


    }


//saving imageview to gallery
    private void saveToGallery(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/QR CODE GENERATOR");
        dir.mkdirs();

//        creating file and extension for image
        String filename = String.format("QR_%d.jpg",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
//        create image as jpeg file
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
//            close the file for saving
            outputStream.close();
            Toast.makeText(this, "QR CODE Saved Successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}