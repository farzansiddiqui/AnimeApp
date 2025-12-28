package com.siddiqui.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.siddiqui.animeapp.data.api.NetworkModule
import com.siddiqui.animeapp.data.db.AnimeDatabase
import com.siddiqui.animeapp.data.repository.AnimeRepository
import com.siddiqui.animeapp.ui.screen.AnimeDetailScreen
import com.siddiqui.animeapp.ui.screen.AnimeListScreen
import com.siddiqui.animeapp.ui.theme.AnimeAppTheme
import com.siddiqui.animeapp.ui.viewModel.AnimeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            AnimeDatabase::class.java,
            "anime.db"
        ).fallbackToDestructiveMigration().build()

        val repo = AnimeRepository(
            NetworkModule.provideAnimeApi(),
            db.animeDao(),db.animeDetailDao()
        )

        val viewModel = AnimeViewModel(repo)

        setContent {

            val nav = rememberNavController()

            NavHost(nav, "list") {
                composable("list") {
                    AnimeListScreen(viewModel, nav)
                }
                composable("detail/{id}") {
                    AnimeDetailScreen(
                        it.arguments!!.getString("id")!!.toInt(),
                        viewModel,nav
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimeAppTheme {
        Greeting("Android")
    }
}