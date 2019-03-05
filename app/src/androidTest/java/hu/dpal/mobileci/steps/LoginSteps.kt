package hu.dpal.mobileci.steps

import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Given
import com.mauriciotogneri.greencoffee.annotations.Then
import com.mauriciotogneri.greencoffee.annotations.When
import hu.dpal.mobileci.R


class LoginSteps : GreenCoffeeSteps() {

    @Given("^form is empty$")
    fun formIsEmpty() {
        onViewWithId(R.id.login_username_input).isEmpty
        onViewWithId(R.id.login_password_input).isEmpty
    }

    @When("^I give username (.+)$")
    fun iGiveUsername(username: String) {
        onViewWithId(R.id.login_username_input).type(username)
    }

    @When("^I give password (.+)$")
    fun iGivePassword(password: String) {
        onViewWithId(R.id.login_password_input).type(password)
    }

    @When("^I try to log in$")
    fun iTryToLogIn() {
        onViewWithId(R.id.login_button).click()
    }

    @Then("^I see message saying '([\\w| ]+)'$")
    fun iSeeMessageSaying(message: String) {
        onViewWithText(message)
    }

}