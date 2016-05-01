package br.com.pedrogutierres.consumocombustivel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private EditText edtKM;
    private Spinner spnTipoCombustivel;
    private EditText edtQtdeLitros;
    private EditText edtPrecoLitro;
    private Button btnCalcular;
    private TextView txtConsumoMedio;
    private TextView txtKmPorLitro;
    private TextView txtTotalPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtKM = (EditText) findViewById(R.id.edt_km_rodados);
        spnTipoCombustivel = (Spinner) findViewById(R.id.spn_combustivel);
        edtQtdeLitros = (EditText) findViewById(R.id.edt_quantidade_litros);
        edtPrecoLitro = (EditText) findViewById(R.id.edt_preco);
        txtConsumoMedio = (TextView) findViewById(R.id.txt_consumo_medio);
        txtKmPorLitro = (TextView) findViewById(R.id.txt_km_por_litro);
        txtTotalPagar = (TextView) findViewById(R.id.txt_total_pagar);
        btnCalcular = (Button) findViewById(R.id.btn_calcular);

        btnCalcular.setOnClickListener(this);

        edtPrecoLitro.setOnEditorActionListener(this);

        ArrayAdapter<String> tipos_combutiveis = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.tipos_combutiveis));

        spnTipoCombustivel.setAdapter(tipos_combutiveis);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCalcular.getId()) {
            calcularConsumoEPagar();
        }
    }

    private void calcularConsumoEPagar() {
        double kmRodados, qtdeLitros, precoCombustivel;

        try {
            kmRodados = Double.parseDouble(edtKM.getText().toString());

            if (kmRodados <= 0) {
                Toast.makeText(this, "Favor informar os KM rodados.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Favor informar os KM rodados.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            qtdeLitros = Double.parseDouble(edtQtdeLitros.getText().toString());

            if (qtdeLitros <= 0) {
                Toast.makeText(this, "Favor informar uma quantidade de litros maior do que zero.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Favor informar a quantidade de litros abastecida.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            precoCombustivel = Double.parseDouble(edtPrecoLitro.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(this, "Favor informar o preço do combustível.", Toast.LENGTH_SHORT).show();
            return;
        }

        double consumoMedioPorKm = qtdeLitros / kmRodados;
        double kmPorLitro = kmRodados / qtdeLitros;
        double totalPagar = qtdeLitros * precoCombustivel;

        txtConsumoMedio.setText(String.format("Cons. médio LT por KM: %.3f", consumoMedioPorKm));
        txtKmPorLitro.setText(String.format("KM rodado por LT: %.1f", kmPorLitro));
        txtTotalPagar.setText(String.format("Total Pagar: %s", nf.format(totalPagar)));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v == edtPrecoLitro && actionId == EditorInfo.IME_ACTION_DONE) {
            calcularConsumoEPagar();
            return true;
        }
        return false;
    }
}
