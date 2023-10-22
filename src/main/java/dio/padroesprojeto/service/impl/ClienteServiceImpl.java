package dio.padroesprojeto.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dio.padroesprojeto.model.Cliente;
import dio.padroesprojeto.model.ClienteRepository;
import dio.padroesprojeto.model.Endereco;
import dio.padroesprojeto.model.EnderecoRepository;
import dio.padroesprojeto.service.ClienteService;
import dio.padroesprojeto.service.ViaCepService;
@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
 private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	@Override
	public Iterable<Cliente> buscarTodos() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}
	
	@Override
	public Cliente buscarPorId(Long id) {
		// TODO Auto-generated method stub
		Optional <Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}
	

	@Override
	public void inserir(Cliente cliente) {
	salvarClienteComCep(cliente);
	}



	@Override
	public void atualizar(Long id, Cliente cliente) {
		// TODO Auto-generated method stub
		Optional <Cliente> clienteBancoDeDados = clienteRepository.findById(id);
		if(clienteBancoDeDados.isPresent()) {
			salvarClienteComCep(cliente);
		}
		
	}

	@Override
	public void deletar(Long id) {
		// TODO Auto-generated method stub
		clienteRepository.deleteById(id);
	}
	
	private void salvarClienteComCep(Cliente cliente) {
		String cep= cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(()->{
			Endereco novoEndereco= viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
		return novoEndereco;
		});
		clienteRepository.save(cliente);
	}

	
	

}
