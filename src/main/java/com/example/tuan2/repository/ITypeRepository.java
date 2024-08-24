package com.example.tuan2.repository;

import com.example.tuan2.model.Type;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ITypeRepository extends CrudRepository<Type, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "call deleteType(:id)")
    void deleteType(@Param("id") Long id);


}
