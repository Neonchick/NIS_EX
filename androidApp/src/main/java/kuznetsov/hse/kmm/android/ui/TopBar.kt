package kuznetsov.hse.kmm.android.ui

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kuznetsov.hse.kmm.android.R

@Composable
fun topBar(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val menuItems = listOf("Add")
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        content = content,
        topBar = {
            TopAppBar(title = { Text("ToDo Menu") },
                backgroundColor = colorResource(id = R.color.colorAccent),
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            expanded.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "image",
                            tint = Color.White,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        offset = DpOffset((-40).dp, (-40).dp),
                        onDismissRequest = { expanded.value = false }) {
                        menuItems.forEach {
                            DropdownMenuItem(onClick = {
                                navController.navigate("add")
                                expanded.value = false
                            }) {
                                Text(text = it)
                            }
                        }
                    }
                }
            )
        },
    )
}