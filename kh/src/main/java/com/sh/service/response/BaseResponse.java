package com.sh.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Created by ankurgupta.p on 30/12/15.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class BaseResponse {
    @JsonProperty
    public final ServiceResponse status;

}
