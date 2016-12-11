package com.github.axet.wget.info.ex;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;

public class DownloadMultipartError extends DownloadError {
    private static final long serialVersionUID = 7835308901669107488L;

    DownloadInfo info;

    public DownloadMultipartError(Throwable e, DownloadInfo info) {
        super(e);
        this.info = info;
    }

    public DownloadMultipartError(DownloadInfo info) {
        super("Multipart error");
        this.info = info;
    }

    public DownloadInfo getInfo() {
        return info;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        for (Part p : getInfo().getParts()) {
            if (p.getException() != null) {
                p.getException().printStackTrace(s);
            }
        }
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        for (Part p : getInfo().getParts()) {
            if (p.getException() != null) {
                p.getException().printStackTrace(s);
            }
        }
        super.printStackTrace(s);
    }
}
