package com.example.messfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    EditText Email,Unique,password,confirmpassword;
    Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Email= findViewById(R.id.email_edit_text);
        Unique= findViewById(R.id.UniqueID);
        password = findViewById(R.id.password_edit_text);
        confirmpassword = findViewById(R.id.confirm_password_edit_text);

        registerBtn = findViewById(R.id.create_account_btn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFormFields();
            }
        });
        TextView tv = (TextView) this.findViewById(R.id.login_text_view_btn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open register activity
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            }
        });

    }


    public void processFormFields(){
        if(!validateUnique() || !validateEmail() || !validatePassword() || !validateConPassword()){
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(CreateAccountActivity.this);
        // The URL For Posting
        String url = "http://10.4.7.238:9080/api/v1/user/register";

        //String Request Object:
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("Success")){

                    Unique.setText(null);
                    Email.setText(null);
                    password.setText(null);

                    Toast.makeText(CreateAccountActivity.this, "User register Successsfully", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                System.out.println(error.getMessage());

                Toast.makeText(CreateAccountActivity.this, "User register Not register", Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pramas = new HashMap<>();
                pramas.put("UNIQUEID", Unique.getText().toString());
                pramas.put("email", Email.getText().toString());
                pramas.put("password", password.getText().toString());

                return pramas;
            }
        };
        queue.add(stringRequest);

    }



    //to check the validation of Unique id
    public boolean validateUnique() {
        String unique = Unique.getText().toString();

        if(unique.isEmpty()){
            Unique.setError("UniqueId must be filled");
            return false;
        }else {
            Unique.setError(null);
            return true;
        }
    }
    public boolean validateEmail() {
        String email = Email.getText().toString();

        if(email.isEmpty()){
            Email.setError("Email Cannot be empty");
            return false;
        }else {
            Email.setError(null);
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
    public boolean validateConPassword() {
        String ConPassword = confirmpassword.getText().toString();
        String Password = password.getText().toString();
        if(ConPassword.isEmpty()){
            confirmpassword.setError("Password must be filled");
            return false;
        }
        else if(!Password.equals(ConPassword)){
            password.setError("Password Do not Matched");
            return false;
        }else {
            confirmpassword.setError(null);
            return true;
        }
    }

}