/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopapplication1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author elialva
 */
@Embeddable
public class ProductoOrtogramaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idBean")
    private String idBean;
    @Basic(optional = false)
    @Column(name = "ortograma")
    private String ortograma;

    public ProductoOrtogramaPK() {
    }

    public ProductoOrtogramaPK(String idBean, String ortograma) {
        this.idBean = idBean;
        this.ortograma = ortograma;
    }

    public String getIdBean() {
        return idBean;
    }

    public void setIdBean(String idBean) {
        this.idBean = idBean;
    }

    public String getOrtograma() {
        return ortograma;
    }

    public void setOrtograma(String ortograma) {
        this.ortograma = ortograma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBean != null ? idBean.hashCode() : 0);
        hash += (ortograma != null ? ortograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoOrtogramaPK)) {
            return false;
        }
        ProductoOrtogramaPK other = (ProductoOrtogramaPK) object;
        if ((this.idBean == null && other.idBean != null) || (this.idBean != null && !this.idBean.equals(other.idBean))) {
            return false;
        }
        if ((this.ortograma == null && other.ortograma != null) || (this.ortograma != null && !this.ortograma.equals(other.ortograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication1.ProductoOrtogramaPK[ idBean=" + idBean + ", ortograma=" + ortograma + " ]";
    }
    
}
