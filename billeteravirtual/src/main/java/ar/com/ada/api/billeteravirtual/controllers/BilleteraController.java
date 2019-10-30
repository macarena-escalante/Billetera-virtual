package ar.com.ada.api.billeteravirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.entities.Movimiento;
import ar.com.ada.api.billeteravirtual.models.request.MovimientoRequest;
import ar.com.ada.api.billeteravirtual.models.response.MovimientoResponse;
import ar.com.ada.api.billeteravirtual.models.response.RegistrationResponse;
import ar.com.ada.api.billeteravirtual.models.response.SaldoResponse;
import ar.com.ada.api.billeteravirtual.services.BilleteraService;
import ar.com.ada.api.billeteravirtual.services.CuentaService;
import ar.com.ada.api.billeteravirtual.services.MovimientoService;

/**
 * BilleteraController
 */
@RestController
public class BilleteraController {

    @Autowired
    BilleteraService billeteraService;

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    CuentaService cuentaService;
   

    @GetMapping("/billeteras/{id}")
    public Billetera getBilleteraById(@PathVariable int id) {
        Billetera b = billeteraService.buscarPorId(id);

        return b;
    }

    @GetMapping("/billeteras/{id}/saldos/{moneda}")
    public SaldoResponse getSaldoByIdBilletera(@PathVariable int id, @PathVariable String moneda){
        Billetera b = billeteraService.buscarPorId(id); //se abre la billetera con el id
        SaldoResponse r = new SaldoResponse();
        r.billeteraId= id;
        r.moneda= moneda;
        r.saldo = billeteraService.consultarSaldo(b, moneda);

        return r;
    }

    @GetMapping("billeteras/{id}/saldosDisponibles/{moneda}")
    public SaldoResponse getSaldoDispoByIdBilletera(@PathVariable int id, @PathVariable String moneda){
        Billetera b = billeteraService.buscarPorId(id);
        SaldoResponse r = new SaldoResponse();
        r.billeteraId= id;
        r.moneda= moneda;
        r.saldo= billeteraService.consultarSaldo(b, moneda);

        return r;
    }

    @PostMapping("/billeteras/{id}/transferencias")
    public MovimientoResponse postNewMovimiento(@PathVariable int id, @RequestBody MovimientoRequest req) {
        MovimientoResponse m = new MovimientoResponse();

        Billetera bOrigen= billeteraService.buscarPorId(id);

        movimientoService.movimientoTransfencia(bOrigen, req.emailDestinatario, req.importe, req.moneda, req.concepto);
    
        m.isOk = true;
        m.message = "Transferencia realizada.";
        return m; 
    }

}