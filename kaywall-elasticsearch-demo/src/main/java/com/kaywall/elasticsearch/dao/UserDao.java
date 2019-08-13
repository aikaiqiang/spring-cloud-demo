package com.kaywall.elasticsearch.dao;

import com.kaywall.elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends ElasticsearchCrudRepository<User, Integer> {

}
