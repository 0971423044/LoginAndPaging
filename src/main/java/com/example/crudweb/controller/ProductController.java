package com.example.crudweb.controller;

import com.example.crudweb.entity.Product;
import com.example.crudweb.service.ProductService;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String homePage(Model theModel)
    {
        String keyword =" ";
        return listByPage(theModel,1,"name","asc",keyword);
    }
    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model theModel,
                             @PathVariable(name = "pageNumber") int currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword)
    {
        Page<Product> page = service.listAll(currentPage, sortField,sortDir,keyword);
        List<Product> listProducts = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        theModel.addAttribute("sortField",sortField);
        theModel.addAttribute("sortDir",sortDir);
        theModel.addAttribute("currentPage",currentPage);
        theModel.addAttribute("totalItems", totalItems);
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("listProducts",listProducts);
        String reverseSortDir = sortDir.equals("asc")? "desc" : "asc";
        theModel.addAttribute("reverseSortDir",reverseSortDir);
        theModel.addAttribute("keyword", keyword);
        return "index";
    }
    @RequestMapping("/new")
    public String showNewProduct(Model theModel)
    {
        Product product = new Product();
        theModel.addAttribute("product",product);
        return "new_product";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product)
    {
        service.save(product);

        return "redirect:/";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showNewProduct(@PathVariable(name = "id") Long id)
    {
        ModelAndView mav = new ModelAndView("edit_product");
        Product product = service.get(id);
        mav.addObject("product", product);

        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id)
    {
        service.delete(id);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String showLloginPage()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
        {
            return "login";
        }
        return "redirect:/";
    }
}
