package de.philipphauer.blog.testingrestservice.service.dataaccess;

import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.BlogEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogRepository extends CrudRepository<BlogEntity, Long> {

    List<BlogEntity> findByName(String name);
}