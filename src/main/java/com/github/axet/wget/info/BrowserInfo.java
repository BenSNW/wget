package com.github.axet.wget.info;

import java.io.Serializable;
import java.net.URL;

/**
 * BrowserInfo - keep all information about browser
 * 
 * @author axet
 * 
 */
public class BrowserInfo implements Serializable {
    private static final long serialVersionUID = 2603968158067492115L;

    static public String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36";

    private String userAgent;
    private URL referer;

    public BrowserInfo() {
        userAgent = USER_AGENT;
    }

    synchronized public String getUserAgent() {
        return userAgent;
    }

    synchronized public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    synchronized public URL getReferer() {
        return referer;
    }

    synchronized public void setReferer(URL referer) {
        this.referer = referer;
    }

    /**
     * copy resume data from oldSource;
     */
    synchronized public void resume(BrowserInfo old) {
        referer = old.referer;
        userAgent = old.userAgent;
    }
}
