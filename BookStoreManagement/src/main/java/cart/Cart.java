/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import dto.BookDTO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
//Quang Vinh >>>>>>>>>>
public class Cart {

    private Map<String, BookDTO> cart;

    public Cart() {
    }

    public Cart(Map<String, BookDTO> cart) {
        this.cart = cart;
    }

    //Lấy giỏ hàng
    public Map<String, BookDTO> getCart() {
        return cart;
    }

    //Thiết đặt giỏ hàng
    public void setCart(Map<String, BookDTO> cart) {
        this.cart = cart;
    }

    //Thêm sản phẩm mới vào giỏ hàng
    public boolean add(BookDTO book) {
        boolean check = false;
        if (this.cart == null) {
            this.cart = new HashMap<>();

        }
        if (this.cart.containsKey(book.getIsbn())) {
            int currentQuantity = this.cart.get(book.getIsbn()).getQuantity();
            book.setQuantity(currentQuantity + book.getQuantity());
        }
        this.cart.put(book.getIsbn(), book);
        check = true;
        return check;
    }

    //Xóa sản phẩm khỏi giỏ hàng
    public boolean remove(String isbn) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(isbn)) {
                this.cart.remove(isbn);
                check = true;
            }
        }
        return check;
    }

    //Chỉnh sửa số lượng sản phẩm
    public boolean edit(String isbn, BookDTO book) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(isbn)) {
                this.cart.replace(isbn, book);
                check = true;
            }
        }
        return check;
    }

    //Lấy tổng thành tiền của giỏ hàng
    public double getTotalMoney() {
        double total = 0;
        for (BookDTO product : cart.values()) {
            total += product.getQuantity() * product.getPrice();
        }
        return total;
    }

    //Xóa tất cả sản phẩm trong giỏ hàng
    public boolean removeAll() {
        boolean check = false;
        if (this.cart != null) {
            this.cart.clear();
            check = true;
        }
        return check;
    }
}
//<<<<<<<<<<