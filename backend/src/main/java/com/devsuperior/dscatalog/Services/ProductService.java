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
import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.entites.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;

@Service
public class ProductService {

  private String messageError = "Resourcer Not Found";
  private String messageErrorViolation = "Resource violation";

  @Autowired
  private ProductRepository repository;

  @Transactional(readOnly = true)
  public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
    Page<Product> list = repository.findAll(pageRequest);
    return list.map(x -> new ProductDto(x));

  }

  @Transactional(readOnly = true)
  public ProductDto findById(Long id) {
    Optional<Product> obj = repository.findById(id);
    Product entity = obj.orElseThrow(
        () -> new com.devsuperior.dscatalog.Services.Exceptions.EntityNotFoundException(messageError));
    return new ProductDto(entity, entity.getCategories());
  }

  @Transactional
  public ProductDto insert(ProductDto dto) {
    Product entity = new Product();
    // entity.setName(dto.getName());
    entity = repository.save(entity);
    return new ProductDto(entity);
  }

  @Transactional
  public ProductDto update(Long id, ProductDto dto) {
    try {
      Product entity = repository.getReferenceById(id);
      // entity.setName(dto.getName());
      entity = repository.save(entity);
      return new ProductDto(entity);
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
