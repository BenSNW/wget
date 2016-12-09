package com.github.axet.wget;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.wget.info.DownloadInfo;

public abstract class Direct {
    /**
     * size of read buffer
     */
    public static int BUF_SIZE = 4 * 1024;

    File target = null;

    DownloadInfo info;

    /**
     * 
     * @param info
     *            download file information
     * @param target
     *            target file
     */
    public Direct(DownloadInfo info, File target) {
        this.target = target;
        this.info = info;
    }

    /**
     * 
     * @param stop
     *            multithread stop command
     * @param notify
     *            progress notify call
     */
    abstract public void download(AtomicBoolean stop, Runnable notify);
}
