package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();

    public void addItem(SanPham sanPham, int quantity) {
        if (items.containsKey(sanPham.getMaSanPham())) {
            CartItem existingItem = items.get(sanPham.getMaSanPham());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.put(sanPham.getMaSanPham(), new CartItem(sanPham, quantity));
        }
    }

    public void updateItem(int productId, int quantity) {
        if (items.containsKey(productId)) {
            CartItem item = items.get(productId);
            item.setQuantity(quantity);
            if (quantity <= 0) {
                items.remove(productId);
            }
        }
    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public double getTotalPrice() {
        return items.values().stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }
}
