import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    val shoppingItemList by remember { mutableStateOf(listOf<ShoppingListItem>()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Shopping List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp))
        Button(onClick = { /*TODO*/ }) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f)
        ) {
            items(shoppingItemList) {
                Text("Item $it")
            }
        }
    }
}

data class ShoppingListItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var editing: Boolean,
)