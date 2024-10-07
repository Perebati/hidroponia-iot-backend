package com.unifei.hidroponia.service;

import com.unifei.hidroponia.entity.Ambiente;
import com.unifei.hidroponia.repository.AmbienteRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {
    
    private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);
    
    @Autowired
    private AmbienteRepository ambienteRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public String gerarRelatorio(String jsonMessage) {
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            
            String cliId = obj.getString("cli_id");
            Integer ambId = Integer.valueOf(obj.getString("amb_id"));
            String horaInicial = obj.getString("hora_inicial");
            String horaFinal = obj.getString("hora_final");
            String dataRef = obj.getString("data_ref");
            
            // Converter strings para objetos de data/hora
            LocalTime horaIni = LocalTime.parse(horaInicial);
            LocalTime horaFim = LocalTime.parse(horaFinal);
            LocalDate data = LocalDate.parse(dataRef, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            // Buscar dados do ambiente
            List<Ambiente> dados = ambienteRepository.findByAmbIdAndTimeRange(ambId, horaIni, horaFim, data);
            
            if (dados.isEmpty()) {
                logger.warn("Nenhum dado encontrado para o relatório");
                return null;
            }
            
            // Extrair listas de dados
            List<Integer> temperaturas = dados.stream()
                .map(Ambiente::getTempAtual)
                .collect(Collectors.toList());
                
            List<String> phs = dados.stream()
                .map(ambiente -> ambiente.getPhAtual().toString())
                .collect(Collectors.toList());
                
            List<Integer> luminosidades = dados.stream()
                .map(Ambiente::getLumAtual)
                .collect(Collectors.toList());
            
            // Registrar solicitação do relatório
            registrarSolicitacaoRelatorio(cliId, ambId);
            
            // Montar resposta JSON
            String response = String.format(
                "{\"tempAtual\": %s, \"phAtual\": %s, \"lumAtual\": %s}",
                temperaturas.toString(),
                phs.toString(),
                luminosidades.toString()
            );
            
            logger.info("Relatório gerado com sucesso para ambiente {}", ambId);
            return response;
            
        } catch (Exception e) {
            logger.error("Erro ao gerar relatório: {}", e.getMessage());
            return null;
        }
    }
    
    private void registrarSolicitacaoRelatorio(String cliId, Integer ambId) {
        try {
            LocalDate hoje = LocalDate.now();
            LocalTime agora = LocalTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            String sql = "INSERT INTO relatorio_log (cli_id, amb_id, dataRegistro, horaRegistro) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, cliId, ambId, hoje.format(dateFormatter), agora.format(timeFormatter));
            
        } catch (Exception e) {
            logger.error("Erro ao registrar solicitação de relatório: {}", e.getMessage());
        }
    }
}
