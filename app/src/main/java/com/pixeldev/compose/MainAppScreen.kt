package com.pixeldev.compose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.schedule


class MainAppScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenNavigation()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenNavigation() {
    val act = LocalContext.current as Activity
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Inbox") }
    Timer().schedule(500) {
        scope.launch { drawerState.open() }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {
                Spacer(Modifier.height(40.dp))
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.person_female_15), contentDescription = null,
                        contentScale = ContentScale.Crop, modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Alyssa Brown",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.W500),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
                Spacer(Modifier.height(20.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                    },
                    label = {
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                "Inbox",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                "24",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                            )
                        }
                    },
                    selected = selectedItem == "Inbox",
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem = "Inbox"
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            "Outbox",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                        )
                    },
                    selected = selectedItem == "Outbox",
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem = "Outbox"
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            "Favorites",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                        )
                    },
                    selected = selectedItem == "Favorites",
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem = "Favorites"
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                Divider(
                    Modifier
                        .padding(horizontal = 25.dp)
                        .padding(top = 15.dp)
                )
                Text(
                    text = "Personal Folder",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.W500),
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 15.dp)
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            "Family",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                        )
                    },
                    selected = selectedItem == "Family",
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem = "Family"
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            "Wedding",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                        )
                    },
                    selected = selectedItem == "Wedding",
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem = "Wedding"
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Navigation Drawer",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                if (drawerState.isClosed) {
                                    scope.launch { drawerState.open() }
                                } else {
                                    scope.launch { drawerState.close() }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                act.finish()
                            }) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                        )

                    )
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp))
                    ) {
                       // IncludeContent.ContentPersonShort()
                    }
                },
            )
        }
    )
}