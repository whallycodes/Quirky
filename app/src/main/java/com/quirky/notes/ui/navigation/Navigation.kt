package com.quirky.notes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.quirky.notes.presentation.screens.NoteListScreen
import com.quirky.notes.presentation.screens.NoteDetailScreen
import com.quirky.notes.presentation.viewmodel.NoteListViewModel
import com.quirky.notes.presentation.viewmodel.NoteDetailViewModel

sealed class Route(val route: String) {
    object NoteList : Route("note_list")
    object NoteDetail : Route("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }
    object NoteCreate : Route("note_create")
}

@Composable
fun QuirkyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.NoteList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.NoteList.route) {
            val viewModel: NoteListViewModel = hiltViewModel()
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Route.NoteDetail.createRoute(noteId))
                },
                onCreateNote = {
                    navController.navigate(Route.NoteCreate.route)
                }
            )
        }

        composable(route = Route.NoteDetail.route) {
            val viewModel: NoteDetailViewModel = hiltViewModel()
            NoteDetailScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Route.NoteCreate.route) {
            val viewModel: NoteDetailViewModel = hiltViewModel()
            NoteDetailScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                isCreatingNew = true
            )
        }
    }
}
