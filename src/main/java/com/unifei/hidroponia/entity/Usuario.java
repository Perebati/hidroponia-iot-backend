package com.unifei.hidroponia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "nome", length = 128)
    private String nome;
    
    @Column(name = "email", length = 128)
    private String email;
    
    @Column(name = "senha", length = 128)
    private String senha;
    
    // Constructors
    public Usuario() {}
    
    public Usuario(Integer userId, String nome, String email, String senha) {
        this.userId = userId;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    // Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
