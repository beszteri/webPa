package com.codecool.web.model;

import java.util.Objects;

public final class Game extends AbstractModel {

    private String name;
    private String platform;
    private String imageUrl;
    private int price;

    public Game(int id, String name, String platform, String imageUrl, int price) {
        super(id);
        this.name = name;
        this.platform = platform;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Game game = (Game) o;
        return price == game.price &&
            Objects.equals(name, game.name) &&
            Objects.equals(platform, game.platform) &&
            Objects.equals(imageUrl, game.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, platform, imageUrl, price);
    }
}
