package com.example.kyleohanian.superclick;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.SnapshotHolder;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AnswerFragment extends Fragment {

    View.OnClickListener activity;
    Button submit;
    RadioButton a,b,c,d,e;
    static TextView submitted;
    static Firebase currentQuestion;
    TextView one,two,three,four,five;

    //We need to first create a new AnswerFragment instance.

    public static AnswerFragment newInstance(View.OnClickListener activity) {
        AnswerFragment a = new AnswerFragment();
        a.activity = activity;
        return a;
    }

    // Create the inflater, container, and state of the instance.

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        /* Now we create a new Firebase object that will start inside the questions child.
           Then we create a new Query in order to get the data. Temporarily, we are using
           the limit to last function until we figure out what to exactly use.
           After that we will create a hash map so we can get the value of the content.

           We then use an onChildAdded Listener which overrides five methods in order to update
           and send data to Firebase.
         */


        Firebase ref = new Firebase("https://easyclick.firebaseio.com/Questions");
        Query queryRef = ref.orderByChild("weight").limitToLast(5);
        queryRef.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                submitted.setText(String.valueOf(value.get("Question")).toString());
                one.setText(String.valueOf(value.get("AnswerA")).toString());
                two.setText(String.valueOf(value.get("AnswerB")).toString());
                three.setText(String.valueOf(value.get("AnswerC")).toString());
                four.setText(String.valueOf(value.get("AnswerD")).toString());
                five.setText(String.valueOf(value.get("AnswerE")).toString());
                currentQuestion = snapshot.getRef();
                Log.d("TAG", value.get("Question").toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());

            }
        });

        View view = inflater.inflate(R.layout.fragment_answer,
                container, false);

        /* This onClickListener is set to the submit button so whenever it is pressed, the answer
           will be sent to the Firebase.
         */

        view.findViewById(R.id.Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getActivity(), "Submitted",
                            Toast.LENGTH_SHORT).show();
                    Firebase.setAndroidContext(getActivity());
                    Firebase ref = new Firebase("https://easyclick.firebaseio.com/Questions");
                    Firebase question = currentQuestion.child("Answers");
                    question.child(LoginFragment.getName()).setValue(isChecked());
                }
                catch (Exception e) {
                    submitted.setText("Error");
                }
            }
        });
        this.submit = (Button)view.findViewById(R.id.Button);
        this.a = (RadioButton)view.findViewById(R.id.radioButton);
        this.b = (RadioButton)view.findViewById(R.id.radioButton2);
        this.c = (RadioButton)view.findViewById(R.id.radioButton3);
        this.d = (RadioButton)view.findViewById(R.id.radioButton4);
        this.e = (RadioButton)view.findViewById(R.id.radioButton5);
        this.one = (TextView)view.findViewById(R.id.textView4);
        this.two = (TextView)view.findViewById(R.id.textView5);
        this.three = (TextView)view.findViewById(R.id.textView6);
        this.four = (TextView)view.findViewById(R.id.textView7);
        this.five = (TextView)view.findViewById(R.id.textView8);
        this.submitted = (TextView)view.findViewById(R.id.textView);
        this.submitted.setVisibility(View.VISIBLE);

        return view;
    }

    /* The method isChecked is made so we can send the appropriate answer that the user has given.
       The default answer is F which is distinct because only five options (A-E) are available.
       The method returns a string which has the answer so it can be sent to the fragment.
     */

    public String isChecked() {
        String z = "F";
        if (a.isChecked()) {
            z="A";
        }
        else if (b.isChecked()) {
            z="B";
        }
        else if (c.isChecked()) {
            z="C";
        }
        else if (d.isChecked()) {
            z="D";
        }
        else if (e.isChecked()) {
            z="E";
        }
        return z;
    }
}
