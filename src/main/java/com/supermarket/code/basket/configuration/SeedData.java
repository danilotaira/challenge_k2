package com.supermarket.code.basket.configuration;

import com.supermarket.code.basket.DTO.ProductDTO;
import com.supermarket.code.basket.model.Basket;
import com.supermarket.code.basket.model.ItemBasket;
import com.supermarket.code.basket.services.BasketService;
import com.supermarket.code.basket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SeedData {
    @Autowired
    private ProductService productService;
    @Autowired
    private BasketService basketService;

    @Bean
    public void seed(){
        ProductDTO pizza = productService.getProduct("Dwt5F7KAhi");
        ProductDTO burger = productService.getProduct("PWWe3w1SDU");
        ProductDTO salad = productService.getProduct("C8GDyLrHJb");
        ProductDTO fries = productService.getProduct("4MB7UfpTQs");

        Basket basket1 = new Basket();
        ItemBasket itemPizza = new ItemBasket(pizza);
        itemPizza.setQty(1);

        ItemBasket itemBurger = new ItemBasket(burger);
        itemBurger.setQty(1);

        ItemBasket itemFries = new ItemBasket(fries);
        itemFries.setQty(1);

        ItemBasket itemSalad = new ItemBasket(salad);
        itemSalad.setQty(1);

        Map<String, ItemBasket> mapItemsBasket1 = new HashMap<>();
        mapItemsBasket1.put(itemPizza.getProductDTO().getName(), itemPizza);
        mapItemsBasket1.put(itemBurger.getProductDTO().getName(), itemBurger);
        mapItemsBasket1.put(itemFries.getProductDTO().getName(), itemFries);

        basket1.setMapItems(mapItemsBasket1);

        basketService.addNewBasket(basket1);
        Basket basket2 = new Basket();

        Map<String, ItemBasket> mapItemsBasket2 = new HashMap<>();
        mapItemsBasket2.put(itemPizza.getProductDTO().getName(), itemPizza);
        mapItemsBasket2.put(itemBurger.getProductDTO().getName(), itemBurger);
        mapItemsBasket2.put(itemFries.getProductDTO().getName(), itemFries);
        mapItemsBasket2.put(itemSalad.getProductDTO().getName(), itemSalad);

        basket2.setMapItems(mapItemsBasket2);
        basketService.addNewBasket(basket2);

        Basket basket3 = new Basket();

        itemPizza = new ItemBasket(pizza);
        itemPizza.setQty(2);

        Map<String, ItemBasket> mapItemsBasket3 = new HashMap<>();
        mapItemsBasket3.put(itemPizza.getProductDTO().getName(), itemPizza);
        mapItemsBasket3.put(itemFries.getProductDTO().getName(), itemFries);

        basket3.setMapItems(mapItemsBasket3);
        basketService.addNewBasket(basket3);

        Basket basket4 = new Basket();

        itemBurger = new ItemBasket(burger);
        itemBurger.setQty(3);

        Map<String, ItemBasket> mapItemsBasket4 = new HashMap<>();
        mapItemsBasket4.put(itemBurger.getProductDTO().getName(), itemBurger);
        mapItemsBasket4.put(itemSalad.getProductDTO().getName(), itemSalad);

        basket4.setMapItems(mapItemsBasket4);
        basketService.addNewBasket(basket4);

        Basket basket5 = new Basket();
        basketService.addNewBasket(basket5);
    }
}
