package com.supermarket.code.basket.services;

import com.supermarket.code.basket.DTO.ItemDTO;
import com.supermarket.code.basket.DTO.ProductDTO;
import com.supermarket.code.basket.model.Basket;
import com.supermarket.code.basket.model.ItemBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketService {

    @Autowired
    private ProductService productService;
    private Map<Long, Basket> mapBasket;
    private Map<String, Long> mapCount;

    public BasketService() {
        mapBasket = new HashMap<>();
        mapCount = new HashMap<>();
    }

    public Basket addNewBasket(Basket basket){
        mapCount.merge("count", 1L, Long::sum);
        basket.setId(mapCount.get("count"));

        mapBasket.putIfAbsent(basket.getId(), basket);
        return basket;
    }

    public Basket addNewBasketWithItems(List<ItemDTO> items){
        Basket basket = addNewBasket(new Basket());

        addOrUpdateItems(items, basket);
        return getById(basket.getId());
    }

    private void addOrUpdateItems(List<ItemDTO> items, Basket basket) {
        items.forEach(i -> {
            ProductDTO product = productService.getProduct(i.getIdProduct());
            ItemBasket itemProduct = new ItemBasket(product);
            itemProduct.setQty(i.getQty());
            addItem(basket.getId(), itemProduct);
        });
    }

    public Basket updateItemBasket(Long idBasket, List<ItemDTO> items){
        Basket basket = mapBasket.get(idBasket);
        addOrUpdateItems(items, basket);
        return getById(basket.getId());
    }

    public List<Basket> getAllBaskets(){
        return mapBasket.values().stream()
                .filter(b -> b.getTotal().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

    public Basket getById(Long id){
        Basket basket = mapBasket.get(id);
        if (basket.getTotal().compareTo(BigDecimal.ZERO) <= 0)
            return null;
        return basket;
    }

    public Basket addItem(Long idBasket, ItemBasket itemBasket){
        Basket basket = mapBasket.get(idBasket);
        Map<String, ItemBasket> mapItems = basket.getMapItems();
        if (itemBasket.getQty() == 0) {
            mapItems.remove(itemBasket.getProductDTO().getName());
        } else {
            mapItems.put(itemBasket.getProductDTO().getName(), itemBasket);
        }
        basket.setMapItems(mapItems);
        mapBasket.put(idBasket, basket);
        return basket;
    }
}
