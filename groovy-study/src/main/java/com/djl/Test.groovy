package com.djl

import org.apache.commons.lang3.StringUtils

// 通过java将传输参数封装为map的格式传递到脚本中
Map handle(Map map) {
    if (StringUtils.isNotBlank(map["value"] as CharSequence)) {
        map["value"] = map["value"] + 1;
    }
    return map;
}

