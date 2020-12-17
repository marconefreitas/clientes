package io.github.marconefreitas.model.rest;


import io.github.marconefreitas.model.entity.Cliente;
import io.github.marconefreitas.model.entity.ServicoPrestado;
import io.github.marconefreitas.model.repository.ClienteRepository;
import io.github.marconefreitas.model.repository.ServicoPrestadoRepository;
import io.github.marconefreitas.model.rest.dto.ServicoPrestadoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import util.BigDecimalConverter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/servicos-prestados")
public class ServicoPrestadoController {


    private final ClienteRepository cliRepository;

    private final ServicoPrestadoRepository spRepository;

    //private final BigDecimalConverter conv;

    /*public ServicoPrestadoController(ClienteRepository cliRepository, ServicoPrestadoRepository spRepository) {
        this.cliRepository = cliRepository;
        this.spRepository = spRepository;
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid  ServicoPrestadoDTO servicoPrestadoDTO) {
        LocalDate data = LocalDate.parse(servicoPrestadoDTO.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer id = servicoPrestadoDTO.getIdCliente();

        Cliente cli = cliRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente"));

        ServicoPrestado sp = new ServicoPrestado();
        sp.setDescricao(servicoPrestadoDTO.getDescricao());
        sp.setData(data);
        sp.setCliente(cli);
        sp.setValor(BigDecimalConverter.converter(servicoPrestadoDTO.getPreco()));
        spRepository.save(sp);
        return sp;
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(
             @RequestParam(value="nome", required = false) String nome,
             @RequestParam(value="mes", required = false) Integer mes
            ){
        return spRepository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }
}
