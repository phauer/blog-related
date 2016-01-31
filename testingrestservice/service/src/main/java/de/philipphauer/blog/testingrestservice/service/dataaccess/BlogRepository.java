package de.philipphauer.blog.testingrestservice.service.dataaccess;

import de.philipphauer.blog.testingrestservice.service.dataaccess.entities.BlogEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<BlogEntity, Long> {

}