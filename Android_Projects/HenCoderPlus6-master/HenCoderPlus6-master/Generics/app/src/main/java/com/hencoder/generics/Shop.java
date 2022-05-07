package com.hencoder.generics;

import java.io.Serializable;
import java.util.List;

interface Shop<T> { // Type Parameter; Type Argument
  T buy();

  float refund(T item);

  <O> List<T> recycle(O item);

  <E> E tradeIn(E item, float money);

  <E extends Runnable & Serializable> void someMethod(E param);


  <R> R take();

  <P> Shop<P> merge(List<P> list1, List<P> list2);
}
