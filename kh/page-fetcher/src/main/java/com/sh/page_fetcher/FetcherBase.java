package com.sh.page_fetcher;

import org.hibernate.internal.util.xml.BufferedXMLEventReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by ankurgupta.p on 09/01/16.
 */
public abstract class FetcherBase implements Fetcher {

    final String TMP_FETCH_DIR = "/Users/ankurgupta.p/sd_data/tmp_fetch_dir";
    public FetcherBase(){

    }
    public String fetchPageHttpGet(InputResource inputResource) throws IOException {
        String url = inputResource.getUrl();
        StringBuilder result = new StringBuilder();
        String uniqFile = UUID.randomUUID().toString();
        File file = new File(String.format("%s/%s", TMP_FETCH_DIR, uniqFile));
        file.createNewFile();

        URL webUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) webUrl.openConnection();
        conn.setRequestMethod("GET");

//        conn.setInstanceFollowRedirects(true);
//        conn.setAllowUserInteraction(true);
//        conn.setConnectTimeout(100);

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

        while ((line = rd.readLine()) != null) {
            writer.write(line);
        }
        rd.close();
        return file.getAbsolutePath();
    }
}
