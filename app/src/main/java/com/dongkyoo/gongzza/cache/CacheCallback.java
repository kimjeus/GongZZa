package com.dongkyoo.gongzza.cache;

import java.util.Date;

public interface CacheCallback<T> {

    void onReceive(T t);
}
