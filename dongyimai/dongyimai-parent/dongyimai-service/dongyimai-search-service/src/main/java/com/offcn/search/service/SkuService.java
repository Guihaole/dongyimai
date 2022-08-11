package com.offcn.search.service;

import java.util.Map;

public interface SkuService {
    void importSku();

    Map searchSku(Map searchMap);
}
