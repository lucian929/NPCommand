package me.raindropz.mc.model;

public interface Loader<O> {

    /**
     * Loads by context O
     *
     * @param context context
     */
    void load(O context);
}
