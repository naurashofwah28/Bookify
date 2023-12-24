package com.example.bookify1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditorActivity extends AppCompatActivity {
        private FirebaseAuth mAuth;
        private RecyclerView recyclerView;
        private TextView option;
        private EditText etTitle, etWriter, etYear, etDesc;
        private ImageView upPhoto;
        private Button btnClear, btnSubmit;
        String imageURL;
        Uri uri;
        BukuAdapter bukuAdapter;
        private DatabaseReference databaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editor);
//            recyclerView.setHasFixedSize(true);
            etTitle = findViewById(R.id.et_title);
            etWriter = findViewById(R.id.et_writer);
            etYear = findViewById(R.id.et_year);
            etDesc = findViewById(R.id.et_desc);
            btnClear = findViewById(R.id.btn_clear);
            btnSubmit = findViewById(R.id.btn_submit);
            upPhoto = findViewById(R.id.uploadImage);
            mAuth = FirebaseAuth.getInstance();

            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                uri = data.getData();
                                upPhoto.setImageURI(uri);
                            } else {
                                Toast.makeText(EditorActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            upPhoto.setOnClickListener(v -> {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            });

            btnClear.setOnClickListener(v -> {
                clearData();
            });

            btnSubmit.setOnClickListener(v -> {
                submitData();
            });
        }

        public void submitData() {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(EditorActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();
                    uploadData();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
//            Intent intent = new Intent(EditorActivity.this,
//                    DaftarBukuActivity.class);
//            startActivity(intent);
        }

        public void uploadData() {
            String title = etTitle.getText().toString();
            String desc = etDesc.getText().toString();
            String year = etYear.getText().toString();
            String writer = etWriter.getText().toString();
            BukuModel bukuModel = new BukuModel(title, writer, desc, year, imageURL);
            String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            FirebaseDatabase.getInstance().getReference("Arsip Buku").child(currentDate)
                    .setValue(bukuModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditorActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditorActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        public void clearData() {
            etTitle.setText("");
            etWriter.setText("");
            etYear.setText("");
            etDesc.setText("");
        }
    }
