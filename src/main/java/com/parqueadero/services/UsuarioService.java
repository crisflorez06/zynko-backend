package com.parqueadero.services;

import com.parqueadero.dtos.usuarios.UsuarioLogin;
import com.parqueadero.models.Usuario;
import com.parqueadero.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurnoIslaService turnoIslaService;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setCedula(usuarioDetails.getCedula());
            return usuarioRepository.save(usuario);
        });
    }

    @Transactional
    public Optional<Usuario> login(UsuarioLogin usuario) {
        Usuario usuarioModel = usuarioRepository.findByNombreAndCedula(usuario.getNombre(), usuario.getCedula());

        if (usuarioModel != null) {
            if (Objects.isNull(usuarioModel.getFechaInicioSesion())) {
                usuarioModel.setFechaInicioSesion(LocalDateTime.now());
                //al momento de iniciar turno creamos un objeto con los datos necesarios
                //lo creamos dentro de este if por si ya habia iniciado turno no me cree un nuevo objeto
                this.turnoIslaService.crearNuevoTurno(usuarioModel);
            }

            usuarioRepository.save(usuarioModel);
            return Optional.of(usuarioModel);
        }
        return Optional.empty();
    }

    public Usuario eliminarInicioSesion(Usuario usuario) {

        usuario.setFechaInicioSesion(null);
        turnoIslaService.cerrarTurnoIsla();
        return usuarioRepository.save(usuario);
    }

    public Boolean masInicioSesion(String cedula) {
        return usuarioRepository.existsByCedulaNotAndFechaInicioSesionIsNotNull(cedula);
    }

}
