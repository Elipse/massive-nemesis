/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.StringUtils;
////import org.jdesktop.application.Application;
//import org.jdesktop.application.ResourceMap;

/**
 *
 * @author elialva
 */
public class SelectorOld {

    private String input;
    private String propOrthogramLink;
    private String propIdBean;
    private String propDescription;
    private Object bean;
    public static String queryCandidates
            = "select idBean"
            + "      from ("
            + "      select distinct o.ortograma, o.idBean,  o.frecuencia"
            + "      from _simigrama as s inner join _alineacion as a "
            + "                                        on a.simigrama = s.simigrama"
            + "                                        inner join ortobean as o"
            + "                                        on o.ortograma = a.ortograma"
            + "      <where> "
            + "      ) as v1"
            + " group by idBean"
            + " having sum(frecuencia) >= <numOfWords>;";
    public static String queryOrthograms
            = "select \'<numegrama>\', s.numegrama, s.simigrama, a.ortograma, a.alineacion "
            + "from _simigrama as s inner join _alineacion as a "
            + "                     on         s.simigrama = a.simigrama "
            + "                     inner join ortobean as o"
            + "                     on         a.ortograma = o.ortograma"
            + " where s.numegrama like '%<numegrama>%'"
            + "  and idBean = <idBean>;";
    private final EntityManager em;

    public SelectorOld() {
//        ResourceMap resourceMap = Application.getInstance(DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("busquedaPU");
        System.out.println("emf " + emf);
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public List<SimpleEntry<Integer, Integer>> calculateSection() {
        return null;
    }

    /**
     * @param bean the bean to set
     */
    public void setBean(Object bean) {
        this.bean = bean;
    }

//    public SimpleEntry<Integer, Integer> getSection(StringBuilder description, List<List<String>> dataset) {
    public SimpleEntry<Integer, Integer> getSection(StringBuilder description, Object dataset) {
        int orthopos = description.length();
        Object[] tempRow = null;
        Vector vector = (Vector) dataset;
        System.out.println("vectorSIZE " + vector.size());
        Object[] oa = (Object[]) vector.get(0);

        System.out.println(" Frop " + oa[0] + "--" + oa[1] + "--" + oa[2] + "--" + oa[3] + "--" + oa[4]);
        System.out.println("dataset " + vector.get(0));
        for (Object rowy : vector) {
            Object[] row = (Object[]) rowy;
            String orthogram = (String) row[3];
            int pos = description.indexOf(orthogram);

            if (pos >= 0 && pos < orthopos) {
                orthopos = pos;
                tempRow = row;
            }
        }

        if (orthopos < description.length()) {
            String orthogram = (String) tempRow[3];

            description.replace(orthopos, orthopos + orthogram.length(), StringUtils.repeat(" ", orthogram.length()));

            String inkling = (String) tempRow[0];
            String numegram = (String) tempRow[1];
            String alignment = (String) tempRow[4];

            int inklpos = numegram.indexOf(inkling);

            int decena = 0;
            char[] toCharArray = alignment.toCharArray();
            int[] posic = new int[toCharArray.length];
            for (int i = 0; i < toCharArray.length; i++) {
                posic[i] = Integer.parseInt("" + toCharArray[i]) + decena;
                if (i + 1 < toCharArray.length && toCharArray[i] > toCharArray[i + 1]) {
                    decena += 10;
                }
            }

            String charpos = alignment.substring(inklpos, inklpos + inkling.length());

            System.out.println("charpos " + charpos + ":" + posic[inklpos] + ":" + posic[inklpos + inkling.length() - 1]);
            int begin = posic[inklpos];
            int end = posic[inklpos + inkling.length() - 1];

            return new SimpleEntry(orthopos + inklpos, end - begin + 1);
        } else {
            return null;
        }
    }

    public List<Integer> getCandidates(String[] inklings) {
        String where = "";
        String like = "where s.numegrama like '%<numegrama>%'";
        for (String inkling : inklings) {
            where += like.replaceAll("<numegrama>", inkling);
            like = " or s.numegrama like '%<numegrama>%'";
        }

        String query = queryCandidates.replaceAll("<where>", where);
        query = query.replaceAll("<numOfWords>", inklings.length + "");
        query = StringUtils.normalizeSpace(query);
        Query createNativeQuery = em.createNativeQuery(query);
//        Query cQuery = em.createQuery("Select new desktopapplication1.Gomop(b.idBean) from Busqproducto b where b.idBean = 9");
//        Object gmp = cQuery.getSingleResult();
//        System.out.println("gmpdesktopapplication1 " + gmp);
        System.out.println("query " + query);

        return createNativeQuery.getResultList();
    }

    public List<List<List<String>>> getOrthograms(String[] inklings, Object key) {
        String queryBean = queryOrthograms.replaceAll("<idBean>", key.toString());

        ArrayList ortolist = new ArrayList();

        for (String inkling : inklings) {
            String query = queryBean.replaceAll("<numegrama>", inkling);
            Query createNativeQuery = em.createNativeQuery(query);
            List<List<String>> dataset = createNativeQuery.getResultList();
            
            System.out.println("inkling " + inkling);
            ortolist.add(dataset);
        }

        return ortolist;
    }

    public List<Busqproducto> getPage(String input) {
        System.out.println("inputPeroo " + input);
        Query createNamedQuery = em.createNamedQuery("Busqproducto.findByIdBean");

        ArrayList<Busqproducto> listaSelect = new ArrayList<Busqproducto>();

        String[] split = StringUtils.split(input);
//        List<List> candidates = getCandidates(split);
        List<Integer> candidates = getCandidates(split);

        System.out.println("candidates " + candidates + " c " + candidates.getClass());
        for (Integer columns : candidates) {
            System.out.println("debug " + split + ":" + input);
//            List orthograms = getOrthograms(split, columns.get(0));
//            createNamedQuery.setParameter("idBean", columns.get(0));
            List orthograms = getOrthograms(split, columns);
            createNamedQuery.setParameter("idBean", columns);
            Busqproducto busqproducto = (Busqproducto) createNamedQuery.getSingleResult();
            String s = busqproducto.getContexto().toLowerCase();
            StringBuilder sb = new StringBuilder(s);
            ArrayList sections = new ArrayList();
            for (Object object : orthograms) {
//                System.out.println("object gop " + ((List) object).get(0).getClass());
                SimpleEntry<Integer, Integer> section = getSection(sb, object);
                if (section != null) {
                    sections.add(section);
                }
            }

            System.out.println("sections.size() " + sections.size() + " orthograms.size() " + orthograms.size());
            if (sections.size() == orthograms.size()) {
                busqproducto.setAlignment(sections);
////                em.merge(busqproducto); //
////                em.flush();
                listaSelect.add(busqproducto);
            }
        }
        return listaSelect;
    }

    public static void main(String[] args) {
        SelectorOld selector = new SelectorOld();
        List<Busqproducto> page = selector.getPage("2 9");
        for (Busqproducto busqproducto : page) {
            System.out.println("busqproducto " + busqproducto.getIdBean() + ":" + busqproducto.getContexto());
        }
    }
}
