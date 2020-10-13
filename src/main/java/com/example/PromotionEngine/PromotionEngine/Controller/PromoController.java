package com.example.PromotionEngine.PromotionEngine.Controller;

import com.example.PromotionEngine.PromotionEngine.Entity.ItemAll;
import com.example.PromotionEngine.PromotionEngine.Entity.PromoEngine;
import com.example.PromotionEngine.PromotionEngine.Exception.NotFoundException;
import com.example.PromotionEngine.PromotionEngine.Model.CartItem;
import com.example.PromotionEngine.PromotionEngine.Repository.ItemRepository;
import com.example.PromotionEngine.PromotionEngine.Repository.PromoRepository;
import com.example.PromotionEngine.PromotionEngine.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class PromoController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PromoRepository promoRepository;

    @Autowired
    CartService cartService;

    @PostMapping("/addItem")
    public String addItem(@RequestBody ItemAll itemAll) {
       ItemAll im = itemRepository.save(itemAll);
        if(Objects.isNull(im)) {
            throw new NotFoundException("Item cannot be added");
        }
        return "Item added succesfully in DB ";
    }

    @PostMapping("/addPromoCode")
    public String addPromoCode(@RequestBody PromoEngine promoEngine) {
        PromoEngine pm = promoRepository.save(promoEngine);
        if(Objects.isNull(pm)) {
            throw new NotFoundException("Item cannot be added");
        }
        return "Promo code saved in DB ";
    }

    @PostMapping("/calculateCartValue")
    public Double calculateCartValue(@RequestBody CartItem cartItem) {
        double totVal = 0;
        totVal = cartService.calculateCartAmount(cartItem);
        return totVal;
    }
}
