/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopapplication1;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elialva
 */
@Entity
@Table(name = "producto_ortograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoOrtograma.findAll", query = "SELECT p FROM ProductoOrtograma p"),
    @NamedQuery(name = "ProductoOrtograma.findByIdBean", query = "SELECT p FROM ProductoOrtograma p WHERE p.productoOrtogramaPK.idBean = :idBean"),
    @NamedQuery(name = "ProductoOrtograma.findByOrtograma", query = "SELECT p FROM ProductoOrtograma p WHERE p.productoOrtogramaPK.ortograma = :ortograma"),
    @NamedQuery(name = "ProductoOrtograma.findByFrecuencia", query = "SELECT p FROM ProductoOrtograma p WHERE p.frecuencia = :frecuencia")})
public class ProductoOrtograma implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoOrtogramaPK productoOrtogramaPK;
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @JoinColumn(name = "idBean", referencedColumnName = "idBean", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Productobusq productobusq;
    @JoinColumn(name = "ortograma", referencedColumnName = "ortograma", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ortograma ortograma1;

    public ProductoOrtograma() {
    }

    public ProductoOrtograma(ProductoOrtogramaPK productoOrtogramaPK) {
        this.productoOrtogramaPK = productoOrtogramaPK;
    }

    public ProductoOrtograma(String idBean, String ortograma) {
        this.productoOrtogramaPK = new ProductoOrtogramaPK(idBean, ortograma);
    }

    public ProductoOrtogramaPK getProductoOrtogramaPK() {
        return productoOrtogramaPK;
    }

    public void setProductoOrtogramaPK(ProductoOrtogramaPK productoOrtogramaPK) {
        this.productoOrtogramaPK = productoOrtogramaPK;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Productobusq getProductobusq() {
        return productobusq;
    }

    public void setProductobusq(Productobusq productobusq) {
        this.productobusq = productobusq;
    }

    public Ortograma getOrtograma1() {
        return ortograma1;
    }

    public void setOrtograma1(Ortograma ortograma1) {
        this.ortograma1 = ortograma1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoOrtogramaPK != null ? productoOrtogramaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoOrtograma)) {
            return false;
        }
        ProductoOrtograma other = (ProductoOrtograma) object;
        if ((this.productoOrtogramaPK == null && other.productoOrtogramaPK != null) || (this.productoOrtogramaPK != null && !this.productoOrtogramaPK.equals(other.productoOrtogramaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication1.ProductoOrtograma[ productoOrtogramaPK=" + productoOrtogramaPK + " ]";
    }
    
}
