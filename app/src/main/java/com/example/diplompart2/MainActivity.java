package com.example.diplompart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.diplompart2.login_regist.fragment_login;
import com.example.diplompart2.login_regist.fragment_registration;
import com.example.diplompart2.login_regist.interfaces.OnActivityDataListener;
import com.example.diplompart2.login_regist.interfaces.OnActivityDataListenerRegister;
import com.example.diplompart2.login_regist.interfaces.OnFragment1DataListener;
import com.example.diplompart2.login_regist.interfaces.OnFragment2DataListener;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marozzi.roundbutton.RoundButton;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity implements OnFragment1DataListener, OnFragment2DataListener {

    Fragment fragment1, fragment2;
    FragmentTransaction fragmentTransaction;
    //Buttons ..
    Button signIn, signUp, back, signUpReg;
    //TextView
  //  private TextView textView;
    //Firebase ..
    private FirebaseAuth mAuth;
    private String TAG = "Check:";
    // Interfaces...
    private OnActivityDataListener mListener;
    private OnActivityDataListenerRegister rListener;
    //String ...
    public String glEmail;
    public String glPassword;
    public String regEmail;
    public String regPassword;
    //layouts ..
    ConstraintLayout mainLayout;
    // Animation
    AnimationDrawable animationDrawable;
    // ProgressBar
    ProgressBar progressBar;


    @Override
    public void onFragment1DataListener(String email, String password) {
//        textView.setText(email);
//        textView.append("   " + password);
        glEmail = email;
        glPassword = password;
    }

    @Override
    public void onFragment2DataListener(String email, String password) {
//        textView.setText(email);
//        textView.append("   " + password);
        regEmail = email;
        regPassword = password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fragments ...
        fragment1 = new fragment_login();
        fragment2 = new fragment_registration();
        //EditText


        //buttons ...
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        back = findViewById(R.id.back);
        signUpReg = findViewById(R.id.sign_up_regis);
        //TextView...
        //textView = findViewById(R.id.textViewTest);
        //work with fragments ...
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment1).commit();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //layouts
        mainLayout = findViewById(R.id.linearL);
        //animation
        animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
        //ProgressBar
        progressBar = findViewById(R.id.progress);

        loginFragment();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void Change(View view) {

        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        Sprite ThreeBounce = new ThreeBounce();

        switch (view.getId()) {

            case R.id.back:
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.frameLayout, fragment1)
                        .commit();

                Animation a = AnimationUtils.loadAnimation(this, R.anim.button_right_to_left);
                Animation c = AnimationUtils.loadAnimation(this, R.anim.button_sec_right_to_left);


                signIn.setAnimation(a);
                signIn.setVisibility(View.VISIBLE);
                signUp.setAnimation(a);
                signUp.setVisibility(View.VISIBLE);
                back.setAnimation(c);
                back.setVisibility(View.INVISIBLE);
                signUpReg.setAnimation(c);
                signUpReg.setVisibility(View.INVISIBLE);
                break;

            case R.id.sign_up:
                fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.frameLayout, fragment2)
                        .commit();


                registerFragment();

                Animation b = AnimationUtils.loadAnimation(this, R.anim.button_left_to_right);
                Animation d = AnimationUtils.loadAnimation(this, R.anim.button_sec_left_to_right);
                signIn.setAnimation(b);
                signIn.setVisibility(View.INVISIBLE);
                signUp.setAnimation(b);
                signUp.setVisibility(View.INVISIBLE);
                back.setAnimation(d);
                back.setVisibility(View.VISIBLE);
                signUpReg.setAnimation(d);
                signUpReg.setVisibility(View.VISIBLE);
                break;
            case R.id.sign_in:
                loginFragment();
                //progress bar
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminateDrawable(ThreeBounce);

                mListener.onActivityDataListener();
                if (!glEmail.equals("") & (!glPassword.equals(""))) {
                    signin(glEmail, glPassword);

                }
                break;
            case R.id.sign_up_regis:
                rListener.onActivityDataListenerRegister();
                if (!regEmail.equals("") & (!regPassword.equals(""))) {
                    registration(regEmail, regPassword);
                }
                break;
            case R.id.skip:
                goToActive(WorkActivity.class);
            default:
                break;
        }

    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ss", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "signInWithEmail:success",
                                    Toast.LENGTH_SHORT).show();
                            goToActive(WorkActivity.class);
                            progressBar.setVisibility(View.INVISIBLE);

                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sss", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void loginFragment() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentLogin);
        if (fragment == null) {
            fragment = new fragment_login();

            if (fragment instanceof OnActivityDataListener) {
                mListener = (OnActivityDataListener) fragment;
            } else {
                throw new RuntimeException(fragment.toString()
                        + " must implement onActivityDataListener");
            }

            fm.beginTransaction()
                    .add(R.id.fragmentLogin, fragment)
                    .commit();
        }
    }

    public void registerFragment() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentRegistration);
        if (fragment == null) {
            fragment = new fragment_registration();

            if (fragment instanceof OnActivityDataListenerRegister) {
                rListener = (OnActivityDataListenerRegister) fragment;
            } else {
                throw new RuntimeException(fragment.toString()
                        + " must implement onActivityDataListener");
            }

            fm.beginTransaction()
                    .add(R.id.fragmentRegistration, fragment)
                    .commit();
        }
    }

    public void goToActive(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        Animatoo.animateFade(this);


    }




}


