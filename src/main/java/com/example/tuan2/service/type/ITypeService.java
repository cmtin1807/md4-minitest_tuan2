package com.example.tuan2.service.type;

import com.example.tuan2.model.Type;
import com.example.tuan2.service.IGenerateService;


public interface ITypeService extends IGenerateService<Type> {
    void deleteType(Long id);


}
