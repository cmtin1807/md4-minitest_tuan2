package com.example.tuan2.controller;



import com.example.tuan2.model.Post;
import com.example.tuan2.model.PostForm;
import com.example.tuan2.model.Type;
import com.example.tuan2.service.post.IPostService;
import com.example.tuan2.service.type.ITypeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller()
@RequestMapping("/posts")
@PropertySource("classpath:upload_file.properties")
public class PostController {
    private final IPostService postService;
    private final ITypeService typeService;

    public PostController(IPostService postService, ITypeService typeService) {
        this.postService = postService;
        this.typeService = typeService;
    }
    @ModelAttribute("types")
    public Iterable<Type> ListType() {
        return typeService.findAll();
    }


    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        return "create";
    }

    @Value("${file-upload}")
    private String upload;

    @PostMapping("/save")
    public String save(PostForm postForm , RedirectAttributes redirectAttributes) {
        MultipartFile multipartFile = postForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
        post.setContent(postForm.getContent());
        post.setImage(fileName);
        post.setType(postForm.getType());
        postService.save(post);
        redirectAttributes.addFlashAttribute("success", "Post created successfully");
        return "redirect:/posts/";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/update");
            modelAndView.addObject("post", post.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }


    @PostMapping("/update")
    public String update(PostForm postForm, RedirectAttributes redirectAttributes) {
        MultipartFile multipartFile = postForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(),new File(upload+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Post post = postService.findById(postForm.getId()).get();
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
        post.setContent(postForm.getContent());
        post.setImage(fileName);
        postService.save(post);
        redirectAttributes.addFlashAttribute("success", "Post updated successfully");
        return "redirect:/posts/";
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteForm(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("post", post.get());
        return modelAndView;

    }

    @PostMapping("/delete")
    public String delete(Post post, RedirectAttributes redirectAttributes) {
        postService.delete(post.getId());
        redirectAttributes.addFlashAttribute("success", "Post deleted successfully");
        return "redirect:/posts/";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id).get());
        return "view";
    }





}
