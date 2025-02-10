package com.company.shop.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    private final ItemService itemService;
    private final S3Service s3Service;

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

    @GetMapping("/list/page/{page}")
    String getListPage(Model model, @PathVariable Integer page) {
        Slice<Item> result = itemRepository.findPageBy(PageRequest.of(page-1,5));
//        result.hasNext();
//        result.getTotalPages(); slice 시 안알랴줌
        model.addAttribute("items", result);
        return "list.html";
    }


    @GetMapping("/write")
    String write() { return "write.html"; }

    @PostMapping("/add")
    String addPost(@RequestParam Map<String, String> formData, Authentication auth) {
        // formData는 Immutable Map이므로 새로 HashMap을 생성
        Map<String, String> updatedFormData = new HashMap<>(formData);

        if (auth.isAuthenticated()) {
            updatedFormData.put("username", auth.getName()); // 로그인한 유저의 이름 추가
        }

        itemService.saveItem(updatedFormData); // 변경된 Map을 서비스에 전달
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

    @GetMapping("/del")
    String delItem(@RequestParam Item id) {
        itemRepository.delete(id);  // id로 삭제
        return "redirect:/list";  // 삭제 후 목록으로 리다이렉트
    }


    @GetMapping("/test2")
    String deleteItem() {
        var result = new BCryptPasswordEncoder().encode("문자");
        System.out.println(result);
        return "redirect:/list";  // 삭제 후 목록으로 리다이렉트
    }


    @GetMapping("/presigned-url")
    @ResponseBody
    String getListPage(@RequestParam String filename) {
        var result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println(result);
        return result;
    }


}
