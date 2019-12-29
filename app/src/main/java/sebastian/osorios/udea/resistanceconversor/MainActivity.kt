package sebastian.osorios.udea.resistanceconversor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double.parseDouble
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recistanceValue : String
        val firstBand : NumberPicker = findViewById(R.id.firstBand)
        val secondBand : NumberPicker = findViewById(R.id.secondBancd)
        val thirdBand : NumberPicker = findViewById(R.id.thirdBand)
        val editText : EditText = findViewById<EditText>(R.id.valuesNumber)
        val colorTwoBand = arrayOf("Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue","Purple","Gray","White")
        val colors = arrayOf("Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue","Gold","Silver")
        firstBand.minValue = 0
        firstBand.maxValue = colorTwoBand.size - 1
        firstBand.displayedValues = colorTwoBand
        secondBand.minValue = 0
        secondBand.maxValue = colorTwoBand.size - 1
        secondBand.displayedValues = colorTwoBand
        thirdBand.minValue = 0
        thirdBand.maxValue = colors.size - 1
        thirdBand.displayedValues = colors

        calculate.setOnClickListener {
            if(!editText.text.equals("")){
                val array :Array<String> = getNumericValues(editText.text.toString())
                val number = array[0]
                val letterValue = validateMultiplier(array[1],this)
                if (letterValue != null) {
                    setColorsToNumberPicker(number,letterValue,this)
                }else{
                    setColorNumberPicker(number,this)
                }
            }
        }

        delete.setOnClickListener {
            editText.text = null
            firstBand.value=0
            secondBand.value=0
            thirdBand.value=0
        }
        firstBand.setOnValueChangedListener { _, _, newVal ->

            // Display the picker selected value to text view
            if(thirdBand.value==0){
                valuesNumber.setText(firstBand.value.toString() + secondBand.value.toString())
            } else if(secondBand.value ==0 && thirdBand.value==7){
                valuesNumber.setText(firstBand.value.toString())
            }else if(secondBand.value==0 && thirdBand.value==8){
                valuesNumber.setText(multiplicador(firstBand.value.toFloat(),validateColor("7")).toString())
            } else{
                val resultado : Float = multiplicador((firstBand.value.toString() + secondBand.value.toString()).toFloat(),validateColor(thirdBand.value.toString()))
                removeDecimals(resultado)
            }

        }


        secondBand.setOnValueChangedListener{_,_,newVal ->

            if(thirdBand.value==0){
                valuesNumber.setText(firstBand.value.toString() + secondBand.value.toString())
            } else if(secondBand.value ==0 && thirdBand.value==7){
                valuesNumber.setText(firstBand.value.toString())
            }else if(secondBand.value==0 && thirdBand.value==8){
                valuesNumber.setText(multiplicador(firstBand.value.toFloat(),validateColor("7")).toString())
            } else{
                val resultado : Float = multiplicador((firstBand.value.toString() + secondBand.value.toString()).toFloat(),validateColor(thirdBand.value.toString()))
                removeDecimals(resultado)
            }
        }

        thirdBand.setOnValueChangedListener{_,_,newVal ->
            if(thirdBand.value==0){
                valuesNumber.setText(firstBand.value.toString() + secondBand.value.toString())
            } else if(secondBand.value ==0 && thirdBand.value==7){
                valuesNumber.setText(firstBand.value.toString())
            }else if(secondBand.value==0 && thirdBand.value==8){
                valuesNumber.setText(multiplicador(firstBand.value.toFloat(),validateColor("7")).toString())
            } else{
                val resultado : Float = multiplicador((firstBand.value.toString() + secondBand.value.toString()).toFloat(),validateColor(thirdBand.value.toString()))
                removeDecimals(resultado)
            }

        }
    }
    fun setColorsToNumberPicker(number : String,letter : String,context: Context){
        var numberZeros : Int = letter.toInt()
        val lastNumbers : String
        firstBand.value = number[0].toString().toInt()
        if(number.length==1){
            secondBancd.value=0
            numberZeros = numberZeros - 1
            lastNumbers = ""
        }else {
            secondBancd.value = number[1].toString().toInt()
            lastNumbers = number.substring(2)
        }
        for(i in lastNumbers.indices){
            if(lastNumbers[i].toString().equals("0")){
                numberZeros = numberZeros + 1
            }else{
                val alert = AlertDialog.Builder(context)
                alert.setTitle("Error")
                alert.setMessage("Valor de la resistencia incorrecto")
                alert.setPositiveButton(
                    "Confirmar", DialogInterface.OnClickListener { dialogo1, id -> null })
                alert.show()
                break
            }
        }
        thirdBand.value=numberZeros

    }

    fun multiplicador(number : Float , mutiply : Float): Float{
        return number * mutiply
    }
    fun setColorNumberPicker(number : String,context: Context){
        val lastNumbers : String
        var numberZeros : Int = 0
        firstBand.value = number[0].toString().toInt()
        if(number.length==1){
            secondBancd.value=0
            thirdBand.value=10
        }else {
            secondBancd.value = number[1].toString().toInt()
            lastNumbers = number.substring(2)
            for(i in lastNumbers.indices){
                if(lastNumbers[i].toString().equals("0")){
                    numberZeros = numberZeros + 1
                }else{
                    val alert = AlertDialog.Builder(context)
                    alert.setTitle("Error")
                    alert.setMessage("Valor de la resistencia incorrecto")
                    alert.setPositiveButton(
                        "Confirmar", DialogInterface.OnClickListener { dialogo1, id -> null })
                    alert.show()
                }
            }
            thirdBand.value =numberZeros
        }

    }


    fun validateColor(number : String) : Float {
        var zeros : Float = 0F
        when (number) {
            "1" -> {
                zeros = 10F
            }
            "2" -> {
                zeros = 100F
            }
            "3" -> {
                zeros = 1000F
            }
            "4" -> {
                zeros = 10000F
            }
            "5" -> {
                zeros = 100000F
            }
            "6" -> {
                zeros = 1000000F
            }
            "7" -> {
                zeros = 0.1F
            }
            "8" -> {
                zeros = 0.01F
            }
            "0" -> {
                zeros = 1F
            }
        }
        return zeros
    }

    fun validateMultiplier(letter : String, context: Context): String? {
        var multiplier : String? = null
        when (letter.toUpperCase()) {
            "K" -> {
                multiplier =  "3"
            }
            "M"-> {
                multiplier = "6"
            }
            "" ->{
                multiplier = null
            }
            else ->{
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Error")
            alert.setMessage("Valor de la resistencia incorrecto")
            alert.setPositiveButton(
                "Confirmar", DialogInterface.OnClickListener { dialogo1, id -> null })
            alert.show()
        }
        }
        return multiplier
    }

    fun getNumericValues(cadena: String): Array<String> {

        val numbers = StringBuilder()
        val letters = StringBuilder()

        for (i in cadena.indices) {
            var numeric = true
            try {
                val num = parseDouble(cadena[i].toString())
            } catch (e: NumberFormatException) {
                numeric = false
            }

            if (numeric) {
                //es un valor numerico.
                numbers.append(cadena[i].toString())
            } else {
                letters.append(cadena[i].toString())
            }

        }
        val arreglo : Array<String> = arrayOf(numbers.toString(),letters.toString())
        return arreglo
    }

    fun removeDecimals(resultado : Float){
        val indice = resultado.toString().indexOf(".")
        val decimales : String = resultado.toString().substring(indice+1)
        var flag : Boolean = false
        for(i in decimales.indices){
            val s : String = resultado.toString().substring(0,indice)
            if(!decimales[i].toString().equals("0")){
                flag = true
            }
        }
        if(flag){
            val decimalFormat = DecimalFormat("#0.00")
            valuesNumber.setText(decimalFormat.format(resultado).toString())
        }else{
            valuesNumber.setText(resultado.toString().substring(0,indice))
        }
    }

}
