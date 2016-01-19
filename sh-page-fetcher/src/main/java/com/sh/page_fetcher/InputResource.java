package com.sh.page_fetcher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ankurgupta.p on 09/01/16.
 */

@Getter
@Setter
public class InputResource {
    @JsonProperty
    String url;
    @JsonProperty
    Object metaData;
    @JsonProperty
    String depth;

}
