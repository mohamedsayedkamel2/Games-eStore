package com.store.videogames.repository.entites;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "customer_money_history")
public class CustomerMoneyHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @Column(name = "money_before_order")
    float moneyBeforeOrder;

    @Column(name = "money_after_order")
    float moneyAfterOrder;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public float getMoneyBeforeOrder() {
        return moneyBeforeOrder;
    }

    public void setMoneyBeforeOrder(float moneyBeforeOrder) {
        this.moneyBeforeOrder = moneyBeforeOrder;
    }

    public float getMoneyAfterOrder() {
        return moneyAfterOrder;
    }

    public void setMoneyAfterOrder(float moneyAfterOrder) {
        this.moneyAfterOrder = moneyAfterOrder;
    }

    @Override
    public String toString() {
        return "CustomerMoneyHistory{" +
                "id=" + id +
                ", order=" + order +
                ", moneyBeforeOrder=" + moneyBeforeOrder +
                ", moneyAfterOrder=" + moneyAfterOrder +
                '}';
    }
}
