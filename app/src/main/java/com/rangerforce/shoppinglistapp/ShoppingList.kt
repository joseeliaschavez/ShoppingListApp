import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rangerforce.shoppinglistapp.ui.theme.ShoppingListAppTheme

data class ShoppingListItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var editing: Boolean,
)

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    var idCounter by remember { mutableIntStateOf(0) }
    var shoppingItemList by remember { mutableStateOf(listOf<ShoppingListItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingListItem?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Shopping List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                editingItem = null
                showDialog = true
            }
        ) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f)
        ) {
            items(items = shoppingItemList, key = { item -> item.id }) { item ->
                ShoppingListItem(
                    item = item,
                    onEdit = {
                        editingItem = item
                        showDialog = true
                    },
                    onDelete = {}
                )
            }
        }
        if (showDialog) {
            AddItemDialog(
                item = editingItem,
                onDismiss = { showDialog = false },
                onConfirm = { name, quantity ->
                    if (editingItem == null) {
                        shoppingItemList = shoppingItemList + ShoppingListItem(
                            id = idCounter++,
                            name = name,
                            quantity = quantity,
                            editing = false
                        )
                    } else {
                        shoppingItemList = shoppingItemList.map {
                            if (it.id == editingItem!!.id) {
                                it.copy(name = name, quantity = quantity)
                            } else {
                                it
                            }
                        }
                        editingItem = null
                    }
                }
            )
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingListItem,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxSize()
            .padding(4.dp)
            .border(
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium)
    ) {
        Text(
            text = item.name,
            modifier = modifier.weight(4f)
                .padding(8.dp)
        )
        Text(
            text = "Qty: ${item.quantity}",
            modifier = modifier.weight(2f)
                .padding(8.dp)
        )
        IconButton(
            onClick = { onEdit(item.id) },
            modifier = modifier.weight(1f)
                .padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
        IconButton(
            onClick = { onDelete(item.id) },
            modifier = modifier.weight(1f)
                .padding(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
fun AddItemDialog(
    item: ShoppingListItem? = null,
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    var itemName by remember { mutableStateOf(item?.name ?: "") }
    var itemQuantity by remember { mutableStateOf(item?.quantity?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
                    placeholder = { Text("Enter item name") }
                )
                OutlinedTextField(
                    value = itemQuantity,
                    onValueChange = { itemQuantity = it },
                    label = { Text("Item Quantity") },
                    placeholder = { Text("Enter item quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (itemName.isEmpty()) return@Button // ensures that the return statement exits the onClick lambda for the Button composable, rather than the enclosing function
                val quantity = itemQuantity.toIntOrNull() ?: 0
                onConfirm(itemName, quantity)
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListAppTheme {
        ShoppingListApp()
    }
}
