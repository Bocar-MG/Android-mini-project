package com.groupe.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button inscription_btn;
    private EditText email, pass;
    private Button se_connecter;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        se_connecter = findViewById(R.id.se_connecter);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        db = FirebaseFirestore.getInstance();

        se_connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em,ps;
                em = email.getText().toString();
                ps = pass.getText().toString();
                if (TextUtils.isEmpty(em)) {Toast.makeText(MainActivity.this, "Veuillez entrer un email", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(ps)) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer un mot de passe", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                auth.signInWithEmailAndPassword(em,ps)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                      if(task.isSuccessful())
                                      {

                                          String UserId = auth.getCurrentUser().getUid();
                                          db.collection("informations").document(UserId)
                                                          .get()
                                                                  .addOnSuccessListener(documentSnapshot -> {
                                                                      String role = documentSnapshot.getString("role");
                                                                      String nom_prenom = documentSnapshot.getString("Nom_prenom");

                                                                      if(role.equals("vendeur"))
                                                                      {
                                                                          Intent intent = new Intent(MainActivity.this,vendeur.class);
                                                                          intent.putExtra("nom_pr",nom_prenom);
                                                                          startActivity(intent);

                                                                      } else if (role.equals("client")) {

                                                                          Toast.makeText(MainActivity.this,"Connexion Reussie: "+role,Toast.LENGTH_LONG).show();
                                                                          Intent intent = new Intent(MainActivity.this,client_shop.class);
                                                                          intent.putExtra("nom_pr",nom_prenom);
                                                                          startActivity(intent);
                                                                      }

                                                                  });

                                      }
                            }
                        });

            }
        });

        inscription_btn = findViewById(R.id.inscription_btn);
        inscription_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, inscription_page.class);
                startActivity(intent);
            }
        });
    }


}