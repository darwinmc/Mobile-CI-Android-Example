package hu.dpal.mobileci.steps

import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Given
import hu.dpal.mobileci.R

class NavigationSteps: GreenCoffeeSteps() {

    @Given("^I navigated to '(\\w+)' screen$")
    fun iNavigatedToScreen(screen: String) {

        var id = when(screen) {
            "login" -> R.id.login_screen
            else -> R.id.login_screen
        }

        onViewWithId(id).isDisplayed
    }

}