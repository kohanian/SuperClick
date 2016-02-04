package com.example.kyleohanian.superclick;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class MainActivity extends Activity implements View.OnClickListener {

    AnswerFragment answerFragment;
    LoginFragment loginFragment;
    SignUpFragment signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://easyclick.firebaseio.com");
        this.answerFragment = AnswerFragment.newInstance(this);
        this.loginFragment = LoginFragment.newInstance(this);
        this.signUpFragment = SignUpFragment.newInstance(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main, this.loginFragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onSubmit(View v) {
        this.answerFragment.activity.onClick(v);
    }

    public void onLogin(View v) {
        this.loginFragment.activity.onClick(v);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login, this.answerFragment);
        ft.commit();
    }

    public void onSignUp(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login, this.signUpFragment);
        ft.commit();
    }

    public void onRegister(View v) {
        this.signUpFragment.activity.onClick(v);
    }

    @Override
    public void onClick(View v) {
        onSubmit(v);
    }
}
