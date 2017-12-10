package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    Button change;
    String newPass,oldPass;
    EditText passo,passn;
    CheckBox checkBox;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangePassword.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, ChangePassword.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change = (Button)findViewById(R.id.changePass);
        passo=(EditText)findViewById(R.id.currPassword2);
        passn=(EditText)findViewById(R.id.newPassword2);
        checkBox = (CheckBox)findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    passn.setInputType(129);
                } else {
                    passn.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });




        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update in firebase

                oldPass=passo.getText().toString();
                newPass=passn.getText().toString();

                if(oldPass.equals("") || newPass.equals(""))
                    Toast.makeText(ChangePassword.this,"Please fill the details correctly",Toast.LENGTH_LONG).show();
                else if(oldPass.equals(newPass))
                    Toast.makeText(ChangePassword.this,"new and old passwords must be different",Toast.LENGTH_LONG).show();
                else if(newPass.length()<=6)
                    Toast.makeText(ChangePassword.this,"Password must be greater than 6 characters",Toast.LENGTH_LONG).show();
                else {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email, oldPass);

                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    System.out.println("password updated");
                                                    Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_LONG).show();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), " Original Password entered is incorrect", Toast.LENGTH_LONG).show();
                                                    System.out.println("Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        System.out.println("Error auth failed");
                                    }
                                }
                            });
                }
            }
        });


    }
}
