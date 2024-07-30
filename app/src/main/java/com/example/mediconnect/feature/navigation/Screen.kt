package com.example.mediconnect.feature.navigation

sealed class Screen(val route:String) {
    object SignUpScreen :Screen("signup_screen")
    object SignInScreen :Screen("signin_screen")
    object ChooseSubjectScreen :Screen("choose_subject_screen")
    object HomeScreen :Screen("home_screen")
    object AdminScreen :Screen("admin_screen")
    object ChooseTopicScreen :Screen("choose_topic_screen")
    object TestScreen :Screen("test_screen")
    object HistoryScreen :Screen("history_screen")
    object RateScreen :Screen("rate_screen")
}