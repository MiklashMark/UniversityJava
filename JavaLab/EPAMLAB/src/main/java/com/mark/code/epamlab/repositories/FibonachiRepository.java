package com.mark.code.epamlab.repositories;

import com.mark.code.epamlab.model.Fibonachi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FibonachiRepository extends JpaRepository<Fibonachi,Integer> {}
