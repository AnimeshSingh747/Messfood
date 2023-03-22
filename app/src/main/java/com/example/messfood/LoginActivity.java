package com.example.messfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.jar.JarException;

public class LoginActivity extends AppCompatActivity {
    Button button;
    EditText username,password;
//    public static final String SHARED_PREFS = "sharedPrefs";
//    ProgressBar progressBar;
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodiee-dfd2d-default-rtdb.firebaseio.com/");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);


        button = findViewById(R.id.logout_btn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticatUser();
            }
        });

        TextView tv = (TextView) this.findViewById(R.id.create_account_text_view_btn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open register activity
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });
    }

    public void authenticatUser() {
        if (!validateusername() || !validatePassword()) {
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        // The URL For Posting
        String url = "http://10.4.7.238:9080/api/v1/user/login";


        // Get Parameters:
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", username.getText().toString());
        params.put("password", password.getText().toString());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String email = (String) response.get("email");
                    String password = (String) response.get("password");



                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                //
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());

                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);



    }
    public boolean validateusername() {
        String Username = username.getText().toString();

        if(Username.isEmpty()){
            username.setError("Email Cannot be empty");
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }
    public boolean validatePassword() {
        String Password = password.getText().toString();

        if(Password.isEmpty()){
            password.setError("Password must be filled");
            return false;
        }else {
            password.setError(null);
            return true;
        }

    }

}