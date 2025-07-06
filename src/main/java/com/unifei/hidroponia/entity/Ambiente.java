package com.unifei.hidroponia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

@Entity
@Table(name = "ambiente")
public class Ambiente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tempAtual")
    private Integer tempAtual;
    
    @Column(name = "tempMin")
    private Integer tempMin;
    
    @Column(name = "tempMax")
    private Integer tempMax;
    
    @Column(name = "phAtual", precision = 14, scale = 2)
    private BigDecimal phAtual;
    
    @Column(name = "phTarget", precision = 14, scale = 2)
    private BigDecimal phTarget;
    
    @Column(name = "lumAtual")
    private Integer lumAtual;
    
    @Column(name = "amb_id")
    private Integer ambId;
    
    @Column(name = "dataRegistro")
    private LocalDate dataRegistro;
    
    @Column(name = "horaRegistro")
    private LocalTime horaRegistro;
    
    // Constructors
    public Ambiente() {}
    
    public Ambiente(Integer tempAtual, Integer tempMin, Integer tempMax, 
                   BigDecimal phAtual, BigDecimal phTarget, Integer lumAtual,
                   Integer ambId, LocalDate dataRegistro, LocalTime horaRegistro) {
        this.tempAtual = tempAtual;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.phAtual = phAtual;
        this.phTarget = phTarget;
        this.lumAtual = lumAtual;
        this.ambId = ambId;
        this.dataRegistro = dataRegistro;
        this.horaRegistro = horaRegistro;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getTempAtual() { return tempAtual; }
    public void setTempAtual(Integer tempAtual) { this.tempAtual = tempAtual; }
    
    public Integer getTempMin() { return tempMin; }
    public void setTempMin(Integer tempMin) { this.tempMin = tempMin; }
    
    public Integer getTempMax() { return tempMax; }
    public void setTempMax(Integer tempMax) { this.tempMax = tempMax; }
    
    public BigDecimal getPhAtual() { return phAtual; }
    public void setPhAtual(BigDecimal phAtual) { this.phAtual = phAtual; }
    
    public BigDecimal getPhTarget() { return phTarget; }
    public void setPhTarget(BigDecimal phTarget) { this.phTarget = phTarget; }
    
    public Integer getLumAtual() { return lumAtual; }
    public void setLumAtual(Integer lumAtual) { this.lumAtual = lumAtual; }
    
    public Integer getAmbId() { return ambId; }
    public void setAmbId(Integer ambId) { this.ambId = ambId; }
    
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
    
    public LocalTime getHoraRegistro() { return horaRegistro; }
    public void setHoraRegistro(LocalTime horaRegistro) { this.horaRegistro = horaRegistro; }
}
