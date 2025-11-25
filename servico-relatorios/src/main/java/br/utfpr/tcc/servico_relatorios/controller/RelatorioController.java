// --- COLE ISTO EM: src/main/java/br/utfpr/tcc/servico_relatorios/controller/RelatorioController.java ---

package br.utfpr.tcc.servico_relatorios.controller;

import br.utfpr.tcc.servico_relatorios.model.Relatorio;
import br.utfpr.tcc.servico_relatorios.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
// A ANOTAÇÃO @CrossOrigin FOI REMOVIDA DESTA LINHA
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    /**
     * Endpoint para CRIAR um novo relatório.
     * Mapeia para: POST http://localhost:8080/api/relatorios
     */
    @PostMapping
    public ResponseEntity<Relatorio> criarRelatorio(@RequestBody Relatorio relatorio) {
        Relatorio relatorioSalvo = relatorioRepository.save(relatorio);
        return ResponseEntity.ok(relatorioSalvo);
    }

    /**
     * Endpoint para LER (listar) todos os relatórios.
     * Mapeia para: GET http://localhost:8080/api/relatorios
     */
    @GetMapping
    public ResponseEntity<List<Relatorio>> listarRelatorios() {
        List<Relatorio> relatorios = relatorioRepository.findAll();
        return ResponseEntity.ok(relatorios);
    }

    /**
     * Endpoint para LER (buscar) um único relatório pelo seu ID.
     * Mapeia para: GET http://localhost:8080/api/relatorios/1 (ou 2, 3, etc.)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarRelatorioPorId(@PathVariable Long id) {
        return relatorioRepository.findById(id)
                .map(relatorio -> ResponseEntity.ok(relatorio))
                .orElse(ResponseEntity.notFound().build());
    }
}