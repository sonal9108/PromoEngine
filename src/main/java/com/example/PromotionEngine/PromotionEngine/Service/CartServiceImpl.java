package com.example.PromotionEngine.PromotionEngine.Service;

import com.example.PromotionEngine.PromotionEngine.Entity.ItemAll;
import com.example.PromotionEngine.PromotionEngine.Entity.PromoEngine;
import com.example.PromotionEngine.PromotionEngine.Exception.NotFoundException;
import com.example.PromotionEngine.PromotionEngine.Model.CartItem;
import com.example.PromotionEngine.PromotionEngine.Model.CartItemDetails;
import com.example.PromotionEngine.PromotionEngine.Model.Item;
import com.example.PromotionEngine.PromotionEngine.Model.PromoDetails;
import com.example.PromotionEngine.PromotionEngine.Repository.ItemRepository;
import com.example.PromotionEngine.PromotionEngine.Repository.PromoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PromoRepository promoRepository;

    ItemAll itemAll;

    @Override
    public Double calculateCartAmount(CartItem cartItem) {
        double val = 0;
       boolean res = checkForItemInAllItems(cartItem,itemAll);
       if(res) {
            boolean ifPromo = checkIfPromoToBeApplied(cartItem);

           if(ifPromo) {
              val = amountWithPromoApplied(cartItem);
               logger.info("Promo code applied on your order..........................................");
           } else {
               val = amountWithoutPromoApplied(cartItem);
               logger.info("Promo not applicable on your order..........................................");
           }
       } else {
           logger.info("Item not present in main Item Artifactory....................................");
           throw new NotFoundException("Item not present in main Item Artifactory");
       }
       return Double.valueOf(val);
    }


    public boolean checkForItemInAllItems(CartItem cartItem, ItemAll itemAll) {
       boolean checkValue = false;
       List<CartItemDetails> cartList = cartItem.getCartItemDetails();
       List<ItemAll> itemAllList = itemRepository.findAll();
       int counter = 0;
       for(CartItemDetails c : cartList) {
           for(ItemAll i : itemAllList) {
               if(c.getcName().equals(i.getItemName()) && c.getcQuantity() <= i.getItemQuantity()) {
                   counter++;
               }
           }
       }
       if(counter == cartList.size()) {
          checkValue = true;
       }
        return checkValue;
    }


    public boolean checkIfPromoToBeApplied(CartItem cartItem) {
        boolean pCodeToBeApplied = false ;
        int cnt = 0;
       Optional<PromoEngine> promoEngine1 = promoRepository.findById(cartItem.getPromocodde());
       PromoDetails promoDetails = promoEngine1.get().getPromoDetails();
       List<Item> itemList = promoDetails.getItems();
        List<CartItemDetails> cartList = cartItem.getCartItemDetails();

        for(CartItemDetails c : cartList) {
           for(Item i : itemList) {
               if(c.getcName().equals(i.getiName()) && c.getcQuantity() >= i.getiQuantity()) {
                   cnt++;
               }
           }
        }
        if(cnt == itemList.size()) {
            pCodeToBeApplied = true;
        }
        return pCodeToBeApplied;
    }

    public Double totAmountForAllItems(CartItem cartItem) {
        Double amt = 0.0;
        List<CartItemDetails> cartList = cartItem.getCartItemDetails();
        List<ItemAll> itemAllList = itemRepository.findAll();
        for(CartItemDetails c : cartList) {
            for(ItemAll i :itemAllList) {
                if(c.getcName().equals(i.getItemName())) {
                    amt = (amt + (c.getcQuantity()*i.getItemPrice()));
                }
            }
        }
        return amt;
    }

    public Double amountWithoutPromoApplied(CartItem cartItem) {
        Double amt = 0.0;
        amt = totAmountForAllItems(cartItem);
        return amt;
    }

    public Double amountWithPromoApplied(CartItem cartItem) {
        Double amt = 0.0;
        amt = totAmountForAllItems(cartItem);
        Optional<PromoEngine> promoEngine1 = promoRepository.findById(cartItem.getPromocodde());
        PromoDetails promoDetails = promoEngine1.get().getPromoDetails();

        if(promoDetails.getDiscountType().equals("Absolute")) {
            amt = amt - promoDetails.getDiscountAmount();
        } else if(promoDetails.getDiscountType().equals("Percent")) {
            amt = amt * (1-(promoDetails.getDiscountAmount()/100));
        }
        return amt;
    }
}
