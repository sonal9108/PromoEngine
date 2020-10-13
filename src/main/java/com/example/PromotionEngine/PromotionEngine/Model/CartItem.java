package com.example.PromotionEngine.PromotionEngine.Model;

import java.util.List;

public class CartItem {

    private List<CartItemDetails> cartItemDetails;
    private Integer promocodde;

    public List<CartItemDetails> getCartItemDetails() {
        return cartItemDetails;
    }

    public void setCartItemDetails(List<CartItemDetails> cartItemDetails) {
        this.cartItemDetails = cartItemDetails;
    }

    public Integer getPromocodde() {
        return promocodde;
    }

    public void setPromocodde(Integer promocodde) {
        this.promocodde = promocodde;
    }
}
