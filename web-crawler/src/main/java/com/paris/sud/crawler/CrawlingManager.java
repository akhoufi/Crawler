package com.paris.sud.crawler;

import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.extraction.WebPage;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Hadhami on 01/11/2016.
 */
public class CrawlingManager {

    private static final String USER_AGENT = "User-agent:";
    private static final String DISALLOW = "Disallow:";

    public long getDelayBetweenUrls() {
        return delayBetweenUrls;
    }

    public long delayBetweenUrls=100L;

    private Map<String, CrawlerUrl> visitedUrls = null;
    public Map<String, Collection<String>> sitePermissions;


    private boolean isAllowedToVisit;
    private boolean isCheckedForPermission = false;


    public CrawlingManager(long delayBetweenUrls) {
        this.delayBetweenUrls = delayBetweenUrls;
        this.visitedUrls = new HashMap<String, CrawlerUrl>();
        this.sitePermissions = new HashMap<String, Collection<String>>();

    }

    public CrawlingManager() {
        this.visitedUrls = new HashMap<String, CrawlerUrl>();
        this.sitePermissions = new HashMap<String, Collection<String>>();
    }

    public boolean isAllowedToVisit() {
        return isAllowedToVisit;
    }

    public boolean isCheckedForPermission() {
        return isCheckedForPermission;
    }

    public void setAllowedToVisit(boolean isAllowedToVisit) {
        this.isAllowedToVisit = isAllowedToVisit;
        this.isCheckedForPermission = true;
    }

    public Collection<String> getDisallowedPaths(String host) throws IOException {
        CrawlerUrl url = new CrawlerUrl("http://" + host + "/robots.txt");
        WebPage content = new WebPage();
        String robotscontent = content.getContent(url);

        Collection<String> disallowedPaths = new ArrayList<String>();
        if (robotscontent != null) {
            Pattern p = Pattern.compile(USER_AGENT);
            String[] permissionSets = p.split(robotscontent);
            String permissionString = "";
            for (String permission : permissionSets) {
                if (permission.trim().startsWith("*")) {
                    permissionString = permission.substring(1);
                }
            }
            p = Pattern.compile(DISALLOW);
            String[] items = p.split(permissionString);
            for (String s : items) {
                disallowedPaths.add(s.trim());
                // System.out.println(s.trim());
            }
        }
        this.sitePermissions.put(host, disallowedPaths);
        return disallowedPaths;
    }

    public boolean computePermissionForVisiting(CrawlerUrl crawlerUrl) throws IOException {
        URL url = crawlerUrl.getURL();

        boolean retValue = true;
        if (retValue) {
            String host = url.getHost();
            String port = "";
            int portN = url.getPort();
            if (portN > -1) {
                port = ":" + String.valueOf(portN);
            }

            Collection<String> disallowedPaths = this.sitePermissions.get(host);
            if (disallowedPaths == null) {
                disallowedPaths = getDisallowedPaths(host + port);
            }
            // On itere pour tous les chemins interdits (disallowed paths)
            String path = url.getPath();
            for (String disallowedPath : disallowedPaths) {
                if (path.contains(disallowedPath)) {
                    retValue = false;
                }
            }
        }
        return retValue;
    }



    public boolean doWeHavePermissionToVisit(CrawlerUrl crawlerUrl) throws IOException {
        if (crawlerUrl == null) {
            return false;
        }
        if (!isCheckedForPermission()) {
            setAllowedToVisit(computePermissionForVisiting(crawlerUrl));
        }
        // We need to check
        return isAllowedToVisit();

    }




}
