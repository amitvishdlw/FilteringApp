package com.yta.mvvm.presentation.userList

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yta.mvvm.R
import com.yta.mvvm.data.repositoryImpl.MockDb
import com.yta.mvvm.data.repositoryImpl.UserRepositoryImpl
import com.yta.mvvm.domain.usecases.FilterUsersUseCase
import com.yta.mvvm.domain.usecases.GetUsersUseCase
import com.yta.mvvm.ui.theme.MVVMTheme

class UserListActivity : ComponentActivity() {
    private lateinit var viewModel: UserListViewModel

    private val getUsersUseCase = GetUsersUseCase(UserRepositoryImpl(MockDb()))
    private val filterUsersUseCase = FilterUsersUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        viewModel = ViewModelProvider(
            this,
            UserListViewModelFactory(getUsersUseCase, filterUsersUseCase)
        )[UserListViewModel::class.java]


        setContent {
            MVVMTheme {
                UserApp(
                    uiModel = viewModel.state.collectAsStateWithLifecycle(),
                    onAction = viewModel::onAction
                )
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    viewModel.event.collect {
                        when (it) {
                            UserListEvent.ErrorOccurredEvent -> {
                                Toast.makeText(
                                    context, "Some error occurred, please restart the app",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is UserListEvent.OpenUserDetailEvent -> {
                                Toast.makeText(
                                    context, "Lets open ${it.userId}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    viewModel.loadUsers()
                }
            }
        }
    }

    @Composable
    private fun UserApp(
        uiModel: State<UserListModel>,
        onAction: (UserListAction) -> Unit
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text(
                    text = stringResource(R.string.filtering_app),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp
                )
                HorizontalDivider(
                    thickness = 2.dp
                )
                Spacer(Modifier.height(12.dp))
                TextField(
                    value = uiModel.value.userQuery,
                    onValueChange = {
                        onAction(UserListAction.OnUserSearch(userQuery = it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Spacer(Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
                ) {
                    items(uiModel.value.filteredUsers) { user ->
                        Text(
                            text = "${user.firstName} ${user.middleName} ${user.lastName} ${user.age} ${user.rank} ${user.company}",
                            modifier = Modifier
                                .clickable { onAction(UserListAction.OnUserClicked(user.userId)) }
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }

        }
    }
}