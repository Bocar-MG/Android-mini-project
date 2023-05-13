package com.groupe.eshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ajout_articles extends AppCompatActivity {

    private EditText nom_champ,description_champs,prix_champs,adresse_image;
    private Button sauvegarder;
    FirebaseAuth auth;
    FirebaseFirestore db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_articles);

        // Recuperation des Instances et des informations d'utilisateurs
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        CollectionReference InfoArticlesRef = db.collection("informations").document(auth.getCurrentUser().getUid()).collection("Articles");
        CollectionReference ListeArticles = db.collection("ListeArticles");

        nom_champ = findViewById(R.id.nom_champ);
        description_champs = findViewById(R.id.description_champs);
        prix_champs = findViewById(R.id.prix_champs);
        adresse_image = findViewById(R.id.adresse_image);
        sauvegarder = findViewById(R.id.sauvegarder);



        sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Recuperation des champs de saisies

                String nom,description,image,prix;
                nom = nom_champ.getText().toString();
                description = description_champs.getText().toString();
                prix = prix_champs.getText().toString();
                image = adresse_image.getText().toString();

                // Teste de validation des champs
                if(nom.isEmpty() || description.isEmpty() || prix.isEmpty() || image.isEmpty())
                {
                    Toast.makeText(ajout_articles.this,"Veuillez renseigner tout les champs",Toast.LENGTH_LONG).show();
                    return;
                }
                 // Si tout est Ok on passe a l'ajout
                Article article = new Article(nom,description,Double.parseDouble(prix),image);
                      InfoArticlesRef.add(article)
                              .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                         InfoArticlesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                             @Override
                             public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                                 if (error != null) {
                                     Toast.makeText(ajout_articles.this,"Erreur double",Toast.LENGTH_LONG).show();
                                     return;
                                 }
                                 ListeArticles.add(article);
                                 // Ajouter les nouveaux articles à la collection "ListeArticle"
                             /*  for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                     if (dc.getType() == DocumentChange.Type.ADDED) {
                                         Article article = dc.getDocument().toObject(Article.class);
                                         ListeArticles.add(article);
                                     }
                                 } */
                             }
                         });

                          Toast.makeText(ajout_articles.this,"Reussite",Toast.LENGTH_LONG).show();
                          Intent intent = new Intent(ajout_articles.this,liste_articles_ajouter.class);
                          startActivity(intent);

                    }
                })
                      .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ajout_articles.this,"Erreur echec d'ajout",Toast.LENGTH_LONG).show();
                    }
                });








            }
        });





    }
}