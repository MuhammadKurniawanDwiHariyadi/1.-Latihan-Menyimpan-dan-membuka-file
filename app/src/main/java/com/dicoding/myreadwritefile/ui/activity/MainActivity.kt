package com.dicoding.myreadwritefile.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myreadwritefile.R
import com.dicoding.myreadwritefile.databinding.ActivityMainBinding
import com.dicoding.myreadwritefile.model.FileModel
import com.dicoding.myreadwritefile.util.FileHelper

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


        binding.BTNews.setOnClickListener(this)
        binding.BTOpen.setOnClickListener(this)
        binding.BTSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.BTNews -> newFile()
            R.id.BTOpen -> showList()
            R.id.BTSave -> saveFile()
        }
    }


    //--------------------------------------------------------------------------------------
    private fun newFile() {
        binding.ETTitle.setText("") // set Edit Text menjadi kosong
        binding.ETFile.setText("") // set Edit Text menjadi kosong
        Toast.makeText(this, "Menghapus File", Toast.LENGTH_SHORT).show() // show toast
    }


    //--------------------------------------------------------------------------------------
    private fun showList() {
        val items = fileList() // dengan fileList, dapat menerima semua nama berkas pada storage aplikasi
//        val items = fileList().filter { fileName -> (fileName != "profileInstalled") }.toTypedArray() // untuk emulator akan ada berkas yang terbuat otomatis (profileInstalled), maka gunakan ini untuk menghiraukanya

        val builder = AlertDialog.Builder(this) // menampilkan dialog dengan pilihan
        builder.setTitle("Pilih File yang di inginkan")
        builder.setItems(items) {_, item -> // dari fileList maka akan di di Item AlertDialog
            loadData(items[item].toString()) // paramataer merupakan nama File dan di konversi menja di String
        }
        val alert = builder.create()
        alert.show()
    }
    private fun loadData (title: String) {
        val fileModel = FileHelper.readFromFile(this, title) // ini mendapatkan return an dari method readFromFile
        binding.ETTitle.setText(fileModel.filename) // set Tittle dari Filename yang di set pada readFromFile
        binding.ETFile.setText(fileModel.data) // set File dari data yang di set pada readFromFile
        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
    }


    //--------------------------------------------------------------------------------------
    private fun saveFile() {
        when {
            binding.ETTitle.text.toString().isEmpty() -> {
                Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
            binding.ETFile.text.toString().isEmpty() -> {
                Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val title = binding.ETTitle.text.toString()
                val text = binding.ETFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
