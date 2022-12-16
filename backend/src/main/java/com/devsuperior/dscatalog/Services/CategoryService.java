package com.devsuperior.dscatalog.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    List<Category> list = repository.findAll();
    return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());

  }
 
  @Transactional(readOnly = true)
  public CategoryDto findById(Long id) {
    Optional<Category> obj = repository.findById(id);
    Category entity = obj.orElseThrow(() -> new com.devsuperior.dscatalog.Services.Exceptions.EntityNotFoundException("Resourcer Not Found"));
    return new CategoryDto(entity);
  }

  @Transactional
  public CategoryDto insert(CategoryDto dto) {
    Category entity = new Category();
    entity.setNome(dto.getName());
    entity = repository.save(entity);
    return new CategoryDto(entity);
  }

}
