

package com.amreshmaurya.bookstoreapp.service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amreshmaurya.bookstoreapp.constant.BookStatus;
import com.amreshmaurya.bookstoreapp.dto.cart.AddToCartRequest;
import com.amreshmaurya.bookstoreapp.dto.cart.CartItemResponse;
import com.amreshmaurya.bookstoreapp.dto.cart.CartResponse;
import com.amreshmaurya.bookstoreapp.entity.Book;
import com.amreshmaurya.bookstoreapp.entity.Cart;
import com.amreshmaurya.bookstoreapp.entity.CartItem;
import com.amreshmaurya.bookstoreapp.entity.User;
import com.amreshmaurya.bookstoreapp.repository.BookRepository;
import com.amreshmaurya.bookstoreapp.repository.CartItemRepository;
import com.amreshmaurya.bookstoreapp.repository.CartRepository;
import com.amreshmaurya.bookstoreapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository; // Assuming you have this

    @Transactional
    public CartResponse addToCart(UUID userId, AddToCartRequest request) {
        // Get or create cart for user
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> createNewCart(userId));

        // Get book details
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Validate book availability
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for purchase");
        }

        if (book.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Available: " + book.getStockQuantity());
        }

        // Check if book already in cart
        CartItem existingItem = cartItemRepository.findByCartAndBook(cart, book).orElse(null);

        if (existingItem != null) {
            // Update quantity
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (book.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Insufficient stock for updated quantity");
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            // Create new cart item
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .book(book)
                    .quantity(request.getQuantity())
                    .unitPrice(book.getPrice())
                    .build();
            cartItem.calculateSubtotal();
            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
        }

        return getCartResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItemQuantity(UUID userId, UUID cartItemId, Integer quantity) {
        Cart cart = getActiveCartByUser(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        // Validate stock
        Book book = cartItem.getBook();
        if (book.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + book.getStockQuantity());
        }

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return getCartResponse(cart);
    }

    @Transactional
    public void removeFromCart(UUID userId, UUID cartItemId) {
        Cart cart = getActiveCartByUser(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        cartItemRepository.delete(cartItem);
    }

    public CartResponse getCart(UUID userId) {
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> createNewCart(userId));
        return getCartResponse(cart);
    }

    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = getActiveCartByUser(userId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
    }

    private Cart createNewCart(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = Cart.builder()
                .user(user)
                .isActive(true)
                .build();
        return cartRepository.save(cart);
    }

    private Cart getActiveCartByUser(UUID userId) {
        return cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    private CartResponse getCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            totalAmount = totalAmount.add(item.getSubtotal());
        }
        
        response.setTotalAmount(totalAmount);
        response.setTotalItems(cart.getCartItems().size());
        
        response.setItems(cart.getCartItems().stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList()));
        
        return response;
    }

    private CartItemResponse mapToCartItemResponse(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(item.getId());
        response.setBookId(item.getBook().getId());
        response.setBookTitle(item.getBook().getTitle());
        response.setAuthor(item.getBook().getAuthor());
        response.setCoverImageUrl(item.getBook().getCoverImageUrl());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setSubtotal(item.getSubtotal());
        return response;
    }
}