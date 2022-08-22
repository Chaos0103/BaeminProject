package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.delivery.domain.Category;
import project.delivery.dto.StoreDto;
import project.delivery.service.StoreService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public String stores(@RequestParam Category category, Model model) {
        List<StoreDto> storeDtos = storeService.searchStores(category);
        model.addAttribute("storeDtos", storeDtos);
        return "/stores/stores";
    }

    @GetMapping("/{storeId}/detail")
    public String store(@PathVariable Long storeId, Model model) {
        StoreDto storeDto = storeService.detailStore(storeId);
        model.addAttribute("storeDto", storeDto);
        return "/stores/detail";
    }
}
