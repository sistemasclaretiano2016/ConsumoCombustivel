package br.com.pedrogutierres.consumocombustivel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private EditText edtKM;
    private Spinner spnTipoCombustivel;
    private EditText edtQtdeLitros;
    private EditText edtPrecoLitro;
    private Button btnCalcular;
    private TextView txtConsumoMedio;
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
        txtTotalPagar = (TextView) findViewById(R.id.txt_total_pagar);
        btnCalcular = (Button) findViewById(R.id.btn_calcular);

        btnCalcular.setOnClickListener(this);

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
        } catch (Exception ex) {
            Toast.makeText(this, "Favor informar os KM rodados.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            qtdeLitros = Double.parseDouble(edtQtdeLitros.getText().toString());
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

        double consumoMedio = kmRodados / qtdeLitros;
        double totalPagar = qtdeLitros * precoCombustivel;

        txtConsumoMedio.setText(String.format("Consumo médio por LT: %s", consumoMedio));
        txtTotalPagar.setText(String.format("Total Pagar: %s", nf.format(totalPagar)));
    }
}
