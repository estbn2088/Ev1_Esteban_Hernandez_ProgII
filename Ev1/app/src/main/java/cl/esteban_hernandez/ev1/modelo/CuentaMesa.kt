package cl.esteban_hernandez.ev1.modelo

class CuentaMesa (val mesa: Int){
    private val items = mutableListOf<ItemMesa>()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        items.add(ItemMesa(itemMenu, cantidad))
    }

    fun agregarItem(itemMesa: ItemMesa){
        items.add(itemMesa)
    }
    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal()}
    }
    fun calcularPropina(): Int {
        return if (aceptaPropina)
                (calcularTotalSinPropina() * 0.1).toInt()
        else
            0
    }
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}