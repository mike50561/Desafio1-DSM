package www.udb.edu.sv.desafio1dsm_ha160257
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle

class CalcularPromedioActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_promedio)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextNota1 = findViewById<EditText>(R.id.editTextNota1)
        val editTextNota2 = findViewById<EditText>(R.id.editTextNota2)
        val editTextNota3 = findViewById<EditText>(R.id.editTextNota3)
        val editTextNota4 = findViewById<EditText>(R.id.editTextNota4)
        val editTextNota5 = findViewById<EditText>(R.id.editTextNota5)
        val buttonCalcularPromedio = findViewById<Button>(R.id.buttonCalcularPromedio)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)

        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonCalcularPromedio.setOnClickListener {
            val nombreEstudiante = editTextNombre.text.toString()
            val nota1 = editTextNota1.text.toString().toDoubleOrNull()
            val nota2 = editTextNota2.text.toString().toDoubleOrNull()
            val nota3 = editTextNota3.text.toString().toDoubleOrNull()
            val nota4 = editTextNota4.text.toString().toDoubleOrNull()
            val nota5 = editTextNota5.text.toString().toDoubleOrNull()

            if (nombreEstudiante.isEmpty() ||
                nota1 == null || nota2 == null || nota3 == null || nota4 == null || nota5 == null ||
                !validateNotas(nota1, nota2, nota3, nota4, nota5)) {
                textViewResultado.text = "Por favor, ingrese todas las notas correctamente (entre 0 y 10)."
                return@setOnClickListener
            }

            val promedio = calcularPromedio(nota1, nota2, nota3, nota4, nota5)
            val resultado = if (promedio >= 6) "Aprobado" else "Reprobado"

            val resultadoTexto = "Nombre Del Estudiante: $nombreEstudiante\n\n" +
                    "Nota final: ${"%.2f".format(promedio)}\n\n" +
                    "Estado: $resultado"

            textViewResultado.text = resultadoTexto
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_calcular_promedio -> startActivity(Intent(this, CalcularPromedioActivity::class.java))
                R.id.nav_calcular_salario -> startActivity(Intent(this, CalcularSalarioActivity::class.java))
                R.id.nav_calculadora_basica -> startActivity(Intent(this, CalculadoraBasicaActivity::class.java))
                R.id.nav_menu_principal -> startActivity(Intent(this, MainActivity::class.java))
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun validateNotas(vararg notas: Double): Boolean {
        return notas.all { it in 0.0..10.0 }
    }

    private fun calcularPromedio(nota1: Double, nota2: Double, nota3: Double, nota4: Double, nota5: Double): Double {
        return (nota1 * 0.15) + (nota2 * 0.15) + (nota3 * 0.20) + (nota4 * 0.25) + (nota5 * 0.25)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
