package com.supermarket.code.basket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    private Long id;
    @Setter(AccessLevel.NONE)
    @JsonProperty(value = "items")
    private Map<String, ItemBasket> mapItems = new HashMap<>();
    @Setter(AccessLevel.NONE)
    private BigDecimal total = new BigDecimal(0);
    @Setter(AccessLevel.NONE)
    private BigDecimal totalDiscount = new BigDecimal(0);
    @Setter(AccessLevel.NONE)
    private BigDecimal totalPayable = new BigDecimal(0);

    public void setMapItems(Map<String, ItemBasket> mapItems) {
        total = new BigDecimal(0);
        totalDiscount = new BigDecimal(0);
        totalPayable = new BigDecimal(0);
        mapItems.values().stream().forEach(i -> {
            total = total.add(i.getTotal());
            totalDiscount = totalDiscount.add(i.getTotalDiscount());
            totalPayable = totalPayable.add(i.getTotalPayable());
        });
        this.mapItems = mapItems;
    }
}
