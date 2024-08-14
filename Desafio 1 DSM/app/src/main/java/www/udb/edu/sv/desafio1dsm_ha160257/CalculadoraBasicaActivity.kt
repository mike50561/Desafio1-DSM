package www.udb.edu.sv.desafio1dsm_ha160257

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CalculadoraBasicaActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var operando1: EditText
    private lateinit var operando2: EditText
    private lateinit var spinnerOperacion: Spinner
    private lateinit var buttonCalcular: Button
    private lateinit var textViewResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora_basica)

        // Configurar el DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        operando1 = findViewById(R.id.editTextOperando1)
        operando2 = findViewById(R.id.editTextOperando2)
        spinnerOperacion = findViewById(R.id.spinnerOperacion)
        buttonCalcular = findViewById(R.id.buttonCalcularOperacion)
        textViewResultado = findViewById(R.id.textViewResultadoOperacion)

        // Configurar Spinner
        val operaciones = resources.getStringArray(R.array.operaciones)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operaciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOperacion.adapter = adapter

        // Configurar el botón para realizar el cálculo
        buttonCalcular.setOnClickListener {
            calcular()
        }
    }

    private fun calcular() {
        val operando1Value = operando1.text.toString().toDoubleOrNull()
        val operando2Value = operando2.text.toString().toDoubleOrNull()
        val operacionSeleccionada = spinnerOperacion.selectedItem.toString()

        if (operando1Value != null && operando2Value != null) {
            val resultado = when (operacionSeleccionada) {
                "Suma" -> operando1Value + operando2Value
                "Resta" -> operando1Value - operando2Value
                "Multiplicación" -> operando1Value * operando2Value
                "División" -> if (operando2Value != 0.0) operando1Value / operando2Value else "Error: División por cero"
                else -> "Operación no válida"
            }
            textViewResultado.text = "Resultado: $resultado"
        } else {
            textViewResultado.text = "Error: Ingrese valores válidos"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_calcular_promedio -> startActivity(Intent(this, CalcularPromedioActivity::class.java))
            R.id.nav_calcular_salario -> startActivity(Intent(this, CalcularSalarioActivity::class.java))
            R.id.nav_calculadora_basica -> startActivity(Intent(this, CalculadoraBasicaActivity::class.java))
            R.id.nav_menu_principal -> {
                // Regresar al menú principal
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
