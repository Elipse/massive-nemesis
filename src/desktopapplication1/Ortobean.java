/*
 * To change this template, choose Tools | Templates
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
@Table(name = "ortobean")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ortobean.findAll", query = "SELECT o FROM Ortobean o"),
    @NamedQuery(name = "Ortobean.findByOrtograma", query = "SELECT o FROM Ortobean o WHERE o.ortobeanPK.ortograma = :ortograma"),
    @NamedQuery(name = "Ortobean.findByIdBean", query = "SELECT o FROM Ortobean o WHERE o.ortobeanPK.idBean = :idBean"),
    @NamedQuery(name = "Ortobean.findByFrecuencia", query = "SELECT o FROM Ortobean o WHERE o.frecuencia = :frecuencia")})
public class Ortobean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrtobeanPK ortobeanPK;
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @JoinColumn(name = "ortograma", referencedColumnName = "ortograma", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ortograma ortograma1;
    @JoinColumn(name = "idBean", referencedColumnName = "idBean", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Busqproducto busqproducto;

    public Ortobean() {
    }

    public Ortobean(OrtobeanPK ortobeanPK) {
        this.ortobeanPK = ortobeanPK;
    }

    public Ortobean(String ortograma, int idBean) {
        this.ortobeanPK = new OrtobeanPK(ortograma, idBean);
    }

    public OrtobeanPK getOrtobeanPK() {
        return ortobeanPK;
    }

    public void setOrtobeanPK(OrtobeanPK ortobeanPK) {
        this.ortobeanPK = ortobeanPK;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Ortograma getOrtograma1() {
        return ortograma1;
    }

    public void setOrtograma1(Ortograma ortograma1) {
        this.ortograma1 = ortograma1;
    }

    public Busqproducto getBusqproducto() {
        return busqproducto;
    }

    public void setBusqproducto(Busqproducto busqproducto) {
        this.busqproducto = busqproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ortobeanPK != null ? ortobeanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ortobean)) {
            return false;
        }
        Ortobean other = (Ortobean) object;
        if ((this.ortobeanPK == null && other.ortobeanPK != null) || (this.ortobeanPK != null && !this.ortobeanPK.equals(other.ortobeanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication1.Ortobean[ ortobeanPK=" + ortobeanPK + " ]";
    }
    
}
