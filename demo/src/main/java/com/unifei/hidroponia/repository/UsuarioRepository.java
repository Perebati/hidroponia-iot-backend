package com.unifei.hidroponia.repository;

import com.unifei.hidroponia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
    Optional<Usuario> findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
    
    @Query(value = "SELECT c.cli_id, e.equi_id, a.amb_id " +
                   "FROM usuario u, cliente c, equipamento e, ambiente_cli a " +
                   "WHERE u.user_id = c.user_key AND " +
                   "u.email = :email AND u.senha = :senha AND " +
                   "e.cli_id = c.cli_id AND a.ambCli_id = c.cli_id", 
           nativeQuery = true)
    Object[] findUserLoginInfo(@Param("email") String email, @Param("senha") String senha);
}
