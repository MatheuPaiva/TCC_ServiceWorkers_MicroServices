package br.utfpr.tcc.servico_relatorios.repository;

import br.utfpr.tcc.servico_relatorios.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    
   
}