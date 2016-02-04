package com.example.kyleohanian.superclick;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/* A login Fragment is implemented so the user can simply login to your account and submit answers.
  */
public class LoginFragment extends Fragment {
    View.OnClickListener activity;
    static EditText username;
    static EditText password;
    AnswerFragment answerFragment;

    public static LoginFragment newInstance(View.OnClickListener activity) {
        LoginFragment l = new LoginFragment();
        l.activity = activity;
        return l;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        this.answerFragment = AnswerFragment.newInstance(activity);

        View view = inflater.inflate(R.layout.fragment_login,
                container, false);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://easyclick.firebaseio.com");
                ref.authWithPassword(username.getText().toString(), username.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error
                    }
                });
                Firebase.setAndroidContext(getActivity());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main, answerFragment);
                ft.commit();
            }
        });

        username = (EditText)view.findViewById(R.id.textView3);
        password = (EditText)view.findViewById(R.id.editText9);
        return view;

    }

    public static String getName() {
        return username.getText().toString();
    }
}
