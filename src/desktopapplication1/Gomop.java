/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;



/**
 *
 * @author elialva
 */
public class Gomop {
    private Integer topi;

    public Gomop() {
    }
    
    public Gomop(Integer topi) {
        this.topi = topi;
        
    }

    public Integer getTopi() {
        return topi;
    }

    public void setTopi(Integer topi) {
        this.topi = topi;
    }

    @Override
    public String toString() {
     return "Chida tu prueba: " + topi;
    }
    
    
    
}
