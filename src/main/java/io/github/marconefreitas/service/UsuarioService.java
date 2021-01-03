package io.github.marconefreitas.service;

import io.github.marconefreitas.model.entity.Usuario;
import io.github.marconefreitas.model.repository.UsuarioRepository;
import io.github.marconefreitas.exception.UsuarioCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    public Usuario salvar(Usuario us){
        boolean existeUser = repo.existsByUsername(us.getUsername());
        if (existeUser) {
            throw  new UsuarioCadastradoException(us.getUsername());
        }
        return repo.save(us);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario us =  repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login não encontrado"));
        return User.builder()
                .username(us.getUsername())
                .password(us.getPassword())
                .roles("USER")
                .build();
    }
}
