package config;

import org.apache.commons.lang3.RandomStringUtils;

public class Order {
    int number;
    int courierId;

    public Order(int number, int courierId) {
        this.number = number;
        this.courierId = courierId;
    }

    public getRandomOrder() {
        return new Order(
                RandomStringUtils.randomNumeric(5),
                RandomStringUtils.randomNumeric(5)
        );
    }

    public int getcourierId() {
        return courierId;
    }

    public void setNcourierId(int courierId) {
        this.courierId = courierId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
