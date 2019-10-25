package ar.com.ada.api.billeteravirtual.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Movimiento
 */
@Entity
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @Column(name = "movimiento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movimientoId;
    @Column(name="fecha_movimiento")
    private Date fechaMovimiento;
    // private Coordenada ubicacion;
    private double importe;
    @Column(name= "tipo_de_operacion")
    private String tipoDeOperacion;
    @Column(name="concepto_de_operacion")
    private String conceptoDeOperacion;
    private String detalle;
    private int estado;
    @Column(name="de_usuario_id")
    private int deUsuarioId;
    @Column(name="a_usuario_id")
    private int aUsuarioId;
    // private Usuario deUsuario;
    // private Usuario aUsuario;
    // private Cuenta cuentaDestino;
    // private Cuenta cuentaOrigen;
    @Column(name = "de_cuenta_id")
    private int deCuentaId;

    @Column(name = "a_cuenta_id")
    private int aCuentaId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cuenta_id", referencedColumnName = "cuenta_id")
    // @MapsId
    private Cuenta cuenta;
    

    public double getImporte() {
        return importe;
    }

    

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getTipoDeOperacion() {
        return tipoDeOperacion;
    }

    public void setTipoDeOperacion(String tipoDeOperacion) {
        this.tipoDeOperacion = tipoDeOperacion;
    }

    public String getConceptoDeOperacion() {
        return conceptoDeOperacion;
    }

    public void setConceptoDeOperacion(String conceptoDeOperacion) {
        this.conceptoDeOperacion = conceptoDeOperacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    /*
     * public Usuario getDeUsuario() { return deUsuario; }
     * 
     * public void setDeUsuario(Usuario deUsuario) { this.deUsuario = deUsuario; }
     * 
     * public Usuario getaUsuario() { return aUsuario; }
     * 
     * public void setaUsuario(Usuario aUsuario) { this.aUsuario = aUsuario; }
     * 
     * public Cuenta getCuentaDestino() { return cuentaDestino; }
     * 
     * public void setCuentaDestino(Cuenta cuentaDestino) { this.cuentaDestino =
     * cuentaDestino; }
     * 
     * public Cuenta getCuentaOrigen() { return cuentaOrigen; }
     * 
     * public void setCuentaOrigen(Cuenta cuentaOrigen) { this.cuentaOrigen =
     * cuentaOrigen; }
     * 
     * /**
     * 
     * @param movimiento the usuario to set
     */

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;

    }

    /**
     * @return the usuario
     */
    public Cuenta getCuenta() {
        return cuenta;
    }
    

    public int getMovimientoId() {
        return movimientoId;
    }
    
    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public int getDeUsuarioId() {
        return deUsuarioId;
    }

    public void setDeUsuarioId(int deUsuarioId) {
        this.deUsuarioId = deUsuarioId;
    }

    public int getaUsuarioId() {
        return aUsuarioId;
    }

    public void setaUsuarioId(int aUsuarioId) {
        this.aUsuarioId = aUsuarioId;
    }



    public Movimiento (){

    }

    public void setMovimientoId(int movimientoId) {
        this.movimientoId = movimientoId;
    }

    public int getDeCuentaId() {
        return deCuentaId;
    }

    public void setDeCuentaId(int deCuentaId) {
        this.deCuentaId = deCuentaId;
    }

    public int getaCuentaId() {
        return aCuentaId;
    }

    public void setaCuentaId(int aCuentaId) {
        this.aCuentaId = aCuentaId;
    }


}