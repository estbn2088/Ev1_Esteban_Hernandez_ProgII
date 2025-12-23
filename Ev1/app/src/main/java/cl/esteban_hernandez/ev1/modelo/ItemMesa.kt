package cl.esteban_hernandez.ev1.modelo

class ItemMesa (val itemMenu: ItemMenu, val cantidad: Int) {
    fun calcularSubtotal(): Int {
        val subtotal = itemMenu.precio.toInt() * cantidad
        return subtotal
    }
}