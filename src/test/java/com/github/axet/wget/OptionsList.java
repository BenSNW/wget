package com.github.axet.wget;

import com.github.axet.wget.info.BrowserInfo;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;

public class OptionsList {
    public static void main(String[] args) {
        DirectMultipart.THREAD_COUNT = 3;
        SpeedInfo.SAMPLE_LENGTH = 1000;
        SpeedInfo.SAMPLE_MAX = 20;
        BrowserInfo.USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36";
        DownloadInfo.PART_LENGTH = 5 * 1024 * 1024; // bytes
        URLInfo.READ_TIMEOUT = 5 * 1000; // milliseconds
        URLInfo.CONNECT_TIMEOUT = 5 * 1000; // milliseconds
        RetryWrap.RETRY_COUNT = 5; /// 5 times then fail or -1 for infinite
        RetryWrap.RETRY_DELAY = 3; // seconds between retries
    }
}