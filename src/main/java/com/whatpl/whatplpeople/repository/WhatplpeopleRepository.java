package com.whatpl.whatplpeople.repository;

import com.whatpl.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatplpeopleRepository extends JpaRepository<Project, Long>, WhatplpeopleQueryRepository {
}
