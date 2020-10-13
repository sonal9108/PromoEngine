package com.example.PromotionEngine.PromotionEngine.Service;

import com.example.PromotionEngine.PromotionEngine.Model.CartItem;

public interface CartService {

  Double calculateCartAmount(CartItem cartItem);

}
