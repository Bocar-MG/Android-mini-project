package com.groupe.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class liste_articles_ajouter extends AppCompatActivity {

    private ListView vendeur_liste;
    FirebaseAuth auth;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_articles_ajouter);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        vendeur_liste = findViewById(R.id.vendeur_liste);
        CollectionReference articleRef = db.collection("informations").document(auth.getCurrentUser().getUid()).collection("Articles");

        articleRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Article> articlesList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Article article = documentSnapshot.toObject(Article.class);
                    articlesList.add(article);
                }

                // Afficher les articles dans la ListView

                liste_adapter liste_article_adapter = new liste_adapter(liste_articles_ajouter.this, articlesList);
                vendeur_liste.setAdapter(liste_article_adapter);
            }
        });











    }
}