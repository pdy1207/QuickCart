package com.company.shop.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Map<String, String> formData) {
        if (formData == null || !formData.containsKey("title") || !formData.containsKey("price")) {
            throw new IllegalArgumentException("필수 값(title, price)이 누락되었습니다.");
        }

        String title = formData.get("title");
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("title 값이 비어있습니다.");
        }

        int price;
        try {
            price = Integer.parseInt(formData.get("price"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("price 값은 숫자여야 합니다.");
        }

        String username = formData.get("username");

        itemRepository.save(new Item(title, price,username));
    }

    public List<Item> findAllList() {
        return itemRepository.findAll();
    }

    public Optional<Item> findByIdList(Long id){
        return itemRepository.findById(id);
    }

    public void editItem(@PathVariable Long id, @ModelAttribute Item item){
        Item editData = new Item();
        if (item.getTitle().length() >= 100 || item.getPrice() < 0) {
            throw new IllegalArgumentException("제목은 100자 이하이어야 하며, 가격은 0 이상이어야 합니다..");
        }
        editData.setId(item.getId());
        editData.setTitle(item.getTitle());
        editData.setPrice(item.getPrice());
        itemRepository.save(editData);
    }


}