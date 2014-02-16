/*
 * To change this template, choose Tools | Templates
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
public class OrtobeanPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ortograma")
    private String ortograma;
    @Basic(optional = false)
    @Column(name = "idBean")
    private int idBean;

    public OrtobeanPK() {
    }

    public OrtobeanPK(String ortograma, int idBean) {
        this.ortograma = ortograma;
        this.idBean = idBean;
    }

    public String getOrtograma() {
        return ortograma;
    }

    public void setOrtograma(String ortograma) {
        this.ortograma = ortograma;
    }

    public int getIdBean() {
        return idBean;
    }

    public void setIdBean(int idBean) {
        this.idBean = idBean;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ortograma != null ? ortograma.hashCode() : 0);
        hash += (int) idBean;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrtobeanPK)) {
            return false;
        }
        OrtobeanPK other = (OrtobeanPK) object;
        if ((this.ortograma == null && other.ortograma != null) || (this.ortograma != null && !this.ortograma.equals(other.ortograma))) {
            return false;
        }
        if (this.idBean != other.idBean) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication1.OrtobeanPK[ ortograma=" + ortograma + ", idBean=" + idBean + " ]";
    }
    
}
