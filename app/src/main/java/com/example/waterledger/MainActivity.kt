package com.example.waterledger

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.startActivity
import java.io.*
import java.util.*
import android.app.Activity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.R.attr.maxDate
import android.R.attr.maxDate
import android.view.MotionEvent



class MainActivity : AppCompatActivity() {


    private var backPressedTime:Long = 0
    lateinit var backToast:Toast
    override fun onBackPressed() {
        backToast = Toast.makeText(this, "Press Again to Exit.", Toast.LENGTH_LONG)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            //return
            finish();
            finishAffinity();

            System.exit(0);
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE)
            && v is EditText
            && !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.getLeft() - scrcoords[0]
            val y = ev.rawY + v.getTop() - scrcoords[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()
            ) hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null && activity.window.decorView != null
        ) {
            val imm = activity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                activity.window.decorView
                    .windowToken, 0
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)





        val sharedPreferences = getSharedPreferences("SP_INFO", MODE_PRIVATE)


/////////////////////////////////// Interface ///////////////////////////////////////////////////

        val quantity        =   findViewById(R.id.quantity) as          EditText
        val date            =   findViewById(R.id.date) as              EditText
        val rate            =   findViewById(R.id.rate) as              EditText

        val ledger          =   findViewById(R.id.ledger) as            Button
        val calculate       =   findViewById(R.id.calculate) as         Button
        val monthEnd        =   findViewById(R.id.monthEnd) as          Button
        val reset           =   findViewById(R.id.reset) as             Button
        val createFile      =   findViewById(R.id.createFile) as        Button

        val amount          =   findViewById(R.id.amount) as            TextView
        val quantityText    =   findViewById(R.id.quantityText) as      TextView
        val totalText       =   findViewById(R.id.totalText) as         TextView
        val dayText         =   findViewById(R.id.dayText) as           TextView
        val quantityText2   =   findViewById(R.id.quantityText3) as     TextView
        val totalText2      =   findViewById(R.id.totalText2) as        TextView
        val dayText2        =   findViewById(R.id.dayText2) as          TextView
        val dateHeading     =   findViewById(R.id.dateHeading) as       TextView
        val quantityHeading =   findViewById(R.id.quantityHeading) as   TextView
        val rateHeading     =   findViewById(R.id.rateHeading) as       TextView
        val extractDisabled1     =   findViewById(R.id.extractDisabled1) as       TextView


/////////////////////////////////// fetch Date /////////////////////////////////////////////

        val sdf         =   SimpleDateFormat("dd/MM/yyyy")
        val currentDate =   sdf.format(Date())


///////////////////////////////// Shared Pref /////////////////////////////////////////////////




        val name = sharedPreferences.getInt("totalText", 0)
        val age = sharedPreferences.getInt("quantityText", 0)
        val experience = sharedPreferences.getInt("dayText", 0)
        totalText.text = "$name"
        quantityText.text = "$age"
        dayText.text = "$experience"

        val name2 = sharedPreferences.getInt("totalText2", 0)
        val age2 = sharedPreferences.getInt("quantityText2", 0)
        val experience2 = sharedPreferences.getInt("dayText2", 0)
        totalText2.text = "$name2"
        quantityText2.text = "$age2"
        dayText2.text = "$experience2"

        val job = sharedPreferences.getInt("extractDisabled", 0)
        extractDisabled1.text = "$job"


////////////////////////////////// Defaul Values in Text Fields ////////////////////////////////

        date.setText(currentDate)
        rate.setText("50")
        quantity.setText("")

