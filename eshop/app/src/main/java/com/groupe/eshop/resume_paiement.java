package com.groupe.eshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class resume_paiement extends AppCompatActivity {

    private EditText code_carte,cvv_code;
    private ListView list_achat;
    private ArrayList<Article> ma_liste;
    private Button paiement;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_paiement);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        paiement = findViewById(R.id.paiement);
        code_carte = findViewById(R.id.code_carte);
        cvv_code = findViewById(R.id.cvv_code);
        ma_liste = new ArrayList<Article>();
        list_achat = findViewById(R.id.list_panier);
        Intent intent = getIntent();
        // On recupere le ArrayList
        ma_liste = intent.getParcelableArrayListExtra("Liste_achat");

        liste_adapter liste_article_adapter = new liste_adapter(resume_paiement.this, ma_liste);
        list_achat.setAdapter(liste_article_adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        paiement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 double total = 0.0;
                 String cc,cvv;
                 cc = code_carte.getText().toString();
                 cvv = cvv_code.getText().toString();
                if(cc.isEmpty() || cvv.isEmpty())
                {
                    Toast.makeText(resume_paiement.this,"Veuillez remplir vos informations de payement",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    for(Article article: ma_liste)
                    {
                        total = total + article.getPrix();
                    }
                    builder.setTitle("Paiment d'article")
                            .setMessage("Voulez-vous confirmer votre achat la somme total: "+total+" DT")
                            .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    builder2.setTitle("Paiement effectué")
                                            .setMessage("Felicitations vos achats on été effectué avec succéss !")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    return;
                                                }
                                            });
                                    builder2.create().show();
                                }
                            });
                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.create().show();

                }

            }
        });



    }
}