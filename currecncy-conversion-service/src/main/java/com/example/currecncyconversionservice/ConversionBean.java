package com.example.currecncyconversionservice;

import java.math.BigDecimal;

public class ConversionBean {
    private Long id ;
    private String from ;
    private String to ;
    private BigDecimal quantity ;
    private BigDecimal conversionMultiple ;
    private BigDecimal total ;
    private int port ;

    public ConversionBean(){

    }

    public ConversionBean(Long id, String from, String to, BigDecimal quantity, BigDecimal conversionMultiple, BigDecimal total, int port) {
        super();
        this.id = id;
        this.from = from;
        this.to = to;
        this.quantity = quantity;
        this.conversionMultiple = conversionMultiple;
        this.total = total;
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
