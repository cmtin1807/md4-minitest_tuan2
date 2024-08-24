package com.example.tuan2.controller;


import com.example.tuan2.model.Type;
import com.example.tuan2.service.post.IPostService;
import com.example.tuan2.service.type.ITypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("types")
public class TypeController {
    private final IPostService postService;
    private final ITypeService typeService;

    public TypeController(IPostService postService, ITypeService typeService) {
        this.postService = postService;
        this.typeService = typeService;
    }

    @GetMapping
    public ModelAndView listCategory() {
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("/type/index");
        Iterable<Type> types = typeService.findAll();
        modelAndView.addObject("types", types);
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        typeService.deleteType(id);
        return "redirect:/types";
    }

}
