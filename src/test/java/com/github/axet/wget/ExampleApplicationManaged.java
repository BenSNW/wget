package com.github.axet.wget;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.wget.info.BrowserInfo;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;
import com.github.axet.wget.info.ex.DownloadMultipartError;

public class ExampleApplicationManaged {

    AtomicBoolean stop = new AtomicBoolean(false);
    DownloadInfo info;
    long last;
    SpeedInfo speedInfo = new SpeedInfo();

    public static String formatSpeed(long s) {
        if (s > 0.1 * 1024 * 1024 * 1024) {
            float f = s / 1024f / 1024f / 1024f;
            return String.format("%.1f GB", f);
        } else if (s > 0.1 * 1024 * 1024) {
            float f = s / 1024f / 1024f;
            return String.format("%.1f MB", f);
        } else {
            float f = s / 1024f;
            return String.format("%.1f kb", f);
        }
    }

    public void run() {
        try {
            Runnable notify = new Runnable() {
                @Override
                public void run() {
                    // notify app or save download state
                    // you can extract information from DownloadInfo info;
                    switch (info.getState()) {
                    case EXTRACTING:
                    case EXTRACTING_DONE:
                        System.out.println(info.getState());
                        break;
                    case DONE:
                        // finish speed calculation by adding remaining bytes speed
                        speedInfo.end(info.getCount());
                        // print speed
                        System.out.println(String.format("%s average speed (%s)", info.getState(),
                                formatSpeed(speedInfo.getAverageSpeed())));
                        break;
                    case RETRYING:
                        System.out.println(info.getState() + " r:" + info.getRetry() + " d:" + info.getDelay());
                        break;
                    case DOWNLOADING:
                        speedInfo.step(info.getCount());
                        long now = System.currentTimeMillis();
                        if (now - 1000 > last) {
                            last = now;

                            String parts = "";

                            if (info.getParts() != null) { // not null if multipart enabled
                                for (Part p : info.getParts()) {
                                    switch (p.getState()) {
                                    case DOWNLOADING:
                                        parts += String.format("Part#%d(%.2f) ", p.getNumber(),
                                                p.getCount() / (float) p.getLength());
                                        break;
                                    case ERROR:
                                    case RETRYING:
                                        parts += String.format("Part#%d(%s) ", p.getNumber(),
                                                p.getException().getMessage() + " r:" + p.getRetry() + " d:"
                                                        + p.getDelay());
                                        break;
                                    default:
                                        break;
                                    }
                                }
                            }

                            float p = info.getCount() / (float) info.getLength();

                            System.out.println(String.format("%.2f %s (%s / %s)", p, parts,
                                    formatSpeed(speedInfo.getCurrentSpeed()),
                                    formatSpeed(speedInfo.getAverageSpeed())));
                        }
                        break;
                    default:
                        break;
                    }
                }
            };

            // choice file
            URL url = new URL("http://download.virtualbox.org/virtualbox/5.0.16/VirtualBox-5.0.16-105871-OSX.dmg");
            // initialize url information object with or without proxy
            // info = new DownloadInfo(url, new ProxyInfo("proxy_addr", 8080, "login", "password"));
            info = new DownloadInfo(url);
            // extract information from the web
            info.extract(stop, notify);
            // enable multipart download
            // info.enableMultipart();
            // Choice target file or set download folder
            File target = new File("/Users/axet/Downloads/VirtualBox-5.0.16-105871-OSX.dmg");
            // create wget downloader
            WGet w = new WGet(info, target);
            // init speedinfo
            speedInfo.start(0);
            // will blocks until download finishes
            w.download(stop, notify);
        } catch (DownloadMultipartError e) {
            for (Part p : e.getInfo().getParts()) {
                Throwable ee = p.getException();
                if (ee != null)
                    ee.printStackTrace();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExampleApplicationManaged e = new ExampleApplicationManaged();
        e.run();
    }
}