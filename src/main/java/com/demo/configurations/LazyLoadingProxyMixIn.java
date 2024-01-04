package com.demo.configurations;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface LazyLoadingProxyMixIn {

    @JsonIgnore
    String getRefId();

    @JsonIgnore
    Object getEntity();

}

