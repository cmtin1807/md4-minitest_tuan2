package com.example.tuan2.controller;


import com.example.tuan2.model.Post;
import com.example.tuan2.model.PostForm;
import com.example.tuan2.model.Type;
import com.example.tuan2.service.post.IPostService;
import com.example.tuan2.service.type.ITypeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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


    @GetMapping
    public ModelAndView findAllPost(@PageableDefault(value = 5) Pageable pageable) {
        Page<Post> posts = postService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("poster/index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView listTittleSearch(@RequestParam("search") Optional<String> search, @PageableDefault(value = 5) Pageable pageable) {
        Page<Post> posts;
        String searchValue = search.orElse("");
        if (!searchValue.isEmpty()) {
            posts = postService.findAllByTitleContaining(pageable, searchValue);
        } else {
            posts = postService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("poster/index");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("search", searchValue);
        return modelAndView;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        return "poster/create";
    }

    @Value("${file-upload}")
    private String upload;

    @PostMapping("/save")
    public String save(PostForm postForm, RedirectAttributes redirectAttributes) {
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
            ModelAndView modelAndView = new ModelAndView("poster/update");
            modelAndView.addObject("post", post.get());
            return modelAndView;
        } else {
            return new ModelAndView("poster/error_404");
        }
    }


    @PostMapping("/update")
    public String update(PostForm postForm, RedirectAttributes redirectAttributes) {
        MultipartFile multipartFile = postForm.getImage();
        String fileName = multipartFile.getOriginalFilename();

        Post post = postService.findById(postForm.getId()).get();
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
        post.setContent(postForm.getContent());
        post.setType(postForm.getType());
        if (multipartFile.isEmpty()) {
            post.setImage(post.getImage());
        } else {
            post.setImage(fileName);
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(upload + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        postService.save(post);
        redirectAttributes.addFlashAttribute("success", "Post updated successfully");
        return "redirect:/posts/";
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteForm(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        ModelAndView modelAndView = new ModelAndView("poster/delete");
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
        return "poster/view";
    }


}
