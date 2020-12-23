package com.durianajak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText register_firstname, register_lastname, register_username, register_email, register_password, register_re_entered_password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_firstname = findViewById(R.id.firstname);
        register_lastname = findViewById(R.id.lastname);
        register_username = findViewById(R.id.username);
        register_re_entered_password = findViewById(R.id.register_password2);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.have_account).setOnClickListener(this);
        findViewById(R.id.save_button2).setOnClickListener(this);
    }

    private  void registerUser() {
        String firstname = register_firstname.getText().toString().trim();
        String lastname = register_lastname.getText().toString().trim();
        String username = register_username.getText().toString().trim();
        String email = register_email.getText().toString().trim();
        String password = register_password.getText().toString().trim();
        String password2 = register_re_entered_password.getText().toString().trim();

        if (firstname.isEmpty()){
            register_firstname.setError("First Name is required");
            register_firstname.requestFocus();
            return;
        }
        if (lastname.isEmpty()){
            register_lastname.setError("Last Name is required");
            register_lastname.requestFocus();
            return;
        }
        if (username.isEmpty()){
            register_username.setError("Username is required");
            register_username.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            register_email.setError("Email is required");
            register_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            register_email.setError("Please enter a valid email");
            register_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            register_password.setError("Password is required");
            register_password.requestFocus();
            return;
        }
        if (password2.isEmpty()) {
            register_re_entered_password.setError("Password is required");
            register_re_entered_password.requestFocus();
            return;
        }

        if(password.length()<6){
            register_password.setError("Minimum length of password should be 6");
            register_password.requestFocus();
            return;
        }
        if(!password.equals(password2)){
            register_re_entered_password.setError("Password mis-match");
            register_re_entered_password.requestFocus();
            return;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(firstname, lastname, username, email, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_button2:
                registerUser();
                break;

            case R.id.have_account:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
