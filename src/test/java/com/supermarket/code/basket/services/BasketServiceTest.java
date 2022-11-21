package com.supermarket.code.basket.services;


import com.supermarket.code.basket.DTO.ItemDTO;
import com.supermarket.code.basket.DTO.ProductDTO;
import com.supermarket.code.basket.DTO.PromotionDTO;
import com.supermarket.code.basket.enuns.DiscountType;
import com.supermarket.code.basket.model.Basket;
import com.supermarket.code.basket.model.ItemBasket;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BasketServiceTest {

    @Mock
    private ProductService productService;
    @InjectMocks
    private BasketService basketService;

    @Test
    public void should_return_empty_list() {
        List<Basket> allBaskets = basketService.getAllBaskets();
        Assertions.assertNotNull(allBaskets);
        Assertions.assertTrue(allBaskets.isEmpty());
    }

    @Test
    public void should_add_new_basket_without_items() {
        Basket basket = new Basket();

        basketService.addNewBasket(basket);
        List<Basket> allBaskets = basketService.getAllBaskets();
        Assertions.assertNotNull(allBaskets);
        Assertions.assertTrue(allBaskets.isEmpty());
    }

    @Test
    public void should_add_new_item_in_basket() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("C8GDyLrHJb");
        productDTO.setName("Amazing Salad");
        productDTO.setPrice(new BigDecimal(499));

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId("Gm1piPn7Fg");
        promotionDTO.setType(DiscountType.FLAT_PERCENT);
        promotionDTO.setAmount(new BigDecimal(10));

        productDTO.setPromotions(Lists.list(promotionDTO));
        ItemBasket build = new ItemBasket(productDTO);
        build.setQty(5);

        Basket basket = new Basket();
        basketService.addNewBasket(basket);
        List<Basket> allBaskets = basketService.getAllBaskets();
        Basket basketReturn = basketService.addItem(basket.getId(), build);

        Assertions.assertEquals(1, basketReturn.getId());
        Assertions.assertEquals(new BigDecimal(2495), basketReturn.getTotal());
        Assertions.assertEquals(new BigDecimal("2245.5"), basketReturn.getTotalPayable());
        Assertions.assertEquals(new BigDecimal("249.5"), basketReturn.getTotalDiscount());
    }

    @Test
    public void should_add_new_item_in_basket_with_promotion_override_price() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("C8GDyLrHJb");
        productDTO.setName("Amazing Salad");
        productDTO.setPrice(new BigDecimal(1099));

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId("Gm1piPn7Fg");
        promotionDTO.setType(DiscountType.QTY_BASED_PRICE_OVERRIDE);
        promotionDTO.setRequired_qty(2);
        promotionDTO.setPrice(new BigDecimal(1799));

        productDTO.setPromotions(Lists.list(promotionDTO));
        ItemBasket build = new ItemBasket(productDTO);
        build.setQty(5);

        Basket basket = new Basket();
        basketService.addNewBasket(basket);
        List<Basket> allBaskets = basketService.getAllBaskets();
        Basket basketReturn = basketService.addItem(basket.getId(), build);

        Assertions.assertEquals(1, basketReturn.getId());
        Assertions.assertEquals(new BigDecimal(5495), basketReturn.getTotal());
        Assertions.assertEquals(new BigDecimal(4697), basketReturn.getTotalPayable());
        Assertions.assertEquals(new BigDecimal(798), basketReturn.getTotalDiscount());
    }

    @Test
    public void should_add_basket_with_items() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("C8GDyLrHJb");
        productDTO.setName("Amazing Salad");
        productDTO.setPrice(new BigDecimal(1099));

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId("Gm1piPn7Fg");
        promotionDTO.setType(DiscountType.QTY_BASED_PRICE_OVERRIDE);
        promotionDTO.setRequired_qty(2);
        promotionDTO.setPrice(new BigDecimal(1799));

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId("PWWe3w1SDU");
        productDTO2.setName("Amazing Burger!");
        productDTO2.setPrice(new BigDecimal(999));

        PromotionDTO promotionDTO2 = new PromotionDTO();
        promotionDTO2.setId("ZRAwbsO2qM");
        promotionDTO2.setType(DiscountType.BUY_X_GET_Y_FREE);
        promotionDTO2.setRequired_qty(2);
        promotionDTO2.setFree_qty(1);

        productDTO.setPromotions(Lists.list(promotionDTO));
        productDTO2.setPromotions(Lists.list(promotionDTO2));

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setQty(5);
        itemDTO.setIdProduct("C8GDyLrHJb");

        ItemDTO itemDTO2 = new ItemDTO();
        itemDTO2.setQty(3);
        itemDTO2.setIdProduct("PWWe3w1SDU");

        Mockito.when(productService.getProduct("C8GDyLrHJb")).thenReturn(productDTO);
        Mockito.when(productService.getProduct("PWWe3w1SDU")).thenReturn(productDTO2);

        Basket basketReturn = basketService.addNewBasketWithItems(Lists.list(itemDTO, itemDTO2));

        Assertions.assertEquals(1, basketReturn.getId());
        Assertions.assertEquals(2, basketReturn.getMapItems().size());
    }

    @Test
    public void should_update_items_in_basket() {
        should_add_basket_with_items();

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setQty(2);
        itemDTO.setIdProduct("C8GDyLrHJb");

        ItemDTO itemDTO2 = new ItemDTO();
        itemDTO2.setQty(0);
        itemDTO2.setIdProduct("PWWe3w1SDU");

        Basket basketReturn = basketService.updateItemBasket(1L, Lists.list(itemDTO, itemDTO2));

        Assertions.assertEquals(1, basketReturn.getId());
        Assertions.assertEquals(1, basketReturn.getMapItems().size());
        Assertions.assertEquals(2, basketReturn.getMapItems().get("Amazing Salad").getQty());
    }
}
