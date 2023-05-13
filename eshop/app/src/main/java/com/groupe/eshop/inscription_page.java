package com.groupe.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class inscription_page extends AppCompatActivity {

    private EditText email_field,pass_field, nom_prenom,telephone;
    private Button sincrire;
    private ProgressBar progressbar;
    private RadioButton client_btn,vendeur_btn;
    FirebaseAuth auth;
    int RoleId;
    String role;
    private RadioGroup choix;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_page);

        // Initialisation des elements
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        email_field = findViewById(R.id.email_field);
        pass_field = findViewById(R.id.pass_field);
        client_btn = findViewById(R.id.client_btn);
        nom_prenom = findViewById(R.id.nom_prenom);
        telephone = findViewById(R.id.telephone);
        vendeur_btn = findViewById(R.id.vendeur_btn);
        progressbar = findViewById(R.id.progressbar);
        sincrire = findViewById(R.id.sinscrire);
         choix = findViewById(R.id.choix);








        // Inscription de l'utilisateur avec Firebase

        sincrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 progressbar.setVisibility(View.VISIBLE);
                // Recupération des champs email, mot de passe et du bouton radio
                RoleId = choix.getCheckedRadioButtonId();
                if(RoleId == client_btn.getId())
                {
                    role = "client";
                }
                else
                {
                    role = "vendeur";
                }
                String email,pass,np,tel;
                email = email_field.getText().toString();
                pass = pass_field.getText().toString();
                np = nom_prenom.getText().toString();
                tel = telephone.getText().toString();

                // Faire la validation des champs
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(np) || TextUtils.isEmpty(tel))
                {
                    Toast.makeText(inscription_page.this,"Veuillez renseiner vos informations s'il vous plait !!",Toast.LENGTH_LONG).show();
                    return;
                }

              /*  if(!client_btn.isChecked() || !vendeur_btn.isChecked())
                {
                    Toast.makeText(inscription_page.this,"Veuillez selectionner un element",Toast.LENGTH_LONG).show();
                    return;
                }

               */
                auth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    // Enregistrement des informations dans un document
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("role", role);
                                    user.put("Nom_prenom", np);
                                    user.put("Telephone", tel);

                                    db.collection("informations").document(auth.getCurrentUser().getUid())
                                                    .set(user);

                                    Toast.makeText(inscription_page.this,"Félications! vous vous etes incrit en tant que "+role,Toast.LENGTH_LONG).show();
                                    progressbar.setVisibility(View.GONE);
                                    Intent intent = new Intent(inscription_page.this,MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(inscription_page.this, "Erreur incription impossible veuillez reessayer", Toast.LENGTH_LONG)
                                            .show();
                                }


                            }
                        });









            }
        });
        // Fin du bouton sincrire



    }
}