package com.hnu.nanamail

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.User
import com.hnu.nanamail.ui.screen.NavScreen
import com.hnu.nanamail.ui.theme.NanaMailTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 用于沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setDecorFitsSystemWindows(false)

        GlobalScope.launch {
            getUser()
        }
        setContent {
            NanaMailTheme {
                // 用于沉浸式状态栏
                val systemUiController = rememberSystemUiController()
                val isDark = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isDark
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavScreen(application = application)
                }
            }
        }
    }

    // 从数据库中获取当前用户信息
    private fun getUser() = CoroutineScope(Dispatchers.IO).launch {
        val db = AppDatabase.getDatabase(application)
        val user = db.userDao().getUser()
        if (user != null) {
            User.currentUser = user
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NanaMailTheme {
        NavScreen(Application())
    }
}