/////////////////////////////////// Pop Up Calender Code //////////////////////////////////////

        @RequiresApi(Build.VERSION_CODES.N)
        fun EditText.transformIntoDatePicker(
            context: Context,
            format: String,
            maxDate: Date? = null
        ) {
            isFocusableInTouchMode = true
            isClickable = true
            isFocusable = true

            val myCalendar = Calendar.getInstance()
            val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val sdf = SimpleDateFormat(format, Locale.UK)
                    setText(sdf.format(myCalendar.time))
                }

            setOnClickListener {
                DatePickerDialog(
                    context, datePickerOnDataSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).run {
                    maxDate?.time?.also { datePicker.maxDate = it }
                    show()
                }
            }
        }

        date.setOnClickListener {
            date.transformIntoDatePicker(this, "dd/MM/yyyy")
            date.transformIntoDatePicker(this, "dd/MM/yyyy", Date())
        }



        val scholar = dayText.text.toString().toInt()
        val scholars = dayText2.text.toString().toInt()
        val bachelor = totalText.text.toString().toInt()
        val bachelors = totalText2.text.toString().toInt()
        val master = totalText.text.toString().toInt()
        val masters = totalText2.text.toString().toInt()




        if (scholar == 0 && scholars == 0 && bachelor == 0 && bachelors == 0) {

            createFile.setEnabled(true);
            createFile.setVisibility(View.INVISIBLE)


        }
        else {

            createFile.setEnabled(false);
            createFile.setVisibility(View.INVISIBLE)



        }


        monthEnd.setEnabled(false);
        monthEnd.setVisibility(View.INVISIBLE)

        extractDisabled1.setVisibility(View.INVISIBLE)

        if (scholar == 0) {

            ledger.performClick()



        }
        else {


            ledger.setVisibility(View.VISIBLE)



        }









/////////////////////////////////////////// Button To Get and Save Entries //////////////////////////////

        //this.currentFocus?.let { view ->
        //    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        //    imm?.hideSoftInputFromWindow(view.windowToken, 0)
        //}

        //getWindow().setSoftInputMode(
        //    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        //)


        calculate.setOnClickListener {

            if (scholar == 0 && scholars == 0 && bachelor == 0 && bachelors == 0) {

                createFile.performClick()

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)



            }
            else {

                Toast.makeText(this, "Entry Saved", Toast.LENGTH_LONG).show()

            }

            //createFile.performClick()


