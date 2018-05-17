package com.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Users;
import com.dao.UsersRepository;

@Service
@Transactional
public class UsersService {

  @Autowired
  UsersRepository repository;

  public List<Users> selectAll() {
    return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
  }

}