package com.github.axet.wget;

import java.net.URL;

public class GetHtml {

    public static void main(String[] args) {
        try {
            long l = 0x124DAF0l;
            System.out.println(String.format("%016x", l));
            // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
            String url = args[0];
            String h = WGet.getHtml(new URL(url));
            System.out.println(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