//Change the color of Text Fields if not filled ////////////////////

            if (date.text.trim().length == 0) {

                dateHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                dateHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (quantity.text.trim().length == 0) {

                quantityHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                quantityHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (rate.text.trim().length == 0) {

                rateHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                rateHeading.setTextColor(getResources().getColor(R.color.black))
            }


// If all fields are filled then calcualte the total and set it to text view  ////////////////////////////

            if (date.text.trim().length > 0 && quantity.text.trim().length > 0 && rate.text.trim().length > 0) {



                var file = "data.txt"
                var spaceX = "            "
                //var space = spaceX
                val datePicker = date.text.toString()
                val quantityPicker = quantity.text.toString().toInt()
                val rate = rate.text.toString().toInt()
                val calcula = quantityPicker * rate
                val calcula1 = calcula.toString()


                //val space = "          $quantityPicker @$rate$spaceX"
                val calcula2 = "$calcula1"
                amount.setText("$calcula1")

                val hiddenText = totalText.text.toString()
                val hiddenText1 = hiddenText.toInt()
                val calculaX = hiddenText1 + calcula
                val calculaZ = calculaX.toString()
                totalText.setText(calculaZ)

                val hiddenCall = quantityText.text.toString()
                val hiddenCall1 = hiddenCall.toInt()
                val calculaA = hiddenCall1 + quantityPicker
                val calculaB = calculaA.toString()
                quantityText.setText(calculaB)

                val hiddenSay = dayText.text.toString()
                val hiddenSay1 = hiddenSay.toInt()
                val calculaAlif = hiddenSay1 + 1
                val calculaBay = calculaAlif.toString()
                dayText.setText(calculaBay)





//Read the text values from file (old values to be updated)  ////////////////////////////////

                var fileInputStream: FileInputStream? = null
                fileInputStream = openFileInput(file)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)

                val StringBuilder: StringBuilder = StringBuilder()
                var text: String? = null

                while ({ text = bufferedReader.readLine(); text }() != null) {
                    StringBuilder.append(text).append("\n")
                }


// write new values + old values in text file ///////////////////////////////////////////////
                    if (quantity.text.trim().length == 1) {

                        var space = "     $quantityPicker   @$rate $spaceX"


                        var fileOutputStream: FileOutputStream

                        try {

                            fileOutputStream = openFileOutput(file, MODE_PRIVATE)
                            fileOutputStream.write(StringBuilder.toString().toByteArray())
                            fileOutputStream.write(datePicker.toByteArray())
                            fileOutputStream.write(space.toByteArray())
                            fileOutputStream.write(calcula2.toByteArray())


                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }




                        while ({ text = bufferedReader.readLine(); text }() != null) {
                            StringBuilder.append(text)
                        }

                    }
                else if (quantity.text.trim().length == 2) {

                    var space = "     $quantityPicker  @$rate $spaceX"


                    var fileOutputStream: FileOutputStream

                    try {

                        fileOutputStream = openFileOutput(file, MODE_PRIVATE)
                        fileOutputStream.write(StringBuilder.toString().toByteArray())
                        fileOutputStream.write(datePicker.toByteArray())
                        fileOutputStream.write(space.toByteArray())
                        fileOutputStream.write(calcula2.toByteArray())


                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }




                    while ({ text = bufferedReader.readLine(); text }() != null) {
                        StringBuilder.append(text)
                    }
                }


                        else {
                            var space = "     $quantityPicker @$rate $spaceX"




                        var fileOutputStream: FileOutputStream

                        try {

                            fileOutputStream = openFileOutput(file, MODE_PRIVATE)
                            fileOutputStream.write(StringBuilder.toString().toByteArray())
                            fileOutputStream.write(datePicker.toByteArray())
                            fileOutputStream.write(space.toByteArray())
                            fileOutputStream.write(calcula2.toByteArray())


                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }




                        while ({ text = bufferedReader.readLine(); text }() != null) {
                            StringBuilder.append(text)
                        }
                    }

                    extractDisabled1.setText("1")
// Saving Shared Pref. So that when application re launced it will set the last values to text views /////////////////////////////////////

                val name = Integer.parseInt(totalText.text.toString().trim())
                val age = Integer.parseInt(quantityText.text.toString().trim())
                val experience = Integer.parseInt(dayText.text.toString().trim())
                val name2 = Integer.parseInt(totalText2.text.toString().trim())
                val age2 = Integer.parseInt(quantityText2.text.toString().trim())
                val experience2 = Integer.parseInt(dayText2.text.toString().trim())
                val job = Integer.parseInt(extractDisabled1.text.toString().trim())



                val editor = sharedPreferences.edit()


                editor.putInt("totalText", name)
                editor.putInt("quantityText", age)
                editor.putInt("dayText", experience)
                editor.putInt("totalText2", name2)
                editor.putInt("quantityText2", age2)
                editor.putInt("dayText2", experience2)


                editor.putInt("totalText3", name)
                editor.putInt("quantityTex3", age)
                editor.putInt("dayText3", experience)
                editor.putInt("totalText4", name2)
                editor.putInt("quantityText4", age)
                editor.putInt("dayText4", experience2)

                editor.putInt("extractDisabled", job)


                editor.apply()




                Toast.makeText(this, "Entry Saved", Toast.LENGTH_LONG).show()


            }
                else
                {
                    Toast.makeText(this, "Enter All Values (Date, Quanitiy, Rate)", Toast.LENGTH_LONG).show()
                }


        }


