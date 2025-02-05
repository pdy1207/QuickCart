package com.company.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

/*
    Lombok X
    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
*/

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(@RequestParam Map<String, String> formDate) {

        String title = formDate.get("title");
        Integer price = Integer.parseInt(formDate.get("price"));

        Item item = new Item(title,price);

        itemRepository.save(item);

        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String addPost(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if( result.isPresent() ){
            model.addAttribute("item", result.get());
        } else {
            model.addAttribute("errorMessage", "해당 아이템을 찾을 수 없습니다.");
        }

        return "detail.html";
    }

}
