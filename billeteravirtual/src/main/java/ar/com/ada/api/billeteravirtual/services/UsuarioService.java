package ar.com.ada.api.billeteravirtual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.repo.UsuarioRepository;
import ar.com.ada.api.billeteravirtual.security.Crypto;
import ar.com.ada.api.billeteravirtual.sistema.comms.EmailService;

/**
 * UsuarioService
 */
@Service
public class UsuarioService {

    @Autowired
    EmailService emailService;

    @Autowired
    UsuarioRepository repo;

    @Autowired
    PersonaService personaService;

    @Autowired
    MovimientoService movimientoService;

    public Usuario save(Persona p, String password) {

        Usuario u = new Usuario();
        u.setUserName(p.getEmail());
        u.setUserEmail(p.getEmail());
        u.setPassword(Crypto.encrypt(password, u.getUserEmail()));

        p.setUsuario(u);

        personaService.save(p);

        emailService.SendEmail(u.getUserEmail(),"Bienvenido a la Billetera Virtual ADA!!!", 
            "Hola "+p.getNombre()+"\nBienvenido a este hermoso proyecto hecho por todas las alumnas de ADA Backend 8va Mañana\n"+
            "Ademas te regalamos 100 pesitos" );

        return p.getUsuario();

    }
    public List<Usuario> getUsuarios() {

        return repo.findAll();
    }

    public Usuario buscarPorId(int id) {

        Optional<Usuario> u = repo.findById(id);

        if (u.isPresent())
            return u.get();
        return null;
    }

    public Usuario buscarPorUserName(String userName) {
    return repo.findByUserName(userName);

    }
    public Usuario buscarporUserEmail(String userEmail) {
        return repo.findByUserEmail(userEmail);

    }
    public void login(String userName, String password) {

        Usuario u = repo.findByUserName(userName);

        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getUserName()))) {

            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

    }
}