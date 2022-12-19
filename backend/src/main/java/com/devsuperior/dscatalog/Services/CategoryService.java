package com.devsuperior.dscatalog.Services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.Services.Exceptions.DataBaseExcpetion;
import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entites.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

  private String messageError = "Resourcer Not Found";
  private String messageErrorViolation = "Resource violation";
  
  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public Page<CategoryDto> findAllPaged(PageRequest pageRequest) {
    Page<Category> list = repository.findAll(pageRequest);
    return list.map(x -> new CategoryDto(x));

  }

  @Transactional(readOnly = true)
  public CategoryDto findById(Long id) {
    Optional<Category> obj = repository.findById(id);
    Category entity = obj.orElseThrow(
        () -> new com.devsuperior.dscatalog.Services.Exceptions.EntityNotFoundException(messageError));
    return new CategoryDto(entity);
  }

  @Transactional
  public CategoryDto insert(CategoryDto dto) {
    Category entity = new Category();
    entity.setNome(dto.getName());
    entity = repository.save(entity);
    return new CategoryDto(entity);
  }

  @Transactional
  public CategoryDto update(Long id, CategoryDto dto) {
    try {
      Category entity = repository.getReferenceById(id);
      entity.setNome(dto.getName());
      entity = repository.save(entity);
      return new CategoryDto(entity);
    } catch (EntityNotFoundException e) {

      throw new EntityNotFoundException(id + messageError);

    }

  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(messageError);
    }

    catch (DataIntegrityViolationException e) {
      throw new DataBaseExcpetion(messageErrorViolation);
    }

  }

}
