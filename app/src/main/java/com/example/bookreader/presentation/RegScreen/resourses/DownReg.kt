package com.example.bookreader.presentation.RegScreen.resourses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.example.bookreader.R
import com.example.bookreader.presentation.RegScreen.RegEvent
import com.example.bookreader.presentation.RegScreen.RegViewModel
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.TextLight
import com.example.bookreader.presentation.utils.Routes

@Composable
fun DownReg(
    viewModel: RegViewModel,
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val login = createRefFor("login")
        val password = createRefFor("password")
        val repeatPassword = createRefFor("repeatPassword")
        val enter = createRefFor("enter")

        constrain(login) {
            top.linkTo(parent.top, 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(password) {
            top.linkTo(login.bottom, 15.dp)
            start.linkTo(login.start)
            end.linkTo(login.end)
        }

        constrain(repeatPassword) {
            top.linkTo(password.bottom, 15.dp)
            start.linkTo(password.start)
            end.linkTo(password.end)
        }

        constrain(enter) {
            top.linkTo(repeatPassword.bottom, 15.dp)
            start.linkTo(repeatPassword.start)
            end.linkTo(repeatPassword.end)
        }
    }


    var passwordText by remember {
        mutableStateOf("")
    }
    var repeatPasswordText by remember {
        mutableStateOf("")
    }
    var vissible by remember {
        mutableStateOf(false)
    }
    var vissibleRepeat by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .layoutId("login")
                .focusRequester(focusRequester),
            value = viewModel.userNameText.value,
            onValueChange = { name ->
                viewModel.onEvent(RegEvent.OnNameTextChange(name))
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            placeholder = {
                Text(text = "Логин")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Логин"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        viewModel.onEvent(RegEvent.OnDeleteNameText)
                        focusManager.clearFocus()
                    }
                )
            },
            isError = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )

        )
        TextField(
            modifier = Modifier
                .layoutId("password")
                .focusRequester(focusRequester),
            value = viewModel.userPasswordText.value,
            onValueChange = { password ->
                viewModel.onEvent(RegEvent.OnPasswordTextChange(password))
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            placeholder = {
                Text(text = "Пароль")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Пароль"
                )
            },
            trailingIcon = {
                Icon(
                    painter = if (viewModel.visiblePassword.value)
                        painterResource(id = R.drawable.ic_eyes)
                    else
                        painterResource(id = R.drawable.ic_eyes_not),
                    contentDescription = "Visible",
                    modifier = Modifier.clickable {
                        viewModel.onEvent(RegEvent.OnChangeVisiblePassword(!viewModel.visiblePassword.value))
                    }
                )
            },
            isError = false,
            visualTransformation = if (viewModel.visiblePassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
//                            keyboardActions = KeyboardActions(
//                                onNext = {
//
//                                }
//                            ),

        )
        TextField(
            modifier = Modifier
                .layoutId("repeatPassword")
                .focusRequester(focusRequester),
            value = viewModel.userRepeatPasswordText.value,
            onValueChange = { repeatPassword ->
                viewModel.onEvent(RegEvent.OnRepeatPasswordTextChange(repeatPassword))
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            placeholder = {
                Text(text = "Повторить пароль")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Пароль Повторить"
                )
            },
            trailingIcon = {
                Icon(
                    painter = if (viewModel.visibleRepeatPassword.value)
                        painterResource(id = R.drawable.ic_eyes)
                    else
                        painterResource(id = R.drawable.ic_eyes_not),
                    contentDescription = "Visible",
                    modifier = Modifier.clickable {
                        viewModel.onEvent(RegEvent.OnChangeVisibleRepeatPassword(!viewModel.visibleRepeatPassword.value))
                    }
                )
            },
            isError = false,
            visualTransformation = if (viewModel.visibleRepeatPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
//                            keyboardActions = KeyboardActions(
//                                onNext = {
//
//                                }
//                            ),

        )
        Button(
            modifier = Modifier.layoutId("enter"),
            onClick = {
                      viewModel.onEvent(RegEvent.OnUserRegistration)
            },
            colors = ButtonDefaults.buttonColors(BlueDark),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 15.dp
            )
        ) {
            Text(
                text = "ЗАРЕГИСТРИРОВАТЬСЯ",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}