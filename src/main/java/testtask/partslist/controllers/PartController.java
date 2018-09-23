package testtask.partslist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import testtask.partslist.entities.Part;
import testtask.partslist.repositories.PartRepository;

import java.util.List;

@Controller
public class PartController {
    private final static String DEFAULT_PAGE = "0";
    private final static String DEFAULT_FILTER = "all";
    private final static String DEFAULT_SEARCH = "";
    private final static String DEFAULT_ERROR = "";

    private final static int DEFAULT_PAGE_SIZE = 10;

    private final static String NAME_VALIDATION_PATTERN = "^[^<>%$&*^:;?'\"`\\/]+$";
    private final static int DEFAULT_NAME_LENGTH = 40;

    @Autowired
    private PartRepository partRepository;

    @GetMapping("/")
    public String showMainPage(Model model,
                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(defaultValue = DEFAULT_FILTER) String filter,
                               @RequestParam(defaultValue = DEFAULT_SEARCH) String search,
                               @RequestParam(defaultValue = DEFAULT_ERROR) String error) {
        Page<Part> queryResult = null;
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        String trimmedSearch = search.trim();

        switch (filter) {
            case "required":
                if (trimmedSearch.isEmpty())
                    queryResult = partRepository
                            .findAllByNeedIsTrue(pageable);
                else
                    queryResult = partRepository
                            .findAllByNeedIsTrueAndNameContainingIgnoreCase(
                                    trimmedSearch, pageable);
                break;
            case "optional":
                if (trimmedSearch.isEmpty())
                    queryResult = partRepository
                            .findAllByNeedIsFalse(pageable);
                else
                    queryResult = partRepository
                            .findAllByNeedIsFalseAndNameContainingIgnoreCase(
                                    trimmedSearch, pageable);
                break;
            default:
                if (trimmedSearch.isEmpty())
                    queryResult = partRepository.findAll(pageable);
                else
                    queryResult = partRepository
                            .findAllByNameContainingIgnoreCase(
                            search, pageable);
        }

        model.addAttribute("parts", queryResult);
        model.addAttribute("current_page", page);
        model.addAttribute("current_filter", filter);
        model.addAttribute("current_search", search);
        model.addAttribute("computers", getComputersCount());
        model.addAttribute("error", error);

        return "index";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id,
                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                               @RequestParam(defaultValue = DEFAULT_FILTER) String filter) {
        model.addAttribute("current_part", partRepository.getOne(id));
        model.addAttribute("current_page", page);
        model.addAttribute("current_filter", filter);

        return "edit";
    }

    @GetMapping("/add")
    public String showAddNewPage(Model model,
                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_FILTER) String filter) {
        model.addAttribute("current_page", page);
        model.addAttribute("current_filter", filter);

        return "add";
    }

    @PostMapping("/save")
    public String save(Part part) {
        part.setName(part.getName().trim());
        if (!validatePart(part))
            return "redirect:/?error=save_incorrect";

        if (part.getId() == -1 && partRepository.existsByName(part.getName()))
            return "redirect:/?error=save_part_exists";

        try {
            partRepository.save(part);
        } catch (DataAccessException exp) {
            return "redirect:/?error=save_fail";
        }

        return "redirect:/?error=save_completed";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam int id) {
        if (!partRepository.existsById(id))
            return "redirect:/?error=delete_part_not_exists";
        try {
            partRepository.deleteById(id);
        } catch (DataAccessException exp) {
            return "redirect:/?error=delete_fail";
        }

        return "redirect:/?error=delete_completed";
    }

    @PostMapping("/find")
    @ResponseBody
    public boolean findOne(@RequestParam String name) {
        return validatePartName(name) && partRepository.existsByName(name);
    }

    private int getComputersCount() {
        List<Part> parts = partRepository.findAllByNeedIsTrue();
        if (parts.isEmpty())
            return 0;
        int result = parts.get(0).getCount();
        for (Part part : parts) {
            if (part.getCount() < result)
                result = part.getCount();
        }

        return result;
    }

    private boolean validatePart(Part part) {
        if (part.getCount() < 0 || !validatePartName(part.getName()))
            return false;
        return true;
    }

    private boolean validatePartName(String name) {
        if (name == null || name.isEmpty()
                || name.length() > DEFAULT_NAME_LENGTH
                || !name.matches(NAME_VALIDATION_PATTERN))
            return false;
        return true;
    }
}
