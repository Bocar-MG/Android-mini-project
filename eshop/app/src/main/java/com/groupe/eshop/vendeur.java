package com.groupe.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class vendeur extends AppCompatActivity {

    private TextView bienvenu;
    private Button ajouter_btn,consulter_btn,nombrev_btn,deconnexion;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendeur);

        bienvenu = findViewById(R.id.bienvenu);
        ajouter_btn = findViewById(R.id.ajouter_btn);
        consulter_btn = findViewById(R.id.consulter_btn);
        deconnexion = findViewById(R.id.deconnexion);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        bienvenu.setText("Bienvenu "+intent.getExtras().get("nom_pr").toString().toUpperCase());



        ajouter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vendeur.this,ajout_articles.class);
                startActivity(intent);
            }
        });
        consulter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vendeur.this,liste_articles_ajouter.class);
                startActivity(intent);
            }
        });
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 auth.signOut();
                {
                    Intent intent = new Intent(vendeur.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });







    }
}