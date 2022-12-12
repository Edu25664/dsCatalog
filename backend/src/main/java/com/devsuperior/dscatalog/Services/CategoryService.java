package com.devsuperior.dscatalog.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entites.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
      List<Category> list =  repository.findAll();
      List<CategoryDto> listDTO = new ArrayList<>();
      for (Category cat : list){
        listDTO.add(new CategoryDto(cat));
      }
       return listDTO;
    }
}
