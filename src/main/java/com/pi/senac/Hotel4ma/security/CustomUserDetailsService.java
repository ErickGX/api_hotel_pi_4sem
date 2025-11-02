package com.pi.senac.Hotel4ma.security;

import com.pi.senac.Hotel4ma.model.Usuario;
import com.pi.senac.Hotel4ma.repository.AdministradorRepository;
import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import com.pi.senac.Hotel4ma.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministradorRepository administradorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //estragetia para buscar o usuário em múltiplas tabelas
        //e retirar o tipo do usuario junto com sua role
        //login multi-usuario (Administrador, Funcionario, Cliente)
        Usuario usuario = administradorRepository.findByEmail(email)//admin nao precisa verificar ativo
                .map(u -> (Usuario) u)
                .or(() -> funcionarioRepository.findByEmailAndAtivoTrue(email).map(u -> (Usuario) u))
                .or(() -> clienteRepository.findByEmailAndAtivoTrue(email).map(u -> (Usuario) u)) //só buscam contas ativas
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        return new CustomUserDetails(usuario);
    }
}
