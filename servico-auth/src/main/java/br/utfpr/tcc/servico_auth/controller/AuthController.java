package br.utfpr.tcc.servico_auth.controller;

import br.utfpr.tcc.servico_auth.model.Usuario;
import br.utfpr.tcc.servico_auth.repository.UsuarioRepository;
import br.utfpr.tcc.servico_auth.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        Optional<Usuario> userOpt = repository.findByEmail(data.email());

        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            if (passwordEncoder.matches(data.senha(), user.getSenha())) {
                String token = tokenProvider.generateToken(user.getEmail());
                return ResponseEntity.ok(new LoginResponse(token, user.getNome(), user.getEmail()));
            }
        }
        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }

    // Classes auxiliares (DTOs)
    public record LoginRequest(String email, String senha) {}
    public record LoginResponse(String token, String nome, String email) {}
}