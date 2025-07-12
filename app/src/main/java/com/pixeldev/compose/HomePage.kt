package com.pixeldev.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pixeldev.compose.presentation.navigation.AppNavHost
import com.pixeldev.compose.presentation.uploadImage.UploadScreen
import com.pixeldev.compose.ui.theme.ComposeKtorDemoTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.ui.draw.clip
import coil.compose.rememberAsyncImagePainter

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Event : BottomNavItem("event", Icons.Default.Event, "Event")
    object Scan : BottomNavItem("scan", Icons.Default.QrCodeScanner, "Scan")
    object Photos : BottomNavItem("photos", Icons.Default.Photo, "Photos")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val bottomItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Event,
        BottomNavItem.Scan,
        BottomNavItem.Photos,
        BottomNavItem.Profile
    )
    val drawerItemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = Color(0xFFFF9800), // Orange background when selected
        unselectedContainerColor = Color.Transparent,
        selectedIconColor = Color.White,
        unselectedIconColor = Color.White.copy(alpha = 0.7f),
        selectedTextColor = Color.White,
        unselectedTextColor = Color.White.copy(alpha = 0.7f)
    )


    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
   // val currentRoute = currentBackStack?.destination?.route
    val currentRoute = currentBackStack?.destination?.route ?: BottomNavItem.Home.route


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Handle back press
    BackHandler(enabled = currentRoute != BottomNavItem.Home.route) {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.background(Color(0xFF1F1F1F)),
                windowInsets = WindowInsets.systemBars.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.End
                )
            ) {
                // 💡 Apply background to a Box/Column inside the drawer sheet
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .background(Color(0xFF1F1F1F))
                        .padding(top=60.dp)
                        .padding(20.dp)
                ) {
                    // Profile Section
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rememberAsyncImagePainter("https://your-image-url.com"), // Replace with actual URL
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color(0xFFFF9800), CircleShape)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Sonali Barnwal",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    NavigationDrawerItem(
                        label = { Text("About Us", color = Color.White) },
                        icon = { Icon(Icons.Outlined.EventNote, contentDescription = null) },
                        selected = currentRoute == "about", // match your actual route name here
                        onClick = {
                            navController.navigate("about") {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        colors = drawerItemColors
                    )
                    // Navigation Items

                    NavigationDrawerItem(
                        label = { Text("Privacy Policy") },
                        icon = { Icon(Icons.Outlined.Description, contentDescription = null) },
                        selected = currentRoute == "privacy_policy",
                        onClick = {
                            navController.navigate("privacy_policy") {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        colors = drawerItemColors
                    )

                    NavigationDrawerItem(
                        label = { Text("Terms & Conditions", color = Color.White) },
                        icon = { Icon(Icons.Outlined.Gavel, contentDescription = null, tint = Color.White) },
                        selected = false,
                        onClick = { /* TODO: Navigate */ }
                    )

                    NavigationDrawerItem(
                        label = { Text("Contact Us", color = Color.White) },
                        icon = { Icon(Icons.Outlined.HeadsetMic, contentDescription = null, tint = Color.White) },
                        selected = false,
                        onClick = { /* TODO: Navigate */ }
                    )

                    NavigationDrawerItem(
                        label = { Text("FAQs", color = Color.White) },
                        icon = { Icon(Icons.Outlined.QuestionAnswer, contentDescription = null, tint = Color.White) },
                        selected = false,
                        onClick = { /* TODO: Navigate */ }
                    )

                    NavigationDrawerItem(
                        label = { Text("Delete Account", color = Color.White) },
                        icon = { Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.White) },
                        selected = false,
                        onClick = { /* TODO: Navigate */ }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Logout button at the bottom
                    NavigationDrawerItem(
                        label = { Text("Logout", color = Color(0xFFFF9800)) },
                        icon = { Icon(Icons.Default.Logout, contentDescription = null, tint = Color(0xFFFF9800)) },
                        selected = false,
                        onClick = {
                            // TODO: Handle logout
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2C2C2C), // dark gray background
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    title = {
                        Text(
                            getPageTitle(currentRoute.toString()),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: notifications */ }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Alerts")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                BottomNavigationBar(navController, bottomItems, currentRoute)
            },
            floatingActionButton = {
                Box() {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(BottomNavItem.Scan.route)
                        },
                        containerColor = Color(0xFFFF9800),
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp)
                            .offset(y = 50.dp)
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = BottomNavItem.Scan.icon,
                            contentDescription = "Scan",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                }

            },
            floatingActionButtonPosition = FabPosition.Center,

            ) { padding ->
            NavHost(
                navController,
                startDestination = BottomNavItem.Home.route,
                Modifier.padding(padding)
            ) {
                composable(BottomNavItem.Home.route) { Screen("Home Screen") }
                composable(BottomNavItem.Event.route) { Screen("Event Screen") }
                composable(BottomNavItem.Scan.route) { Screen("Scan Screen") }
                composable(BottomNavItem.Photos.route) { Screen("Photos Screen") }
                composable(BottomNavItem.Profile.route) { Screen("Profile Screen") }

                composable("about") { Screen("About Us") }
                composable("privacy_policy") { Screen("Privacy Policy") }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = Color(0xFF2C2C2C),
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            if (item == BottomNavItem.Scan) {
                Box(
                    modifier = Modifier
                        .weight(1f, fill = true),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.offset(y = 20.dp) // move label down to match others
                    ) {
                        //Spacer(modifier = Modifier.height(36.dp)) // match icon height visually
                        Text(
                            text = "Scan",
                            fontSize = 12.sp,
                            color = if (currentRoute == BottomNavItem.Scan.route) Color(0xFFFF9800) else Color.Gray
                        )
                    }
                }
            } else {
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.label)
                    },
                    label = {
                        Text(item.label, fontWeight = FontWeight.Bold)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF9800),
                        selectedTextColor = Color(0xFFFF9800),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
@Composable
fun getPageTitle(route: String) = when (route) {
    BottomNavItem.Home.route -> "Home"
    BottomNavItem.Event.route -> "Events"
    BottomNavItem.Scan.route -> "Scan"
    BottomNavItem.Photos.route -> "Photos"
    BottomNavItem.Profile.route -> "Profile"
    else -> "App"
}
@Composable
fun Screen(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color(0xFF2C2C2C))
    ) {
        Text(text = text, color = Color.White)
    }
}

