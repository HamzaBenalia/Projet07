package com.openclassrooms.poseidon.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BidList")
public class BidList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId", updatable = false)
    private Long bidListId;

    @Column(name = "account")
    private String account;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @Column(name = "askQuantity")
    private Double askQuantity;

    @Column(name = " bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "bidListDate")
    private LocalDateTime bidListDate;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "security")
    private String security;

    @Column(name = "status")
    private String status;

    @Column(name = "trader")
    private String trader;

    @Column(name = "book")
    private String book;

    @Column(name = "creationName")
    private String creationName;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "revisionName")
    private String revisionName;

    @Column(name = "revisionDate")
    private LocalDateTime revisionDate;

    @Column(name = "dealName")
    private String dealName;

    @Column(name = "dealType")
    private String dealType;

    @Column(name = "sourceListId")
    private String sourceListId;

    @Column(name = "side")
    private String side;
}
