/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
//import org.jdesktop.application.Application;
//import org.jdesktop.application.ResourceMap;
//import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 * @author elialva
 */
public class ETLDic {

    private static EntityManager entityManager;

    public static void detector(String[] args) throws java.io.IOException {
        if (args.length != 1) {
            System.err.println("Usage: java TestDetector FILENAME");
            System.exit(1);
        }

        byte[] buf = new byte[4096];
        String fileName = args[0];
        java.io.FileInputStream fis = new java.io.FileInputStream(fileName);

        // (1)
        UniversalDetector detector = new UniversalDetector(null);

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
        } else {
            System.out.println("No encoding detected.");
        }

        // (5)
        detector.reset();
    }

    public static void main(String[] args) throws IOException {

//        ResourceMap resourceMap = Application.getInstance(DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        entityManager = Persistence.createEntityManagerFactory("busquedaPU").createEntityManager();
        entityManager.getTransaction().begin();

//        System.exit(0);

        String path = "C:\\Users\\IBM_ADMIN\\Documents\\@Projects_Eli\\201309 Finder&Getter\\";
        File file = new File(path + "Book1.csv");
        detector(new String[]{path + "Book1.csv"});
        Iterator<Entry<String, Charset>> iterator = Charset.availableCharsets().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Charset> next = iterator.next();
//            System.out.println("It " + next.getKey() + " * " + next.getValue().name());
        }

        List<String> readLines = FileUtils.readLines(file, "windows-1252");
        for (String string : readLines) {
            String[] split = StringUtils.split(string, ',');
            System.out.println("split[0] " + split[0]);
            System.out.println("split[1] " + split[1]);
            Busqproducto bp = new Busqproducto(new Integer(split[0].trim()));
            bp.setContexto(split[1]);
            ETLDic eTLDic = new ETLDic();
            eTLDic.breakdownContext(split[1], bp);
            entityManager.merge(bp);
        }

        entityManager.getTransaction().commit();
    }

    private void breakdownContext(String context, Busqproducto bp) {
        String[] split = StringUtils.split(context);
        for (int i = 0; i < split.length; i++) {
            String ortograma = StringUtils.lowerCase(split[i]);

            Ortograma ortograma1 = new Ortograma(ortograma, createNumegrama(ortograma));
            entityManager.merge(ortograma1);

            Ortobean ortobean = new Ortobean(ortograma, bp.getIdBean());
            entityManager.merge(ortobean);

            String simigrama = Normalizer.normalize(ortograma, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            Simigrama simigrama1 = new Simigrama(simigrama, createNumegrama(simigrama));
            entityManager.merge(simigrama1);

            Alineacion alineacion = new Alineacion(new AlineacionPK(simigrama, ortograma), createAlignment(simigrama, ortograma));
            entityManager.merge(alineacion);
        }
    }

    private String createNumegrama(String text) {
        text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        text = StringUtils.replaceChars(text, "abcdefghijklmnopqrstuvwxyz", "22233344455566677778889999");
        return text;
    }

    private String createAlignment(String word1, String word2) {
        byte[] bytes = word1.getBytes();

        int ind = 0;
        String alignment = "";
        for (int i = 0; i < bytes.length; i++) {
            ind = i % 10 + 1;
            if (ind == 10) {
                ind = 0;
            }
            alignment += ind;
        }

        return alignment;
    }

    public static Object[][] align(String text) {

        String[] split = StringUtils.split(text);

        Object[][] posiciones = new Object[split.length][2];

        for (int i = 0; i < split.length; i++) {
            int indice = text.indexOf(split[i]);
            text = text.replaceFirst(split[i], StringUtils.repeat(" ", split[i].length()));
            posiciones[i][0] = split[i];
            posiciones[i][1] = indice;
        }
        return posiciones;
    }

    public static List<List<List<String>>> extractOrtogramas(String input, Integer idCandidate) {
        String queryModel =
                "select \'<numegrama>\', a.numegrama, a.simigrama, b.ortograma, b.alineacion "
                + "from _simigrama as a inner join _alineacion as b "
                + "                     on         a.simigrama = b.simigrama "
                + "                     inner join ortobean as c"
                + "                     on         b.ortograma = c.ortograma"
                + " where a.numegrama like '%<numegrama>%'"
                + "  and idBean = <idBean>;";

        ArrayList ortolist = new ArrayList();

        String[] inklings = StringUtils.split(input);
        for (int i = 0; i < inklings.length; i++) {
            String query = queryModel.replaceAll("<numegrama>", inklings[i]);
            query = query.replaceAll("<idBean>", idCandidate.toString());
            Query createNativeQuery = entityManager.createNativeQuery(query);
            List<List<String>> dataset = createNativeQuery.getResultList();
            ortolist.add(dataset);
        }

        return ortolist;
    }  
}
