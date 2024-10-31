package com.example.tim_kiem_trong_danh_sach

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText
    private lateinit var listViewStudents: ListView
    private lateinit var studentList: ArrayList<Student>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var filteredList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        edtSearch = findViewById(R.id.edtSearch)
        listViewStudents = findViewById(R.id.listViewStudents)

        // Tạo danh sách sinh viên mẫu
        studentList = arrayListOf(
            Student("Nguyễn Văn A", "20210001"),
            Student("Nguyễn Văn AB", "20210011"),
            Student("Nguyễn Văn ABC", "202101"),
            Student("Trần Thị B", "20210002"),
            Student("Lê Văn C", "20210003"),
            Student("Phạm Văn D", "20210004"),
            Student("Võ Thị E", "20210005")
        )

        val studentNames = studentList.map { "${it.name} - ${it.id}" }
        filteredList = ArrayList(studentNames)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredList)
        listViewStudents.adapter = adapter

        // Xử lý sự kiện
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val keyword = s.toString().trim()
                filterList(keyword)
            }
        })
    }

    //tìm kiếm sinh viên
    private fun filterList(keyword: String) {
        filteredList.clear()
        if (keyword.length > 2) {
            // Lọc danh sách sinh viên có họ tên hoặc MSSV chứa từ khóa
            val matchingStudents = studentList.filter {
                it.name.contains(keyword, ignoreCase = true) || it.id.contains(keyword, ignoreCase = true)
            }
            filteredList.addAll(matchingStudents.map { "${it.name} - ${it.id}" })
        } else {
            // Hiển thị toàn bộ danh sách khi từ khóa có ít hơn 3 ký tự
            filteredList.addAll(studentList.map { "${it.name} - ${it.id}" })
        }
        adapter.notifyDataSetChanged()
    }

    // Lớp dữ liệu sinh viên
    data class Student(val name: String, val id: String)
}