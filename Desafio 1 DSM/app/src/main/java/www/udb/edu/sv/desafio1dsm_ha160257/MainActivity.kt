package www.udb.edu.sv.desafio1dsm_ha160257

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCalcularPromedio = findViewById<Button>(R.id.button_calcular_promedio)
        val buttonCalcularSalario = findViewById<Button>(R.id.button_calcular_salario)
        val buttonCalculadoraBasica = findViewById<Button>(R.id.button_calculadora_basica)

        buttonCalcularPromedio.setOnClickListener {
            startActivity(Intent(this, CalcularPromedioActivity::class.java))
        }

        buttonCalcularSalario.setOnClickListener {
            startActivity(Intent(this, CalcularSalarioActivity::class.java))
        }

        buttonCalculadoraBasica.setOnClickListener {
            startActivity(Intent(this, CalculadoraBasicaActivity::class.java))
        }
    }
}
