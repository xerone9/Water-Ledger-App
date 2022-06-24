package com.example.waterledger

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ContextUtils.getActivity
import java.io.*
import android.content.DialogInterface
import android.icu.text.SimpleDateFormat
import androidx.core.content.ContextCompat.startActivity
import java.util.*


val sdf         =   SimpleDateFormat("dd/MM/yyyy")
val currentDate =   sdf.format(Date())

class WriteFile: AsyncTask<String, Int, String>() {
    private val mFolder = "/WaterLedger"
    lateinit var folder: File
    internal var writeThis = "string to cacheApp.txt"
    internal var cacheApptxt = "Ledger.txt"
    override fun doInBackground(vararg writethis: String): String? {
        val received = writethis[0]
        if(received.isNotEmpty()){
            writeThis = received
        }
        folder = File(Environment.getExternalStorageDirectory(),"$mFolder/")
        if(!folder.exists() || folder.exists()){
            folder.mkdir()
            val readME = File(folder, cacheApptxt)
            val file = File(readME.path)
            val out: BufferedWriter
            try {
                out = BufferedWriter(FileWriter(file, true), 1024)
                //out.newLine()
                out.write("****Ledger Updated On Dated:$currentDate*****\n")
                out.newLine()
                out.write(writeThis)
                out.newLine()
                //out.write("**************Ledger Updated On Dated: $currentDate*******\n")

                out.close()
                Log.d("Output_Success", folder.path)
            } catch (e: Exception) {
                Log.d("Output_Exception", "$e")
            }
        }
        return folder.path

    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        if(result.isNotEmpty()){
            //implement an interface or do something
            Log.d("onPostExecuteSuccess", result)
        }else{
            Log.d("onPostExecuteFailure", result)
        }
    }

}

class WriteFile2: AsyncTask<String, Int, String>() {
    private val mFolder = "/WaterLedger"
    lateinit var folder: File
    internal var writeThis2 = "string to cacheApp.txt"
    internal var cacheApptxt2 = "Ledger.txt"
    override fun doInBackground(vararg writethis: String): String? {
        val received2 = writethis[0]
        if(received2.isNotEmpty()){
            writeThis2 = received2
        }
        folder = File(Environment.getExternalStorageDirectory(),"$mFolder/")
        if(!folder.exists() || folder.exists()){
            folder.mkdir()
            val readME = File(folder, cacheApptxt2)
            val file = File(readME.path)
            val out: BufferedWriter
            try {
                out = BufferedWriter(FileWriter(file, false), 1024)
                //out.newLine()
                out.write("")
                //out.write("**************Ledger Updated On Dated: $currentDate*******\n")

                out.close()
                Log.d("Output_Success", folder.path)
            } catch (e: Exception) {
                Log.d("Output_Exception", "$e")
            }
        }
        return folder.path

    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        if(result.isNotEmpty()){
            //implement an interface or do something
            Log.d("onPostExecuteSuccess", result)
        }else{
            Log.d("onPostExecuteFailure", result)
        }
    }

}


class ReadandWrite {
    private val mREAD = 9
    private val mWRITE = 10
    private var readAndWrite: Boolean = false
    fun readAndwriteStorage(ctx: Context, atividade: AppCompatActivity): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            readAndWrite = true
        } else {
            val mRead = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE)
            val mWrite = ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (mRead != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(atividade, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), mREAD)
            } else {
                readAndWrite = true
            }

            if (mWrite != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(atividade, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), mWRITE)
            } else {
                readAndWrite = true
            }
        }
        return readAndWrite
    }
}
class ReadandWrite2 {
    private val mREAD2 = 9
    private val mWRITE2 = 10
    private var readAndWrite2: Boolean = false
    fun readAndwriteStorage(ctx: Context, atividade: AppCompatActivity): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            readAndWrite2 = true
        } else {
            val mRead2 = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE)
            val mWrite2 = ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (mRead2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(atividade, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), mREAD2)
            } else {
                readAndWrite2= true
            }

            if (mWrite2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(atividade, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), mWRITE2)
            } else {
                readAndWrite2 = true
            }
        }
        return readAndWrite2
    }
}

fun dialogYesOrNo(
    activity: Activity,
    title: String,
    message: String,
    listener: DialogInterface.OnClickListener
) {
    val builder = AlertDialog.Builder(activity)


    builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
        dialog.dismiss()
        listener.onClick(dialog, id)
    })
    builder.setNegativeButton("", DialogInterface.OnClickListener { monologue, cd ->
        monologue.dismiss()
        listener.onClick(monologue, cd)
    })
    val alert = builder.create()
    alert.setTitle(title)
alert.setMessage(message)
    alert.show()
}

class SecondActivity : AppCompatActivity() {

