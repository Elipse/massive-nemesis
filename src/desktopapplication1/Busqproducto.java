/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elialva
 */
@Entity
@Table(name = "busqproducto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Busqproducto.findAll", query = "SELECT b FROM Busqproducto b"),
    @NamedQuery(name = "Busqproducto.findByIdBean", query = "SELECT b FROM Busqproducto b WHERE b.idBean = :idBean")})
public class Busqproducto implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "busqproducto")
    private Collection<Ortobean> ortobeanCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idBean")
    private Integer idBean;
    @Lob
    @Column(name = "contexto")
    private String contexto;
    
    @Transient
    private List alignment;

    public Busqproducto() {
    }

    public Busqproducto(Integer idBean) {
        this.idBean = idBean;
    }

    public Integer getIdBean() {
        return idBean;
    }

    public void setIdBean(Integer idBean) {
        Integer oldIdBean = this.idBean;
        this.idBean = idBean;
        changeSupport.firePropertyChange("idBean", oldIdBean, idBean);
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        String oldContexto = this.contexto;
        this.contexto = contexto;
        changeSupport.firePropertyChange("contexto", oldContexto, contexto);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBean != null ? idBean.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Busqproducto)) {
            return false;
        }
        Busqproducto other = (Busqproducto) object;
        if ((this.idBean == null && other.idBean != null) || (this.idBean != null && !this.idBean.equals(other.idBean))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication1.Busqproducto[ idBean=" + idBean + " ]";
    }

    @XmlTransient
    public Collection<Ortobean> getOrtobeanCollection() {
        return ortobeanCollection;
    }

    public void setOrtobeanCollection(Collection<Ortobean> ortobeanCollection) {
        this.ortobeanCollection = ortobeanCollection;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * @return the alignment
     */
    public List getAlignment() {
        return alignment;
    }

    /**
     * @param alignment the alignment to set
     */
    public void setAlignment(List alignment) {
        List oldAlignment = this.alignment;
        this.alignment = alignment;        
        changeSupport.firePropertyChange("alignment", oldAlignment, alignment);
    }
}
