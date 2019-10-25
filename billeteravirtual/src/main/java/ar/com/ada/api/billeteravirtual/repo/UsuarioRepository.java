package ar.com.ada.api.billeteravirtual.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.billeteravirtual.entities.*;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUserName (String userName);

   Usuario findByUserEmail (String userEmail);
   //Usuario findByUserName (String userName);
    
}