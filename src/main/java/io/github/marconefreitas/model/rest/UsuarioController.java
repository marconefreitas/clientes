package io.github.marconefreitas.model.rest;

import io.github.marconefreitas.exception.UsuarioCadastradoException;
import io.github.marconefreitas.model.entity.Usuario;
import io.github.marconefreitas.model.repository.UsuarioRepository;
import io.github.marconefreitas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {



    private final UsuarioService repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        try{
            repo.salvar(usuario);
        } catch(UsuarioCadastradoException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
}
