package com.github.axet.wget;

import java.net.URL;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ProxyInfo;

public class GetHtmlProxy {

    public static void main(String[] args) {
        try {
            // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
            String url = args[0];
            String h1 = WGet.getHtml(new DownloadInfo(new URL(url), new ProxyInfo("10.10.10.10", 3128, "x", "pass")));
            System.out.println(h1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
