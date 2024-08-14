package www.udb.edu.sv.desafio1dsm_ha160257

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CalcularSalarioActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var editTextNombreEmpleado: EditText
    private lateinit var editTextSalarioBase: EditText
    private lateinit var buttonCalcularSalario: Button
    private lateinit var textViewResultadoSalario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_salario)

        // Inicializar vistas
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        editTextNombreEmpleado = findViewById(R.id.editTextNombreEmpleado)
        editTextSalarioBase = findViewById(R.id.editTextSalarioBase)
        buttonCalcularSalario = findViewById(R.id.buttonCalcularSalario)
        textViewResultadoSalario = findViewById(R.id.textViewResultadoSalario)

        // Configurar el botón para calcular el salario
        buttonCalcularSalario.setOnClickListener {
            calcularSalario()
        }
    }

    private fun calcularSalario() {
        val nombreEmpleado = editTextNombreEmpleado.text.toString()
        val salarioBase = editTextSalarioBase.text.toString().toDoubleOrNull()

        if (nombreEmpleado.isEmpty() || salarioBase == null || salarioBase <= 0) {
            textViewResultadoSalario.text = "Por favor, ingrese un nombre válido y un salario base mayor a 0."
            return
        }

        // Calcular descuento renta
        val renta = calcularDescuentoRenta(salarioBase)

        // Calcular AFP y ISSS
        val afp = salarioBase * 0.0725
        val isss = salarioBase * 0.03

        // Calcular salario neto
        val salarioNeto = salarioBase - renta - afp - isss

        textViewResultadoSalario.text = """
            Nombre del empleado: $nombreEmpleado
            
            Salario base: $${"%.2f".format(salarioBase)}
            
            Descuentos aplicados:
            Renta: $${"%.2f".format(renta)}
            AFP (7.25%): $${"%.2f".format(afp)}
            ISSS (3%): $${"%.2f".format(isss)}
            
            Salario Neto: $${"%.2f".format(salarioNeto)}
        """.trimIndent()
    }

    private fun calcularDescuentoRenta(salarioBase: Double): Double {
        return when {
            salarioBase <= 472.00 -> 0.0
            salarioBase <= 895.24 -> (salarioBase - 472.00) * 0.10 + 17.67
            salarioBase <= 2038.10 -> (salarioBase - 895.24) * 0.20 + 60.00
            else -> (salarioBase - 2038.10) * 0.30 + 288.57
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
            R.id.nav_menu_principal -> startActivity(Intent(this, MainActivity::class.java))
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
