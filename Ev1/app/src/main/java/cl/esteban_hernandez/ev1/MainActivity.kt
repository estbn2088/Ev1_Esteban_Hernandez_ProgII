package cl.esteban_hernandez.ev1

import android.annotation.SuppressLint
import android.content.ClipData
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.esteban_hernandez.ev1.modelo.CuentaMesa
import cl.esteban_hernandez.ev1.modelo.ItemMenu
import cl.esteban_hernandez.ev1.modelo.ItemMesa


class MainActivity : AppCompatActivity() {
    private var etCantPastelChoclo: EditText? = null
    private var tvTotalPastelChoclo: TextView? = null
    private var etCantCazuela: EditText? = null
    private var tvtotalCazuela: TextView? = null
    private var tvMontoSubtotal: TextView? = null
    private var tvMontoPropina: TextView? = null
    private var tvMontoTotal: TextView? = null
    private var switchPropina: Switch? = null
    val pastelChoclo = ItemMenu ("Pastel de Choclo", "12000" )
    val cazuela = ItemMenu("Cazuela", "10000")
    private val formatoPrecio = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        switchPropina =  findViewById<Switch>(R.id.switchPropina)
        etCantPastelChoclo = findViewById<EditText>(R.id.etCantPastelChoclo)
        etCantCazuela = findViewById<EditText>(R.id.etCantCazuela)
        tvTotalPastelChoclo = findViewById<TextView>(R.id.tvTotalPastelChoclo)
        tvtotalCazuela = findViewById<TextView>(R.id.tvTotalCazuela)
        tvMontoSubtotal = findViewById<TextView>(R.id.tvMontoSubtotal)
        tvMontoPropina = findViewById<TextView>(R.id.tvMontoPropina)
        tvMontoTotal = findViewById<TextView>(R.id.tvMontoTotal)

        val textWatcher: TextWatcher = object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calcularCuenta()
            }
        }

        etCantPastelChoclo?.addTextChangedListener(textWatcher)
        etCantCazuela?.addTextChangedListener(textWatcher)
        switchPropina?.setOnCheckedChangeListener { _, _ ->
            calcularCuenta()
        }
    }
    private fun calcularCuenta() {
        val cantPastelChoclo = etCantPastelChoclo?.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCantCazuela?.text.toString().toIntOrNull() ?: 0

        val item1 = ItemMesa(pastelChoclo, cantPastelChoclo)
        val item2 = ItemMesa(cazuela, cantCazuela)

        val mesa = CuentaMesa(1)
        mesa.aceptaPropina = switchPropina?.isChecked ?: true
        mesa.agregarItem(item1)
        mesa.agregarItem(item2)

        tvTotalPastelChoclo?.setText("$${formatoPrecio.format(item1.calcularSubtotal())}")
        tvtotalCazuela?.setText("$${formatoPrecio.format(item2.calcularSubtotal())}")

        val totalSinPropina = mesa.calcularTotalSinPropina()
        tvMontoSubtotal?.setText("$${formatoPrecio.format(totalSinPropina)}".toString())

        val totalPropina = mesa.calcularPropina()
        tvMontoPropina?.setText("$${formatoPrecio.format(totalPropina)}".toString())

        val totalConPropina = mesa.calcularTotalConPropina()
        tvMontoTotal?.setText("$${formatoPrecio.format(totalConPropina)}".toString())
    }
}

