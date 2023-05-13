package com.groupe.eshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class liste_adapter extends ArrayAdapter<Article> {

    private Context context;
    private List<Article> articleList;

    public liste_adapter(Context context, List<Article> articleList)
    {
        super(context,0,articleList);
        this.context = context;
        this.articleList = articleList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_article, parent, false);
        }

        Article article = articleList.get(position);

        TextView nomTextView = listItemView.findViewById(R.id.article_name);
        nomTextView.setText(article.getNom());

        TextView DescriptionTextView = listItemView.findViewById(R.id.article_description);
        DescriptionTextView.setText(article.getDescription());

        TextView prixTextView = listItemView.findViewById(R.id.article_price);
        prixTextView.setText(article.getPrix().toString());


        ImageView articleImageView = listItemView.findViewById(R.id.article_image);
        Glide.with(context).load(article.getImage_url()).into(articleImageView);

        return listItemView;
    }


}
