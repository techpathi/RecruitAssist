package com.techpathi.rbassist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class CompanyProfileActivity extends AppCompatActivity {


    private TextInputEditText et_cname, et_hq, et_aboutUs,
            et_yearFounded, et_companySize,et_specialities,et_website;
    private Button btnRegister;
    private ImageButton photoPicker;
    private String CompanyType;
    private String logoUrl;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mLogoReference;

    private static final int RC_PHOTO_PICKER=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        et_cname = (TextInputEditText) findViewById(R.id.et_companyName);
        et_hq = (TextInputEditText) findViewById(R.id.et_headquarters);
        et_aboutUs = (TextInputEditText) findViewById(R.id.et_aboutus);
        et_yearFounded = (TextInputEditText) findViewById(R.id.et_yearfounded);
        et_companySize = (TextInputEditText) findViewById(R.id.et_companysize);
        et_specialities = (TextInputEditText) findViewById(R.id.et_specialities);
        et_website=(TextInputEditText)findViewById(R.id.et_companywebsite);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        photoPicker=(ImageButton)findViewById(R.id.photoPickerButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("companies");

        mFirebaseStorage=FirebaseStorage.getInstance();
        mLogoReference=mFirebaseStorage.getReference().child("company_logos");

        final ProgressDialog companyProfileProgress=new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {


                                           DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                                           String RegistedDate = df.format(Calendar.getInstance().getTime());


                                           Companies companies = new Companies(et_cname.getText().toString(), et_hq.getText().toString(), et_aboutUs.getText().toString(), et_yearFounded.getText().toString(), et_companySize.getText().toString(), et_specialities.getText().toString(),
                                                   RegistedDate,CompanyType,logoUrl,et_website.getText().toString());
                                           Task RegisterCompany = databaseReference.child(et_cname.getText().toString()).setValue(companies);

                                           companyProfileProgress.setMessage("Registering");
                                           companyProfileProgress.show();

                                           RegisterCompany.addOnSuccessListener(new OnSuccessListener() {
                                               @Override
                                               public void onSuccess(Object o) {

                                                   companyProfileProgress.dismiss();

                                                   Toast.makeText(CompanyProfileActivity.this, "Job posted Successfully", Toast.LENGTH_SHORT).show();

                                                   clearForm();


                                               }
                                           });

                                           RegisterCompany.addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {

                                                   Toast.makeText(CompanyProfileActivity.this, "Sorry! We couldn't post right now.", Toast.LENGTH_SHORT).show();

                                               }
                                           });

                                       }


                                   }


        );

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RC_PHOTO_PICKER)
        {
            Uri selecetedImageUri=data.getData();
            final StorageReference photoRef=mLogoReference.child(et_cname.getText().toString());
            UploadTask uploadTask=photoRef.putFile(selecetedImageUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(CompanyProfileActivity.this,"Logo upload failed",Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Toast.makeText(CompanyProfileActivity.this,"Logo uploaded successfully",Toast.LENGTH_SHORT).show();

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

    private void clearForm() {

        et_cname.setText(null);
        et_companySize.setText(null);
        et_specialities.setText(null);
        et_yearFounded.setText(null);
        et_aboutUs.setText(null);
        et_hq.setText(null);

        }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_public:
                if (checked)
                {
                    CompanyType="Public Company";
                }
                break;
            case R.id.radio_private:
                if (checked)
                {
                    CompanyType="Private Company";
                }
                break;
        }
    }


}

