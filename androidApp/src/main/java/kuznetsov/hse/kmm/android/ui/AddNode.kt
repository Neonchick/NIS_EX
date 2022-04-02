package kuznetsov.hse.kmm.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kuznetsov.hse.kmm.SpacePictureVO
import kuznetsov.hse.kmm.android.AndroidViewModel
import kuznetsov.hse.kmm.android.R

@Composable
fun addTodo(
    model: AndroidViewModel,
    navController: NavHostController,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(50.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        var textTo by remember { mutableStateOf("") }
        OutlinedTextField(
            value = textTo,
            label = { Text("Text") },
            onValueChange = { text -> textTo = text },
            maxLines = Int.MAX_VALUE,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.colorAccent),
                unfocusedBorderColor = colorResource(id = R.color.colorPrimary),
                focusedLabelColor = colorResource(id = R.color.colorAccent),
                cursorColor = colorResource(id = R.color.colorAccent),
            ),
            modifier = Modifier
        )

        Button(
            onClick = {
                model.sharedViewModel.add(textTo)
                navController.navigate("new")
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorAccent)
            ),
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            Text(
                text = "Save",
                color = Color.White,
                style = TextStyle(fontSize = 18.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}