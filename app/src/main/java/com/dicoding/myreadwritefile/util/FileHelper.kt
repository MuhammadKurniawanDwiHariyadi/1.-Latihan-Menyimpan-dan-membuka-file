package com.dicoding.myreadwritefile.util

import android.content.Context
import com.dicoding.myreadwritefile.model.FileModel

internal object FileHelper {
    // terdapat 2 method write dan read

    // menyimpan data bertipekan string kedalam sebuah berkas di internal storage dengan fileOutputStream
    // pada proses inisialisasi, menggunakasn openFileOutput untuk membuka berkas sesuai nama (jika belum ada, di buatkan otomatis), untuk menggunakannya harus tahu context aplikasi yang menggunakanya
    // karenanya diperlukan inputan paramater context
    // setelah file di buka maka tuliskan datanya dengan write(data)
    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use { // disini untuk membuka atau membuat file dari fileModel.filename, lalu akesenya di buat private agar hanya aplikasi pembuatnya yang bisa membukanya
            it.write(fileModel.data?.toByteArray())  // menyimpan fileModel.data ke file yang dibuka dan di konversi ke array byte (harus di konversi ke array byte)
        }
    }



    // menggunakan komponen FileInputStream dengan metode openFileInput
    // data pada berkas dibaca oleh stream dan data tiap baris mampu di peroleh dengan bufferReader
    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel() // membuat object baru dari class FileModel
        fileModel.filename = filename // set filename
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines -> // set data dengan membuka data dengan openFilerInput() dari context dengan nama file filename, pembacaan lebih efisien dengan bufferedReafer dan useLines { lines -> untuk membaca perline
            lines.fold("") { some, text -> // line.fold untuk menggabungkan baris teks menjadi 1 string tunggal
                "$some$text\n" // membuat enter dengan \n
            }
        }
        return fileModel // lalu dikembalikan lagi ke MainActivity, objek fileModel yang berisi nama file dan data yang dibaca dari file tersebut
    }


    // kedua method diatas berbentuk static (internal object) dan dapat di pnaggil tanpa menginisialisasi classnya
}