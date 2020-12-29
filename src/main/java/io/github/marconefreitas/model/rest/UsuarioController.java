package io.github.marconefreitas.model.rest;

import io.github.marconefreitas.model.entity.Usuario;
import io.github.marconefreitas.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        repo.save(usuario);

    }
}
