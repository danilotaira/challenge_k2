package com.supermarket.code.basket.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.supermarket.code.basket.enuns.DiscountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionDTO {
    private String id;
    private DiscountType type;
    private BigDecimal amount;
    private Integer required_qty;
    private Integer free_qty;
    private BigDecimal price;
}
