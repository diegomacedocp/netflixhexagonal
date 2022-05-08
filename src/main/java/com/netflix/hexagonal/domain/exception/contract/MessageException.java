package com.netflix.hexagonal.domain.exception.contract;

import java.util.Map;

public interface MessageException {
    String getExceptionKey();
    Map<String, Object> getMapDetails();
}
