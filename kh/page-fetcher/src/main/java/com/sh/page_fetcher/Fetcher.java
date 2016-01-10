package com.sh.page_fetcher;

import java.io.IOException;

/**
 * Created by ankurgupta.p on 09/01/16.
 */
public interface Fetcher {
    public String fetchPage(InputResource inputResource) throws IOException;
    public void savePage();
}
