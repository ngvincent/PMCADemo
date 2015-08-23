package com.github.ma1co.pmcademo.app;

import android.os.Environment;
import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;

public class HttpServer extends NanoHTTPD {
    public static final int PORT = 8080;

    public HttpServer() {
        super(PORT);
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {

        File path = new File( "/" +  File.separator  + session.getUri());

        if(path.isFile()){
            try {
                FileInputStream fis = new FileInputStream(path);
                return new NanoHTTPD.Response(Response.Status.OK, URLConnection.guessContentTypeFromStream(fis), fis);
            } catch (Exception e) {
                e.printStackTrace();
                return new NanoHTTPD.Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Unable to load file");
            }
        }
        File[] files2 = path.listFiles();
        String answer = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>PMCA Server</title>";
        for (File detailsOfFiles : files2) {

                answer += "<a href=\"" + detailsOfFiles.getAbsolutePath()
                        + "\" alt = \"\">" + (detailsOfFiles.isFile() ? "\t" : "") + detailsOfFiles.getAbsolutePath()
                        + "</a><br>";
        }
        answer += "</head></html>";
        return new NanoHTTPD.Response(answer);
    }
}
