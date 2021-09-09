package com.ariielm.unittestsdemo.service;

import com.ariielm.unittestsdemo.domain.Cliente;
import com.ariielm.unittestsdemo.repository.ClienteRepository;
import com.ariielm.unittestsdemo.service.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;

    private ClienteService service;

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @BeforeEach
    public void init() {
        service = new ClienteService(repository);
    }

    @Test
    void nomeDoCliente() {
        assertEquals(service.nomeDoCliente(), "NOME_DO_CLIENTE");
    }

    @Test
    void adicionaSilvaAoNome() {
        assertEquals(service.adicionaSilvaAoNome("Ariel"), "Ariel Silva");
    }

    @Test
    void juntaNomeESobrenome() {
        assertEquals(service.juntaNomeESobrenome("Ariel", "Molina"), "Ariel Molina");
    }

    @Test
    void buscaClientePeloId() {
        Cliente cliente = new Cliente("um-id-qualquer", "nome", "sobrenome");
        when(repository.buscaClientePeloId("um-id-qualquer")).thenReturn(cliente);

        assertEquals(cliente, service.buscaClientePeloId("um-id-qualquer"));
    }

    @Test
    void buscaNomeDoClientePeloId() {
        when(repository.buscaNomeDoClientePeloId("abc")).thenReturn("def");

        assertEquals("def", service.buscaNomeDoClientePeloId("abc"));
    }

    @Test
    void buscaNomeESobrenomeDoClientePeloId() {
        Cliente cliente = new Cliente("um-id-qualquer", "nome", "sobrenome");
        when(repository.buscaClientePeloId("um-id-qualquer")).thenReturn(cliente);

        assertEquals("nome sobrenome", service.buscaNomeESobrenomeDoClientePeloId("um-id-qualquer"));
    }

    @Test
    void salvaCliente() {
        Cliente cliente = new Cliente("um-id-qualquer", "nome", "sobrenome");

        service.salvaCliente(cliente);

        verify(repository).salvaCliente(cliente);
    }

    @Test
    void atualizaClientes() {
        Cliente cliente = new Cliente("um-id-qualquer", "nome", "sobrenome");
        Cliente outroCliente = new Cliente("um-outro-id", "outro nome", "outro sobrenome");
//        Cliente maisOutroCliente = new Cliente("mais-outro-id", "mais outro nome", "mais outro sobrenome");

        doNothing().when(repository).atualizaCliente(cliente);
        doNothing().when(repository).atualizaCliente(outroCliente);
//        doNothing().when(repository).atualizaCliente(any(Cliente.class));

        service.atualizaClientes(List.of(cliente, outroCliente));

//        verify(repository, times(3)).atualizaCliente(any(Cliente.class));
    }

    @Test
    void salvaClienteDTO() {
        var clienteDTO = new ClienteDTO("um-id-qualquer", "nome", "sobrenome");

        service.salvaClienteDTO(clienteDTO);

        verify(repository).salvaCliente(clienteCaptor.capture());

        var cliente = clienteCaptor.getValue();

        assertTrue(isValidUUID(cliente.getId()));
        assertEquals("nome", cliente.getNome());
        assertEquals("sobrenome", cliente.getSobrenome());
    }

    private static boolean isValidUUID(String str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }
}