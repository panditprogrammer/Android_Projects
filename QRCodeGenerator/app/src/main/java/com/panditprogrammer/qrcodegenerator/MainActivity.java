package com.panditprogrammer.qrcodegenerator;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    //initialize variables
    EditText text;
    Button generate;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning variables
        text = findViewById(R.id.input);
        generate = findViewById(R.id.button3);
        image = findViewById(R.id.imageView2);

        //event handling

        generate.setOnClickListener(v -> {
            //extract data from input text
            String Text = text.getText().toString().trim();
            //initialize multiformat writer
            MultiFormatWriter writer   = new MultiFormatWriter();

            //initialize bit matrix
            try {
                BitMatrix matrix = writer.encode(Text, BarcodeFormat.QR_CODE,350,350);
                //create object of barcode encoder
                BarcodeEncoder encoder = new BarcodeEncoder();

                //create image of qr code
                Bitmap bitmap = encoder.createBitmap(matrix);
                image.setImageBitmap(bitmap);
                //input manager for keyboard
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                manager.hideSoftInputFromWindow(text.getApplicationWindowToken(),0);

                //create toste
                Toast.makeText(this, "QR CODE Generated Successfully", Toast.LENGTH_SHORT).show();



            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
    }
}