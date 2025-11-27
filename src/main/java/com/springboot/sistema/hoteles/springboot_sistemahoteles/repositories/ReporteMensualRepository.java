package com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.ReporteMensual;

public interface ReporteMensualRepository extends JpaRepository<ReporteMensual, Long>{
    List<ReporteMensual> findByYearOrderByCreatedAtDesc(Integer year);

    List<ReporteMensual> findByYearAndMonthOrderByCreatedAtDesc(Integer year, Integer month);

    @Query("SELECT r FROM ReporteMensual r ORDER BY r.year ASC, r.month ASC, r.createdAt ASC")
    List<ReporteMensual> findAllOrderByFecha(); 
}
