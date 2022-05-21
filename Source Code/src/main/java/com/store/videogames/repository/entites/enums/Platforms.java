package com.store.videogames.repository.entites.enums;


public enum Platforms
{
    PC("PC"),
    PS3("Playstation 3"), PS4("Playstation 4"), PS5("Playstation 5"),
    XBOX_360("Xbox 360"),
    XBOX_ONE("Xbox One"), XBOX_ONE_S("Xbox One S"), XBOX_ONE_X("Xbox One X"),
    XBOX_SERIES_X("Xbox Series X"), XBOX_SERIES_S("Xbox Series S"),
    SWITCH("NINTENDO SWITCH");

    private String platformName;

    Platforms(String enumName)
    {
        this.platformName = enumName;
    }


    public String getPlatformName()
    {
        return platformName;
    }

}