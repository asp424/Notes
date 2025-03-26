import org.gradle.api.JavaVersion

const val appId = "com.lm.notes"
const val appVersion = "1.0"
const val proGName = "proguard-android-optimize.txt"
const val proGRules = "proguard-rules.pro"
val javaVersion = JavaVersion.VERSION_19
const val jvm = "19"
const val res = "/META-INF/{AL2.0,LGPL2.1}"
val argsList = listOf("-Xjvm-default=all", "-opt-in=kotlin.RequiresOptIn")
const val composeCompilerVersion = "1.5.15"

