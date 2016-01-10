package com.sh.page_fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Created by ankurgupta.p on 10/01/16.
 */
public class DefaultPageFetcher extends FetcherBase {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String file = "/Users/ankurgupta.p/sd_data/rssDir/urlstofetch";
//        InputResource inputResource1 = new InputResource();
//        inputResource1.setUrl("url");
//        inputResource1.setMetaData("{}");
//        inputResource1.setDepth("d");
//        System.out.println(objectMapper.writeValueAsString(inputResource1));

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                InputResource inputResource = objectMapper.readValue(line, InputResource.class);
                System.out.println(new DefaultPageFetcher().fetchPage(inputResource));
            }
        }

    }

    @Override
    public String fetchPage(InputResource inputResource) throws IOException {
        return fetchPageHttpGet(inputResource);
    }

    @Override
    public void savePage() {

    }
}
