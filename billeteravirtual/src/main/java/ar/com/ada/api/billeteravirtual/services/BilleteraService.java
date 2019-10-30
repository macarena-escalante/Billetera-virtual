package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.entities.Movimiento;
import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.models.request.MovimientoRequest;
import ar.com.ada.api.billeteravirtual.models.response.MovimientoResponse;
import ar.com.ada.api.billeteravirtual.repo.BilleteraRepository;



/**
 * BilleteraService
 */
@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository repo;

    @Autowired
    PersonaService personaService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MovimientoService movimientoService;


    public Billetera crearBilletera (Persona p){

        Billetera b = new Billetera();
        p.setBilletera(b);

        Cuenta c = new Cuenta();
        c.setMoneda("ARS");
        b.agregarCuenta(c);

        repo.save(b);
        
        movimientoService.regalarSaldoInicial(p, c);

        repo.save(b);

        
        return b;
    }
    
    public Billetera buscarPorId(int id) {

        Optional<Billetera> b = repo.findById(id);

        if (b.isPresent())
            return b.get();
        return null;                                                                   
    }
    /*public int transferir(int billeteraOrigen1, int billeteraDestino2, double importe, String concepto ){
        Billetera b1= this.buscarPorId(billeteraOrigen1);
        Billetera b2= this.buscarPorId(billeteraDestino2);
        int mov= movimientoService.movimientoTransferencia(b1, -importe, b1.getCuenta(0), b2.getCuenta(0), concepto); 
        movimientoService.movimientoTransferencia(b2, +importe, b2.getCuenta(0), b1.getCuenta(0), concepto);
        repo.save(b1);
        repo.save(b2);


        return mov;
        
    }*/

    public BigDecimal consultarSaldoDisponible(Billetera b, String moneda) {
        BigDecimal s = new BigDecimal(0);
        for (Cuenta c : b.getCuentas()) {
            if (c.getMoneda().equals(moneda)){
                s = c.getSaldoDisponible();
            }
        }
        return s;
    } 

    

    public void save(Billetera b) {
        repo.save(b);
    }

	public double consultarSaldo(Billetera b, String moneda) {
		return 0;
	}
}