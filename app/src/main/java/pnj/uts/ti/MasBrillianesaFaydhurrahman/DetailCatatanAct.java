package pnj.uts.ti.MasBrillianesaFaydhurrahman;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pnj.uts.ti.MasBrillianesaFaydhurrahman.fragment.catatan.CatatanFragment;

public class DetailCatatanAct extends AppCompatActivity {
    Button actTambahDataCatatan;
    EditText edtJudulCat, edtIsiCat;
    public static String TANGGAL;
    public static final int request_code = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);
        edtJudulCat = findViewById(R.id.edtJudulCat);
        edtIsiCat = findViewById(R.id.edtIsiCat);
        CatatanFragment catatanFragment = new CatatanFragment();
        actTambahDataCatatan = findViewById(R.id.actTambahDataCat);

        actTambahDataCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermission()){
                    ubahData();
                    Toast.makeText(DetailCatatanAct.this, "Ubah data berhasil!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DetailCatatanAct.this, "Ubah data gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            edtJudulCat.setText(extras.getString("judul",""));
            bacaData();
        }

    }

    void buatData() {
        TANGGAL= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String judul = edtJudulCat.getText().toString();
        String data = edtIsiCat.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Catatan",judul + " "+TANGGAL+".txt");

        FileOutputStream outputStream = null;
        try {
            String folder_main = "Catatan";

            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdir();
            }

            file.createNewFile();
            outputStream = new FileOutputStream(file,false);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
            Log.e("dsdas", ""+Environment.getExternalStorageDirectory()) ;

        }catch (Exception e){
            Log.e("ERROR", ""+e.getMessage());
        }
    }

    boolean CheckPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                return true;
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
                return false;
            }
        }else {
            return  true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case request_code:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(DetailCatatanAct.this, "Izin Berhasil", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void ubahData(){
        String judul = edtJudulCat.getText().toString();
        String data = edtIsiCat.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Catatan",judul);

        FileOutputStream outputStream = null;
        try {
            String folder_main = "Catatan";

            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdir();
            }

            file.createNewFile();
            outputStream = new FileOutputStream(file,false);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
            Log.e("dsdas", ""+Environment.getExternalStorageDirectory()) ;

        }catch (Exception e){
            Log.e("ERROR", ""+e.getMessage());
        }
    }

    void bacaData(){
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Catatan", edtJudulCat.getText().toString());

        if(file.exists()) {
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = bufferedReader.readLine();

                while (line !=null){
                    text.append(line);
                    line = bufferedReader.readLine();
                }

                bufferedReader.close();


            }catch (Exception e){
                Log.e("ERROR", ""+e.getMessage());
            }

            edtIsiCat.setText(text.toString());
        }
    }
}
