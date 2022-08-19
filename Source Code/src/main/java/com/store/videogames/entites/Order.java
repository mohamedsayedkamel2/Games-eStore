package com.store.videogames.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @JoinTable(name = "scustomerorders",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "newcustomer_order"))
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videogame_id")
    private Videogame videogame;

    @JoinColumn(name = "digital_code", nullable = true, columnDefinition = "")
    private String digitalVideogameCode;

    @Column(name = "order_date")
    private LocalDate purchaseDate;

    @Column(name = "order_time")
    private LocalTime purchaseTime;

    @Override
    public String toString()
    {
        return "Order{" +
                "id=" + id +
                ", orderTransaction='" + orderTransaction + '\'' +
                ", customer=" + customer +
                ", videogame=" + videogame +
                ", digitalVideogameCode='" + digitalVideogameCode + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}