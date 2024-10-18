package insyncwithfoo.rustanalyzer

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.PropertyKey


private const val BUNDLE_PATH = "messages.rustanalyzer"


private object Bundle {
    val instance = DynamicBundle(Bundle::class.java, BUNDLE_PATH)
}


@Nls
internal fun message(
    @PropertyKey(resourceBundle = BUNDLE_PATH) key: String,
    vararg params: Any,
) =
    Bundle.instance.getMessage(key, *params)
