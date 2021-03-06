package com.br.thiago.themoviedatabaseapp.ui.login

interface LoginContract {

    interface View {
        fun onSuccessfulLogin()
        fun wrongParametersErrorMessage()
        fun unexpectedErrorMessage()
    }

    interface Presenter {
        fun login(user: String, password: String)
        fun destroyView()
        fun onCurrentUserLogin()
    }
}
