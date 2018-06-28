package com.techpathi.rbassist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostJobActivity extends AppCompatActivity {


    private TextInputEditText et_jid, et_jobTitle, et_companyName,
            et_location, et_description, et_responsibilities, et_qualifications,et_applydate;
    private Button btnPost;
    private String employmentType;
    private ImageButton photoPicker;
    private String logoUrl;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mLogoReference;
    private static final int RC_PHOTO_PICKER=2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        //Getting instances of Input Widgets

        et_jid = (TextInputEditText) findViewById(R.id.et_jid);
        et_jobTitle = (TextInputEditText) findViewById(R.id.et_jobTitle);
        et_companyName = (TextInputEditText) findViewById(R.id.et_companyName);
        et_description = (TextInputEditText) findViewById(R.id.et_description);
        et_qualifications = (TextInputEditText) findViewById(R.id.et_qualifications);
        et_responsibilities = (TextInputEditText) findViewById(R.id.et_responsibilities);
        et_location = (TextInputEditText) findViewById(R.id.et_location);
        et_applydate=(TextInputEditText)findViewById(R.id.et_applybydate);

        photoPicker=(ImageButton)findViewById(R.id.photoPickerButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("jobs");

        mFirebaseStorage=FirebaseStorage.getInstance();
        mLogoReference=mFirebaseStorage.getReference().child("company_logos");


        btnPost = (Button) findViewById(R.id.btnPost);


        final ProgressDialog jobPostProgress=new ProgressDialog(this);

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Post Another Job?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(PostJobActivity.this,MainActivity.class));
                    }
                });


        btnPost.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {



                                               DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                                               String postedAt = df.format(Calendar.getInstance().getTime());


                                               Jobs jobs = new Jobs(et_jid.getText().toString(),et_jobTitle.getText().toString(),
                                                       et_companyName.getText().toString(),et_location.getText().toString(),
                                                       et_description.getText().toString(),et_responsibilities.getText().toString(),
                                                       et_qualifications.getText().toString(),employmentType,postedAt,et_applydate.getText().toString(),logoUrl);
                                               Task submitJob = databaseReference.push().setValue(jobs);

                                               jobPostProgress.setMessage("Posting Job");
                                               jobPostProgress.show();

                                               submitJob.addOnSuccessListener(new OnSuccessListener() {
                                                   @Override
                                                   public void onSuccess(Object o) {

                                                       jobPostProgress.dismiss();

                                                       Toast.makeText(PostJobActivity.this, "Job posted Successfully", Toast.LENGTH_SHORT).show();

                                                       clearForm();

                                                       builder.create();
                                                       builder.show();
                                                   }
                                               });

                                               submitJob.addOnFailureListener(new OnFailureListener() {
                                                   @Override
                                                   public void onFailure(@NonNull Exception e) {

                                                       Toast.makeText(PostJobActivity.this, "Sorry! We couldn't post right now.", Toast.LENGTH_SHORT).show();

                                                   }
                                               });

                                           }


                                   }


        );

        final Calendar myCalendar=Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {

                String myFormat = "EEE, d MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                et_applydate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        et_applydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PostJobActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        photoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Choose logo file using"),RC_PHOTO_PICKER);
            }


        });




    }

    private void clearForm() {

        et_jid.setText(null);
        et_jobTitle.setText(null);
        et_companyName.setText(null);
        et_location.setText(null);
        et_responsibilities.setText(null);
        et_qualifications.setText(null);
        et_description.setText(null);
        et_applydate.setText(null);



    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_fulltime:
                if (checked)
                {
                    employmentType="Full Time";
                }
                break;
            case R.id.radio_internship:
                if (checked)
                {
                     employmentType="Internship";
                }
                    break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RC_PHOTO_PICKER)
        {
            Uri selecetedImageUri=data.getData();
            final StorageReference photoRef=mLogoReference.child(et_jid.getText().toString());
            UploadTask uploadTask=photoRef.putFile(selecetedImageUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(PostJobActivity.this,"Logo upload failed",Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PostJobActivity.this,"Logo uploaded successfully",Toast.LENGTH_SHORT).show();

                }

            });

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return photoRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        logoUrl= task.getResult().toString();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }
    }
}