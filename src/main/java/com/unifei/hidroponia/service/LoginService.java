package com.unifei.hidroponia.service;

import com.unifei.hidroponia.repository.UsuarioRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public String verificarLogin(String jsonMessage) {
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            String email = obj.getString("username");
            String senha = obj.getString("password");
            
            Object[] result = usuarioRepository.findUserLoginInfo(email, senha);
            
            if (result != null && result.length > 0) {
                String cliId = result[0].toString();
                String equipamentoId = result[1].toString();
                String ambId = result[2].toString();
                
                String response = String.format(
                    "{\"cli_id\": \"%s\", \"equipamento\": \"%s\", \"amb_id\": \"%s\"}", 
                    cliId, equipamentoId, ambId
                );
                
                logger.info("Login bem-sucedido para usuário: {}", email);
                return response;
            } else {
                logger.warn("Falha no login para usuário: {}", email);
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Erro ao verificar login: {}", e.getMessage());
            return null;
        }
    }
}
