package de.jadehs.trawell.api;

/**
 * Created by luxn on 09.05.2017.
 */

public interface OnTaskCompletedListener<T> {

    public void onSuccess(T object);

    public void onException(Exception e);
}
