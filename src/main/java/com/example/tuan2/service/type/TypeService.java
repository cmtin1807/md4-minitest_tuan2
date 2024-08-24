package com.example.tuan2.service.type;

import com.example.tuan2.model.Type;
import com.example.tuan2.repository.ITypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TypeService implements ITypeService {
    private ITypeRepository repository;
    public TypeService(ITypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Type> findAll() {
        return repository.findAll();

    }

    @Override
    public Optional<Type> findById(Long id) {
return repository.findById(id);    }

    @Override
    public void save(Type type) {

        repository.save(type);
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public void deleteType(Long id) {
        repository.deleteType(id);
    }
}
