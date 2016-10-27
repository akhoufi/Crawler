package com.paris.sud.extraction;

import com.paris.sud.crawler.CrawlerUrl;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * Created by Hadhami on 26/10/2016.
 */
public class WebPage {

    private Map<String, CrawlerUrl> visitedUrls = null;

    public String getContent(CrawlerUrl url) { // methode essentielle --
        // recuperation du fichier .html depuis le serveur
        HttpClient httpclient = new DefaultHttpClient();
        String text = new String();
        try {
            HttpGet httpget = new HttpGet(url.getUrlString()); //construction
            //  de l'objet qui fera la connexion

            System.out.println("executing request " + httpget.getURI());
            // construction de l'objet qui gerera le dialogue avec le serveur
            ResponseHandler<String> responseHandler =
                    new BasicResponseHandler();
            text = httpclient.execute(httpget, responseHandler);

            //System.out.println(text);
        }
        catch(Throwable t) {
            System.out.println("OOPS YIKES "+ t . toString());
            t . printStackTrace();
        }
        finally {
            // Lorsque on n'a plus besoin de l'objet de type HttpClient
            // on ferme la connexion pour eliberer rapidement les resources
            // systeme qu'on avait monopolisees
            httpclient.getConnectionManager().shutdown();
        }
       // markUrlAsVisited(url); // on marque l'URL
        url . setRawContent(text); // on donne le texte HTML brut au parseur
        // appele dans la classe CrawlerUrl -- qui en extrait le texte,
        // le titre, les liens sortants, etc. (apres avoir parse le texte
        // HTML ainsi fourni)
        return text;

    }

    private void markUrlAsVisited(CrawlerUrl url) {
        this.visitedUrls.put(url.getUrlString(), url);
        url.setIsVisited();
    }


}
