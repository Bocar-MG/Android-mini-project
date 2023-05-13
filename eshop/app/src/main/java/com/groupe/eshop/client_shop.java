package com.groupe.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class client_shop extends AppCompatActivity {
     private TextView nom_client_shop;
     private Button client_deconnexion,panier;
     private ListView list_client_view;
     private ArrayList<Article> panier_article;
     FirebaseAuth auth;
     FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shop);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nom_client_shop = findViewById(R.id.nom_client_shop);
        list_client_view = findViewById(R.id.liste_client_view);
        CollectionReference articleRef = db.collection("ListeArticles");
        panier_article = new ArrayList<>();
        panier = findViewById(R.id.panier);
        client_deconnexion = findViewById(R.id.client_deconnexion);

        articleRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Article> articles = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Article article = document.toObject(Article.class);
                        articles.add(article);
                    }
                    // Mettre à jour la ListView avec la liste des articles
                    liste_adapter liste_article_adapter = new liste_adapter(client_shop.this, articles);
                    list_client_view.setAdapter(liste_article_adapter);
                }
                else
                {
                    Toast.makeText(client_shop.this,"Erreur de recuperation",Toast.LENGTH_LONG).show();
                }

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        list_client_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Article article = (Article) adapterView.getItemAtPosition(i);
                   builder
                        .setTitle("Achat d'article")
                        .setMessage("Voulez vous ajouter "+article.getNom()+" sur votre panier")
                        .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                     panier_article.add(article);
                                     String taille = "Panier : "+panier_article.size();

                                     panier.setText(taille);
                            }
                        });
                   builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                       }
                   });
                   builder.create().show();

            }
        });

        Intent intent = getIntent();
        nom_client_shop.setText(intent.getExtras().get("nom_pr").toString());

        client_deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(client_shop.this,MainActivity.class);
                startActivity(intent);
            }
        });
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(client_shop.this,resume_paiement.class);
                 // On utilise ici le parcelable pour envoyer un ArrayList comme extra
                 intent.putParcelableArrayListExtra("Liste_achat",panier_article);
                 startActivity(intent);
            }
        });




    }
}