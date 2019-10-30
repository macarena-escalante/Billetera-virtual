package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Cuenta
 */
@Entity
@Table(name = "cuenta")

public class Cuenta {
    @Id
    @Column(name = "cuenta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cuentaId;

    private String moneda;
    private BigDecimal saldo = new BigDecimal(0);
    @Column(name="saldo_disponible")
    private BigDecimal saldoDisponible = new BigDecimal(0);
    @JsonIgnore

    @ManyToOne
    @JoinColumn(name = "billetera_id", referencedColumnName = "billetera_id")
    // @MapsId
    private Billetera billetera;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<Movimiento> movimientos = new ArrayList<Movimiento>();
    

    
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }



    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public List<Movimiento> getMovimiento() {
        return movimientos;
    }

    public void setMovimiento(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    /**
     * @param billetera the usuario to set
     */

    public void setBilletera(Billetera billetera) {
        this.billetera = billetera;

    }

    /**
     * @return the usuario
     */
    public Billetera getBilletera() {
        return billetera;
    }

    public int getCuentaId() {
        return cuentaId;
    }


    public void setCuentaId(int cuentaId) {
        this.cuentaId = cuentaId;
    }
    public Usuario getUsuario(){

        Usuario u = this.getBilletera().getPersona().getUsuario();
        return u;
        
    }

    public void agregarPlata(int usuarioDe, String concepto, BigDecimal importe, String detalle) {
        Movimiento m = new Movimiento();

        m.setCuenta(this);
        m.setConceptoDeOperacion("INGRESO");
        m.setImporte(importe);
        m.setConceptoDeOperacion(concepto);
        m.setDetalle(detalle);
        m.setFechaMovimiento(new Date());
        m.setDeUsuarioId(usuarioDe);
        m.setaUsuarioId(usuarioDe);
        m.setDeCuentaId(this.cuentaId);
        m.setaCuentaId(this.cuentaId);

        this.movimientos.add(m);
    }

    public void descontarPlata(int usuarioOr, String concepto, BigDecimal importe, String detalle) {
        Movimiento m = new Movimiento();

        m.setCuenta(this);
        m.setConceptoDeOperacion("EGRESO");
        m.setImporte(importe.negate());
        m.setConceptoDeOperacion(concepto);
        m.setFechaMovimiento(new Date());
        m.setDeUsuarioId(usuarioOr);
        m.setaUsuarioId(usuarioOr);
        m.setDeCuentaId(this.cuentaId);
        m.setaCuentaId(this.cuentaId);

    }

	public void agregarMovimiento(Movimiento m) {
	}

	public void setSaldo(BigDecimal bigDecimal) {
	}

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
