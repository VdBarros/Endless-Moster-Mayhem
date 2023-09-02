package com.vinibarros.mvvmcomposetemplate.util.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.safeNavigation(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}

fun NavController.safeNavigation(
    navDirections: NavDirections
) {
    val action = currentDestination?.getAction(navDirections.actionId) ?: graph.getAction(navDirections.actionId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(navDirections)
    }
}