////////////////////////////////////// Close the month With Totals Saved on Other File /////////////////////////////////////

        monthEnd.setOnClickListener {


            if (date.text.trim().length == 0) {

                dateHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                dateHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (quantity.text.trim().length == 0) {

                quantityHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                quantityHeading.setTextColor(getResources().getColor(R.color.black))
            }

            if (rate.text.trim().length == 0) {

                rateHeading.setTextColor(getResources().getColor(R.color.red))

            }
            else {
                rate.setTextColor(getResources().getColor(R.color.black))
            }




            if (date.text.trim().length > 0 && quantity.text.trim().length > 0 && rate.text.trim().length > 0) {


            val file = "data.txt"
            val datePicker = date.text.toString()
            val drawLine = "==================================="
            val quantityPicker = quantity.text.toString().toInt()
            val rate = rate.text.toString().toInt()
            val calcula = quantityPicker * rate
            val calcula1 = calcula.toString()
            val space = "          $quantityPicker @$rate                       "
            val calcula2 = "$calcula1"
            amount.setText("$calcula1")

            val hiddenText = totalText.text.toString()
            val hiddenText1 = hiddenText.toInt()
            val calculaX = hiddenText1 + calcula
            val calculaZ = calculaX.toString()
            totalText.setText(calculaZ)
            totalText2.setText(calculaZ)

            val hiddenCall = quantityText.text.toString()
            val hiddenCall1 = hiddenCall.toInt()
            val calculaA = hiddenCall1 + quantityPicker
            val calculaB = calculaA.toString()
            quantityText.setText(calculaB)
            quantityText2.setText(calculaB)

            val hiddenSay = dayText.text.toString()
            val hiddenSay1 = hiddenSay.toInt()
            //val calculaAlif = hiddenSay1 + 1
            val calculaBay = hiddenSay1.toString()
            dayText.setText(calculaBay)
            dayText2.setText(calculaBay)

            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput(file)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val StringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while ({ text = bufferedReader.readLine(); text }() != null) {
                StringBuilder.append(text).append("\n")
            }


            var fileOutputStream: FileOutputStream

            try {

                fileOutputStream = openFileOutput(file, MODE_PRIVATE)
                fileOutputStream.write(StringBuilder.toString().toByteArray())
                //fileOutputStream.write(datePicker.toByteArray())
                //fileOutputStream.write(space.toByteArray())
                //fileOutputStream.write(calcula2.toByteArray())
                fileOutputStream.write("$drawLine\n".toByteArray())
                fileOutputStream.write("    Days: $calculaBay           Bottles: $calculaB       Total: $calculaZ".toByteArray())
                fileOutputStream.write("\n$drawLine".toByteArray())


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()


            }





            totalText.text = "0"
            quantityText.text = "0"
            dayText.text = "0"

                val name = Integer.parseInt(totalText.text.toString().trim())
                val age = Integer.parseInt(quantityText.text.toString().trim())
                val experience = Integer.parseInt(dayText.text.toString().trim())
                val name2 = Integer.parseInt(totalText2.text.toString().trim())
                val age2 = Integer.parseInt(quantityText2.text.toString().trim())
                val experience2 = Integer.parseInt(dayText2.text.toString().trim())
                val editor = sharedPreferences.edit()

                editor.putInt("totalText", name)
                editor.putInt("quantityText", age)
                editor.putInt("dayText", experience)
                editor.putInt("totalText2", name2)
                editor.putInt("quantityText2", age2)
                editor.putInt("dayText2", experience2)
                editor.apply()

            Toast.makeText(this, "Month Closed", Toast.LENGTH_LONG).show()

                }
                else
                {
                    Toast.makeText(this, "Enter All Values (Date, Quanitiy, Rate)", Toast.LENGTH_LONG).show()
                }



        }


    ////////////////////////////////////////////// Opened Second Activity and Shows Saved Data /////////////////////////////

        ledger.setOnClickListener {

            if (scholar == 0 && scholars == 0 && bachelor == 0 && bachelors == 0) {


                Toast.makeText(this, "Ledger is Empty", Toast.LENGTH_LONG).show()


            } else {






            //val file = "data.txt"


            //var fileInputStream: FileInputStream? = null
            //fileInputStream = openFileInput(file)
            //val inputStreamReader = InputStreamReader(fileInputStream)
            //val bufferedReader = BufferedReader(inputStreamReader)

            //val StringBuilder: StringBuilder = StringBuilder()
            //var text: String? = null

            //while ({ text = bufferedReader.readLine(); text }() != null) {
            //    StringBuilder.append(text).append("\n")
            //}


            val intent = Intent(this, SecondActivity::class.java)
                //intent.putExtra("ggwp", "$StringBuilder")

                startActivity(intent)
        }

        }


///////////////////////////////// Will Reset All Text Fields (User Inputs //////////////////////////////////

        reset.setOnClickListener {

            date.setText("")
            rate.setText("")
            quantity.setText("")
            amount.setText("")



        }

//////////////////////////////////// Extract Text File To Storage (NOT WORKING) ////////////////////////////////



        createFile.setOnClickListener {

            //var file = "data.txt"
            //var NamaFile="Ledger.txt"
            //var strwrt:FileWriter
            //strwrt=FileWriter(File("sdcard/${NamaFile}"))
            //strwrt.write(file)
            //strwrt.close()
            val file = "data.txt"

            var fileOutputStream: FileOutputStream

            try {
                fileOutputStream = openFileOutput(file, MODE_PRIVATE)
            }

            catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }


            //createFile.setClickable(false);






            Toast.makeText(this, "File Created", Toast.LENGTH_LONG).show()



        }






    }


}


fun Resources.getRawTextFile(@RawRes id: Int) =
    openRawResource(id).bufferedReader().use { it.readText() }














