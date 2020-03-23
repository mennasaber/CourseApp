package com.example.examapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.examapp.Models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Arrays;

public class SignupActivity extends AppCompatActivity {
    EditText nameSignUpET , emailSignUpET , password ,confirmPasswordET;
    Button signup ;
    CheckBox isStudent ;
    TextView hasAccountTV;
    ImageView profilepicture ;
    StorageReference firebaseStorage ;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;
    FirebaseAuth firebaseAuth ;

    Uri imageURI ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameSignUpET = findViewById(R.id.nameSignUpET);
        emailSignUpET = findViewById(R.id.emailSignUpET);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        isStudent=findViewById(R.id.isStudent);
        hasAccountTV = findViewById(R.id.hasAccountTV) ;
        profilepicture = findViewById(R.id.profilepicture);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        firebaseStorage= FirebaseStorage.getInstance().getReference() ;
        firebaseAuth = FirebaseAuth.getInstance() ;
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference().child("Users") ;


        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!nameSignUpET.getText().toString().isEmpty()&&!emailSignUpET.getText().toString().isEmpty()&&
                        !password.getText().toString().isEmpty()&&imageURI!=null&&password.getText().toString().equals(confirmPasswordET.getText().toString())) {
                    final StorageReference path = firebaseStorage.child("Profile_Pics").child(imageURI.getLastPathSegment()) ;
                    firebaseAuth.createUserWithEmailAndPassword(emailSignUpET.getText().toString(), password.getText().toString()).
                            addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        path.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String userid = firebaseAuth.getCurrentUser().getUid() ;
                                                        DatabaseReference mDatabaseReference = databaseReference.child(userid);
                                                        User user = new User(nameSignUpET.getText().toString()
                                                                ,userid,uri.toString(),emailSignUpET.getText().toString(),!isStudent.isChecked());
                                                        mDatabaseReference.setValue(user);
                                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                        finish();
                                                    }
                                                });
                                            }
                                        });

                                    } else
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                    Toast.makeText(getApplicationContext(),"Please enter your full data", Toast.LENGTH_SHORT).show();
            }
        });


        hasAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_GET_CONTENT) ;
                intent.setType("image/*") ;
                startActivityForResult(intent , 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            imageURI= data.getData() ;
            profilepicture.setImageURI(imageURI);
        }

    }




}