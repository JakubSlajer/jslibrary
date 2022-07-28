package com.etnetera.jslibrary.repository;

import com.etnetera.jslibrary.domain.Framework;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FrameworkRepository extends MongoRepository<Framework, String> {

}
