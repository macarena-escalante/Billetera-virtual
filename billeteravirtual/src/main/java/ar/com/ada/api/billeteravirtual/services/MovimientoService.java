package ar.com.ada.api.billeteravirtual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repo.MovimientoRepository;

/**
 * MovimientoService
 */
@Service
public class MovimientoService {

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
        m.setImporte(320);
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

    public void movimientoTransfencia(Billetera billeteraOrigen, Billetera billeteraDestino, double importe,
            Cuenta cuentaDesde, Cuenta cuentaHasta, String concepto) {
        Movimiento m = new Movimiento();
        m.setImporte(-importe);
        // m.setCuenta(b1.getCuenta(0));
        m.setConceptoDeOperacion("Envío");
        m.setTipoDeOperacion("Transferencia");
        m.setFechaMovimiento(new Date());
        m.setDeCuentaId(cuentaDesde.getCuentaId());
        m.setaCuentaId(cuentaHasta.getCuentaId());
        m.setDeUsuarioId(cuentaDesde.getUsuario().getUsuarioId());
        m.setaUsuarioId(cuentaHasta.getUsuario().getUsuarioId());
        cuentaDesde.setSaldo(cuentaDesde.getSaldo() - importe);
        cuentaDesde.setSaldoDisponible(cuentaDesde.getSaldoDisponible() - importe);
        cuentaDesde.agregarMovimiento(m);

        movimientoRepository.save(m);
        billeteraService.save(billeteraOrigen);

        Movimiento m2 = new Movimiento();
        m2.setImporte(importe);
        // m2.setCuenta(b1.getCuenta(0)); deberia ser b2?
        m2.setConceptoDeOperacion("Recibo");
        m2.setTipoDeOperacion("Transferencia");
        m2.setFechaMovimiento(new Date());
        m2.setDeCuentaId(cuentaDesde.getCuentaId());
        m2.setaCuentaId(cuentaHasta.getCuentaId());
        m2.setDeUsuarioId(cuentaDesde.getUsuario().getUsuarioId());
        m2.setaUsuarioId(cuentaHasta.getUsuario().getUsuarioId());
        cuentaHasta.setSaldo(cuentaHasta.getSaldo() + importe);
        cuentaHasta.setSaldoDisponible(cuentaHasta.getSaldoDisponible() + importe);
        cuentaHasta.agregarMovimiento(m2);

        movimientoRepository.save(m2);
        billeteraService.save(billeteraDestino);
    }

    public void movimientoTransfencia (Billetera billeteraOrigen, String emailDestinatario, double importe,
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