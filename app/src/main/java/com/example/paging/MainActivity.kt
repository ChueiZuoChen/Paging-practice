package com.example.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging.data.Word
import com.example.paging.databinding.ActivityMainBinding
import com.example.paging.paging.WordAdapter
import com.example.paging.viewmodel.MainViewModel
import com.example.paging.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var wAdapter: WordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*
        * viewmodel 使用 AndroidViewModelFactory創建，目的是為了在創建Viewmodel的時候直接給他 dao (database的實體)
        * */

        viewModel = ViewModelProvider(this,MainViewModelFactory(application)).get(MainViewModel::class.java)


        wAdapter = WordAdapter()

        //recyclerview adapter(PagingDataAdapter)初始化
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = wAdapter

        }

        /*
        * 使用生命週期的CoroutineScope來取得viewmodel轉換後的PagingData透過Flow流出
        * 因為回傳的是Flow型態，所以使用Flow的collectLatest方法，他可以即時收集最新的資料
        * collectLatest{} 如果有新資料的出現，則會捨棄舊的資料流並用新的資料流覆蓋掉
        * submitData() 可以將取得的資料更新給Paging的 Diff
        * */
        lifecycleScope.launch {
            viewModel.getStream().collectLatest {
                wAdapter.submitData(it)
            }
        }


        binding.fab.setOnClickListener {
            val input = binding.etInput.text.toString()
            val word = Word(0,input)
            viewModel.insertWord(word)
        }

    }
}