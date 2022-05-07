package com.hencoder.generics;

import java.util.ArrayList;
import java.util.Collections;

class ReversableArrayList<T> extends ArrayList {
    public void reverse() {
        Collections.reverse(this);
    }
}
