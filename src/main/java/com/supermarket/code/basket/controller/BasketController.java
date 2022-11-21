package com.supermarket.code.basket.controller;

import com.supermarket.code.basket.DTO.ItemDTO;
import com.supermarket.code.basket.model.Basket;
import com.supermarket.code.basket.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Long id) {
        Basket basket = basketService.getById(id);
        if (basket == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(basket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateItems(@PathVariable Long id, @RequestBody List<ItemDTO> items) {
        Basket basket = basketService.updateItemBasket(id, items);
        if (basket == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(basket);
    }

    @GetMapping()
    public ResponseEntity<List<Basket>> getAll() {
        List<Basket> baskets = basketService.getAllBaskets();
        if (baskets == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baskets);
    }

    @PostMapping()
    public ResponseEntity<Basket> newBasket(@RequestBody List<ItemDTO> itemAddDTOList) {
        Basket basket = basketService.addNewBasketWithItems(itemAddDTOList);
        if (basket == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(basket);
    }
}