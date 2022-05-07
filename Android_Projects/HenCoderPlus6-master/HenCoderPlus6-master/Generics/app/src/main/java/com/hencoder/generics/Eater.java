package com.hencoder.generics;


interface Eater<T extends Food> {
    void eat(T food);
}
