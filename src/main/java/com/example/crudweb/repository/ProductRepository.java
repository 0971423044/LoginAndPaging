package com.example.crudweb.repository;

import com.example.crudweb.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;



public interface ProductRepository extends PagingAndSortingRepository<Product, Long>
{
        @Query("select p FROM Product p WHERE " + "CONCAT(p.id,' ', p.name,' ', p.brand,' ', p.madein,' ', p.price)" + " LIKE %?1%")
        public Page<Product> findALL(String keyword, Pageable pageable);

}