    //private var backPressedTime:Long = 0
    lateinit var backToast:Toast
    override fun onBackPressed() {
        backToast = Toast.makeText(this, "Press Again to Exit.", Toast.LENGTH_LONG)
        //if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("ggwp", "$StringBuilder")

            startActivity(intent)
            //return
            //finish();
            //finishAffinity();

            //System.exit(0);
        //} else {
        //    backToast.show()
        //}
        //backPressedTime = System.currentTimeMillis()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        val button = findViewById(R.id.button) as Button
        val monthEnd2 = findViewById(R.id.monthEnd2) as Button
        val extract = findViewById(R.id.extract) as Button
        val textView5 = findViewById(R.id.textView) as TextView
        val textView3 = findViewById(R.id.textView3) as TextView
        val textView = findViewById(R.id.textView5) as TextView
        val textView13 = findViewById(R.id.textView11) as TextView
        val textView12 = findViewById(R.id.textView12) as TextView
        val textView11 = findViewById(R.id.textView13) as TextView
        val meg = findViewById(R.id.meg) as TextView
        val extractDisabled = findViewById(R.id.extractDisabled) as TextView




        //if (scholarHain == 0) {

        //monthEnd2.setEnabled(false);
        //monthEnd2.setVisibility(View.INVISIBLE)


        //}
        //else {

        //  monthEnd2.setEnabled(true);
        //  monthEnd2.setVisibility(View.VISIBLE)


        //}


        val profileName = intent.getStringExtra("ggwp")
        val textView7 = findViewById(R.id.textView7) as TextView
        textView7.setMovementMethod(ScrollingMovementMethod())
        findViewById<TextView>(R.id.textView7).apply { text = "$profileName" }


        val name3 = sharedPreferences.getInt("totalText", 0)
        val age3 = sharedPreferences.getInt("quantityText", 0)
        val experience3 = sharedPreferences.getInt("dayText", 0)
        textView.text = "$name3"
        textView3.text = "$age3"
        textView5.text = "$experience3"

        val name4 = sharedPreferences.getInt("totalText2", 0)
        val age4 = sharedPreferences.getInt("quantityText2", 0)
        val experience4 = sharedPreferences.getInt("dayText2", 0)
        textView11.text = "$name4"
        textView12.text = "$age4"
        textView13.text = "$experience4"

        val job = sharedPreferences.getInt("extractDisabled", 0)
        extractDisabled.text = "$job"

        val boj = sharedPreferences.getInt("meg", 1)
        meg.text = "$boj"

        textView.setVisibility(View.INVISIBLE)
        textView3.setVisibility(View.INVISIBLE)
        textView5.setVisibility(View.INVISIBLE)
        textView11.setVisibility(View.INVISIBLE)
        textView12.setVisibility(View.INVISIBLE)
        textView13.setVisibility(View.INVISIBLE)
        extractDisabled.setVisibility(View.INVISIBLE)
        meg.setVisibility(View.INVISIBLE)


        val scholarHain = textView5.text.toString().toInt()

        if (scholarHain == 0) {

            monthEnd2.setEnabled(false);
            monthEnd2.setVisibility(View.INVISIBLE)


        } else {

            monthEnd2.setEnabled(true);
            monthEnd2.setVisibility(View.VISIBLE)


        }


        val scholarThay = extractDisabled.text.toString().toInt()

        //  if (scholarThay == 1) {

        //     extract.setEnabled(true);
        //     extract.setVisibility(View.VISIBLE)


        //}
        //else {

        //  extract.setEnabled(false);
        //  extract.setVisibility(View.INVISIBLE)


        //}

        //val scholar = textView7.text.toString().toInt()


        val file = "data.txt"
        var fileInputStream: FileInputStream? = null
        fileInputStream = openFileInput(file)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        val StringBuilder: StringBuilder = StringBuilder()
        var text: String? = null

        while ({ text = bufferedReader.readLine(); text }() != null) {
            StringBuilder.append(text).append("\n")
        }

        textView7.setText("$StringBuilder")



        button.setOnClickListener {


            val file = "data.txt"

            var fileOutputStream: FileOutputStream

            try {

                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write("".toByteArray())


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            textView7.setText("")


            val name = 0
            val age = 0
            val experience = 0
            val name2 = 0
            val age2 = 0
            val experience2 = 0
            val editor = sharedPreferences.edit()

            editor.putInt("totalText", name)
            editor.putInt("quantityText", age)
            editor.putInt("dayText", experience)
            editor.putInt("totalText2", name2)
            editor.putInt("quantityText2", age2)
            editor.putInt("dayText2", experience2)
            editor.apply()

            dialogYesOrNo(
                this,
                "Confirmation !!!",
                "Would you like to wipe file from storage as well?",
                DialogInterface.OnClickListener { dialog, id ->

                    //do whatever you need to do when user presses "Yes"
                    var pathToFileCreated = ""
                    val anRW2 = ReadandWrite2().readAndwriteStorage(this, this)
                    if (anRW2) {
                        pathToFileCreated = WriteFile2().execute("").get()
                        Log.d("pathToFileCreated", pathToFileCreated)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(
                            this,
                            "Ledger View and Storage File Cleared",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }


                    DialogInterface.OnClickListener { monologue, cd ->

                        //do whatever you need to do when user presses "No"

                        Toast.makeText(this, "Ledger View Cleared", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    //dialogYesOrNo(
                    //   this,
                    // "Question",
                    // "Alert!!! It will replace the old file. Would you like to Continue?",
                    // DialogInterface.OnClickListener { dialog, id ->
                    // do whatever you need to do when user presses "Yes"
                })


            //}
        }

        monthEnd2.setOnClickListener {


            val file = "data.txt"
            //val datePicker = date.text.toString()
            val drawLine = "========================================"
            //val quantityPicker = quantity.text.toString().toInt()
            //val rate = rate.text.toString().toInt()
            //val calcula = quantityPicker * rate
            //val calcula1 = calcula.toString()
            //val space = "          $quantityPicker @$rate                       "
            //val calcula2 = "$calcula1"
            //amount.setText("$calcula1")

            val calculaZ = textView5.text.toString()
            textView5.setText(calculaZ)
            textView13.setText(calculaZ)

            val calculaB = textView3.text.toString()

            textView3.setText(calculaB)
            textView12.setText(calculaB)

            val calculaBay = textView.text.toString()
            textView.setText(calculaBay)
            textView11.setText(calculaBay)

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

                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(StringBuilder.toString().toByteArray())
                //fileOutputStream.write(datePicker.toByteArray())
                //fileOutputStream.write(space.toByteArray())
                //fileOutputStream.write(calcula2.toByteArray())
                fileOutputStream.write("$drawLine\n".toByteArray())
                fileOutputStream.write("  Days: $calculaZ   Bottles: $calculaB    Total: $calculaBay".toByteArray())
                fileOutputStream.write("\n$drawLine".toByteArray())


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()


            }

            textView.setText("0")
            textView3.setText("0")
            textView5.setText("0")
            extractDisabled.setText("1")


            val name3 = Integer.parseInt(textView.text.toString().trim())
            val age3 = Integer.parseInt(textView3.text.toString().trim())
            val experience3 = Integer.parseInt(textView5.text.toString().trim())
            val name4 = Integer.parseInt(textView11.text.toString().trim())
            val age4 = Integer.parseInt(textView12.text.toString().trim())
            val experience4 = Integer.parseInt(textView13.text.toString().trim())
            val job = Integer.parseInt(extractDisabled.text.toString().trim())
            val editor = sharedPreferences.edit()

            editor.putInt("totalText", name3)
            editor.putInt("quantityText", age3)
            editor.putInt("dayText", experience3)
            editor.putInt("totalText2", name4)
            editor.putInt("quantityText2", age4)
            editor.putInt("dayText2", experience4)
            editor.putInt("extractDisabled", job)
            editor.apply()


            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            //val intent = getIntent();
            //finish();
            //startActivity(intent)
            //val intent2 = Intent(this, SecondActivity::class.java)
            //startActivity(intent2)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)

            val intent2 = Intent(this, SecondActivity::class.java)
            intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent2)
            Toast.makeText(this, "Month Closed", android.widget.Toast.LENGTH_LONG).show()

        }

        extract.setOnClickListener {

            //dialogYesOrNo(
            //   this,
            // "Question",
            // "Alert!!! It will replace the old file. Would you like to Continue?",
            // DialogInterface.OnClickListener { dialog, id ->
            // do whatever you need to do when user presses "Yes"

            extractDisabled.setText("10")


            val job = Integer.parseInt(extractDisabled.text.toString().trim())
            val editor = sharedPreferences.edit()
            editor.putInt("extractDisabled", job)
            editor.apply()

            val scholarThay = meg.text.toString().toInt()

            //if (scholarThay == 1) {

            var pathToFileCreated = ""
            val anRW = ReadandWrite().readAndwriteStorage(this, this)
            if (anRW) {
                pathToFileCreated = WriteFile().execute("$StringBuilder").get()
                Log.d("pathToFileCreated", pathToFileCreated)

                meg.setText("12")
                val boj = Integer.parseInt(meg.text.toString().trim())
                val editor = sharedPreferences.edit()
                editor.putInt("meg", boj)
                editor.apply()

                Toast.makeText(
                    this,
                    "Keep pressing the button until DOWNLOAD button disappears",
                    android.widget.Toast.LENGTH_LONG
                ).show()


                //    }


                //}
                //else {


                //var pathToFileCreated = ""
                //val anRW = ReadandWrite().readAndwriteStorage(this,this)
                //if(anRW){
                //    pathToFileCreated =  WriteFile().execute("$StringBuilder").get()
                //    Log.d("pathToFileCreated",pathToFileCreated)
                //}

                //val intent = Intent(this, MainActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                //startActivity(intent)

                //val intent2 = Intent(this, SecondActivity::class.java)
                //intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                //startActivity(intent2)

                //Toast.makeText(this, "Leger is Updated in Water Ledger Folder", android.widget.Toast.LENGTH_LONG).show()

                //})

                //}
            }

        }

    }
}