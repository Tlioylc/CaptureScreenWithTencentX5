package com.tlioylc.capturescreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class pictureAcitivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_acitivity);
        imageView = (ImageView) findViewById(R.id.image);
        Intent intent=getIntent();
        if(intent !=null)
        {
            byte [] bis=intent.getByteArrayExtra("bitmap");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            bitmap = compressBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
    private Bitmap compressBitmap(Bitmap bitmap){
        //进行一些压缩
        if(bitmap.getByteCount()/1024/1024 <= 3){
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(0.75f, 0.75f);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        if(bitmap != null){
            bitmap.recycle();
        }
        return compressBitmap(bm);
    }
}
