package com.paris.sud.extraction;

import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;

import com.paris.sud.transformation.TransformWebPage;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class WebPage {

    public String getContent(CrawlerUrl url) throws IOException {
        TransformWebPage transform = new TransformWebPage(url.getUrlString());
        // methode essentielle --
        // recuperation du fichier .html depuis le serveur
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 7000);
        HttpClient httpclient = new DefaultHttpClient(httpParams);
        String text = new String();

        HttpGet httpget = new HttpGet(url.getUrlString()); //construction
        //  de l'objet qui fera la connexion
        System.out.println("executing request " + httpget.getURI());
        // construction de l'objet qui gerera le dialogue avec le serveur
        ResponseHandler<String> responseHandler =
                new BasicResponseHandler();
        text = httpclient.execute(httpget, responseHandler);
        transform.setRawContent(text, url.getUrlString()); // on donne le texte HTML brut au parseur
        httpclient.getConnectionManager().shutdown();
        //System.out.println(text);

        // markUrlAsVisited(url); // on marque l'URL
        // appele dans la classe CrawlerUrl -- qui en extrait le texte,
        // le titre, les liens sortants, etc. (apres avoir parse le texte
        // HTML ainsi fourni)
        return text;

    }

}
