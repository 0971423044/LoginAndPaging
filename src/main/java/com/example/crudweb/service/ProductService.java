package com.example.crudweb.service;

import com.example.crudweb.entity.Product;
import com.example.crudweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;
    public Page<Product> listAll(int pageNumber, String sortField, String sortDir, String keyword)
    {
        Sort sort = Sort.by("name");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber-1,5);
        if(keyword!=null)
        {
            return repo.findALL(keyword, pageable);
        }
        return repo.findAll(pageable);
    }
    public void save(Product product)
    {
         repo.save(product);
    }

    public Product get(Long id)
    {
        return  repo.findById(id).get();
    }
    public void delete(Long id)
    {
        repo.deleteById(id);
    }
}
