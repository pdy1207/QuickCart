package com.company.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    private final ItemService itemService;

/*
    Lombok X
    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
*/

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemService.findAllList();
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(@RequestParam Map<String, String> formDate) {

        itemService.saveItem(formDate);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String findByDetail(@PathVariable Long id, Model model)  {

        Optional<Item> result = itemService.findByIdList(id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
        } else {
            model.addAttribute("errorMessage", "해당 아이템을 찾을 수 없습니다.");
        }

        return "detail.html";
    }

    @GetMapping("/edit/{id}")
    String editByDetail(@PathVariable Long id, Model model) {
        Optional<Item> result = itemService.findByIdList(id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
        } else {
            model.addAttribute("errorMessage", "해당 아이템을 찾을 수 없습니다.");
        }

        return "edit.html";
    }

    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, @ModelAttribute Item item) {
        itemService.editItem(id,item);
        return "redirect:/list";
    }




}
