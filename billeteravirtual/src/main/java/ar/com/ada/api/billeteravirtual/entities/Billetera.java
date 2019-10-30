package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Billetera
 */
@Entity
@Table(name= "billetera")
public class Billetera {

    @Id
    @Column (name = "billetera_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billeteraId;
    
    
    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
    //@MapsId
    private Persona persona;

    @OneToMany(mappedBy = "billetera", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cuenta> cuentas = new ArrayList<Cuenta>();

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        
    }

    public List<Cuenta> getCuentas(){
        return cuentas;
    }

    public void setCuenta(List<Cuenta> cuentas){
        this.cuentas = cuentas;
    }

    public void agregarCuenta (Cuenta cuenta){
        cuenta.setBilletera(this);
        this.cuentas.add(cuenta);
    }

    public int getBilleteraId() {
        return billeteraId;
    }

    public void setBilleteraId(int billeteraId) {
        this.billeteraId = billeteraId;
    }

    public Cuenta getCuenta(int index){
        return getCuentas().get(index);
    }
    
    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;   }

    public void agregarPlata(BigDecimal plata,String moneda, String concepto, String detalle) {
        // Agarro el primero y le meto plata (esto se hacia antes porqeu le agregaba a la primer cuenta)
        //this.cuentas.get(0).agregarPlata(persona.getUsuario().getUsuarioId(), concepto, plata, detalle);
        this.buscarCuenta(moneda).agregarPlata(persona.getUsuario().getUsuarioId(), concepto, plata, detalle);
    
    }

    public void descontarPlata(BigDecimal plata,String moneda, String concepto, String detalle) {
        this.buscarCuenta(moneda).descontarPlata(persona.getUsuario().getUsuarioId(), concepto, plata, detalle);
    
    }

    public void transferencia (Billetera aBilletera,BigDecimal plata,String moneda, String concepto, String detalle){
        this.descontarPlata(plata, moneda, concepto, detalle);
        aBilletera.agregarPlata(plata, moneda, concepto, detalle);
    }


    private Cuenta buscarCuenta (String moneda){
        for (Cuenta cta : this.cuentas) {
            if (moneda.equals(cta.getMoneda())) {
                return cta;
            }
            
        }

        return null;
    }

    
    
}