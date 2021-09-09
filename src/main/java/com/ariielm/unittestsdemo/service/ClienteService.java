package com.ariielm.unittestsdemo.service;

import com.ariielm.unittestsdemo.domain.Cliente;
import com.ariielm.unittestsdemo.repository.ClienteRepository;
import com.ariielm.unittestsdemo.service.dto.ClienteDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }


    public String nomeDoCliente() {
        return "NOME_DO_CLIENTE";
    }


    public String adicionaSilvaAoNome(String nome) {
        return nome.concat(" Silva");
    }


    public String juntaNomeESobrenome(String nome, String sobrenome) {
        return nome.concat(" ").concat(sobrenome);
    }


    public Cliente buscaClientePeloId(String id) {
        Cliente cliente = repository.buscaClientePeloId(id);
        return cliente;
    }


    public String buscaNomeDoClientePeloId(String id) {
        return repository.buscaNomeDoClientePeloId(id);
    }


    public String buscaNomeESobrenomeDoClientePeloId(String id) {
        Cliente cliente = repository.buscaClientePeloId(id);

        return cliente.getNome().concat(" ").concat(cliente.getSobrenome());
    }


    public void salvaCliente(Cliente cliente) {
        repository.salvaCliente(cliente);
    }


    public void atualizaClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            repository.atualizaCliente(cliente);
        }
    }




    public void salvaClienteDTO(ClienteDTO clienteDTO) {
        var cliente = new Cliente();
        cliente.setId(this.geraIdDoCliente());
        cliente.setNome(clienteDTO.getNome());
        cliente.setSobrenome(clienteDTO.getSobrenome());

        repository.salvaCliente(cliente);
    }

    private String geraIdDoCliente() {
        return UUID.randomUUID().toString();
    }

}
