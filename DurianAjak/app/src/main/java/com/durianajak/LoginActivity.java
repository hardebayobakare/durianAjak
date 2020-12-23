package com.durianajak;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.durianajak.utilities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText register_username, register_password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register_username = findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.new_account).setOnClickListener(this);
        findViewById(R.id.forget_password).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);
    }

    private void userLogin() {

        String username = register_username.getText().toString().trim();
        String password = register_password.getText().toString().trim();

        if (username.isEmpty()) {
            register_username.setError("Username is required");
            register_username.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            register_password.setError("Password is required");
            register_password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            register_password.setError("Minimum length of password should be 6");
            register_password.requestFocus();
            return;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String id = jsonResponse.getString("id");
                        String fname = jsonResponse.getString("fname");
                        String lname = jsonResponse.getString("lname");
                        String username = jsonResponse.getString("username");
                        String email = jsonResponse.getString("email");


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("first_name", fname);
                        intent.putExtra("last_name", lname);
                        intent.putExtra("username", username);
                        intent.putExtra("email", email);
                        LoginActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);

        //progressBar.setVisibility(View.VISIBLE);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_account:
               startActivity(new Intent(this, RegisterActivity.class));
               break;

            case R.id.forget_password:
                //startActivity(new Intent(this, ForgetPassword.class));
                break;

            case R.id.login_button:
               userLogin();
               break;
       }
    }
}
