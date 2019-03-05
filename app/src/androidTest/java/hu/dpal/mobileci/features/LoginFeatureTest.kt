package hu.dpal.mobileci.features

import androidx.test.rule.ActivityTestRule
import com.mauriciotogneri.greencoffee.GreenCoffeeConfig
import com.mauriciotogneri.greencoffee.GreenCoffeeTest
import com.mauriciotogneri.greencoffee.Scenario
import com.mauriciotogneri.greencoffee.ScenarioConfig
import hu.dpal.mobileci.TestSuite.ENGLISH
import hu.dpal.mobileci.TestSuite.HUNGARIAN
import hu.dpal.mobileci.steps.LoginSteps
import hu.dpal.mobileci.steps.NavigationSteps
import hu.dpal.mobileci.ui.login.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException
import java.util.*

@RunWith(Parameterized::class)
class LoginFeatureTest(scenarioConfig: ScenarioConfig) : GreenCoffeeTest(scenarioConfig) {

    @get:Rule
    var activity: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun test() {
        start(
            NavigationSteps(),
            LoginSteps()
        )
    }

    override fun beforeScenarioStarts(scenario: Scenario?, locale: Locale?) {
        // do something
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        @Throws(IOException::class)
        fun scenarios(): Iterable<ScenarioConfig> {
            return GreenCoffeeConfig()
                .withFeatureFromAssets("assets/features/login.feature")
                .takeScreenshotOnFail()
                .scenarios(ENGLISH, HUNGARIAN)
        }
    }

}