package com.supermarket.code.basket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermarket.code.basket.DTO.ProductDTO;
import com.supermarket.code.basket.enuns.DiscountType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class ItemBasket {
    @NonNull
    @JsonIgnore
    private ProductDTO productDTO;
    @Setter(AccessLevel.NONE)
    private BigDecimal total= new BigDecimal(0);
    @Setter(AccessLevel.NONE)
    private BigDecimal totalPayable= new BigDecimal(0);
    @Setter(AccessLevel.NONE)
    private Integer qty;
    @Setter(AccessLevel.NONE)
    private BigDecimal totalDiscount= new BigDecimal(0);

    public void setQty(Integer qty){
        this.qty = qty;
        total = productDTO.getPrice().multiply(new BigDecimal(qty));
        totalPayable = productDTO.getPrice().multiply(new BigDecimal(qty));
        totalDiscount = new BigDecimal(0);

        productDTO.getPromotions().forEach(p -> {

            if (p.getType().equals(DiscountType.BUY_X_GET_Y_FREE)) {
                var qtyPromotion = qty / p.getRequired_qty();
                var newQty = qty - (qtyPromotion * p.getFree_qty());
                totalPayable = productDTO.getPrice().multiply(new BigDecimal(newQty));
                totalDiscount = productDTO.getPrice().multiply(new BigDecimal(qty)).subtract(totalPayable);
            } else if (p.getType().equals(DiscountType.FLAT_PERCENT)) {
                totalDiscount = totalPayable.multiply(p.getAmount().divide(new BigDecimal(100)));
                totalPayable = totalPayable.subtract(totalDiscount);
            } else if (p.getType().equals(DiscountType.QTY_BASED_PRICE_OVERRIDE)) {
                var qtyPromotion = qty / p.getRequired_qty();
                var newQty = qty - (qtyPromotion * p.getRequired_qty());
                totalPayable = productDTO.getPrice().multiply(new BigDecimal(newQty))
                        .add(p.getPrice().multiply(new BigDecimal(qtyPromotion)));
                totalDiscount = productDTO.getPrice().multiply(new BigDecimal(qty)).subtract(totalPayable);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemBasket that = (ItemBasket) o;
        return productDTO.equals(that.productDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDTO);
    }
}
