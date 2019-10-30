package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repo.MovimientoRepository;
import ar.com.ada.api.billeteravirtual.sistema.comms.EmailService;

/**
 * MovimientoService
 */
@Service
public class MovimientoService {

    @Autowired
    EmailService emailService;

    @Autowired
    BilleteraService billeteraService;

    @Autowired
    CuentaService cuentaService;

    @Autowired
    PersonaService personaService;

    @Autowired
    MovimientoRepository movimientoRepository;
    @Autowired
    UsuarioService usuarioService;

    public Movimiento regalarSaldoInicial(Persona p, Cuenta c) {
        Movimiento m = new Movimiento();
        m.setImporte(new BigDecimal(320));
        m.setDeUsuarioId(p.getUsuario().getUsuarioId());
        m.setaUsuarioId(p.getUsuario().getUsuarioId());
        m.setaCuentaId(c.getCuentaId());
        m.setDeCuentaId(c.getCuentaId());
        m.setConceptoDeOperacion("Regalo");
        m.setTipoDeOperacion("Cargar dinero");
        m.setEstado(0);
        m.setFechaMovimiento(new Date());
        c.setSaldo(m.getImporte());
        c.agregarMovimiento(m);

        return m;
    }

    public void movimientoTransfencia(Billetera billeteraOrigen, Billetera billeteraDestino, BigDecimal importe,
            Cuenta cuentaDesde, Cuenta cuentaHasta, String concepto) {
        Movimiento m = new Movimiento();
        m.setImporte(importe.negate());
        m.setCuenta(billeteraOrigen.getCuenta(0));
        m.setConceptoDeOperacion("Envío");
        m.setTipoDeOperacion("Transferencia");
        m.setFechaMovimiento(new Date());
        m.setDeCuentaId(cuentaDesde.getCuentaId());
        m.setaCuentaId(cuentaHasta.getCuentaId());
        m.setDeUsuarioId(cuentaDesde.getUsuario().getUsuarioId());
        m.setaUsuarioId(cuentaHasta.getUsuario().getUsuarioId());
        cuentaDesde.setSaldo(cuentaDesde.getSaldo().add(importe.negate()));
        cuentaDesde.setSaldoDisponible(cuentaDesde.getSaldoDisponible().add(importe.negate()));
        cuentaDesde.agregarMovimiento(m);

        movimientoRepository.save(m);
        billeteraService.save(billeteraOrigen);

        Movimiento m2 = new Movimiento();
        m2.setImporte(importe);
        m2.setCuenta(billeteraDestino.getCuenta(0));
        m2.setConceptoDeOperacion("Recibo");
        m2.setTipoDeOperacion("Transferencia");
        m2.setFechaMovimiento(new Date());
        m2.setDeCuentaId(cuentaDesde.getCuentaId());
        m2.setaCuentaId(cuentaHasta.getCuentaId());
        m2.setDeUsuarioId(cuentaDesde.getUsuario().getUsuarioId());
        m2.setaUsuarioId(cuentaHasta.getUsuario().getUsuarioId());
        cuentaHasta.setSaldo(cuentaHasta.getSaldo().add(importe));
        cuentaHasta.setSaldoDisponible(cuentaHasta.getSaldoDisponible().add(importe.negate()));
        cuentaHasta.agregarMovimiento(m2);

        movimientoRepository.save(m2);
        billeteraService.save(billeteraDestino);

        emailService.SendEmail(cuentaDesde.getUsuario().getUserEmail(),"Realizaste una transferencia!!", 
            "Hola "+cuentaDesde.getUsuario().getUserEmail()+ " realizaste una transferencia exitosa a "+cuentaHasta.getUsuario().getUserEmail()+ "\n datos de la transferencia:\n" + "importe: $"+m2.getImporte() + "\n"+ "fecha: " + m2.getFechaMovimiento());
   
            emailService.SendEmail(cuentaHasta.getUsuario().getUserEmail(),"Te realizaron una transferencia!!", 
            "Hola "+cuentaHasta.getUsuario().getUserEmail()+ " recibiste una transferencia exitosa de "+cuentaDesde.getUsuario().getUserEmail()+ "\n datos de la transferencia:\n" + "importe: $"+m2.getImporte() + "\n"+ "fecha: " + m2.getFechaMovimiento());
   
        }

    public void movimientoTransfencia (Billetera billeteraOrigen, String emailDestinatario, BigDecimal importe,
            String moneda, String concepto) {

        Billetera billeteraDestino;
        Usuario u;
        u = usuarioService.buscarporUserEmail(emailDestinatario);
        billeteraDestino = u.getPersona().getBilletera();

        Cuenta cuentaDesde = billeteraOrigen.getCuenta(0);
        Cuenta cuentaHasta = billeteraDestino.getCuenta(0);

        this.movimientoTransfencia(billeteraOrigen, billeteraDestino, importe, cuentaDesde, cuentaHasta, concepto);

    }
}