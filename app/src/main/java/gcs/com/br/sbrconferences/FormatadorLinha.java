package gcs.com.br.sbrconferences;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by welisson on 19/09/16.
 */

public class FormatadorLinha {

    public static String formata(String linha) {
        if (linha.startsWith("#"))
            return formataData(linha);
        else if (linha.startsWith("*"))
            return formataHTML(linha);
        else
          return "<br>";
    }

    private static String formataData(String linha) {
        linha = linha.substring(5);
        String result = "";

        Map<String, String> map = new HashMap<String, String>();
        map.put("January", "Janeiro");
        map.put("February", "Fevereiro");
        map.put("March", "Março");
        map.put("April", "Abril");
        map.put("May", "Maio");
        map.put("June", "Junho");
        map.put("July", "Julho");
        map.put("August", "Agosto");
        map.put("September", "Setembro");
        map.put("October", "Outubro");
        map.put("November", "Novembro");
        map.put("December", "Dezembro");

        if (map.containsKey(linha))
            result = map.get(linha);

        return "<b>" + result + "</b><br>";
    }

    public static String formataHTML(String linha) {

        //* [EVENTO](URL) | **January 26-31** | LOCAL
        String evento = devolveEvento(linha);
        String url = devolveURL(linha);
        String data = devolveData(linha);
        String local = devolveLocal(linha);

        linha = String.format("<a href='%s'>%s</a> - Data: %s - Local: %s <br>",
                url, evento, data, local);
        //linha = String.format("<a href=%s>%s</a> - Data: %s - Local: %s<br>",
        //        url, evento, data, local);

        return linha;
    }

    private static String devolveLocal(String linha) {
        int i = linha.indexOf("** |") + 4;

        return linha.substring(i);
    }

    private static String devolveData(String linha) {
        int i = linha.indexOf("| **") + 4;
        int f = linha.indexOf("** |");

        String d = linha.substring(i, f);
//        String m = d.substring(0, d.indexOf(" "));
//        m = formataData("     " + m);

        d = d.substring(d.indexOf(" ") + 1);

        return d;
    }

    private static String devolveURL(String linha) {
        int i = linha.indexOf("(") + 1;
        int f = linha.indexOf(")");

        return linha.substring(i, f);
    }

    private static String devolveEvento(String linha) {
        int i = linha.indexOf("[") + 1;
        int f = linha.indexOf("]");

        return linha.substring(i, f);
    }

}
