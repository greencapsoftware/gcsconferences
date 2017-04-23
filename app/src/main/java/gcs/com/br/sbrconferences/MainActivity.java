package gcs.com.br.sbrconferences;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private class DevolveArquivoTask extends AsyncTask<String, Void, String> {

        //private Exception exception;

        protected String doInBackground(String... urls) {

            try {
                // Create a URL for the desired page
                URL url = new URL("https://raw.githubusercontent.com/jchahoud/BRConferences/master/README.md");
                String tmp = "";

                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;

                while ((str = in.readLine()) != null) {
                    // str is one line of text; readLine() strips the newline character(s)
                    tmp += str + "\n";
                }

                in.close();
                return tmp;

            } catch (Exception e) {
                //this.exception = e;

                return e.getMessage();
            }
        }

        protected void onPostExecute(String result) {
            // TODO: check this.exception
            // TODO: do something with the feed

            result = limpaDados(result);
            result = formataDados(result);

            tvDados.setText(Html.fromHtml(result));
        }

        private String formataDados(String result) {
            String lines[] = result.split("\\r?\\n");
            result = "";

            for (String s : lines) {
                result += FormatadorLinha.formata(s) + "\n";
            }

            return result;
        }

        private String limpaDados(String result) {
            //Pega só o texto que interessa
            result = result.substring(result.indexOf("###"), result.indexOf("### More"));

            //Pega só a partir do mês que interessa
            Calendar mCalendar = Calendar.getInstance();
            String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

            int i = result.indexOf("#### " + month);

            result = result.substring(i);

            return  result;
        }

    }


    TextView tvDados = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregaControles();
        readHttp();
    }

    private void carregaControles() {
        TextView tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvTitulo.setMovementMethod(LinkMovementMethod.getInstance());
        tvDados = (TextView)findViewById(R.id.tvTexto);
        tvDados.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void readHttp() {

        try {
            new DevolveArquivoTask().execute("");
        } catch (Exception ex) {
            tvDados.setText(ex.getMessage());
        }

    }
}
