package br.utfpr.tcc.servico_auth.config;

import br.utfpr.tcc.servico_auth.model.Usuario;
import br.utfpr.tcc.servico_auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Garante o Admin
            criarUsuarioSeNaoExistir(repository, passwordEncoder, "admin@siga.com", "Administrador", "ADMIN");

            // 2. Cria 50 Técnicos para o Teste de Carga
            System.out.println("Gerando massa de dados para teste...");
            for (int i = 1; i <= 50; i++) {
                String email = "tecnico" + i + "@siga.com"; // tecnico1@siga.com, tecnico2@siga.com...
                criarUsuarioSeNaoExistir(repository, passwordEncoder, email, "Técnico de Campo " + i, "TECNICO");
            }
            System.out.println("=== BANCO DE DADOS POPULADO COM 50 TÉCNICOS! ===");
        };
    }

    private void criarUsuarioSeNaoExistir(UsuarioRepository repo, PasswordEncoder encoder, String email, String nome, String perfil) {
        if (repo.findByEmail(email).isEmpty()) {
            Usuario user = new Usuario();
            user.setNome(nome);
            user.setEmail(email);
            user.setSenha(encoder.encode("123456")); // Todos com a mesma senha para facilitar o script
            user.setPerfil(perfil);
            repo.save(user);
        }
    }
}