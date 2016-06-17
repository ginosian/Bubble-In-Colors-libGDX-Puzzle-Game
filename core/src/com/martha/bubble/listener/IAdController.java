package com.martha.bubble.listener;

/**
 * Created by Martha on 1/31/2016.
 */
public interface IAdController {

    boolean isConnectedToInternet();
    void loadAndShowAD();
    void hide();
    int getAdHeight();

}
