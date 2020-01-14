package com.example.parkeunjeong.mycoordinator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private  static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$"); // 비밀번호 형식 4자리부터 16자리

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button mRegister;

    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in : " + user.getUid());
                }
                else {
                    Log.d(TAG, "onAuthStateChanged : signed_out");
                }
            }
        };

        mRegister = (Button)findViewById(R.id.register_btn);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    public void onButtonLogin(View v){
        email = ((EditText)findViewById(R.id.email_edit)).getText().toString();
        password = ((EditText)findViewById(R.id.pwd_edit)).getText().toString();

        if(isVaildEmail() && isVaildPassword()){
            loginUser(email, password);
        }
    }

    public void onButtonLogout(View v){
        mAuth.signOut();
    }

    private boolean isVaildEmail(){
        if(email.isEmpty()){
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(MainActivity.this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isVaildPassword(){
        if(password.isEmpty()){
            return false;
        } else if(!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(MainActivity.this, "비밀번호를 다시 입력하시오.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.w(TAG, "signInWithEmail : success", task.getException());
                            Toast.makeText(MainActivity.this, "로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SearchWeatherActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}