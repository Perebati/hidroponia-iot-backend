package com.unifei.hidroponia.repository;

import com.unifei.hidroponia.entity.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {
    
    @Query("SELECT a FROM Ambiente a WHERE a.ambId = :ambId " +
           "AND a.horaRegistro > :horaInicial AND a.horaRegistro < :horaFinal " +
           "AND a.dataRegistro = :dataRef")
    List<Ambiente> findByAmbIdAndTimeRange(@Param("ambId") Integer ambId,
                                          @Param("horaInicial") LocalTime horaInicial,
                                          @Param("horaFinal") LocalTime horaFinal,
                                          @Param("dataRef") LocalDate dataRef);
}
