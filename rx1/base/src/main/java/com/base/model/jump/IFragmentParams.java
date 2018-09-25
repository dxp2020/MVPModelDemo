package com.base.model.jump;

import java.io.Serializable;

public class IFragmentParams<T> implements Serializable {

    public T params;

    public IFragmentParams(T arg){
        this.params = arg;
    }

}
