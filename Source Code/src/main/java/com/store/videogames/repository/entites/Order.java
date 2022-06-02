package com.store.videogames.repository.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "customer_order")
@Table(name = "customer_order")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "order_transaction")
    private String orderTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "videogame_id")
    private Videogame videogame;

    @JoinColumn(name = "digital_code", nullable = true, columnDefinition = "")
    private String digitalVideogameCode;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "order_date")
    private LocalDate purchaseDate;

    @Column(name = "order_time")
    private LocalTime purchaseTime;

    @Override
    public String toString()
    {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer.getFirstName() +
                ", videogame=" +
                ", quantity=" + quantity +
                ", purchaseDate=" + purchaseDate +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}