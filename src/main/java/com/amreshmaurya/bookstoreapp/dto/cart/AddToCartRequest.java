package  com.amreshmaurya.bookstoreapp.dto.cart;

import java.util.UUID;

import lombok.Data;

@Data
public class AddToCartRequest {
    private UUID bookId;
    private Integer quantity;
}