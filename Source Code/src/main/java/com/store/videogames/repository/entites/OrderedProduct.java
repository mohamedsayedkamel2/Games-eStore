package com.store.videogames.repository.entites;

import lombok.ToString;

@ToString
public class OrderedProduct
{
    private int gameId;
    private String gameName;
    private int orderedQuantity;
    private int avaliableQuantity;
    private float price;
    private boolean isDigitallyAvaliable;

    private OrderedProduct()
    {
    }

    public OrderedProduct(Videogame videogame)
    {
        this.gameId = videogame.getId();
        this.gameName = videogame.getGameName();
        this.orderedQuantity = 0;
        this.avaliableQuantity = videogame.getQuantity();
        this.price = videogame.getPrice();
//        this.isDigitallyAvaliable = videogame.isDigitallyAvaliable();
    }

    public int getGameId()
    {
        return gameId;
    }

    public void setGameId(int gameId)
    {
        this.gameId = gameId;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public int getOrderedQuantity()
    {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity)
    {
        this.orderedQuantity = orderedQuantity;
    }

    public int getAvaliableQuantity()
    {
        return avaliableQuantity;
    }

    public void setAvaliableQuantity(int avaliableQuantity)
    {
        this.avaliableQuantity = avaliableQuantity;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public boolean isDigital()
    {
        return isDigitallyAvaliable;
    }

    public void setDigital(boolean digital)
    {
        isDigitallyAvaliable = digital;
    }
}
