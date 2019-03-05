package hu.dpal.mobileci

import hu.dpal.mobileci.features.LoginFeatureTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

@RunWith(Suite::class)
@Suite.SuiteClasses(LoginFeatureTest::class)
object TestSuite {
    val ENGLISH = Locale("en", "GB")
    val HUNGARIAN = Locale("hu", "HU")
}