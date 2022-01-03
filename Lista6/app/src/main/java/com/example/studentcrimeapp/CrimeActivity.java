package com.example.studentcrimeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private UUID crimeUUID;
    private Crime crime;
    private EditText crimeText;
    private Button crimeDate;
    private Button crimeTime;
    private CheckBox crimeSolve;
    private Calendar c;
    private Calendar c2;
    private Date date = new Date();
    private ImageView imageView;
    private Button imageButton;
    private static final int CAMERA_INTENT = 1;
    private  Uri savePicturePath = null;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        crimeText = findViewById(R.id.crimeNameEdit);
        crimeDate = findViewById(R.id.crimeDateButton);
        crimeTime = findViewById(R.id.crimeTimeButton);
        crimeSolve = findViewById(R.id.checkBoxSolve);
        imageView = findViewById(R.id.crime_view_picture);


        imageButton = findViewById(R.id.button_add_picture);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            crimeUUID = UUID.fromString(extras.get("crimeUUID").toString());
        }

        crime = CrimeLab.get(this).getCrime(crimeUUID);
        crimeText.setText(crime.getTitle());
        crimeDate.setText(crime.getDate().toString());
        crimeSolve.setChecked(crime.getSolved());
        if  (crime.getImg_Path() != null) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(crime.getImg_Path()));
        }

        crimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        crimeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        date.setDate(c.get(Calendar.DAY_OF_MONTH));
        date.setMonth(c.get(Calendar.MONTH));
        date.setYear(c.get(Calendar.YEAR) - 1900);

        crimeDate.setText(c.get(Calendar.DAY_OF_MONTH) +"-"+ c.get(Calendar.MONTH) +"-"+ c.get(Calendar.YEAR));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c2 = Calendar.getInstance();
        c2.set(Calendar.HOUR, hourOfDay);
        c2.set(Calendar.MINUTE, minute);

        date.setHours(c2.get(Calendar.HOUR));
        date.setMinutes(c2.get(Calendar.MINUTE));

        crimeTime.setText(c2.get(Calendar.HOUR) + ":" + c2.get(Calendar.MINUTE));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CrimeLab.get(getApplicationContext()).updateCrime(crimeUUID,crimeText.getText().toString(),crimeSolve.isChecked(), date.toString(),crime.getImg_Path());
    }

    public void crimeDelete(View view) {
        CrimeLab.get(getApplicationContext()).deleteCrime(crimeUUID);
        finish();
    }

    private void captureImage() {
        Dexter.withContext(this).withPermission(
                Manifest.permission.CAMERA
        ).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_INTENT);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                showRationaleDialog();
            }
        }).onSameThread().check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==CAMERA_INTENT){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(thumbnail);
                savePicturePath = savePicture(thumbnail);
                crime.setImg_Path(savePicturePath.toString());
                Log.d("Picture", "Path" + savePicturePath);
            }
        }
    }

    private void showRationaleDialog(){
        new AlertDialog.Builder(this)
                .setMessage("Camera permissions needed")
                .setPositiveButton("Ask me", (dialog, which) -> {
                    try{
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private  Uri savePicture(Bitmap bitmap){
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("myGallery", Context.MODE_PRIVATE);
        file = new File(file, UUID.randomUUID().toString() + ".jpg");

        try {
            OutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return Uri.parse(file.getAbsolutePath());

    }

}